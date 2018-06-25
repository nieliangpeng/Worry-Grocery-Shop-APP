package com.example.worrygroceryshop.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.worrygroceryshop.R;

public class ShutActivity extends AppCompatActivity {
    private TextView write;
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shut);
        //
        preferences=getSharedPreferences("userData", Context.MODE_PRIVATE);
        final String userJson=preferences.getString("userJson","不存在");
        //
        write=findViewById(R.id.write);
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果没有登录，提示先登录
                if(userJson.equals("不存在")){
                    Toast.makeText(ShutActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent=new Intent(ShutActivity.this,TextShutActivity.class);
                    startActivity(intent);
                }

            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            //按键keyCode是否是返回键，如果是的话
            Intent intent=new Intent(this,MoreActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
