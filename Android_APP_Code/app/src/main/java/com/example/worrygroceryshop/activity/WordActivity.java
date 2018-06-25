package com.example.worrygroceryshop.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.worrygroceryshop.R;
import com.example.worrygroceryshop.bean.Mmsic;
import com.example.worrygroceryshop.bean.Word;
import com.example.worrygroceryshop.common.MyPathUrl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WordActivity extends AppCompatActivity {
    private ImageView imageView;
    private Word word;
    private MediaPlayer mediaPlayer=null;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 10:
                    Toast.makeText(WordActivity.this,"连接失败",Toast.LENGTH_SHORT).show();
                    break;
                case 11:
                    //加载图片
                    Glide.with(WordActivity.this)
                            .load(word.getWord_imgResource())
                            .placeholder(R.mipmap.placeholder)
                            .error(R.mipmap.error)
                            .fallback(R.mipmap.ic_launcher)
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(imageView);
                    //播放音乐
                    mediaPlayer = new MediaPlayer();
                    try {
                        Uri uri = Uri.parse(word.getWord_musicResource());
                        mediaPlayer.setDataSource(WordActivity.this,uri);
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);
        imageView=findViewById(R.id.imageView);
        //请求后台，得到Word对象
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        FormBody body = builder.build();
        final Request request = new Request.Builder().post(body)
                .url(MyPathUrl.MyURL+"EverydayWord.action").build();
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
                    //返回每日一言对象json串
                    Log.i("json串",jsonString);
                    Gson gson = new GsonBuilder()
                            .serializeNulls()
                            .setPrettyPrinting()
                            .create();
                    word=gson.fromJson(jsonString,Word.class);
                    Message message = Message.obtain();
                    message.what = 11;
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
}
