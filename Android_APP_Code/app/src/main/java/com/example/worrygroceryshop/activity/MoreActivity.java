package com.example.worrygroceryshop.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.worrygroceryshop.R;

public class MoreActivity extends AppCompatActivity {
    private RelativeLayout bigteacher;
    private RelativeLayout shut;

    private RelativeLayout tree;
    private RelativeLayout music;
    private ImageView image5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        findViews();
        setListener();
    }

    private void setListener() {
        ClickListener listener=new ClickListener();
        image5.setOnClickListener(listener);
        tree.setOnClickListener(listener);
        music.setOnClickListener(listener);
        shut.setOnClickListener(listener);
        bigteacher.setOnClickListener(listener);
    }

    private void findViews() {
        bigteacher=findViewById(R.id.bigteacher);
        shut=findViewById(R.id.shut);

        tree=findViewById(R.id.tree);
        image5=findViewById(R.id.image5);
        music=findViewById(R.id.music);
    }
    class ClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()){
                case R.id.image5:
                    intent=new Intent(MoreActivity.this,IndexActivity.class);
                    startActivity(intent);
                    break;
                case R.id.tree:
                    intent=new Intent(MoreActivity.this,TreeHolesActivity.class);
                    startActivity(intent);
                    break;
                case R.id.music:
                    intent=new Intent(MoreActivity.this,MusicActivity.class);
                    startActivity(intent);
                    break;
                case R.id.shut:
                    //跳转到呐喊首页
                    intent=new Intent(MoreActivity.this,ShutActivity.class);
                    startActivity(intent);
                    break;
                case R.id.bigteacher:
                    //跳转到心灵大师页面
                    intent=new Intent(MoreActivity.this,BigTeacherActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }
}
