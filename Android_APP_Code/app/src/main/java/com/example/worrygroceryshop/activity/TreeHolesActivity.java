package com.example.worrygroceryshop.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.worrygroceryshop.R;
import com.example.worrygroceryshop.bean.TreeHoles;

public class TreeHolesActivity extends AppCompatActivity {
    private ImageView getLetter;//获取信件
    private ImageView writeLetter;//写信
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_holes);
        getViews();
        getListener();
    }

    private void getListener() {
        ClickListener listener=new ClickListener();
        getLetter.setOnClickListener(listener);
        writeLetter.setOnClickListener(listener);
    }

    private void getViews() {
        getLetter=(ImageView)findViewById(R.id.getLetter);
        writeLetter=(ImageView)findViewById(R.id.writeLetter);
    }
    class ClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent;
            preferences=getSharedPreferences("userData", Context.MODE_PRIVATE);
            String userJson=preferences.getString("userJson","不存在");
            switch (v.getId()){
                case R.id.getLetter:
                    break;
                case R.id.writeLetter:
//                    if(userJson.equals("不存在")){
//                        Toast.makeText(TreeHolesActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
//                    }else{
//                        //跳转到写信页面
//                        intent=new Intent(TreeHolesActivity.this,WriteLetterActivity.class);
//                        startActivity(intent);
//
//                    }
                    //跳转到写信页面
                    intent=new Intent(TreeHolesActivity.this,WriteLetterActivity.class);
                    startActivity(intent);
                    break;

            }
        }
    }
}
