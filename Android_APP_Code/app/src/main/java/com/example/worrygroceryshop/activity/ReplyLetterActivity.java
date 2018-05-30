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

public class ReplyLetterActivity extends AppCompatActivity {
    private ImageView back;
    private TextView send;
    private TextView toUsername;
    private EditText lettercontent;
    private TextView letter_time;
    private TextView fromUsername;
    private String []data;
    private SharedPreferences preferences;
    private User user;
    private String letterContent;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 20:
                    Toast.makeText(ReplyLetterActivity.this,"连接失败",Toast.LENGTH_SHORT).show();
                    break;
                case 21:
                    Toast.makeText(ReplyLetterActivity.this,"发送成功",Toast.LENGTH_SHORT).show();
                    //跳转到发送成功页面
                    Intent intent=new Intent(ReplyLetterActivity.this,ReplyLetterSuccessfulActivity.class);
                    startActivity(intent);
                    break;
                case 22:
                    Toast.makeText(ReplyLetterActivity.this,"回信失败",Toast.LENGTH_SHORT).show();

                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_letter);
        getViews();

        getUser();
        //初始化
        initPage();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ReplyLetterActivity.this,WatchGetALetterDetailActivity.class);
                intent.putExtra("letterContent",letterContent);
                startActivity(intent);
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发送，user_id,letter_id,letter_content
                OkHttpClient okHttpClient = new OkHttpClient();
                FormBody.Builder builder = new FormBody.Builder();
                builder.add("user_id",user.getUser_id()+"");
                builder.add("letter_id",data[0]);
                builder.add("letter_content",lettercontent.getText().toString().trim());
                FormBody body = builder.build();
                final Request request = new Request.Builder().post(body)
                        .url(MyPathUrl.MyURL+"replyLetter.action").build();
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
                        String result=response.body().string();
                        if(result.equals("success")){
                            //回信成功
                            Log.i("app","回信成功");
                            Message message = Message.obtain();
                            message.what = 21;
                            handler.sendMessage(message);
                        }else{
                            Log.i("app","回信失败");
                            Message message = Message.obtain();
                            message.what = 22;
                            handler.sendMessage(message);
                        }
                    }
                });
            }
        });
    }

    private void initPage() {
        Intent intent=getIntent();
        letterContent=intent.getStringExtra("letterContent");
        data=letterContent.split("\\+");
        //初始化界面 0 letter_id, 1 user_id,2 user_name,3 letter_content,4 letter_time
        toUsername.setText(data[2]);
        fromUsername.setText("发件人：#"+user.getUser_name()+"#");
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        letter_time.setText(sdf.format(d));
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
        back=findViewById(R.id.back);
        send=findViewById(R.id.send);
        toUsername=findViewById(R.id.toUsername);
        lettercontent=findViewById(R.id.lettercontent);
        letter_time=findViewById(R.id.letter_time);
        fromUsername=findViewById(R.id.fromUsername);
    }
}
