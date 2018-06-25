package com.example.worrygroceryshop.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.solver.LinearSystem;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.worrygroceryshop.R;
import com.example.worrygroceryshop.adapter.CommentAdapter;
import com.example.worrygroceryshop.bean.Comment;
import com.example.worrygroceryshop.bean.Invitation;
import com.example.worrygroceryshop.bean.User;
import com.example.worrygroceryshop.common.MyPathUrl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.easeui.EaseConstant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class InvitationDetailActivity extends AppCompatActivity {
    private CircleImageView user_image;
    private TextView user_name;
    private TextView title;
    private TextView time;
    private TextView message;
    private ImageView invt_image;
    private TextView type_name;
    private TextView num;
    private ListView listview;
    private EditText write;
    private Button send;
    private Button chat;
    private Invitation invitation;//帖子
    private List<Comment> dataList=new ArrayList<>();
    private CommentAdapter adapter;
    private String penFriendJson;

    private LinearLayout shoucang;
    private ImageButton collent;
    private TextView shoucangtext;

    private ImageView dianzan;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 10:
                    Toast.makeText(InvitationDetailActivity.this,"连接失败",Toast.LENGTH_SHORT).show();
                    break;
                case 11:
                    Toast.makeText(InvitationDetailActivity.this,"获取评论失败",Toast.LENGTH_SHORT).show();
                    break;
                case 12:
                    //更新页面评论区
                    adapter=new CommentAdapter(InvitationDetailActivity.this,dataList,R.layout.comment_item,new int[]{R.id.user_image,R.id.user_name,R.id.time,R.id.content,R.id.num});
                    listview.setAdapter(adapter);
                    setListViewHeightBasedOnChildren(listview);
                    break;
                case 13:
                    //更新评论区
                    adapter.refresh(dataList);
                    write.setText("");
                    Toast.makeText(InvitationDetailActivity.this,"成功回复",Toast.LENGTH_SHORT).show();
                    setListViewHeightBasedOnChildren(listview);
                    break;
                case 40:
                    Toast.makeText(InvitationDetailActivity.this,"返回笔友信息失败",Toast.LENGTH_SHORT).show();
                    break;
                case 41:
                    Intent intent=new Intent(InvitationDetailActivity.this,PenFriendHomePageActivity.class);
                    intent.putExtra("userJson",penFriendJson);
                    startActivity(intent);
                    break;
                case 42:
                    Toast.makeText(InvitationDetailActivity.this,"点赞失败",Toast.LENGTH_SHORT).show();
                    break;
                case 43:
                    dianzan.setImageResource(R.mipmap.dianzan1);
                    num.setText((invitation.getIvtn_PraiseNum()+1)+"");
                    num.setTextColor(Color.RED);
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation_detail);
        getViews();
        //显示帖子详情
        initInvitation();
        //显示评论列表，上传invt_id，返回该帖子的所有评论，更新评论区域
        initComment();

        setListener();

    }

    private void setListener() {
        ClickListener listener=new ClickListener();
        send.setOnClickListener(listener);
        user_image.setOnClickListener(listener);
        chat.setOnClickListener(listener);
        shoucang.setOnClickListener(listener);
        dianzan.setOnClickListener(listener);
    }

    private void initComment() {
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("invt_id",invitation.getIvtn_id()+"");
        FormBody body = builder.build();
        final Request request = new Request.Builder().post(body)
                .url(MyPathUrl.MyURL+"getComment.action").build();
        final Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
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
                String data=response.body().string();//返回comment json串
                if(data!=null&&!data.equals("failure")){
                    Log.i("json串",data);
                    Gson gson = new GsonBuilder()
                            .serializeNulls()
                            .setPrettyPrinting()
                            .create();
                    dataList=gson.fromJson(data, new TypeToken<List<Comment>>(){}.getType());
                    Message message = Message.obtain();
                    message.what = 12;
                    handler.sendMessage(message);
                }else{
                    Log.i("app","获取评论失败");
//                    Message message = Message.obtain();
//                    message.what = 11;
//                    handler.sendMessage(message);
                }
            }
        });
    }

    private void initInvitation() {
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("bundle");
        invitation=(Invitation) bundle.get("invitation");//得到帖子
        //更新页面
        //头像
        Glide.with(this)
                .load(MyPathUrl.MyURL+"getHeader.action?user_phone="+invitation.getUser_phone())
                .placeholder(R.mipmap.placeholder)
                .error(R.mipmap.error)
                .fallback(R.mipmap.ic_launcher_round)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(user_image);
        //名字
        user_name.setText(invitation.getUser_name());
        //标题
        title.setText(invitation.getType_name()+"");
        //时间
        time.setText(invitation.getIvtn_time());
        //内容
        message.setText(invitation.getIvtn_content()+"");
        //图片
        Glide.with(this)
                .load(MyPathUrl.MyURL+"invitationPicture.action?invt_id="+invitation.getIvtn_id())
                .placeholder(R.mipmap.placeholder)
                .error(R.mipmap.error)
                .fallback(R.mipmap.ic_launcher_round)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(invt_image);
        //帖子类型
        type_name.setText(invitation.getType_name()+"");
        //赞的个数
        num.setText(invitation.getIvtn_PraiseNum()+"");
    }

    private void getViews() {
        user_image=findViewById(R.id.user_image);
        user_name=findViewById(R.id.user_name);
        title=findViewById(R.id.title);
        time=findViewById(R.id.time);
        message=findViewById(R.id.message);
        invt_image=findViewById(R.id.invt_image);
        type_name=findViewById(R.id.type_name);
        num=findViewById(R.id.num);
        listview=findViewById(R.id.listview);
        write=findViewById(R.id.write);
        send=findViewById(R.id.send);
        chat=findViewById(R.id.chat);

        shoucang=findViewById(R.id.shoucang);
        collent=findViewById(R.id.collent);
        shoucangtext=findViewById(R.id.shoucangtext);

        dianzan=findViewById(R.id.dianzan);
    }
    class ClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.send:
                    SharedPreferences preferences=getSharedPreferences("userData", Context.MODE_PRIVATE);
                    String userJson=preferences.getString("userJson","不存在");
                    if(userJson.equals("不存在")){
                        Toast.makeText(InvitationDetailActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
                    }else{
                        //已经登录
                        //发表评论 user_id,init_id,comment_content,上传成功后更新评论区，清空输入框
                        Gson gson = new GsonBuilder()
                                .serializeNulls()
                                .setPrettyPrinting()
                                .create();
                        User user = gson.fromJson(userJson, User.class);
                        //请求服务器
                        OkHttpClient okHttpClient = new OkHttpClient();
                        FormBody.Builder builder = new FormBody.Builder();
                        builder.add("user_id",user.getUser_id()+"");
                        builder.add("invt_id",invitation.getIvtn_id()+"");
                        builder.add("comment_content",write.getText().toString()+"");
                        FormBody body = builder.build();
                        final Request request = new Request.Builder().post(body)
                                .url(MyPathUrl.MyURL+"sendComment.action").build();
                        final Call call = okHttpClient.newCall(request);
                        call.enqueue(new Callback() {
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
                                String data=response.body().string();//返回最新的comment json串
                                if(data!=null){
                                    Log.i("json串",data);
                                    Gson gson = new GsonBuilder()
                                            .serializeNulls()
                                            .setPrettyPrinting()
                                            .create();
                                    dataList=gson.fromJson(data, new TypeToken<List<Comment>>(){}.getType());
                                    Message message = Message.obtain();
                                    message.what = 13;
                                    handler.sendMessage(message);
                                }else{
                                    Log.i("app","获取评论失败");
                                    Message message = Message.obtain();
                                    message.what = 11;
                                    handler.sendMessage(message);
                                }
                            }
                        });
                    }

                    break;
                case R.id.user_image:
                    //点击头像，跳转到笔友详情页面
                    OkHttpClient okHttpClient1 = new OkHttpClient();
                    FormBody.Builder builder1 = new FormBody.Builder();
                    Log.i("user_id",invitation.getUser_id()+"");
                    builder1.add("user_id",invitation.getUser_id()+"");
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
                    break;
                case R.id.chat:
                    startActivity(new Intent(InvitationDetailActivity.this, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID,invitation.getUser_name()));

                    break;
                case R.id.shoucang:
                    collent.setBackgroundResource(R.mipmap.collent_t);
                    shoucangtext.setTextColor(Color.RED);
                    break;
                case R.id.dianzan:
                    OkHttpClient okHttpClient2 = new OkHttpClient();
                    FormBody.Builder builder2 = new FormBody.Builder();
                    builder2.add("invt_id",invitation.getIvtn_id()+"");
                    FormBody body2 = builder2.build();
                    final Request request2 = new Request.Builder().post(body2)
                            .url(MyPathUrl.MyURL+"dianzan.action").build();
                    final Call call2 = okHttpClient2.newCall(request2);
                    call2.enqueue(new Callback() {
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
                            String data=response.body().string();
                            if(data.equals("success")){
                                Log.i("json串",data);
                                Message message = Message.obtain();
                                message.what = 43;
                                handler.sendMessage(message);
                            }else{
                                Log.i("app","点赞失败");
                                Message message = Message.obtain();
                                message.what = 42;
                                handler.sendMessage(message);
                            }
                        }

                    });
                    break;
            }
        }
    }
    public void setListViewHeightBasedOnChildren(ListView listView) {
        CommentAdapter listAdapter = (CommentAdapter) listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);  // 获取item高度
            totalHeight += listItem.getMeasuredHeight();

        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // 最后再加上分割线的高度和padding高度，否则显示不完整。
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1))+listView.getPaddingTop()+listView.getPaddingBottom();
        listView.setLayoutParams(params);
    }
}
