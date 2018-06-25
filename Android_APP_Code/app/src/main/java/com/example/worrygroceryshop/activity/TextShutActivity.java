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
import android.widget.EditText;
import android.widget.TextView;
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

public class TextShutActivity extends AppCompatActivity {
    private EditText content;
    private TextView ren;
    private SharedPreferences preferences;
    private  User user;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 10:
                    Toast.makeText(TextShutActivity.this,"连接失败",Toast.LENGTH_SHORT).show();
                    break;
                case 11:
                    //呐喊成功，跳转到成功页面
                    Intent intent=new Intent(TextShutActivity.this,TextShoutSuccessfulActivity.class);
                    startActivity(intent);
                    break;
                case 12:
                    Toast.makeText(TextShutActivity.this,"呐喊失败",Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_shut);
        getViews();

        getUser();

        ren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt=content.getText().toString().trim();
                if(txt==null||txt.equals("")){
                    Toast.makeText(TextShutActivity.this,"内容不可以为空",Toast.LENGTH_SHORT);
                }else{
                    //内容不为空
                    //提交user_id以及context给服务器，返回成功or失败
                    OkHttpClient okHttpClient = new OkHttpClient();
                    FormBody.Builder builder = new FormBody.Builder();
                    builder.add("user_id",user.getUser_id()+"");
                    builder.add("textContent",txt);
                    FormBody body = builder.build();
                    final Request request = new Request.Builder().post(body)
                            .url(MyPathUrl.MyURL+"textshout.action").build();
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
                            if(data.equals("success")){
                                Message message = Message.obtain();
                                message.what = 11;
                                handler.sendMessage(message);
                            }else{
                                Message message = Message.obtain();
                                message.what = 12;
                                handler.sendMessage(message);
                            }
                        }
                    });
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

    private void getViews() {
        content=findViewById(R.id.content);
        ren=findViewById(R.id.ren);
    }
}
