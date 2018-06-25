package com.example.worrygroceryshop.activity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.worrygroceryshop.R;
import com.example.worrygroceryshop.adapter.MusicAdapter;
import com.example.worrygroceryshop.bean.Mmsic;
import com.example.worrygroceryshop.common.MyPathUrl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

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

public class MusicActivity extends AppCompatActivity {
    private ListView dataList;
    private CircleImageView photo;
    private TextView songName;
    private TextView singerName;
    private MusicAdapter adapter;
    private List<Mmsic> musicList=new ArrayList<>();
    private TextView musictxt;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 10:
                    Toast.makeText(MusicActivity.this,"连接失败",Toast.LENGTH_SHORT).show();
                    break;
                case 32:
                    adapter.refresh(musicList);
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private MediaPlayer mediaPlayer=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        getViews();
        //初始化
        adapter=new MusicAdapter(MusicActivity.this,musicList,R.layout.music_item,new int[]{R.id.music_image,R.id.music_name,R.id.musicer,R.id.menu});
        dataList.setAdapter(adapter);
        dataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Mmsic music= (Mmsic) parent.getAdapter().getItem(position);

                //更新底部
                //头像
                Glide.with(MusicActivity.this)
                        .load(music.getMusic_img())
                        .placeholder(R.mipmap.placeholder)
                        .error(R.mipmap.error)
                        .fallback(R.mipmap.ic_launcher)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(photo);
                songName.setText(music.getMusic_name());
                singerName.setText(music.getMusic_auth());
                //旋转
                ObjectAnimator objAnimatorRotate = ObjectAnimator.ofFloat(photo, "rotation", 0, 360);
                objAnimatorRotate.setDuration(10000);
                objAnimatorRotate.setRepeatMode(Animation.RESTART);
                objAnimatorRotate.setRepeatCount(Animation.INFINITE);
                objAnimatorRotate.start();
                if(musictxt!=null){
                    musictxt.setTextColor(Color.BLACK);
                    musictxt=null;
                }
                musictxt=((TextView)view.findViewById(R.id.music_name));
                musictxt.setTextColor(Color.argb(255,221,175,175));
                //播放
                if(mediaPlayer!=null){
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    mediaPlayer=null;
                }
                try {
                    mediaPlayer = new MediaPlayer();
                    Uri uri = Uri.parse(music.getMusic_url());
                     try {
                        mediaPlayer.setDataSource(MusicActivity.this,uri);
                    } catch (IOException e) {
                        Log.i("11",e.getMessage());
                    }
                    mediaPlayer.prepare();
                    mediaPlayer.start();

                } catch (IOException e) {
                    Log.i("111",e.getMessage());
                }

            }
        });
        //请求资源
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        FormBody body = builder.build();
        final Request request = new Request.Builder().post(body)
                .url(MyPathUrl.MyURL+"music.action").build();
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
                    //返回音乐的json串
                    Log.i("json串",jsonString);
                    Gson gson = new GsonBuilder()
                            .serializeNulls()
                            .setPrettyPrinting()
                            .create();
                    musicList = gson.fromJson(jsonString, new TypeToken<List<Mmsic>>(){}.getType());
                    Message message = Message.obtain();
                    message.what = 32;
                    handler.sendMessage(message);
                }
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            //按键keyCode是否是返回键，如果是的话
            if(mediaPlayer!=null){
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer=null;
            }
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private void getViews() {
        dataList=findViewById(R.id.musicList);
        photo=findViewById(R.id.photo);
        songName=findViewById(R.id.songName);
        singerName=findViewById(R.id.singerName);
    }
}
