package com.example.worrygroceryshop.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.example.worrygroceryshop.R;
import com.example.worrygroceryshop.activity.MoreActivity;

/**
 * Created by 夏天 on 2018/5/17.
 */

public class HomePageFragment extends Fragment {
    private Context context;
    private ImageView more;
    private static FragmentTabHost myTabhost;
    private String mFragmentTabs[]={"推荐","最新","最热","话题"};//标签项
    private Class mFragments[]={HomePageTuiJianFragment.class,HomePageNewFragment.class,
            HomePageHotFragment.class,HomePageTypeFragment.class};//标签页
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_homepage, container,false);
        findViews(layout);
        setListeners();
        //设置fragmentTabHost
        initTabHost();
        //
        myTabhost.setCurrentTab(0);
        changeTabBackGround();
        myTabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                changeTabBackGround();
            }
        });
        return layout;

    }
    //初始化tabhost
    private void initTabHost() {
        myTabhost.setup(getActivity().getApplicationContext(),getChildFragmentManager(), android.R.id.tabhost);
        //去掉分割线
        myTabhost.getTabWidget().setDividerDrawable(null);
        for (int i = 0; i < mFragmentTabs.length; i++) {
            //对Tab按钮添加标记和图片，getTextView(i)方法是获取了一个标签项
            TabHost.TabSpec tabSpec = myTabhost.newTabSpec(mFragmentTabs[i]).setIndicator(getTextView(i));
            //添加Fragment
            myTabhost.addTab(tabSpec, mFragments[i], null);
        }
    }
    private View getTextView(int index){
        View view = getLayoutInflater().inflate(R.layout.fragment_tab_homepage_item, null);
        TextView textView = view.findViewById(R.id.tabname);
        textView.setText(mFragmentTabs[index]);
//        textView.setCompoundDrawablesWithIntrinsicBounds(null, this.getResources().getDrawable(mImages[index], null), null, null);
        return view;
    }

    private void setListeners() {
        ClickListener listener=new ClickListener();
        more.setOnClickListener(listener);
    }

    private void findViews(View layout) {
        more=layout.findViewById(R.id.more);
        myTabhost = layout.findViewById(android.R.id.tabhost);


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

//    @Override
//    public void onPause() {
//        myTabhost.destroyDrawingCache();
//        super.onPause();
//    }



    @Override
    public void onAttach(Context context) {
        this.context=context;
        super.onAttach(context);
    }
    //点击某一个选项卡切换该选项卡背景的方法
    private void changeTabBackGround(){
        //得到当前选中选项卡的索引
        int index = myTabhost.getCurrentTab();
        //调用tabhost中的getTabWidget()方法得到TabWidget
        TabWidget tabWidget = myTabhost.getTabWidget();
        //得到选项卡的数量
        int count = tabWidget.getChildCount();
        //循环判断，只有点中的索引值改变背景颜色，其他的则恢复未选中的颜色
        for(int i=0;i<count;i++){
            View view = tabWidget.getChildAt(i);
            TextView txt=view.findViewById(R.id.tabname);
            if(index == i){
                txt.setTextColor(0xe1ddafaf);
                txt.setTextSize(18);
            }else{
                txt.setTextColor(Color.WHITE);
                txt.setTextSize(16);
            }
        }
    }
}
