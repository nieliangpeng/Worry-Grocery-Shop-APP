package com.example.worrygroceryshop.adapter;

import android.content.Context;
import android.util.Log;
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
import com.example.worrygroceryshop.bean.TreeHoles;
//import com.example.worrygroceryshop.common.GlideApp;
import com.example.worrygroceryshop.common.MyPathUrl;

import java.util.List;

/**
 * Created by 夏天 on 2018/5/27.
 */

public class MyTreeHolesAdapter extends BaseAdapter {
    private Context context;
    private List<String> letterList;
    private int item_layout_id;
    private int[] ids;

    public MyTreeHolesAdapter(Context context, List<String> letterList, int item_layout_id, int[] ids) {
        this.context = context;
        this.letterList = letterList;
        this.item_layout_id = item_layout_id;
        this.ids = ids;
    }

    @Override
    public int getCount() {
        return letterList.size();
    }

    @Override
    public Object getItem(int position) {
        return letterList.get(position);
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
            viewHoder.treehole_mytree_letterImg = convertView.findViewById(ids[0]);
            viewHoder.letter_state=convertView.findViewById(ids[1]);
            viewHoder.letter_time=convertView.findViewById(ids[2]);
            viewHoder.letter_toRead=convertView.findViewById(ids[3]);
            convertView.setTag(viewHoder);
        }else {
            viewHoder = (ViewHoder)convertView.getTag();
        }
        final String treeHoles = letterList.get(position);
        String[] detail=treeHoles.split("\\+");
        viewHoder.treehole_mytree_letterImg.setImageResource(R.mipmap.my_letter);
        if(detail[3].equals("1")){
            viewHoder.letter_state.setText("还在树洞中");
        }else{
            viewHoder.letter_state.setText("不在树洞中");
        }
        viewHoder.letter_time.setText(detail[2]);
        viewHoder.letter_toRead.setImageResource(R.mipmap.find);

        return convertView;
    }
    public void refresh(List<String> t){
        letterList=t;
        notifyDataSetChanged();
    }
    private static class ViewHoder{
        ImageView treehole_mytree_letterImg;
        TextView letter_state;
        TextView letter_time;
        ImageView letter_toRead;
    }
}
