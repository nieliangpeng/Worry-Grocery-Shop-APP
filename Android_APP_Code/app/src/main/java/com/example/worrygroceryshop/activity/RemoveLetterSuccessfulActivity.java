package com.example.worrygroceryshop.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.worrygroceryshop.R;

public class RemoveLetterSuccessfulActivity extends AppCompatActivity {
    private TextView returnToTreeHoles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_letter_successful);
        returnToTreeHoles=findViewById(R.id.returnToTreeHoles);
        returnToTreeHoles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RemoveLetterSuccessfulActivity.this,TreeHolesActivity.class);
                startActivity(intent);
            }
        });
    }
}
