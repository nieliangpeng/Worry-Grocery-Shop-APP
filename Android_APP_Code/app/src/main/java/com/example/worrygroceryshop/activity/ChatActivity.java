package com.example.worrygroceryshop.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.worrygroceryshop.R;
import com.hyphenate.easeui.ui.EaseChatFragment;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        EaseChatFragment chatFragment=new EaseChatFragment();
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.fl,chatFragment).commit();
    }
}
