package com.example.worrygroceryshop.adapter;

import android.content.Context;
import android.graphics.Picture;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.worrygroceryshop.R;
import com.example.worrygroceryshop.bean.FollowType;
import com.example.worrygroceryshop.bean.InvtType;
import com.example.worrygroceryshop.common.GlideApp;
import com.example.worrygroceryshop.common.MyPathUrl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by 夏天 on 2018/5/24.
 */

public class FollowTypeAdapter extends BaseAdapter {
    private Context context;
    private List<FollowType> followTypeList;
    private int item_layout_id;
    private int[] ids;

    public FollowTypeAdapter(Context context, List<FollowType> followTypeList, int item_layout_id, int[] ids) {
        this.context = context;
        this.followTypeList = followTypeList;
        this.item_layout_id = item_layout_id;
        this.ids = ids;
    }

    @Override
    public int getCount() {
        return followTypeList.size();
    }

    @Override
    public Object getItem(int position) {
        return followTypeList.get(position);
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
            viewHoder.type_image = convertView.findViewById(ids[0]);
            viewHoder.type_name=convertView.findViewById(ids[1]);

            convertView.setTag(viewHoder);
        }else {
            viewHoder = (ViewHoder)convertView.getTag();
        }
        final FollowType followType = followTypeList.get(position);
        final InvtType invtType=followType.getInvtType();
        //加载图片
        GlideApp.with(context)
                .load(MyPathUrl.MyURL+"getTypeImage.action?type_id="+invtType.getType_id())
                .placeholder(R.mipmap.placeholder)
                .error(R.mipmap.error)
                .fallback(R.mipmap.ic_launcher)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHoder.type_image);
        viewHoder.type_name.setText(invtType.getType_name());


        return convertView;
    }
    public void refresh(List<FollowType> f){
        followTypeList=f;
        notifyDataSetChanged();
    }
    private static class ViewHoder{
        ImageView type_image;
        TextView type_name;
    }
}
