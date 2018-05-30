package com.example.worrygroceryshop.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.worrygroceryshop.R;
import com.example.worrygroceryshop.adapter.MyGetReplyLetterAdapter;
import com.example.worrygroceryshop.adapter.MyTreeHolesAdapter;
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

public class MyReplyLetterItemActivity extends AppCompatActivity {
    private ListView listview;
    private String letter_id;
    private String data;
    private MyGetReplyLetterAdapter adapter;
    List<String> letterList=new ArrayList<>();
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 30:
                    Toast.makeText(MyReplyLetterItemActivity.this,"连接失败",Toast.LENGTH_SHORT).show();
                    break;
                case 32:
                    Toast.makeText(MyReplyLetterItemActivity.this,"目前没有回信",Toast.LENGTH_SHORT).show();
                    break;
                case 33:
                    //更新adapter
                    adapter.refresh(letterList);
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reply_letter);
        //getview
        listview=findViewById(R.id.listview);
        //初始化listview
        adapter=new MyGetReplyLetterAdapter(MyReplyLetterItemActivity.this,letterList,R.layout.myreplyletter_list_item,new int[]{R.id.treehole_mytree_letterImg,R.id.letter_fromUser,R.id.letter_time,R.id.letter_toRead});
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String letter=(String) parent.getAdapter().getItem(position);
                // user_id,user_name,letter_content,letter_time,letter_reply_id
                Intent intent=new Intent(MyReplyLetterItemActivity.this,LookReplyLetterActivity.class);
                intent.putExtra("letter",letter);
                startActivity(intent);
            }
        });
        //得到letter_id
        Intent intent=getIntent();
        letter_id=intent.getStringExtra("letter_id");//这封信的回信

        //给letter_id,从后台传过来List<String>  user_id,user_name,letter_content,letter_time,letter_reply_id
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("letter_id",letter_id);
        FormBody body = builder.build();
        final Request request = new Request.Builder().post(body)
                .url(MyPathUrl.MyURL+"readReplyLetter.action").build();
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
                data=response.body().string();
                if(data!="failure"){
                    //返回回信信件的List
                    Log.i("json串",data);
                    Gson gson = new GsonBuilder()
                            .serializeNulls()
                            .setPrettyPrinting()
                            .create();
                    letterList = gson.fromJson(data, new TypeToken<List<String>>(){}.getType());
                    Log.i("测试",letterList.get(0));
                    //有回信，更新adapter
                    Message message = Message.obtain();
                    message.what = 33;
                    handler.sendMessage(message);
                }else{
                    Log.i("app","没有回信");
                    Message message = Message.obtain();
                    message.what = 32;
                    handler.sendMessage(message);
                }
            }

        });
    }
}
