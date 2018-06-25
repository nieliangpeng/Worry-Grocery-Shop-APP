package com.example.worrygroceryshop.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.worrygroceryshop.R;
import com.example.worrygroceryshop.common.MyPathUrl;

import de.hdodenhof.circleimageview.CircleImageView;

public class InfomationActivity extends AppCompatActivity {
    private CircleImageView user_image;
    private TextView user_name;
    private TextView user_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomation);
        user_image=findViewById(R.id.user_image);
        user_name=findViewById(R.id.user_name);
        user_phone=findViewById(R.id.user_phone);
        Intent intent=getIntent();
        String userName=intent.getStringExtra("userName");
        String userPhone=intent.getStringExtra("userPhone");
        Glide.with(this)
                .load(MyPathUrl.MyURL+"getHeader.action?user_phone="+userPhone)
                .placeholder(R.mipmap.placeholder)
                .error(R.mipmap.error)
                .fallback(R.mipmap.ic_launcher_round)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(user_image);
        user_name.setText(userName);
        user_phone.setText(userPhone);


    }
}
