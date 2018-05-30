package com.example.worrygroceryshop.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

public class LookReplyLetterActivity extends AppCompatActivity {
    private Intent intent;
    private String[] data;
    //控件
    private ImageView deleteReplyLetter;
    private TextView setUsername;
    private TextView lettercontent;
    private TextView letter_time;
    private String userJson;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 30:
                    Toast.makeText(LookReplyLetterActivity.this,"连接失败",Toast.LENGTH_SHORT).show();
                    break;
                case 31:
                    Toast.makeText(LookReplyLetterActivity.this,"删除失败",Toast.LENGTH_SHORT).show();
                    break;
                case 32:
                    Toast.makeText(LookReplyLetterActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                    //跳转到删除成功界面
                    Intent i=new Intent(LookReplyLetterActivity.this,deleteReplyLetterSuccessfulActivity.class);
                    startActivity(i);
                    break;
                case 40:
                    Toast.makeText(LookReplyLetterActivity.this,"返回笔友信息失败",Toast.LENGTH_SHORT).show();
                    break;
                case 41:
                    Intent intent=new Intent(LookReplyLetterActivity.this,PenFriendHomePageActivity.class);
                    intent.putExtra("userJson",userJson);
                    startActivity(intent);
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_reply_letter);
        intent=getIntent();
        String letter=intent.getStringExtra("letter");
        data=letter.split("\\+");// user_id,user_name,letter_content,letter_time,letter_reply_id，letter_id

        getViews();
        //初始化界面
        initPage();
        setListener();
    }

    private void setListener() {
        ClickListener listener=new ClickListener();
        deleteReplyLetter.setOnClickListener(listener);
        setUsername.setOnClickListener(listener);
    }

    private void initPage() {
        setUsername.setText(data[1]);
        lettercontent.setText(data[2]);
        letter_time.setText(data[3]);
    }

    private void getViews() {
        deleteReplyLetter=findViewById(R.id.deleteReplyLetter);
        setUsername=findViewById(R.id.setUsername);
        lettercontent=findViewById(R.id.lettercontent);
        letter_time=findViewById(R.id.letter_time);
    }
    class ClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.deleteReplyLetter:
                    //传一个letter_reply_id，删除信
                    OkHttpClient okHttpClient = new OkHttpClient();
                    FormBody.Builder builder = new FormBody.Builder();
                    builder.add("letter_reply_id",data[4]);
                    builder.add("letter_id",data[5]);
                    FormBody body = builder.build();
                    final Request request = new Request.Builder().post(body)
                            .url(MyPathUrl.MyURL+"deleteReplyLetter.action").build();
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
                            String result=response.body().string();
                            if(result.equals("success")){
                                //删除成功
                                Message message = Message.obtain();
                                message.what = 32;
                                handler.sendMessage(message);
                            }else{
                                //删除失败
                                Message message = Message.obtain();
                                message.what = 31;
                                handler.sendMessage(message);
                            }
                        }
                    });
                    break;
                case R.id.setUsername:
                    //点击名字，上传user_id,返回得到userjson串,跳转到笔友主页显示信息
                    OkHttpClient okHttpClient1 = new OkHttpClient();
                    FormBody.Builder builder1 = new FormBody.Builder();
                    builder1.add("user_id",data[0]);
                    FormBody body1 = builder1.build();
                    final Request request1 = new Request.Builder().post(body1)
                            .url(MyPathUrl.MyURL+"findPenFriend.action").build();
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
                            userJson=response.body().string();
                            if(userJson!=null&&!userJson.equals("")&&!userJson.equals("failure")){
                                //成功返回user json串
                                Log.i("json串",userJson);
                                Message message = Message.obtain();
                                message.what = 41;
                                handler.sendMessage(message);
                            }else{
                                Log.i("app","返回笔友信息失败");
                                Message message = Message.obtain();
                                message.what = 40;
                                handler.sendMessage(message);
                            }
                        }

                    });
                    break;
            }
        }
    }
}
