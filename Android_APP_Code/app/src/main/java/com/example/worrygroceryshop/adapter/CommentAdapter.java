package com.example.worrygroceryshop.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.worrygroceryshop.activity.InvitationDetailActivity;
import com.example.worrygroceryshop.activity.PenFriendHomePageActivity;
import com.example.worrygroceryshop.bean.Comment;
import com.example.worrygroceryshop.bean.Invitation;
import com.example.worrygroceryshop.bean.User;
import com.example.worrygroceryshop.common.MyPathUrl;

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

public class CommentAdapter extends BaseAdapter {
    private Context context;
    private List<Comment> commentList;
    private int item_layout_id;
    private int[] ids;
    private String penFriendJson=null;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 10:
                    Toast.makeText(context,"连接失败",Toast.LENGTH_SHORT).show();
                    break;
                case 40:
                    Toast.makeText(context,"返回笔友信息失败",Toast.LENGTH_SHORT).show();
                    break;
                case 41:
                    Intent intent=new Intent(context,PenFriendHomePageActivity.class);
                    intent.putExtra("userJson",penFriendJson);
                    context.startActivity(intent);
                    break;
            }
            super.handleMessage(msg);
        }
    };
    public CommentAdapter(Context context, List<Comment> commentList, int item_layout_id, int[] ids) {
        this.context = context;
        this.commentList = commentList;
        this.item_layout_id = item_layout_id;
        this.ids = ids;
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoder viewHoder=null;
        if(null==convertView){
            viewHoder = new ViewHoder();
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(item_layout_id, null);
            viewHoder.user_image = convertView.findViewById(ids[0]);
            viewHoder.user_name = convertView.findViewById(ids[1]);
            viewHoder.time=convertView.findViewById(ids[2]);
            viewHoder.content=convertView.findViewById(ids[3]);
            viewHoder.num=convertView.findViewById(ids[4]);

            convertView.setTag(viewHoder);
        }else {
            viewHoder = (ViewHoder)convertView.getTag();
        }
        //评论内容
        final Comment comment = commentList.get(position);
        //头像
        Glide.with(context)
                .load(MyPathUrl.MyURL+"getHeader.action?user_phone="+comment.getUser_phone())
                .placeholder(R.mipmap.placeholder)
                .error(R.mipmap.error)
                .fallback(R.mipmap.ic_launcher_round)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHoder.user_image);
        //名字
        viewHoder.user_name.setText(comment.getUser_name());
        //时间
        viewHoder.time.setText(comment.getComment_time()+"");
        //内容
        viewHoder.content.setText(comment.getComment_content()+"");
        //赞的个数
        viewHoder.num.setText(comment.getComment_praiseNum()+"");
        viewHoder.user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击头像，跳转到笔友详情页面
                OkHttpClient okHttpClient1 = new OkHttpClient();
                FormBody.Builder builder1 = new FormBody.Builder();
                builder1.add("user_id",comment.getUser_id()+"");
                FormBody body1 = builder1.build();
                final Request request1 = new Request.Builder().post(body1)
                        .url(MyPathUrl.MyURL+"findPenFriend.action").build();
                final Call call1 = okHttpClient1.newCall(request1);
                call1.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i("app","连接失败");
                        Log.i("错误信息",e.getMessage());
                        Message message = Message.obtain();
                        message.what = 10;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.i("app","连接成功");
                        penFriendJson=response.body().string();
                        if(penFriendJson!=null&&!penFriendJson.equals("")&&!penFriendJson.equals("failure")){
                            //成功返回user json串
                            Log.i("json串",penFriendJson);
                            Message message = Message.obtain();
                            message.what = 41;
                            handler.sendMessage(message);
                        }else{
                            Log.i("app","返回笔友信息失败");
                            Message message = Message.obtain();
                            message.what = 40;
                            handler.sendMessage(message);
                        }
                    }

                });
            }
        });
        return convertView;
    }
    public void refresh(List<Comment> c){
        commentList=c;
        notifyDataSetChanged();
    }
    private static class ViewHoder{
        CircleImageView user_image;
        TextView user_name;
        TextView time;
        TextView content;
        TextView num;
    }

}
