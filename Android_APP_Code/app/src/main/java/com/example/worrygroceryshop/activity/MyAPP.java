package com.example.worrygroceryshop.activity;

import android.app.Application;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseUI;
import com.mob.MobSDK;

/**
 * Created by 夏天 on 2018/5/23.
 */

public class MyAPP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        EaseUI.getInstance().init(this,null);
        EMClient.getInstance().setDebugMode(true);

        MobSDK.init(this);
    }
}
