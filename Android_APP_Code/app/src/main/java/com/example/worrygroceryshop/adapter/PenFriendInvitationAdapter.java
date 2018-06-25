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
import com.example.worrygroceryshop.bean.Invitation;
import com.example.worrygroceryshop.bean.User;
import com.example.worrygroceryshop.common.MyPathUrl;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 夏天 on 2018/6/1.
 */

public class PenFriendInvitationAdapter extends BaseAdapter {
    private Context context;
    private List<Invitation> invitationList;
    private int item_layout_id;
    private int[] ids;
    private  ViewHoder viewHoder = null;
    private Invitation invitation;

    public PenFriendInvitationAdapter(Context context, List<Invitation> invitationList, int item_layout_id, int[] ids) {
        this.context = context;
        this.invitationList = invitationList;
        this.item_layout_id = item_layout_id;
        this.ids = ids;
    }

    @Override
    public int getCount() {
        return invitationList.size();
    }

    @Override
    public Object getItem(int position) {
        return invitationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(null==convertView){
            viewHoder = new ViewHoder();
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(item_layout_id, null);
            viewHoder.img = convertView.findViewById(ids[0]);
            viewHoder.context = convertView.findViewById(ids[1]);
            viewHoder.invt_type_name = convertView.findViewById(ids[2]);
            convertView.setTag(viewHoder);
        }else {
            viewHoder = (ViewHoder)convertView.getTag();
        }
        //帖子信息
        invitation = invitationList.get(position);
        //图片
        Glide.with(context)
                .load(MyPathUrl.MyURL+"invitationPicture.action?invt_id="+invitation.getIvtn_id())
                .placeholder(R.mipmap.placeholder)
                .error(R.mipmap.error)
                .fallback(R.mipmap.ic_launcher_round)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHoder.img);
        //内容
        String c=invitation.getIvtn_content();
        if(c.length()>15){
            c=c.substring(0,15)+"....";
        }
        viewHoder.context.setText(c);
        //类型
        viewHoder.invt_type_name.setText(invitation.getType_name()+"");
        return convertView;
    }
    public void refresh(List<Invitation> i){
        invitationList=i;
        notifyDataSetChanged();
    }
    private static class ViewHoder{
        ImageView img;
        TextView context;
        TextView invt_type_name;
    }

}
