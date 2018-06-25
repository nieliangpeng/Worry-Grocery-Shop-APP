package com.example.worrygroceryshop.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.worrygroceryshop.R;
import com.example.worrygroceryshop.bean.User;
import com.example.worrygroceryshop.common.MyPathUrl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShezhiActivity extends AppCompatActivity {
    private CircleImageView user_image;
    private RelativeLayout go;
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shezhi);
        findViews();
        //初始化头像
        initImage();
        //设置监听器
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到账号管理页面
                startActivity(new Intent(ShezhiActivity.this,AccountManagerActivity.class));
            }
        });
    }

    private void initImage() {
        preferences=getSharedPreferences("userData", Context.MODE_PRIVATE);
        String userJson=preferences.getString("userJson","不存在");
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setPrettyPrinting()
                .create();
        User user = gson.fromJson(userJson, User.class);
        Glide.with(ShezhiActivity.this)
                .load(MyPathUrl.MyURL+"getHeader.action?user_phone="+user.getUser_phone())
                .placeholder(R.mipmap.placeholder)
                .error(R.mipmap.error)
                .fallback(R.mipmap.ic_launcher_round)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(user_image);
    }

    private void findViews() {
        user_image=findViewById(R.id.user_image);
        go=findViewById(R.id.go);
    }
}
