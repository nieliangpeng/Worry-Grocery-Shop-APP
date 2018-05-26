package com.example.worrygroceryshop.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.worrygroceryshop.R;
import com.example.worrygroceryshop.activity.MoreActivity;

/**
 * Created by 夏天 on 2018/5/17.
 */

public class HomePageFragment extends Fragment {
    private Context context;
    private ImageView more;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_homepage, container,false);
        findViews(layout);
        setListeners();
        //绑定监听器
        return layout;

    }

    private void setListeners() {
        ClickListener listener=new ClickListener();
        more.setOnClickListener(listener);
    }

    private void findViews(View layout) {
        more=layout.findViewById(R.id.more);
    }
    class ClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.more:
                    Intent i=new Intent(context, MoreActivity.class);
                    startActivity(i);
                    break;
            }
        }
    }
    @Override
    public void onAttach(Context context) {
        this.context=context;
        super.onAttach(context);
    }
}
