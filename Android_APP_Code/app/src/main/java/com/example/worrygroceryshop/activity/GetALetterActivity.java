package com.example.worrygroceryshop.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.worrygroceryshop.R;

public class GetALetterActivity extends AppCompatActivity {
    private Button watchTheLetter;
    private ImageView back;
    private String letterContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_aletter);
        watchTheLetter=findViewById(R.id.watchTheLetter);
        back=findViewById(R.id.back);

        Intent i=getIntent();
        letterContent=i.getStringExtra("letterContent");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GetALetterActivity.this,TreeHolesActivity.class);
                startActivity(intent);
            }
        });
        watchTheLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GetALetterActivity.this,WatchGetALetterDetailActivity.class);
                intent.putExtra("letterContent",letterContent);
                startActivity(intent);
            }
        });

    }
}
