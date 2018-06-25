package com.example.worrygroceryshop.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.worrygroceryshop.R;
import com.example.worrygroceryshop.bean.User;
//import com.example.worrygroceryshop.common.GlideApp;
import com.example.worrygroceryshop.common.MyPathUrl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.util.NetUtils;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private ImageView back;
    private CircleImageView header;
    private EditText edPhone;
    private EditText edPwd;
    private CheckBox rememberPwd;
    private TextView forgetPwd;
    private Button login;
    private String data;
    private SharedPreferences preferences;
    private User user;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 10:
                    Toast.makeText(LoginActivity.this,"连接失败",Toast.LENGTH_SHORT).show();
                    break;
                case 11:
                    Toast.makeText(LoginActivity.this,"登录失败，用户名和密码不合，请确认",Toast.LENGTH_SHORT).show();
                    break;
                case 12:
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                    preferences=getSharedPreferences("userData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("userJson",data);
                    editor.commit();
                    Intent intent=new Intent(LoginActivity.this,IndexActivity.class);
                    intent.putExtra("go",4);
                    startActivity(intent);
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getView();
        setListener();
    }

    private void setListener() {
        clickListener listener=new clickListener();
        OnFocusListener listener1=new OnFocusListener();
        back.setOnClickListener(listener);
        edPhone.setOnFocusChangeListener(listener1);
        login.setOnClickListener(listener);
    }

    private void getView() {
        back=findViewById(R.id.back);
        header=findViewById(R.id.user_image);
        edPhone=findViewById(R.id.user_phone);
        edPwd=findViewById(R.id.user_pwd);
        rememberPwd=findViewById(R.id.checkBox);
        forgetPwd=findViewById(R.id.forget);
        login=findViewById(R.id.login);
    }
    class clickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.back:
                    Intent i=new Intent(LoginActivity.this,IndexActivity.class);
                    setResult(1,i);
                    finish();
                    break;
                case R.id.login:
                    String mobilePhone=edPhone.getText().toString().trim();
                    String pwd=edPwd.getText().toString().trim();
                    if(!mobilePhone.equals("")){
                        if(!pwd.equals("")){
                            //登录
                            loginUser(mobilePhone,pwd);
                        }else{
                            Toast.makeText(LoginActivity.this,"输入密码",Toast.LENGTH_SHORT).show();

                        }
                    }else{
                        Toast.makeText(LoginActivity.this,"手机号码输入完整",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }
    class OnFocusListener implements View.OnFocusChangeListener{

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            switch (v.getId()){
                case R.id.user_phone:
//                    GlideApp.with(LoginActivity.this)
//                            .load(MyPathUrl.MyURL+"getHeader.action?user_phone="+edPhone.getText().toString().trim())
//                            .placeholder(R.mipmap.placeholder)
//                            .error(R.mipmap.error)
//                            .fallback(R.mipmap.ic_launcher_round)
//                            .centerCrop()
//                            .diskCacheStrategy(DiskCacheStrategy.ALL)
//                            .into(header);
                    Glide.with(LoginActivity.this)
                            .load(MyPathUrl.MyURL+"getHeader.action?user_phone="+edPhone.getText().toString().trim())
                            .placeholder(R.mipmap.placeholder)
                            .error(R.mipmap.error)
                            .fallback(R.mipmap.ic_launcher_round)
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(header);
                    break;
            }
        }
    }
    public void loginUser(String phone,String pwd){
        //上传数据到服务器进行验证
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("user_phone",phone);
        builder.add("user_pwd",pwd);
        FormBody body = builder.build();
        final Request request = new Request.Builder().post(body)
                .url(MyPathUrl.MyURL+"loginUser.action").build();
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
                data=response.body().string();
                if(data!=null&&!data.equals("")&&!data.equals("failure")){
                    //登录成功，返回user json串
                    Log.i("json串",data);
                    Gson gson = new GsonBuilder()
                            .serializeNulls()
                            .setPrettyPrinting()
                            .create();
                    user = gson.fromJson(data, User.class);
                    Log.i("userphone",user.getUser_phone());
                    if(user!=null){
                        //登录环信
                        EMClient.getInstance().login(user.getUser_name(), user.getUser_pwd(), new EMCallBack() {
                            @Override
                            public void onSuccess() {
                                Log.i("im","环信登录成功");

                            }

                            @Override
                            public void onError(int i, String s) {
                                Log.i("im","登录失败"+i+","+s);
                            }

                            @Override
                            public void onProgress(int i, String s) {

                            }
                        });
                        //
                        Message message = Message.obtain();
                        message.what = 12;
                        handler.sendMessage(message);
                    }else{
                        Log.i("user信息","user为空");
                    }

                }else{
                    Log.i("app","登录失败，用户名或者密码错误");
                    Message message = Message.obtain();
                    message.what = 11;
                    handler.sendMessage(message);
                }
            }
        });


    }
    /**
     *申请权限的回调
     */
    @Override
    public void onRequestPermissionsResult(
            int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //获取了权限后需要处理的逻辑
                    EMClient.getInstance().login(user.getUser_name(), user.getUser_pwd(), new EMCallBack() {
                        @Override
                        public void onSuccess() {
                            Log.i("im","环信登录成功");
                        }

                        @Override
                        public void onError(int i, String s) {
                            Log.i("im","登录失败"+i+","+s);
                        }

                        @Override
                        public void onProgress(int i, String s) {

                        }
                    });
                } else {
                    Toast.makeText(this, "你拒绝了这个权限", Toast.LENGTH_SHORT).show();
                } break;
        }
    }
    //实现ConnectionListener接口
    public class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
        }
        @Override
        public void onDisconnected(final int error) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(error == EMError.USER_REMOVED){
                        // 显示帐号已经被移除

                    }else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                        // 显示帐号在其他设备登录
                    } else {
                        if (NetUtils.hasNetwork(LoginActivity.this)){
                            //连接不到聊天服务器
                        }else{
                            //当前网络不可用，请检查网络设置
                        }

                    }
                }
            });
        }
    }
}
