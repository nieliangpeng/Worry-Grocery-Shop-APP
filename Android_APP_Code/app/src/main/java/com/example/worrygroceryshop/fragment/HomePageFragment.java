package com.example.worrygroceryshop.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.worrygroceryshop.R;

/**
 * Created by 夏天 on 2018/5/17.
 */

public class HomePageFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_homepage, container,false);
        TextView contentPage = layout.findViewById(R.id.txt1);
        //绑定监听器
        return layout;

    }
}
