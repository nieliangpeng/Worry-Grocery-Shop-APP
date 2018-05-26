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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.worrygroceryshop.R;
import com.example.worrygroceryshop.bean.User;
import com.example.worrygroceryshop.common.MyPathUrl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WriteLetterActivity extends AppCompatActivity {
    private ImageView back;
    private TextView put_into_tree;
    private TextView setUsername;
    private EditText lettercontent;
    private TextView letter_time;
    private SharedPreferences preferences;
    private User user;
    private String data;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 20:
                    Toast.makeText(WriteLetterActivity.this,"连接失败",Toast.LENGTH_SHORT).show();
                    break;
                case 21:
                    Toast.makeText(WriteLetterActivity.this,"放入树洞失败",Toast.LENGTH_SHORT).show();
                    break;
                case 22:
                    Toast.makeText(WriteLetterActivity.this,"放入树洞成功",Toast.LENGTH_SHORT).show();
                    preferences=getSharedPreferences("userData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("userJson",data);
                    editor.commit();
                    Intent intent=new Intent(WriteLetterActivity.this,PutIntoTreeSuccessActivity.class);
                    startActivity(intent);
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_letter);
        gertViews();

        init();

        setListener();
    }

    private void init() {
        preferences=getSharedPreferences("userData", Context.MODE_PRIVATE);
        String userJson=preferences.getString("userJson","不存在");
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setPrettyPrinting()
                .create();
        user = gson.fromJson(userJson, User.class);
        setUsername.setText(user.getUser_name());
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        letter_time.setText(sdf.format(d));
    }

    private void setListener() {
        ClickListener listener=new ClickListener();
        back.setOnClickListener(listener);
        put_into_tree.setOnClickListener(listener);
    }

    private void gertViews() {
        back=findViewById(R.id.back);
        put_into_tree=findViewById(R.id.put_into_tree);
        setUsername=findViewById(R.id.setUsername);
        lettercontent=findViewById(R.id.lettercontent);
        letter_time=findViewById(R.id.letter_time);
    }
    class ClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()){
                case R.id.back:
                    intent=new Intent(WriteLetterActivity.this,TreeHolesActivity.class);
                    startActivity(intent);
                    break;
                case R.id.put_into_tree:
                    OkHttpClient okHttpClient = new OkHttpClient();
                    FormBody.Builder builder = new FormBody.Builder();
                    builder.add("user_id",user.getUser_id()+"");
                    builder.add("letter_content",lettercontent.getText().toString().trim());
                    FormBody body = builder.build();
                    final Request request = new Request.Builder().post(body)
                            .url(MyPathUrl.MyURL+"writeLetter.action").build();
                    final Call call = okHttpClient.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.i("app","连接失败");
                            Log.i("错误信息",e.getMessage());
                            Message message = Message.obtain();
                            message.what = 20;
                            handler.sendMessage(message);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Log.i("app","连接成功");
                            data=response.body().string();
                            if(data!=null&&!data.equals("")&&!data.equals("failure")){
                                //加入树洞成功，返回更新后的用户信息
                                Log.i("json串",data);
                                Gson gson = new GsonBuilder()
                                        .serializeNulls()
                                        .setPrettyPrinting()
                                        .create();
                                User user = gson.fromJson(data, User.class);
                                Log.i("userphone",user.getUser_phone());
                                if(user!=null){
                                    Message message = Message.obtain();
                                    message.what = 22;
                                    handler.sendMessage(message);
                                }else{
                                    Log.i("user信息","user为空");
                                }

                            }else{
                                Log.i("app","放入树洞失败");
                                Message message = Message.obtain();
                                message.what = 21;
                                handler.sendMessage(message);
                            }
                        }
                    });
                    break;
            }
        }
    }
}
