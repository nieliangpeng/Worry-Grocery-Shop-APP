package com.example.worrygroceryshop.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.worrygroceryshop.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.show.api.ShowApiRequest;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
public class JokActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    private  SharedPreferences.Editor editor;
    private TextView txt;
    private Button btn;
    protected Handler mHandler =  new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jok);
        preferences=getSharedPreferences("pagenum", Context.MODE_PRIVATE);
        String num=preferences.getString("num",1+"");
        editor=preferences.edit();
        editor.putString("num",num+"");
        editor.commit();
        txt=findViewById(R.id.txt);
        btn=findViewById(R.id.btn);
        //显示一条先
        new Thread(){
            //在新线程中发送网络请求
            public void run() {
                String appid="66467";//要替换成自己的
                String secret="0f5047e37ffd4a4b8867d1d0e4321e51";//要替换成自己的
                final String res=new ShowApiRequest( "http://route.showapi.com/341-1", appid, secret)
                        .addTextPara("page", preferences.getString("num","1"))
                        .addTextPara("maxResult", "1")
                        .post();
                final com.alibaba.fastjson.JSONObject jsonObject= com.alibaba.fastjson.JSONObject.parseObject(res);
                System.out.println(res);
                //把返回内容通过handler对象更新到界面
                mHandler.post(new Thread(){
                    public void run() {
                        Map map=new HashMap();
                        map=(Map)(jsonObject.getJSONObject("showapi_res_body").getJSONArray("contentlist").get(0));
                        String txt1=map.get("text")+"";
                        String s=txt1.replaceAll("<br />","");
                        s=s.replaceAll("<br>","");
                        txt.setText(s+"");
                    }
                });

            }
        }.start();
        //点击显示下一条
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new Thread(){
                    //在新线程中发送网络请求
                    public void run() {
                        String n=preferences.getString("num","1");
                        int num1=Integer.parseInt(n)+1;
                        editor.putString("num",num1+"");
                        editor.commit();
                        String appid="66467";//要替换成自己的
                        String secret="0f5047e37ffd4a4b8867d1d0e4321e51";//要替换成自己的
                        final String res=new ShowApiRequest( "http://route.showapi.com/341-1", appid, secret)
                                .addTextPara("page", preferences.getString("num","1"))
                                .addTextPara("maxResult", "1")
                                .post();
                        final com.alibaba.fastjson.JSONObject jsonObject= com.alibaba.fastjson.JSONObject.parseObject(res);
                        System.out.println(res);
                        //把返回内容通过handler对象更新到界面
                        mHandler.post(new Thread(){
                            public void run() {
                                Map map=new HashMap();
                                map=(Map)(jsonObject.getJSONObject("showapi_res_body").getJSONArray("contentlist").get(0));
                                String txt1=map.get("text")+"";
                                String s=txt1.replaceAll("<br />","");
                                s=s.replaceAll("<br>","");
                                txt.setText(s+"");
                            }
                        });
                    }
                }.start();


            }
        });
    }

}
