package com.example.worrygroceryshop.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.worrygroceryshop.R;
import com.example.worrygroceryshop.adapter.MyInvitationAdapter;
import com.example.worrygroceryshop.bean.Invitation;
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

public class MyInvitationActivity extends AppCompatActivity {
    private ListView invitationList;
    private List<Invitation> dataList=new ArrayList<>();
    private SharedPreferences preferences;
    private User user;
    private MyInvitationAdapter adapter;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 30:
                    Toast.makeText(MyInvitationActivity.this,"连接失败",Toast.LENGTH_SHORT).show();
                    break;
                case 31:
                    Toast.makeText(MyInvitationActivity.this,"获取我的帖子失败",Toast.LENGTH_SHORT).show();
                    break;
                case 32:
                    //更新页面
                    adapter.refresh(dataList);
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_invitation);
        //listview列表
        invitationList=findViewById(R.id.invitationList);
        //初始化
        adapter=new MyInvitationAdapter(MyInvitationActivity.this,dataList,R.layout.invitation_item,
                new int[]{R.id.user_image,R.id.user_name,R.id.news_sendTime,R.id.news_content,R.id.invt_image,R.id.invt_type_name,R.id.news_discussSum,R.id.news_thumbsUpSum,R.id.news_toLove,R.id.news_toThumbsUp});
        invitationList.setAdapter(adapter);
        invitationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Invitation invitation=(Invitation) parent.getAdapter().getItem(position);//帖子信息
                Intent intent=new Intent(MyInvitationActivity.this,InvitationDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("invitation",invitation);
                intent.putExtra("bundle",bundle);
                startActivity(intent);
            }
        });
        //得到用户
        getUser();
        //user_id,请求得到帖子数据，更新页面
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


    }

    private void getUser() {
        preferences=getSharedPreferences("userData", Context.MODE_PRIVATE);
        String userJson=preferences.getString("userJson","不存在");
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setPrettyPrinting()
                .create();
        user = gson.fromJson(userJson, User.class);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            //按键keyCode是否是返回键，如果是的话
            Intent intent=new Intent(MyInvitationActivity.this,IndexActivity.class);
            intent.putExtra("go",4);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
