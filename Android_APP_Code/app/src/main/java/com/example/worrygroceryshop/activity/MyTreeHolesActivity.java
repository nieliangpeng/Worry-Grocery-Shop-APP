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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.worrygroceryshop.R;
import com.example.worrygroceryshop.adapter.MyTreeHolesAdapter;
import com.example.worrygroceryshop.bean.TreeHoles;
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

public class MyTreeHolesActivity extends AppCompatActivity {
    private ImageView back;
    private ListView myletter;
    private SharedPreferences preferences;
    private String data;
    private MyTreeHolesAdapter adapter;
    List<String> letterList=new ArrayList<>();//数据源
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 30:
                    Toast.makeText(MyTreeHolesActivity.this,"连接失败",Toast.LENGTH_SHORT).show();
                    break;
                case 31:
                    Toast.makeText(MyTreeHolesActivity.this,"获取我的树洞信件失败",Toast.LENGTH_SHORT).show();
                    break;
                case 32:
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
        setContentView(R.layout.activity_my_tree_holes);
        getViews();
        setListener();
        adapter=new MyTreeHolesAdapter(MyTreeHolesActivity.this,letterList,R.layout.myletter_item,new int[]{R.id.treehole_mytree_letterImg,R.id.letter_state,R.id.letter_time,R.id.letter_toRead});
        myletter.setAdapter(adapter);
        myletter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String letter=(String) parent.getAdapter().getItem(position);
                Intent intent=new Intent(MyTreeHolesActivity.this,MyLetterItemDetailActivity.class);
                intent.putExtra("letter",letter);
                startActivity(intent);
            }
        });
        //给user_id，获得从后台传过来的数据源
        preferences=getSharedPreferences("userData", Context.MODE_PRIVATE);
        String userJson=preferences.getString("userJson","不存在");
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setPrettyPrinting()
                .create();
        User user = gson.fromJson(userJson, User.class);
        //上传数据到服务器进行验证
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("user_id",user.getUser_id()+"");
        FormBody body = builder.build();
        final Request request = new Request.Builder().post(body)
                .url(MyPathUrl.MyURL+"read.action").build();
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
                if(data!=null){
                    //返回信件的List
                    Log.i("json串",data);
                    Gson gson = new GsonBuilder()
                            .serializeNulls()
                            .setPrettyPrinting()
                            .create();
                    letterList = gson.fromJson(data, new TypeToken<List<String>>(){}.getType());
//                    Log.i("json串1",letterList.get(0));
                    Message message = Message.obtain();
                    message.what = 32;
                    handler.sendMessage(message);



                }else{
                    Log.i("app","获取信件失败");
                    Message message = Message.obtain();
                    message.what = 31;
                    handler.sendMessage(message);
                }
            }
        });
    }

    private void setListener() {
        ClickListener listener=new ClickListener();
        back.setOnClickListener(listener);
    }

    private void getViews() {
        back=findViewById(R.id.back);
        myletter=findViewById(R.id.myletter);
    }
    class ClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()){
                case R.id.back:
                    intent=new Intent(MyTreeHolesActivity.this,IndexActivity.class);
                    intent.putExtra("go",4);
                    startActivity(intent);
                    break;
            }
        }
    }
}
