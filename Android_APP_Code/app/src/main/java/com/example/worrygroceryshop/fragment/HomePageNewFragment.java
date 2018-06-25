package com.example.worrygroceryshop.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.worrygroceryshop.R;
import com.example.worrygroceryshop.activity.InvitationDetailActivity;
import com.example.worrygroceryshop.adapter.MyInvitationAdapter;
import com.example.worrygroceryshop.bean.Invitation;
import com.example.worrygroceryshop.common.MyPathUrl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 夏天 on 2018/6/10.
 */

public class HomePageNewFragment extends Fragment {
    private Context context;
    private ListView invitationList;
    private List<Invitation> dataList=new ArrayList<>();
    private MyInvitationAdapter adapter;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 30:
                    Toast.makeText(getActivity(),"连接失败",Toast.LENGTH_SHORT).show();
                    break;
                case 31:
                    Toast.makeText(getActivity(),"获取帖子失败",Toast.LENGTH_SHORT).show();
                    break;
                case 32:
                    //更新页面
                    adapter.refresh(dataList);
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_homepage_new, container,false);
        //getviews
        invitationList=layout.findViewById(R.id.invitationList);
        //初始化界面
        adapter=new MyInvitationAdapter(getActivity(),dataList,R.layout.invitation_item,
                new int[]{R.id.user_image,R.id.user_name,R.id.news_sendTime,R.id.news_content,R.id.invt_image,R.id.invt_type_name,R.id.news_discussSum,R.id.news_thumbsUpSum,R.id.news_toLove,R.id.news_toThumbsUp});
        invitationList.setAdapter(adapter);
        invitationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Invitation invitation=(Invitation) parent.getAdapter().getItem(position);
                Intent intent=new Intent(context,InvitationDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("invitation",invitation);
                intent.putExtra("bundle",bundle);
                startActivity(intent);
            }
        });
        //请求得到所有帖子数据,按时间排序，更新页面
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        FormBody body = builder.build();
        final Request request = new Request.Builder().post(body)
                .url(MyPathUrl.MyURL+"showNewInvitation.action").build();
        final Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("app","连接失败");
                Log.i("错误信息",e.getMessage());
                Message message = Message.obtain();
                message.what = 30;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("app","连接成功");
                String data=response.body().string();
                if(data!=null){
                    //返回所有的帖子的List
                    Log.i("json串",data);
                    Gson gson = new GsonBuilder()
                            .serializeNulls()
                            .setPrettyPrinting()
                            .create();
                    dataList = gson.fromJson(data, new TypeToken<List<Invitation>>(){}.getType());
                    Message message = Message.obtain();
                    message.what = 32;
                    handler.sendMessage(message);
                }else{
                    Log.i("app","获取帖子失败");
                    Message message = Message.obtain();
                    message.what = 31;
                    handler.sendMessage(message);
                }
            }
        });
        return layout;

    }
    @Override
    public void onAttach(Context context) {
        this.context=context;
        super.onAttach(context);
    }
}
