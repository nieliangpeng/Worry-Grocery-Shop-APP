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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.worrygroceryshop.R;
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

public class GetABigTeacherActivity extends AppCompatActivity {
    private EditText profile;
    private EditText content;
    private Button send;
    private SharedPreferences preferences;
    private String data;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 10:
                    Toast.makeText(GetABigTeacherActivity.this,"连接失败",Toast.LENGTH_SHORT).show();
                    break;
                case 11:
                    Toast.makeText(GetABigTeacherActivity.this,"申请失败",Toast.LENGTH_SHORT).show();
                    break;
                case 12:
                    Toast.makeText(GetABigTeacherActivity.this,"申请成功",Toast.LENGTH_SHORT).show();
                    //设置更新信息
                    preferences=getSharedPreferences("userData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("userJson",data);
                    editor.commit();
                    //跳转到
                    Intent intent=new Intent(GetABigTeacherActivity.this,IndexActivity.class);
                    intent.putExtra("go",4);
                    startActivity(intent);
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_abig_teacher);
        getViews();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String master_profile=profile.getText().toString().trim();//简介
                String detailintroduction=content.getText().toString().trim();//详情
                preferences=getSharedPreferences("userData", Context.MODE_PRIVATE);
                String userJson=preferences.getString("userJson","不存在");
                Gson gson = new GsonBuilder()
                        .serializeNulls()
                        .setPrettyPrinting()
                        .create();
                User user = gson.fromJson(userJson, User.class);//用户

                //如果内容为null
                if(master_profile.equals("")||detailintroduction.equals("")){
                    Toast.makeText(GetABigTeacherActivity.this,"请填写完整资料",Toast.LENGTH_SHORT).show();
                }else{
                    //发送申请
                    OkHttpClient okHttpClient = new OkHttpClient();
                    FormBody.Builder builder = new FormBody.Builder();
                    builder.add("user_id",user.getUser_id()+"");
                    builder.add("master_profile",master_profile);
                    builder.add("detailintroduction",detailintroduction);
                    FormBody body = builder.build();
                    final Request request = new Request.Builder().post(body)
                            .url(MyPathUrl.MyURL+"senior.action").build();
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
                            data=response.body().string();
                            if(data!=null){
                                Log.i("json",data);
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

            }
        });
    }

    private void getViews() {
        profile=findViewById(R.id.profile);
        content=findViewById(R.id.content);
        send=findViewById(R.id.send);
    }
}
