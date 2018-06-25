package com.example.worrygroceryshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.worrygroceryshop.R;
import com.example.worrygroceryshop.bean.InvtType;
import com.example.worrygroceryshop.bean.Mmsic;
import com.example.worrygroceryshop.common.MyPathUrl;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 夏天 on 2018/6/1.
 */

public class InvtTypeAdapter extends BaseAdapter {
    private Context context;
    private List<InvtType> invtTypeList;
    private int item_layout_id;
    private int[] ids;

    public InvtTypeAdapter(Context context, List<InvtType> invtTypeList, int item_layout_id, int[] ids) {
        this.context = context;
        this.invtTypeList = invtTypeList;
        this.item_layout_id = item_layout_id;
        this.ids = ids;
    }

    @Override
    public int getCount() {
        return invtTypeList.size();
    }

    @Override
    public Object getItem(int position) {
        return invtTypeList.get(position);
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
        final InvtType invtType = invtTypeList.get(position);
        //加载图片
        Glide.with(context)
                .load(MyPathUrl.MyURL+"picture.action?type_id="+invtType.getType_id())
                .placeholder(R.mipmap.placeholder)
                .error(R.mipmap.error)
                .fallback(R.mipmap.ic_launcher)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHoder.type_image);
        viewHoder.type_name.setText("#"+invtType.getType_name()+"#");
        return convertView;
    }
    public void refresh(List<InvtType> i){
        invtTypeList=i;
        notifyDataSetChanged();
    }
    private static class ViewHoder{
        CircleImageView type_image;
        TextView type_name;
    }
}
