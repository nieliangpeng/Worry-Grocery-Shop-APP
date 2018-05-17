package com.example.worrygroceryshop.activity;

import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;

import com.example.worrygroceryshop.R;
import com.example.worrygroceryshop.fragment.FindFragment;
import com.example.worrygroceryshop.fragment.HomePageFragment;
import com.example.worrygroceryshop.fragment.MessageFragment;
import com.example.worrygroceryshop.fragment.MyFragment;
import com.example.worrygroceryshop.fragment.PostFragment;

public class IndexActivity extends AppCompatActivity {
    //标签项
    private int tabs[] = {R.mipmap.tab1, R.mipmap.tab2, R.mipmap.tab3,R.mipmap.tab4, R.mipmap.tab5};
    private String tags[]={"首页","发现","发帖","消息","我的"};
    private Class fragments[] = {
            HomePageFragment.class, FindFragment.class, PostFragment.class, MessageFragment.class, MyFragment.class
    };
    private FragmentTabHost myFragmentTabhost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        //初始化
        initFragmentTabHost();
        myFragmentTabhost.setCurrentTab(0);

    }
    //初始化
    private void initFragmentTabHost() {
        myFragmentTabhost = findViewById(android.R.id.tabhost);
        myFragmentTabhost.setup(this, getSupportFragmentManager(), android.R.id.tabhost);
        //去掉分割线
        myFragmentTabhost.getTabWidget().setDividerDrawable(null);
        for (int i = 0; i < tags.length; i++) {
            //对Tab按钮添加标记，getTextView(i)方法是获取了一个标签项
            TabHost.TabSpec tabSpec = myFragmentTabhost.newTabSpec(tags[i]).setIndicator(getImage(i));
            //添加Fragment
            myFragmentTabhost.addTab(tabSpec, fragments[i], null);
        }
    }
    private View getImage(int index){
        View view = getLayoutInflater().inflate(R.layout.fragment_tab_index, null);
        ImageView imageView = view.findViewById(R.id.img);
        imageView.setImageResource(tabs[index]);
        return view;
    }

    }
