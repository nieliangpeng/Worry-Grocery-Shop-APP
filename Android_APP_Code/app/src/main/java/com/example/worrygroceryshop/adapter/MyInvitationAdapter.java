package com.example.worrygroceryshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.worrygroceryshop.R;
import com.example.worrygroceryshop.activity.IndexActivity;
import com.example.worrygroceryshop.activity.LoginActivity;
import com.example.worrygroceryshop.activity.PenFriendHomePageActivity;
import com.example.worrygroceryshop.bean.Invitation;
import com.example.worrygroceryshop.bean.User;
import com.example.worrygroceryshop.common.MyPathUrl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 夏天 on 2018/6/1.
 */

public class MyInvitationAdapter extends BaseAdapter {
    private Context context;
    private List<Invitation> invitationList;
    private int item_layout_id;
    private int[] ids;
    private User user;
    private  ViewHoder viewHoder = null;
    private Invitation invitation;
//    Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 10:
//                    Toast.makeText(context,"连接失败",Toast.LENGTH_SHORT).show();
//                    break;
//                case 40:
//                    Toast.makeText(context,"点赞失败",Toast.LENGTH_SHORT).show();
//                    break;
//                case 41:
//                    //
//                    viewHoder.news_thumbsUpSum.setText(invitation.getIvtn_PraiseNum()+"");
//                    viewHoder.news_toThumbsUp.setImageResource(R.mipmap.dianzan1);
//                    break;
//            }
//            super.handleMessage(msg);
//        }
//    };
    public MyInvitationAdapter(Context context, List<Invitation> invitationList, int item_layout_id, int[] ids) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(null==convertView){
            viewHoder = new ViewHoder();
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(item_layout_id, null);
            viewHoder.user_image = convertView.findViewById(ids[0]);
            viewHoder.user_name = convertView.findViewById(ids[1]);
            viewHoder.news_sendTime = convertView.findViewById(ids[2]);
            viewHoder.news_content = convertView.findViewById(ids[3]);
            viewHoder.invt_image = convertView.findViewById(ids[4]);
            viewHoder.invt_type_name = convertView.findViewById(ids[5]);
            viewHoder.news_discussSum = convertView.findViewById(ids[6]);
            viewHoder.news_thumbsUpSum = convertView.findViewById(ids[7]);
            viewHoder.news_toLove=convertView.findViewById(ids[8]);
            viewHoder.news_toThumbsUp=convertView.findViewById(ids[9]);
            convertView.setTag(viewHoder);
        }else {
            viewHoder = (ViewHoder)convertView.getTag();
        }
        //帖子信息
        invitation = invitationList.get(position);
        //头像
        Glide.with(context)
                .load(MyPathUrl.MyURL+"getHeader.action?user_phone="+invitation.getUser_phone())
                .placeholder(R.mipmap.placeholder)
                .error(R.mipmap.error)
                .fallback(R.mipmap.ic_launcher_round)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHoder.user_image);
        //名字
        viewHoder.user_name.setText(invitation.getUser_name());
        //时间
        viewHoder.news_sendTime.setText(invitation.getIvtn_time()+"");
        //内容
        viewHoder.news_content.setText(invitation.getIvtn_content()+"");
        //图片
        Glide.with(context)
                .load(MyPathUrl.MyURL+"invitationPicture.action?invt_id="+invitation.getIvtn_id())
                .placeholder(R.mipmap.placeholder)
                .error(R.mipmap.error)
                .fallback(R.mipmap.ic_launcher_round)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHoder.invt_image);
        //类型
        viewHoder.invt_type_name.setText(invitation.getType_name()+"");
        //评论个数
        viewHoder.news_discussSum.setText(invitation.getCommentNum()+"");
        //赞的个数
        viewHoder.news_thumbsUpSum.setText(invitation.getIvtn_PraiseNum()+"");
        //点击喜欢
//        viewHoder.news_toLove.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                viewHoder.news_toLove.setImageResource(R.mipmap.collent_t);
//            }
//        });
        //点赞
//        viewHoder.news_toThumbsUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                OkHttpClient okHttpClient1 = new OkHttpClient();
//                FormBody.Builder builder1 = new FormBody.Builder();
//                builder1.add("invt_id",invitation.getIvtn_id()+"");
//                FormBody body1 = builder1.build();
//                final Request request1 = new Request.Builder().post(body1)
//                        .url(MyPathUrl.MyURL+"dianzan.action").build();
//                final Call call1 = okHttpClient1.newCall(request1);
//                call1.enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        Log.i("app","连接失败");
//                        Log.i("错误信息",e.getMessage());
//                        Message message = Message.obtain();
//                        message.what = 10;
//                        handler.sendMessage(message);
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        Log.i("app","连接成功");
//                        String data=response.body().string();
//                        if(data.equals("success")){
//                            Log.i("json串",data);
//                            invitation.setIvtn_PraiseNum(invitation.getIvtn_PraiseNum()+1);
//                            invitationList.set(position,invitation);
//                            Message message = Message.obtain();
//                            message.what = 41;
//                            handler.sendMessage(message);
//                        }else{
//                            Log.i("app","点赞失败");
//                            Message message = Message.obtain();
//                            message.what = 40;
//                            handler.sendMessage(message);
//                        }
//                    }
//
//                });
//            }
//        });
        return convertView;
    }
    public void refresh(List<Invitation> i){
        invitationList=i;
        notifyDataSetChanged();
    }
    private static class ViewHoder{
        CircleImageView user_image;
        TextView user_name;
        TextView news_sendTime;
        TextView news_content;
        ImageView invt_image;
        TextView invt_type_name;
        TextView news_discussSum;
        TextView news_thumbsUpSum;
        ImageView news_toLove;//喜欢
        ImageView news_toThumbsUp;//点赞
    }

}
