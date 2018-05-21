package com.example.worrygroceryshop.fragment;

import android.content.Context;
import android.content.Intent;
import android.provider.Contacts;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.worrygroceryshop.R;
import com.example.worrygroceryshop.activity.RegisterActivity;

/**
 * Created by 夏天 on 2018/5/17.
 */

public class MyFragment extends Fragment {
    private Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //没有登录的操作
        View layout = inflater.inflate(R.layout.fragment_my, container,false);
        Button register=layout.findViewById(R.id.register);
        Button login=layout.findViewById(R.id.login);
        Listener listener=new Listener();
        register.setOnClickListener(listener);
        return layout;

    }

    @Override
    public void onAttach(Context context) {
        this.context=context;
        super.onAttach(context);
    }

    class Listener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.register:
                    Intent intent=new Intent(context, RegisterActivity.class);
                    startActivity(intent);
                    break;
                case R.id.login:
                    break;
            }
        }
    }
}
