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
import com.example.worrygroceryshop.bean.FollowType;
import com.example.worrygroceryshop.bean.InvtType;
import com.example.worrygroceryshop.bean.Mmsic;
import com.example.worrygroceryshop.common.MyPathUrl;

import java.util.List;

/**
 * Created by 夏天 on 2018/6/1.
 */

public class MusicAdapter extends BaseAdapter {
    private Context context;
    private List<Mmsic> mmsicsList;
    private int item_layout_id;
    private int[] ids;

    public MusicAdapter(Context context, List<Mmsic> mmsicsList, int item_layout_id, int[] ids) {
        this.context = context;
        this.mmsicsList = mmsicsList;
        this.item_layout_id = item_layout_id;
        this.ids = ids;
    }

    @Override
    public int getCount() {
        return mmsicsList.size();
    }

    @Override
    public Object getItem(int position) {
        return mmsicsList.get(position);
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
            viewHoder.music_image = convertView.findViewById(ids[0]);
            viewHoder.music_name=convertView.findViewById(ids[1]);
            viewHoder.musicer = convertView.findViewById(ids[2]);
            viewHoder.menu=convertView.findViewById(ids[3]);
            convertView.setTag(viewHoder);
        }else {
            viewHoder = (ViewHoder)convertView.getTag();
        }
        final Mmsic mmsic = mmsicsList.get(position);
        //加载图片
        Glide.with(context)
                .load(mmsic.getMusic_img())
                .placeholder(R.mipmap.placeholder)
                .error(R.mipmap.error)
                .fallback(R.mipmap.ic_launcher)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHoder.music_image);
        viewHoder.music_name.setText(mmsic.getMusic_name());
        viewHoder.musicer.setText(mmsic.getMusic_auth());
        viewHoder.menu.setImageResource(R.mipmap.love);
        return convertView;
    }
    public void refresh(List<Mmsic> m){
        mmsicsList=m;
        notifyDataSetChanged();
    }
    private static class ViewHoder{
        ImageView music_image;
        TextView music_name;
        TextView musicer;
        ImageView menu;
    }
}
