package com.example.worrygroceryshop.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.GridView;
import android.widget.Toast;

import com.example.worrygroceryshop.R;
import com.example.worrygroceryshop.activity.IndexActivity;
import com.example.worrygroceryshop.activity.InvitationDetailActivity;
import com.example.worrygroceryshop.activity.LoginActivity;
import com.example.worrygroceryshop.activity.MyInvitationActivity;
import com.example.worrygroceryshop.activity.OrderByInvtTypeActivity;
import com.example.worrygroceryshop.adapter.InvtTypeAdapter;
import com.example.worrygroceryshop.bean.Invitation;
import com.example.worrygroceryshop.bean.InvtType;
import com.example.worrygroceryshop.bean.Mmsic;
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

public class HomePageTypeFragment extends Fragment {
    private GridView gridView;
    private Context context;
    private List<InvtType> dataList=new ArrayList<>();
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 10:
                    Toast.makeText(context,"连接失败",Toast.LENGTH_SHORT).show();
                    break;
                case 11:
                    Toast.makeText(context,"获取数据失败",Toast.LENGTH_SHORT).show();
                    break;
                case 12:
                    //进行初始化
                    InvtTypeAdapter adapter=new InvtTypeAdapter(context,dataList,R.layout.invt_type_item,new int[]{R.id.type_image,R.id.type_name});
                    gridView.setAdapter(adapter);
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            InvtType invtType=(InvtType) parent.getAdapter().getItem(position);//话题信息
                            Intent intent=new Intent(context,OrderByInvtTypeActivity.class);
                            Bundle bundle=new Bundle();
                            bundle.putSerializable("invtType",invtType);
                            intent.putExtra("bundle",bundle);
                            startActivity(intent);
                        }
                    });
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_homepage_type, container,false);
        gridView=layout.findViewById(R.id.gridview);
        //请求服务器，得到数据
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        FormBody body = builder.build();
        final Request request = new Request.Builder().post(body)
                .url(MyPathUrl.MyURL+"getTypeList.action").build();
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
                String data=response.body().string();
                if(data!=null){
                    Log.i("json串",data);
                    Gson gson = new GsonBuilder()
                            .serializeNulls()
                            .setPrettyPrinting()
                            .create();
                    dataList=gson.fromJson(data, new TypeToken<List<InvtType>>(){}.getType());
                    Message message = Message.obtain();
                    message.what = 12;
                    handler.sendMessage(message);
                }else{
                    Message message = Message.obtain();
                    message.what = 11;
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
