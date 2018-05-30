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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.worrygroceryshop.R;
import com.example.worrygroceryshop.common.MyPathUrl;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WatchGetALetterDetailActivity extends AppCompatActivity {
    private ImageView back;
    private TextView backToTreeHoles;
    private TextView hole_letter_writer;
    private TextView hole_letter_words;
    private TextView hole_letter_time;
    private Button treehole_read_letter_replyLetter;
    private  String letterContent;
    private  String[] data;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 10:
                    Toast.makeText(WatchGetALetterDetailActivity.this,"连接失败",Toast.LENGTH_SHORT).show();
                    break;
                case 11:
                    Toast.makeText(WatchGetALetterDetailActivity.this,"放回树洞失败，请重新放回",Toast.LENGTH_SHORT).show();
                    break;
                case 12:
                    Toast.makeText(WatchGetALetterDetailActivity.this,"放回树洞成功",Toast.LENGTH_SHORT).show();
                    //跳转到放回树洞成功界面
                    Intent i=new Intent(WatchGetALetterDetailActivity.this,ReturnIntoTreeHolesSuccessfulActivity.class);
                    startActivity(i);
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_get_aletter_detail);
        getViews();
        initPage();
        setListener();

    }

    private void setListener() {
        ClickListener listener=new ClickListener();
        back.setOnClickListener(listener);
        backToTreeHoles.setOnClickListener(listener);
        treehole_read_letter_replyLetter.setOnClickListener(listener);
    }

    private void initPage() {
        Intent i=getIntent();
        if(i!=null){
            letterContent=i.getStringExtra("letterContent");
        }
        data=letterContent.split("\\+");
        //初始化界面 0 letter_id, 1 user_id,2 user_name,3 letter_content,4 letter_time
        hole_letter_writer.setText("来自:#"+data[2]+"#");
        hole_letter_words.setText(data[3]);
        hole_letter_time.setText(data[4]);
    }

    private void getViews() {
        back=findViewById(R.id.back);
        backToTreeHoles=findViewById(R.id.backToTreeHoles);
        hole_letter_writer=findViewById(R.id.hole_letter_writer);
        hole_letter_words=findViewById(R.id.hole_letter_words);
        hole_letter_time=findViewById(R.id.hole_letter_time);
        treehole_read_letter_replyLetter=findViewById(R.id.treehole_read_letter_replyLetter);
    }
    class ClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()){
                case R.id.back:
                    intent=new Intent(WatchGetALetterDetailActivity.this,TreeHolesActivity.class);
                    startActivity(intent);
                    break;
                case R.id.backToTreeHoles:
                    //给user_id,letter_id，修改状态为1
                    OkHttpClient okHttpClient = new OkHttpClient();
                    FormBody.Builder builder = new FormBody.Builder();
//                    builder.add("user_id",data[1]);
                    builder.add("letter_id",data[0]);
                    FormBody body = builder.build();
                    final Request request = new Request.Builder().post(body)
                            .url(MyPathUrl.MyURL+"backLetter.action").build();
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
                                //放回树洞成功
                                Message message = Message.obtain();
                                message.what = 12;
                                handler.sendMessage(message);
                            }else{
                                Log.i("app","放回树洞失败，请重新放回");
                                Message message = Message.obtain();
                                message.what = 11;
                                handler.sendMessage(message);
                            }
                        }
                    });
                    break;
                case R.id.treehole_read_letter_replyLetter:
                   //跳转到回信页面；
                    Intent intent1=new Intent(WatchGetALetterDetailActivity.this,ReplyLetterActivity.class);
                    intent1.putExtra("letterContent",letterContent);
                    startActivity(intent1);
                    break;
            }

        }
    }
}
