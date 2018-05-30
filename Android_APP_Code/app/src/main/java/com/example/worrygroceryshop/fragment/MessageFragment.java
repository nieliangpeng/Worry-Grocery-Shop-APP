package com.example.worrygroceryshop.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.worrygroceryshop.R;
import com.example.worrygroceryshop.activity.ChatActivity;
import com.example.worrygroceryshop.activity.MainActivity;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseConversationListFragment;

/**
 * Created by 夏天 on 2018/5/17.
 */

public class MessageFragment extends Fragment {
    private Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_message, container,false);
        EaseConversationListFragment conversationListFragment = new EaseConversationListFragment();
        conversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {

            @Override
            public void onListItemClicked(EMConversation conversation) {
                startActivity(new Intent(context, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, conversation.conversationId()));
            }
        });
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.CONVERN,conversationListFragment).commit();
        //绑定监听器
        return layout;

    }
    @Override
    public void onAttach(Context context) {
        this.context=context;
        super.onAttach(context);
    }
}
