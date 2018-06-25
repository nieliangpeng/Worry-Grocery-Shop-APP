package com.example.worrygroceryshop.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.worrygroceryshop.R;
import com.example.worrygroceryshop.adapter.VideoAdapter;
import com.example.worrygroceryshop.bean.Mmsic;
import com.example.worrygroceryshop.bean.Video;
import com.example.worrygroceryshop.common.MyPathUrl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
//https://v.qq.com/iframe/player.html?vid=f065078dp1j&tiny=0&auto=0
//https://v.qq.com/iframe/player.html?vid=b01942ournd&tiny=0&auto=0
//https://v.qq.com/iframe/player.html?vid=u0533dasi5d&tiny=0&auto=0
//https://v.qq.com/iframe/player.html?vid=n06313qv00m&tiny=0&auto=0
public class VideoActivity extends AppCompatActivity {
    private ListView listView;
    private VideoAdapter videoAdapter;
    private List<Video> videoList=new ArrayList<>();
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 10:
                    Toast.makeText(VideoActivity.this,"连接失败",Toast.LENGTH_SHORT).show();
                    break;
                case 32:
                    videoAdapter.refresh(videoList);
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        //初始化
        listView=findViewById(R.id.listview);
        videoAdapter=new VideoAdapter(this,videoList,R.layout.video_item,new int[]{R.id.webview,R.id.videoname,R.id.videoname1,R.id.videotime});
        listView.setAdapter(videoAdapter);
        //请求后台,获取资源，更新页面
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        FormBody body = builder.build();
        final Request request = new Request.Builder().post(body)
                .url(MyPathUrl.MyURL+"video.action").build();
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
                String jsonString=response.body().string();
                if(jsonString!=null){
                    //返回视频的json串
                    Log.i("json串",jsonString);
                    Gson gson = new GsonBuilder()
                            .serializeNulls()
                            .setPrettyPrinting()
                            .create();
                    videoList = gson.fromJson(jsonString, new TypeToken<List<Video>>(){}.getType());
                    Message message = Message.obtain();
                    message.what = 32;
                    handler.sendMessage(message);
                }
            }
        });
    }


}
