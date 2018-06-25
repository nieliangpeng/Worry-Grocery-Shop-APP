package com.example.worrygroceryshop.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.worrygroceryshop.R;
import com.example.worrygroceryshop.bean.User;
import com.example.worrygroceryshop.common.MyPathUrl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountManagerActivity extends AppCompatActivity {
    private CircleImageView user_image;
    private TextView user_name;
    private TextView phone;
    private RelativeLayout rejuest;
    private RelativeLayout changePassword;
    private Button turnout;
    private SharedPreferences preferences;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_manager);
        getViews();
        //获得user
        getUser();
        //初始化信息
        initInfo();
        //点击注册新账号
        rejuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AccountManagerActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        //点击修改密码
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //到修改密码的页面

            }
        });
        //点击退出账号
        turnout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //去掉preference中的userJson，跳转到登录注册页面
                preferences=getSharedPreferences("userData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("userJson","不存在");
                editor.commit();
                Intent intent=new Intent(AccountManagerActivity.this,IndexActivity.class);
                intent.putExtra("go",4);
                startActivity(intent);
            }
        });
    }

    private void initInfo() {
        //头像
        Glide.with(AccountManagerActivity.this)
                .load(MyPathUrl.MyURL+"getHeader.action?user_phone="+user.getUser_phone())
                .placeholder(R.mipmap.placeholder)
                .error(R.mipmap.error)
                .fallback(R.mipmap.ic_launcher_round)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(user_image);
        //名字
        user_name.setText(user.getUser_name());
        //电话
        phone.setText(user.getUser_phone());
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
        user_image=findViewById(R.id.user_image);
        user_name=findViewById(R.id.user_name);
        phone=findViewById(R.id.phone);
        rejuest=findViewById(R.id.rejuest);
        changePassword=findViewById(R.id.changePassword);
        turnout=findViewById(R.id.turnout);
    }
}
