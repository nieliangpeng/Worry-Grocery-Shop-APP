package com.example.worrygroceryshop.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.Text;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.worrygroceryshop.R;
import com.example.worrygroceryshop.adapter.MyInvitationAdapter;
import com.example.worrygroceryshop.bean.Invitation;
import com.example.worrygroceryshop.bean.InvtType;
import com.example.worrygroceryshop.bean.User;
import com.example.worrygroceryshop.common.MyPathUrl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OrderByInvtTypeActivity extends AppCompatActivity {
    private ImageView type_image;
    private TextView typename;
    private TextView typedesc;
    private ListView listview;
    private TextView joinInvtType;
    private TextView guanzhu;
    private InvtType invtType;
    private List<Invitation> dataList=new ArrayList<>();
    private MyInvitationAdapter adapter;
    private SharedPreferences preferences;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 30:
                    Toast.makeText(OrderByInvtTypeActivity.this,"连接失败",Toast.LENGTH_SHORT).show();
                    break;
                case 31:
                    Toast.makeText(OrderByInvtTypeActivity.this,"获取帖子失败",Toast.LENGTH_SHORT).show();
                    break;
                case 32:
                    //更新页面
                    adapter.refresh(dataList);
                    break;
                case 42:
                    Toast.makeText(OrderByInvtTypeActivity.this,"关注失败",Toast.LENGTH_SHORT).show();
                    break;
                case 43:
                    Toast.makeText(OrderByInvtTypeActivity.this,"关注成功",Toast.LENGTH_SHORT).show();
                    break;
                case 44:
                    Toast.makeText(OrderByInvtTypeActivity.this,"您已经关注了该话题，不需要再次关注！",Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_by_invt_type);
        getViews();
        //得到intent
        getInvtType();
        //初始化页面
        initPage();
        setListener();
    }

    private void setListener() {
        ClickListener listener=new ClickListener();
        joinInvtType.setOnClickListener(listener);
        guanzhu.setOnClickListener(listener);
    }

    private void initPage() {
        //加载图片
        Glide.with(this)
                .load(MyPathUrl.MyURL+"picture.action?type_id="+invtType.getType_id())
                .placeholder(R.mipmap.placeholder)
                .error(R.mipmap.error)
                .fallback(R.mipmap.ic_launcher)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(type_image);
        //话题名称
        typename.setText("#"+invtType.getType_name()+"#");
        //话题描述
        typedesc.setText(invtType.getType_desc());
        //加载帖子
        adapter=new MyInvitationAdapter(OrderByInvtTypeActivity.this,dataList,R.layout.invitation_item,
                new int[]{R.id.user_image,R.id.user_name,R.id.news_sendTime,R.id.news_content,R.id.invt_image,R.id.invt_type_name,R.id.news_discussSum,R.id.news_thumbsUpSum,R.id.news_toLove,R.id.news_toThumbsUp});
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Invitation invitation=(Invitation) parent.getAdapter().getItem(position);//帖子信息
                Intent intent=new Intent(OrderByInvtTypeActivity.this,InvitationDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("invitation",invitation);
                intent.putExtra("bundle",bundle);
                startActivity(intent);
            }
        });
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("type_id",invtType.getType_id()+"");
        FormBody body = builder.build();
        final Request request = new Request.Builder().post(body)
                .url(MyPathUrl.MyURL+"showInvitationByType.action").build();
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
                    //返回帖子的List
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
    }

    private void getInvtType() {
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("bundle");
        invtType=(InvtType) bundle.get("invtType");//得到帖子
    }

    private void getViews() {
        type_image=findViewById(R.id.type_image);
        typename=findViewById(R.id.typename);
        typedesc=findViewById(R.id.typedesc);
        listview=findViewById(R.id.listview);
        joinInvtType=findViewById(R.id.jointype);
        guanzhu=findViewById(R.id.guanzhu);

    }
    class ClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.jointype:
                    //加入话题
                    //跳转到发帖页面
                    Intent intent=new Intent(OrderByInvtTypeActivity.this,PostActivity.class);
                    intent.putExtra("type_id",invtType.getType_id());
                    intent.putExtra("type_name",invtType.getType_name());
                    startActivity(intent);
                    break;
                case R.id.guanzhu:
                    //看是否已经登录
                    preferences=getSharedPreferences("userData", Context.MODE_PRIVATE);
                    String userJson=preferences.getString("userJson","不存在");
                    if(userJson.equals("不存在")) {
                        //未登录
                        Toast.makeText(OrderByInvtTypeActivity.this,"请先登录，再操作",Toast.LENGTH_SHORT).show();
                    }else{
                        //登录
                        Gson gson = new GsonBuilder()
                                .serializeNulls()
                                .setPrettyPrinting()
                                .create();
                        User user = gson.fromJson(userJson, User.class);
                        //关注话题,上传user_id,type_id,后台查看该用户是否已经关注了该话题，如果已经关注，
                        // 返回followed;没有关注，去关注，返回是否关注成功
                        OkHttpClient okHttpClient2 = new OkHttpClient();
                        FormBody.Builder builder2 = new FormBody.Builder();
                        builder2.add("user_id",user.getUser_id()+"");
                        builder2.add("type_id",invtType.getType_id()+"");
                        FormBody body2 = builder2.build();
                        final Request request2 = new Request.Builder().post(body2)
                                .url(MyPathUrl.MyURL+"followInv.action").build();
                        final Call call2 = okHttpClient2.newCall(request2);
                        call2.enqueue(new Callback() {
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
                                if(data.equals("success")){
                                    Log.i("json串",data);
                                    Message message = Message.obtain();
                                    message.what = 43;
                                    handler.sendMessage(message);
                                }else if(data.equals("followed")){
                                    Message message = Message.obtain();
                                    message.what = 44;
                                    handler.sendMessage(message);
                                }else{
                                    Log.i("app","关注失败");
                                    Message message = Message.obtain();
                                    message.what = 42;
                                    handler.sendMessage(message);
                                }
                            }

                        });
                    }


                    break;
            }
        }
    }
}
