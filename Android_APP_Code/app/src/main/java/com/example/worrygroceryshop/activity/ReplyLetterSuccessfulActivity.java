package com.example.worrygroceryshop.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.worrygroceryshop.R;

public class ReplyLetterSuccessfulActivity extends AppCompatActivity {
    private TextView gotoTree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_letter_successful);
        gotoTree=findViewById(R.id.gotoTree);
        gotoTree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ReplyLetterSuccessfulActivity.this,TreeHolesActivity.class);
                startActivity(intent);
            }
        });
    }
}
