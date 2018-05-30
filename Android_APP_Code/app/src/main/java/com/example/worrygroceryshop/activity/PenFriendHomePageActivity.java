package com.example.worrygroceryshop.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.worrygroceryshop.R;
import com.example.worrygroceryshop.bean.User;
//import com.example.worrygroceryshop.common.GlideApp;
import com.example.worrygroceryshop.common.MyPathUrl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import de.hdodenhof.circleimageview.CircleImageView;

public class PenFriendHomePageActivity extends AppCompatActivity {
    private CircleImageView photo;
    private TextView user_name;
    private TextView leave;
    private TextView phone;
    private TextView desc;
    private User user;
    private TextView addPenFriend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pen_friend_home_page);
        getViews();
        getUserInfo();
        //初始化界面
        initPage();
        setListener();

    }

    private void setListener() {
        ClickListener listener=new ClickListener();
        addPenFriend.setOnClickListener(listener);
    }

    private void initPage() {
        //头像
        Glide.with(PenFriendHomePageActivity.this)
                .load(MyPathUrl.MyURL+"getHeader.action?user_phone="+user.getUser_phone())
                .placeholder(R.mipmap.placeholder)
                .error(R.mipmap.error)
                .fallback(R.mipmap.ic_launcher_round)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(photo);
        //名字
        user_name.setText(user.getUser_name());
        //等级
        if(user.getUser_state().equals("normal")){
            leave.setText("普通用户");
        }else{
            leave.setText("心灵大师");
        }
        //电话
        phone.setText(user.getUser_phone());
        //描述
        if(!user.getUser_desc().equals("")){
            desc.setText(user.getUser_desc());
        }
    }

    private void getViews() {
        photo=findViewById(R.id.photo);
        user_name=findViewById(R.id.user_name);
        leave=findViewById(R.id.leave);
        phone=findViewById(R.id.phone);
        desc=findViewById(R.id.desc);
        addPenFriend=findViewById(R.id.addPenFriend);
    }

    private void getUserInfo() {
        Intent intent=getIntent();
        String userJson=intent.getStringExtra("userJson");
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setPrettyPrinting()
                .create();
        user = gson.fromJson(userJson, User.class);
    }
    class ClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.addPenFriend:
                    //加好友
                    //参数为要添加的好友的username和添加理由
                    try {
                        EMClient.getInstance().contactManager().addContact(user.getUser_name(),"你的回信我收到了，希望能和你成为好朋友");
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        Log.i("添加好友信息",e.getMessage());
                    }
                    break;
            }
        }
    }
}
