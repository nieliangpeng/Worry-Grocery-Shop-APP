package com.example.worrygroceryshop.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.worrygroceryshop.R;
import com.example.worrygroceryshop.common.MyPathUrl;
import com.hyphenate.easeui.EaseConstant;

public class BigTeacherDetailActivity extends AppCompatActivity {
    private ImageView header;
    private TextView name;
    private TextView detail;
    private ImageView chat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_teacher_detail);
        getviews();
        //得到信息
        Intent intent=getIntent();
        String user_phone=intent.getStringExtra("user_phone");
        final String user_name=intent.getStringExtra("user_name");
        String Detailintroduction=intent.getStringExtra("Detailintroduction");
        //初始化页面
        Glide.with(this)
                .load(MyPathUrl.MyURL+"getHeader.action?user_phone="+user_phone)
                .placeholder(R.mipmap.placeholder)
                .error(R.mipmap.error)
                .fallback(R.mipmap.ic_launcher_round)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(header);
        name.setText(user_name);
        detail.setText(Detailintroduction);
        //点击聊天
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BigTeacherDetailActivity.this, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID,user_name));
            }
        });
    }

    private void getviews() {
        header=findViewById(R.id.header);
        name=findViewById(R.id.name);
        detail=findViewById(R.id.detail);
        chat=findViewById(R.id.chat);
    }
}
