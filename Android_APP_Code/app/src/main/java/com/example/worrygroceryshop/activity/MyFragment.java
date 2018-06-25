package com.example.worrygroceryshop.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.worrygroceryshop.R;
import com.example.worrygroceryshop.adapter.FollowTypeAdapter;
import com.example.worrygroceryshop.bean.FollowType;
import com.example.worrygroceryshop.bean.InvtType;
import com.example.worrygroceryshop.bean.User;
//import com.example.worrygroceryshop.common.GlideApp;
import com.example.worrygroceryshop.common.MyPathUrl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 夏天 on 2018/5/17.
 */

public class MyFragment extends Fragment {
    //没有登录
    private Context context;
    private Button register;
    private Button login;
    //登录
    private ImageView back;
    private ImageView my;
    private CircleImageView photo;
    private TextView user_name;
    private TextView leave;
    private TextView phone;
    private TextView desc;
    private TextView num1;
    private TextView num2;
    private TextView num3;
    private TextView num4;
    private TextView num5;
    private RelativeLayout shu;
    private RelativeLayout pen;
    private RelativeLayout tie;
    private RelativeLayout teacher;
    private RelativeLayout shezhi;
    private GridView grid;
    private RelativeLayout about;
    private RelativeLayout kfphone;
    private RelativeLayout fankui;
    private SharedPreferences preferences;
    private List<String> usernames = new ArrayList<>();
    private List<InvtType> dataList=new ArrayList<>();
    private FollowTypeAdapter followTypeAdapter;
    private User user;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    num1.setText(""+usernames.size());
                    break;
                case 10:
                    Toast.makeText(context,"连接失败",Toast.LENGTH_SHORT).show();
                    break;
                case 11:
                    Toast.makeText(context,"获取数据失败",Toast.LENGTH_SHORT).show();
                    break;
                case 12:
                    followTypeAdapter.refresh(dataList);
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
          View layout =null;
          preferences=context.getSharedPreferences("userData", Context.MODE_PRIVATE);
          String userJson=preferences.getString("userJson","不存在");
          if(userJson.equals("不存在")){
              //没有登录的操作
              layout = inflater.inflate(R.layout.fragment_my, container,false);
              getViews(layout);
              setListener();
          }else{

              //已经登录
              layout = inflater.inflate(R.layout.fragment_loginin, container,false);
              findViews(layout);
              //初始化我的主页
              initMy(userJson);
              setListener1();
          }
        return layout;

    }

    private void initMy(String userJson) {
        //json串转换成user对象
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setPrettyPrinting()
                .create();
        user = gson.fromJson(userJson, User.class);
        //根据当前的user对象更新页面
        //1.头像
        Glide.with(MyFragment.this)
                .load(MyPathUrl.MyURL+"getHeader.action?user_phone="+user.getUser_phone())
                .placeholder(R.mipmap.placeholder)
                .error(R.mipmap.error)
                .fallback(R.mipmap.ic_launcher_round)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(photo);

        //2.姓名
        user_name.setText(user.getUser_name());
        //3.普通用户or心灵大师
        if(user.getUser_state().equals("normal")){
            leave.setText("普通用户");
        }else{
            leave.setText("心灵大师");
        }
        //4.电话
        phone.setText(user.getUser_phone());
        //描述
        if(!user.getUser_desc().equals("")){
            desc.setText(user.getUser_desc());
        }

        //笔友个数
        new Thread(){
            @Override
            public void run() {

                try {
                    usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
                Message message = Message.obtain();
                message.what = 1;
                handler.sendMessage(message);

            }
        }.start();



        //关注的人数
        num2.setText(""+user.getFollow_num());
        //收藏帖子的个数
        num3.setText(""+user.getCollect_num());
        //发帖的个数
        num4.setText(""+user.getInvitation_num());
        //树洞信封数
        num5.setText(""+user.getLetter_num());
        //关注的话题
        followTypeAdapter=new FollowTypeAdapter(context,dataList, R.layout.fragment_mypage_item,new int[]{R.id.type_image, R.id.type_name});
        grid.setAdapter(followTypeAdapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击跳转到话题详情帖子页
                InvtType invtType=(InvtType) parent.getAdapter().getItem(position);//话题信息
                Intent intent=new Intent(context,OrderByInvtTypeActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("invtType",invtType);
                intent.putExtra("bundle",bundle);
                startActivity(intent);
            }
        });
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("user_id",user.getUser_id()+"");
        FormBody body = builder.build();
        final Request request = new Request.Builder().post(body)
                .url(MyPathUrl.MyURL+"getFollowType.action").build();
        final Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("app","连接失败");
                Log.i("错误信息",e.getMessage());
                Message message = Message.obtain();
                message.what = 10;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("app","连接成功");
                String data=response.body().string();
                if(data!=null){
                    Log.i("json串",data);
                    Gson gson = new GsonBuilder()
                            .serializeNulls()
                            .setPrettyPrinting()
                            .create();
                    dataList=gson.fromJson(data, new TypeToken<List<InvtType>>(){}.getType());
                    Message message = Message.obtain();
                    message.what = 12;
                    handler.sendMessage(message);
                }else{
                    Message message = Message.obtain();
                    message.what = 11;
                    handler.sendMessage(message);
                }
            }
        });


    }

    private void findViews(View layout) {
        back=layout.findViewById(R.id.back);
        my=layout.findViewById(R.id.my);
        photo=layout.findViewById(R.id.photo);
        user_name=layout.findViewById(R.id.user_name);
        leave=layout.findViewById(R.id.leave);
        phone=layout.findViewById(R.id.phone);
        desc=layout.findViewById(R.id.desc);
        num1=layout.findViewById(R.id.num1);
        num2=layout.findViewById(R.id.num2);
        num3=layout.findViewById(R.id.num3);
        num4=layout.findViewById(R.id.num4);
        num5=layout.findViewById(R.id.num5);
        teacher=layout.findViewById(R.id.teacher);
        shezhi=layout.findViewById(R.id.shezhi);
        grid=layout.findViewById(R.id.grid);
        about=layout.findViewById(R.id.about);
        kfphone=layout.findViewById(R.id.kfphone);
        fankui=layout.findViewById(R.id.fankui);
        shu=layout.findViewById(R.id.shu);
        pen=layout.findViewById(R.id.pen);
        tie=layout.findViewById(R.id.tie);
    }

    private void setListener() {
        Listener listener=new Listener();
        register.setOnClickListener(listener);
        login.setOnClickListener(listener);

    }
    private void setListener1() {
        Listener listener=new Listener();
        shu.setOnClickListener(listener);
        pen.setOnClickListener(listener);
        shezhi.setOnClickListener(listener);
        teacher.setOnClickListener(listener);
        tie.setOnClickListener(listener);
        my.setOnClickListener(listener);
    }
    private void getViews(View layout) {
        register=layout.findViewById(R.id.register);
        login=layout.findViewById(R.id.login);
    }

    @Override
    public void onAttach(Context context) {
        this.context=context;
        super.onAttach(context);
    }

    class Listener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()){
                case R.id.register:
                    intent=new Intent(context, RegisterActivity.class);
                    startActivityForResult(intent,1);
                    break;
                case R.id.login:
                    intent=new Intent(context, LoginActivity.class);
                    startActivity(intent);
                    break;
                case R.id.shu:
                    intent=new Intent(context,MyTreeHolesActivity.class);
                    startActivity(intent);
                    break;
                case R.id.pen:
                    //跳转到好友列表界面
                    startActivity(new Intent(context,PenFriendActivity.class));
                    break;
                case R.id.shezhi:
                    //跳转到设置界面
                    startActivity(new Intent(context,ShezhiActivity.class));
                    break;
                case R.id.teacher:
                    startActivity(new Intent(context,GetABigTeacherActivity.class));
                    break;
                case R.id.tie:
                    startActivity(new Intent(context,MyInvitationActivity.class));
                    break;
                case R.id.my:
                    intent=new Intent(context,InfomationActivity.class);
                    intent.putExtra("userName",user.getUser_name());
                    intent.putExtra("userPhone",user.getUser_phone());
                    startActivity(intent);
                    break;
            }
        }
    }

}
