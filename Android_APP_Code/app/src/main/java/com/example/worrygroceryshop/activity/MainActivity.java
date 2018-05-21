package com.example.worrygroceryshop.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.worrygroceryshop.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                //实现页面跳转
                finish();
                startActivity(new Intent(getApplicationContext(),IndexActivity.class));
                return false;
            }
        }).sendEmptyMessageDelayed(0,3000);//表示延迟3秒发送任务
    }
}
