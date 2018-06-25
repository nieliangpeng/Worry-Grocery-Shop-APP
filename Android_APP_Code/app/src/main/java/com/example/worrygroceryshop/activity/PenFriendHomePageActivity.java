package com.example.worrygroceryshop.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.worrygroceryshop.R;
import com.example.worrygroceryshop.adapter.CommentAdapter;
import com.example.worrygroceryshop.adapter.FollowTypeAdapter;
import com.example.worrygroceryshop.adapter.MyInvitationAdapter;
import com.example.worrygroceryshop.adapter.PenFriendInvitationAdapter;
import com.example.worrygroceryshop.bean.Invitation;
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

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PenFriendHomePageActivity extends AppCompatActivity {
    private CircleImageView photo;
    private TextView user_name;
    private TextView leave;
    private TextView phone;
    private TextView desc;
    private TextView howmuch;
    private ListView listview;
    private User user;
    private TextView addPenFriend;
    private ImageView my;
    private List<Invitation> dataList=new ArrayList<>();
    private PenFriendInvitationAdapter adapter;

    private GridView grid;
    private List<InvtType> dataList1=new ArrayList<>();
    private FollowTypeAdapter followTypeAdapter;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 30:
                    Toast.makeText(PenFriendHomePageActivity.this,"连接失败",Toast.LENGTH_SHORT).show();
                    break;
                case 31:
                    Toast.makeText(PenFriendHomePageActivity.this,"获取我的帖子失败",Toast.LENGTH_SHORT).show();
                    break;
                case 32:
                    //更新页面
                    adapter.refresh(dataList);
                    setListViewHeightBasedOnChildren(listview);
                    break;
                case 11:
                    Toast.makeText(PenFriendHomePageActivity.this,"获取数据失败",Toast.LENGTH_SHORT).show();
                    break;
                case 12:
                    followTypeAdapter.refresh(dataList1);
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pen_friend_home_page);
        getViews();
        getUserInfo();
        //初始化界面
        initPage();
        setListener();

    }

    private void setListener() {
        ClickListener listener=new ClickListener();
        addPenFriend.setOnClickListener(listener);
        my.setOnClickListener(listener);
    }

    private void initPage() {
        //头像
        Glide.with(PenFriendHomePageActivity.this)
                .load(MyPathUrl.MyURL+"getHeader.action?user_phone="+user.getUser_phone())
                .placeholder(R.mipmap.placeholder)
                .error(R.mipmap.error)
                .fallback(R.mipmap.ic_launcher_round)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(photo);
        //名字
        user_name.setText(user.getUser_name());
        //等级
        if(user.getUser_state().equals("normal")){
            leave.setText("普通用户");
        }else{
            leave.setText("心灵大师");
        }
        //电话
        phone.setText(user.getUser_phone());
        //描述
        if(!user.getUser_desc().equals("")){
            desc.setText(user.getUser_desc());
        }
        //多少人关注
        howmuch.setText(user.getFollow_num()+"");
        //帖子
        adapter=new PenFriendInvitationAdapter(PenFriendHomePageActivity.this,dataList,R.layout.penfriendinvitation_item,
                new int[]{R.id.img,R.id.context,R.id.invt_type_name});
        listview.setAdapter(adapter);
        setListViewHeightBasedOnChildren(listview);
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("user_id",user.getUser_id()+"");
        FormBody body = builder.build();
        final Request request = new Request.Builder().post(body)
                .url(MyPathUrl.MyURL+"showInvitation.action").build();
        final Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("app","连接失败");
                Log.i("错误信息",e.getMessage());
                Message message = Message.obtain();
                message.what = 30;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("app","连接成功");
                String data=response.body().string();
                if(data!=null){
                    //返回我的帖子的List
                    Log.i("json串",data);
                    Gson gson = new GsonBuilder()
                            .serializeNulls()
                            .setPrettyPrinting()
                            .create();
                    dataList = gson.fromJson(data, new TypeToken<List<Invitation>>(){}.getType());
                    Message message = Message.obtain();
                    message.what = 32;
                    handler.sendMessage(message);
                }else{
                    Log.i("app","获取帖子失败");
                    Message message = Message.obtain();
                    message.what = 31;
                    handler.sendMessage(message);
                }
            }
        });
        //关注话题
        followTypeAdapter=new FollowTypeAdapter(this,dataList1, R.layout.fragment_mypage_item,new int[]{R.id.type_image, R.id.type_name});
        grid.setAdapter(followTypeAdapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击跳转到话题详情帖子页
                InvtType invtType=(InvtType) parent.getAdapter().getItem(position);//话题信息
                Intent intent=new Intent(PenFriendHomePageActivity.this,OrderByInvtTypeActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("invtType",invtType);
                intent.putExtra("bundle",bundle);
                startActivity(intent);
            }
        });
        OkHttpClient okHttpClient1 = new OkHttpClient();
        FormBody.Builder builder1 = new FormBody.Builder();
        builder1.add("user_id",user.getUser_id()+"");
        FormBody body1 = builder1.build();
        final Request request1 = new Request.Builder().post(body1)
                .url(MyPathUrl.MyURL+"getFollowType.action").build();
        final Call call1 = okHttpClient1.newCall(request1);
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("app","连接失败");
                Log.i("错误信息",e.getMessage());
                Message message = Message.obtain();
                message.what = 30;
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
                    dataList1=gson.fromJson(data, new TypeToken<List<InvtType>>(){}.getType());
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

    private void getViews() {
        photo=findViewById(R.id.photo);
        user_name=findViewById(R.id.user_name);
        leave=findViewById(R.id.leave);
        phone=findViewById(R.id.phone);
        desc=findViewById(R.id.desc);
        howmuch=findViewById(R.id.howmuch);
        addPenFriend=findViewById(R.id.addPenFriend);
        listview=findViewById(R.id.listview);
        grid=findViewById(R.id.grid);
        my=findViewById(R.id.my);
    }

    private void getUserInfo() {
        Intent intent=getIntent();
        String userJson=intent.getStringExtra("userJson");
        Log.i("userJson",userJson);
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setPrettyPrinting()
                .create();
        user = gson.fromJson(userJson, User.class);
    }
    class ClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.my:
                    Intent intent=new Intent(PenFriendHomePageActivity.this,InfomationActivity.class);
                    intent.putExtra("userName",user.getUser_name());
                    intent.putExtra("userPhone",user.getUser_phone());
                    startActivity(intent);
                    break;
                case R.id.addPenFriend:
                    //加好友
                    //参数为要添加的好友的username和添加理由
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Log.i("添加好友信息执行",user.getUser_name());
                                EMClient.getInstance().contactManager().addContact(user.getUser_name(),"你的回信我收到了，希望能和你成为好朋友");
//                                EMClient.getInstance().contactManager().acceptInvitation("bob");
                                Log.i("添加好友信息执行","完成");
                            } catch (HyphenateException e) {
                                e.printStackTrace();
                                Log.i("添加好友信息",e.getMessage());
                            }
                        }
                    }).start();

                    break;
            }
        }
    }
    public void setListViewHeightBasedOnChildren(ListView listView) {
        PenFriendInvitationAdapter listAdapter = (PenFriendInvitationAdapter) listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);  // 获取item高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // 最后再加上分割线的高度和padding高度，否则显示不完整。
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1))+listView.getPaddingTop()+listView.getPaddingBottom();
        listView.setLayoutParams(params);
    }
}
