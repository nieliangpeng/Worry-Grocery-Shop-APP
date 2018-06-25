package com.example.worrygroceryshop.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.worrygroceryshop.R;
import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.exceptions.HyphenateException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PenFriendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pen_friend);
        final EaseContactListFragment contactListFragment= new EaseContactListFragment();
        //需要设置联系人列表才能启动fragment
        new Thread(){
            @Override
            public void run() {
                contactListFragment.setContactsMap(getContact());
                //要刷新
                contactListFragment.refresh();
            }
        }.start();

        //设置item点击事件
        contactListFragment.setContactListItemClickListener(new EaseContactListFragment.EaseContactListItemClickListener() {

            @Override
            public void onListItemClicked(EaseUser user) {
                startActivity(new Intent(PenFriendActivity.this, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, user.getUsername()));
            }
        });
        getSupportFragmentManager().beginTransaction().add(R.id.friend,contactListFragment).commit();
        EMClient.getInstance().contactManager().setContactListener(new EMContactListener() {
            @Override
            public void onContactAdded(String s) {
                contactListFragment.setContactsMap(getContact());
                contactListFragment.refresh();
            }

            @Override
            public void onContactDeleted(String s) {
                contactListFragment.setContactsMap(getContact());
                contactListFragment.refresh();
            }

            @Override
            public void onContactInvited(String s, String s1) {
                try {
                    Log.i("收到好友请求","true");
                    EMClient.getInstance().contactManager().acceptInvitation(s);
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
                contactListFragment.setContactsMap(getContact());
                contactListFragment.refresh();
            }

            @Override
            public void onFriendRequestAccepted(String s) {
                contactListFragment.setContactsMap(getContact());
                contactListFragment.refresh();
            }

            @Override
            public void onFriendRequestDeclined(String s) {
                contactListFragment.setContactsMap(getContact());
                contactListFragment.refresh();
            }
        });
    }
    /**
     * 获取联系人
     * @return
     */
    private Map<String, EaseUser> getContact() {
        Map<String, EaseUser> map = new HashMap<>();
        try {
            List<String> userNames = EMClient.getInstance().contactManager().getAllContactsFromServer();
//            KLog.e("......有几个好友:" + userNames.size());
            for (String userId : userNames) {
//                KLog.e("好友列表中有 : " + userId);
                map.put(userId, new EaseUser(userId));
            }
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
        return map;
    }
}
