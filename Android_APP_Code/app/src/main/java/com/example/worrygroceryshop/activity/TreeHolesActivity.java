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
import android.widget.ImageView;
import android.widget.Toast;

import com.example.worrygroceryshop.R;
import com.example.worrygroceryshop.bean.TreeHoles;
import com.example.worrygroceryshop.bean.User;
import com.example.worrygroceryshop.common.MyPathUrl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TreeHolesActivity extends AppCompatActivity {
    private ImageView getLetter;//获取信件
    private ImageView writeLetter;//写信
    private SharedPreferences preferences;
    private ImageView back;
    private String result;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 10:
                    Toast.makeText(TreeHolesActivity.this,"连接失败",Toast.LENGTH_SHORT).show();
                    break;
                case 11:
                    Toast.makeText(TreeHolesActivity.this,"树洞中已经无信",Toast.LENGTH_SHORT).show();
                    break;
                case 12:
                    //取信件成功，跳转到取信件成功界面
                    Intent intent=new Intent(TreeHolesActivity.this,GetALetterActivity.class);
                    intent.putExtra("letterContent",result);
                    startActivity(intent);
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_holes);
        getViews();
        getListener();
    }

    private void getListener() {
        ClickListener listener=new ClickListener();
        getLetter.setOnClickListener(listener);
        writeLetter.setOnClickListener(listener);
        back.setOnClickListener(listener);
    }

    private void getViews() {
        getLetter=(ImageView)findViewById(R.id.getLetter);
        writeLetter=(ImageView)findViewById(R.id.writeLetter);
        back=findViewById(R.id.back);
    }
    class ClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent;
            preferences=getSharedPreferences("userData", Context.MODE_PRIVATE);
            String userJson=preferences.getString("userJson","不存在");
            switch (v.getId()){
                case R.id.getLetter:
                    if(userJson.equals("不存在")){
                        Toast.makeText(TreeHolesActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
                    }else{
                        //取信,给一个user_id,返回一个信件的字符串
                        Gson gson = new GsonBuilder()
                                .serializeNulls()
                                .setPrettyPrinting()
                                .create();
                        User user = gson.fromJson(userJson, User.class);

                        OkHttpClient okHttpClient = new OkHttpClient();
                        FormBody.Builder builder = new FormBody.Builder();
                        builder.add("user_id",user.getUser_id()+"");
                        FormBody body = builder.build();
                        final Request request = new Request.Builder().post(body)
                                .url(MyPathUrl.MyURL+"getALetter.action").build();
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
                                result=response.body().string();
                                if(!result.equals("failure")){
                                    //取信成功，跳转到取信成功页面
                                    Message message = Message.obtain();
                                    message.what = 12;
                                    handler.sendMessage(message);
                                }else{
                                    Log.i("app","树洞中已经无信");
                                    Message message = Message.obtain();
                                    message.what = 11;
                                    handler.sendMessage(message);
                                }
                            }
                        });

                    }

                    break;
                case R.id.writeLetter:
                    if(userJson.equals("不存在")){
                        Toast.makeText(TreeHolesActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
                    }else{
                        //跳转到写信页面
                        intent=new Intent(TreeHolesActivity.this,WriteLetterActivity.class);
                        startActivity(intent);

                    }
                    break;
                case R.id.back:
                    intent=new Intent(TreeHolesActivity.this,MoreActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }
}
