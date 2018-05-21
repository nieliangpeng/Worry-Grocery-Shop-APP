package com.example.worrygroceryshop.activity;

import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

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
    private long firstKeyDownTime;//第一次按返回键的时间
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        //初始化
        initFragmentTabHost();
        myFragmentTabhost.setCurrentTab(0);
        myFragmentTabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                changeTabBackGround();
            }
        });
    }
    //初始化
    private void initFragmentTabHost() {
        myFragmentTabhost = findViewById(android.R.id.tabhost);
        myFragmentTabhost.setup(this, getSupportFragmentManager(), android.R.id.tabhost);
        //去掉分割线
//        myFragmentTabhost.getTabWidget().setDividerDrawable(null);
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
    //点击某一个选项卡切换该选项卡背景的方法
    private void changeTabBackGround(){
        int Imgs[]={R.mipmap.tab1,R.mipmap.tab22, R.mipmap.tab3,R.mipmap.tab44,R.mipmap.tab55};
        int tabss[] = {R.mipmap.tab11, R.mipmap.tab2, R.mipmap.tab3,R.mipmap.tab4, R.mipmap.tab5};
        //得到当前选中选项卡的索引
        int index = myFragmentTabhost.getCurrentTab();
        //调用tabhost中的getTabWidget()方法得到TabWidget
        TabWidget tabWidget = myFragmentTabhost.getTabWidget();
        //得到选项卡的数量
        int count = tabWidget.getChildCount();
        //循环判断，只有点中的索引值改变背景颜色，其他的则恢复未选中的颜色
        for(int i=0;i<count;i++){
            View view = tabWidget.getChildAt(i);
            ImageView img=view.findViewById(R.id.img);
            if(index == i){
                img.setImageResource(Imgs[i]);


            }else{
                img.setImageResource(tabss[i]);
            }
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {//按键keyCode是否是返回键，如果是的话
            if (System.currentTimeMillis() - firstKeyDownTime > 2000) {//第一次按键
                //提示再按一次退出系统
                Toast.makeText(IndexActivity.this,
                        "再按一次返回键退出解忧杂货铺",
                        Toast.LENGTH_SHORT).show();
                //记录下当前按键的时间
                firstKeyDownTime = System.currentTimeMillis();
            } else {//第二次按键
                //退出系统
                finish();

            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
