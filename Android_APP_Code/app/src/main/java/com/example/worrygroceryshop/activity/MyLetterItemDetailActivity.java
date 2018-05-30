package com.example.worrygroceryshop.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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

public class MyLetterItemDetailActivity extends AppCompatActivity {
    private ImageView back;
    private TextView putOutTree;
    private TextView hole_letter_writer;
    private TextView hole_letter_words;
    private TextView hole_letter_time;
    private Button deleteMyLetter;
    private TextView checkReplyLetter;
    private Intent i;
    private String []data;
    private SharedPreferences preferences;
    private User user;
    private String result1;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 10:
                    Toast.makeText(MyLetterItemDetailActivity.this,"连接失败",Toast.LENGTH_SHORT).show();
                    break;
                case 11:
                    Toast.makeText(MyLetterItemDetailActivity.this,"移除信件失败，请重新移除",Toast.LENGTH_SHORT).show();
                    break;
                case 12:
                    //移除新建成功，跳转到移除成功界面
                    Intent intent=new Intent(MyLetterItemDetailActivity.this,RemoveLetterSuccessfulActivity.class);
                    startActivity(intent);
                    break;
                case 21:
                    Toast.makeText(MyLetterItemDetailActivity.this,"删除信件失败，请重新删除",Toast.LENGTH_SHORT).show();
                    break;
                case 22:
                    //更新user信息，跳转到删除信件成功界面
                    Toast.makeText(MyLetterItemDetailActivity.this,"删除信件成功",Toast.LENGTH_SHORT).show();
                    preferences=getSharedPreferences("userData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("userJson",result1);
                    editor.commit();
                    Intent intent1=new Intent(MyLetterItemDetailActivity.this,DeleteLetterSuccessfulActivity.class);
                    startActivity(intent1);
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_letter_item_detail);
        getViews();
        getUser();

        initPage();
        setListener();

    }

    private void setListener() {
        ClickListener listener=new ClickListener();
        back.setOnClickListener(listener);
        putOutTree.setOnClickListener(listener);
        deleteMyLetter.setOnClickListener(listener);
        checkReplyLetter.setOnClickListener(listener);
    }

    private void initPage() {
        i=getIntent();
        String letter=i.getStringExtra("letter");
        data=letter.split("\\+");
        hole_letter_writer.setText("来自:#"+user.getUser_name()+"#");
        hole_letter_words.setText(data[1]);
        hole_letter_time.setText(data[2]);
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
        putOutTree=findViewById(R.id.putOutTree);
        hole_letter_writer=findViewById(R.id.hole_letter_writer);
        hole_letter_words=findViewById(R.id.hole_letter_words);
        hole_letter_time=findViewById(R.id.hole_letter_time);
        deleteMyLetter=findViewById(R.id.deleteMyLetter);
        checkReplyLetter=findViewById(R.id.checkReplyLetter);
    }
    class ClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()){

                case R.id.back:
                    intent=new Intent(MyLetterItemDetailActivity.this,MyTreeHolesActivity.class);
                    startActivity(intent);
                    break;
                case R.id.putOutTree:
                    if(data[3].equals("1")){
                        //传一个letter_id,让后台改变属性为O
                        OkHttpClient okHttpClient = new OkHttpClient();
                        FormBody.Builder builder = new FormBody.Builder();
                        builder.add("letter_id",data[0]);
                        FormBody body = builder.build();
                        final Request request = new Request.Builder().post(body)
                                .url(MyPathUrl.MyURL+"removeLetter.action").build();
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
                                String result=response.body().string();
                                if(result.equals("success")){
                                    //移除成功，跳转到移除成功页面
                                    Message message = Message.obtain();
                                    message.what = 12;
                                    handler.sendMessage(message);
                                }else{
                                    Log.i("app","移除信件失败，请重新移除");
                                    Message message = Message.obtain();
                                    message.what = 11;
                                    handler.sendMessage(message);
                                }
                            }
                        });
                    }else{
                        Toast.makeText(MyLetterItemDetailActivity.this,"已经移除出树洞了",Toast.LENGTH_SHORT).show();
                    }

                    break;
                case R.id.deleteMyLetter:
                    //给一个letter_id,user_id,数据库中删除，成功返回更新后的user Json串
                    OkHttpClient okHttpClient1 = new OkHttpClient();
                    FormBody.Builder builder1 = new FormBody.Builder();
                    builder1.add("letter_id",data[0]);
                    builder1.add("user_id",user.getUser_id()+"");
                    FormBody body1 = builder1.build();
                    final Request request1 = new Request.Builder().post(body1)
                            .url(MyPathUrl.MyURL+"deleteLetter.action").build();
                    final Call call1 = okHttpClient1.newCall(request1);
                    call1.enqueue(new Callback() {
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
                            result1=response.body().string();
                            if(result1!=null&&!result1.equals("")&&!result1.equals("failure")){
                                //删除成功，返回user json串
                                Log.i("json串",result1);
                                Message message = Message.obtain();
                                message.what = 22;
                                handler.sendMessage(message);
                            }else{
                                Log.i("删除状态","失败");
                                Message message = Message.obtain();
                                message.what = 21;
                                handler.sendMessage(message);
                            }

                        }
                    });
                    break;
                case R.id.checkReplyLetter:
                    //跳转到回信List页面
                    Intent intent1=new Intent(MyLetterItemDetailActivity.this,MyReplyLetterItemActivity.class);
                    intent1.putExtra("letter_id",data[0]);
                    startActivity(intent1);
                    break;
            }
        }
    }
}
