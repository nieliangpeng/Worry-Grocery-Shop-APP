package com.example.worrygroceryshop.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.worrygroceryshop.R;
import com.example.worrygroceryshop.activity.LoginActivity;
import com.example.worrygroceryshop.bean.User;
import com.example.worrygroceryshop.bean.Video;
import com.example.worrygroceryshop.common.MyPathUrl;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 夏天 on 2018/6/1.
 */

public class BigTeacherAdapter extends BaseAdapter {
    private Context context;
    private List<User> BigTeacherList;
    private int item_layout_id;
    private int[] ids;

    public BigTeacherAdapter(Context context, List<User> bigTeacherList, int item_layout_id, int[] ids) {
        this.context = context;
        BigTeacherList = bigTeacherList;
        this.item_layout_id = item_layout_id;
        this.ids = ids;
    }

    @Override
    public int getCount() {
        return BigTeacherList.size();
    }

    @Override
    public Object getItem(int position) {
        return BigTeacherList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoder viewHoder = null;
        if(null==convertView){
            viewHoder = new ViewHoder();
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(item_layout_id, null);
            viewHoder.user_image = convertView.findViewById(ids[0]);
            viewHoder.user_name=convertView.findViewById(ids[1]);
            viewHoder.master_profile = convertView.findViewById(ids[2]);
            convertView.setTag(viewHoder);
        }else {
            viewHoder = (ViewHoder)convertView.getTag();
        }
        final User teacher = BigTeacherList.get(position);
        //头像
        Glide.with(context)
                .load(MyPathUrl.MyURL+"getHeader.action?user_phone="+teacher.getUser_phone())
                .placeholder(R.mipmap.placeholder)
                .error(R.mipmap.error)
                .fallback(R.mipmap.ic_launcher_round)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHoder.user_image);
        //名字
        viewHoder.user_name.setText(teacher.getUser_name());
        //简介
        viewHoder.master_profile.setText(teacher.getMaster_profile());
        return convertView;
    }
    public void refresh(List<User> u){
        BigTeacherList=u;
        notifyDataSetChanged();
    }
    private static class ViewHoder{
        CircleImageView user_image;
        TextView user_name;
        TextView master_profile;
    }

}
