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
import com.example.worrygroceryshop.adapter.BigTeacherAdapter;
import com.example.worrygroceryshop.bean.Mmsic;
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

public class BigTeacherActivity extends AppCompatActivity {
    private ListView listview;
    private BigTeacherAdapter adapter;
    private List<User> listData=new ArrayList<>();//数据源
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 10:
                    Toast.makeText(BigTeacherActivity.this,"连接失败",Toast.LENGTH_SHORT).show();
                    break;
                case 32:
                    adapter.refresh(listData);
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_teacher);
        getViews();
        //初始化
        adapter=new BigTeacherAdapter(this,listData,R.layout.bigteacher_item,new int[]{R.id.user_image,R.id.user_name,R.id.master_profile});
        listview.setAdapter(adapter);
        //item点击
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击跳转到详情页
                User user= (User) parent.getAdapter().getItem(position);
                Intent intent=new Intent(BigTeacherActivity.this,BigTeacherDetailActivity.class);
                intent.putExtra("user_phone",user.getUser_phone());
                intent.putExtra("user_name",user.getUser_name());
                intent.putExtra("Detailintroduction",user.getDetailintroduction());
                startActivity(intent);
            }
        });
        //发送请求，得到心灵大师list json串，更新页面
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        FormBody body = builder.build();
        final Request request = new Request.Builder().post(body)
                .url(MyPathUrl.MyURL+"selectSenior.action").build();
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
                String jsonString=response.body().string();
                if(jsonString!=null){
                    //返回音乐的json串
                    Log.i("json串",jsonString);
                    Gson gson = new GsonBuilder()
                            .serializeNulls()
                            .setPrettyPrinting()
                            .create();
                    listData = gson.fromJson(jsonString, new TypeToken<List<User>>(){}.getType());
                    Message message = Message.obtain();
                    message.what = 32;
                    handler.sendMessage(message);
                }
            }
        });
    }

    private void getViews() {
        listview=findViewById(R.id.listview);
    }
}
