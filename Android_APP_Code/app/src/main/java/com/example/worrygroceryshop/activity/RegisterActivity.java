package com.example.worrygroceryshop.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.worrygroceryshop.R;
import com.example.worrygroceryshop.common.MyPathUrl;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.io.File;
import java.io.IOException;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {
    private CircleImageView imageView;
    private EditText user_name;
    private EditText user_pwd;
    private EditText user_repwd;
    private EditText user_phone;
    private EditText validateCode;
    private Button getValideteCode;
    private Button register;
    private ImageView back;
    private static final int IMAGE = 8888;
    private Bitmap photo=null;
    String imagePath;//头像路径
    String name;
    String pwd;
    String repwd;
    String phone1;
    String code;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                   // Toast.makeText(RegisterActivity.this,"获取验证码请求发送成功",Toast.LENGTH_SHORT).show();

                    break;
                case 2:
                    Toast.makeText(RegisterActivity.this,"获取验证码请求发送失败",Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    //验证成功，进行注册
                    new Thread(){
                        @Override
                        public void run() {
                            registerUser(imagePath,name,pwd, phone1);
//                            Log.i("app","完成");
                        }
                    }.start();

                    break;
                case 4:
                    //验证失败
                    Toast.makeText(RegisterActivity.this,"验证失败",Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_SHORT).show();;
                    break;
                case 6:
                    Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                    new Thread(){
                        @Override
                        public void run() {
                            try {
                                EMClient.getInstance().createAccount(name,pwd);
                                Log.i("im","环信注册成功");
                            } catch (HyphenateException e) {
                                e.printStackTrace();
                                Log.i("im","环信注册失败"+e.getErrorCode()+","+e.getMessage());
                            }

                        }
                    }.start();
                    //跳转到登录页面
                    Intent i=new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(i);
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getView();
        setListener();


    }

    private void setListener() {
        ClickListener listener=new ClickListener();
        imageView.setOnClickListener(listener);
        getValideteCode.setOnClickListener(listener);
        register.setOnClickListener(listener);
        back.setOnClickListener(listener);
    }

    public void getView() {
        imageView=findViewById(R.id.user_image);
        user_name=findViewById(R.id.user_name);
        user_pwd=findViewById(R.id.user_pwd);
        user_repwd=findViewById(R.id.user_repwd);
        user_phone=findViewById(R.id.user_phone);
        validateCode=findViewById(R.id.validateCode);
        getValideteCode=findViewById(R.id.getValidateCode);
        register=findViewById(R.id.register);
        back=findViewById(R.id.back);
    }
    class ClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.user_image:
                    Intent imageIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(imageIntent, IMAGE);
                    break;
                case R.id.getValidateCode:
                    String phone=user_phone.getText().toString().trim();
                    if(!phone.equals("")){
                        if(phone.length()==11){
                            sendCode("86", phone);
                        }else{
                            Toast.makeText(RegisterActivity.this,"请输入有效的手机号码",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(RegisterActivity.this,"请输入手机号码",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.register:
                    Bitmap image=photo;
                    name=user_name.getText().toString().trim();
                    pwd=user_pwd.getText().toString().trim();
                    repwd=user_repwd.getText().toString().trim();
                    phone1=user_phone.getText().toString().trim();
                    code=validateCode.getText().toString().trim();
                    if(image==null){
                        Toast.makeText(RegisterActivity.this,"请点击头像框选择一个头像",Toast.LENGTH_SHORT).show();
                    }else{
                        if(!name.equals("")){
                            if(!pwd.equals("")){
                                if(pwd.equals(repwd)){
                                    if(!phone1.equals("")){
                                        if(phone1.length()==11){
//                                            if(!code.equals("")){
//                                                submitCode("86",phone1,code);
//                                            }else{
//                                                Toast.makeText(RegisterActivity.this,"请输入手机验证码",Toast.LENGTH_SHORT).show();
//
//                                            }
                                            new Thread(){
                                                @Override
                                                public void run() {
                                                    registerUser(imagePath,name,pwd, phone1);
//                                                  Log.i("app","完成");
                                                }
                                            }.start();
                                        }else{
                                            Toast.makeText(RegisterActivity.this,"请输入有效的手机号码",Toast.LENGTH_SHORT).show();
                                        }
                                    }else{
                                        Toast.makeText(RegisterActivity.this,"请输入手机号码",Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(RegisterActivity.this,"两次密码输入不一致，请重新输入",Toast.LENGTH_SHORT).show();
                                    user_repwd.setText("");
                                }

                            }else{
                                Toast.makeText(RegisterActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();

                            }
                        }else{
                            Toast.makeText(RegisterActivity.this,"请输入昵称",Toast.LENGTH_SHORT).show();

                        }
                    }


                    break;
                case R.id.back:
                    Log.i("app","onclickback");
                    Intent i=new Intent(RegisterActivity.this,IndexActivity.class);
                    setResult(1,i);
                    finish();
                    break;

            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //需要有读取手机存储的权限
        if(requestCode==IMAGE && resultCode == Activity.RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            if( c.moveToFirst()){
                int columnIndex = c.getColumnIndex(filePathColumns[0]);
                imagePath = c.getString(columnIndex);
                //Toast.makeText(this,imagePath,Toast.LENGTH_SHORT).show();
                photo= BitmapFactory.decodeFile(imagePath);
                imageView.setImageBitmap(photo);
            }

        }
    }
    // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
    public void sendCode(String country, String phone) {
        // 注册一个事件回调，用于处理发送验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                Message message = Message.obtain();
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理成功得到验证码的结果
                    // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                    Log.i("app","获取验证码成功");


                    message.what = 1;


                } else{
                    // TODO 处理错误的结果
                    ((Throwable)data).printStackTrace();
                    String str = data.toString();

                      Log.i("app","获取验证码失败"+str);
                    message.what = 2;
                }
                handler.sendMessage(message);
            }
        });
        // 触发操作
        Log.i("app","触发操作"+phone);
        SMSSDK.getVerificationCode(country,phone);
    }

    // 提交验证码，其中的code表示验证码，如“1357”
    public void submitCode(String country, String phone, String code) {
        // 注册一个事件回调，用于处理提交验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            Message message = Message.obtain();
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理验证成功的结果
                    Log.i("app","验证成功");
                    message.what = 3;


                } else{
                    // TODO 处理错误的结果
                    Log.i("app","验证失败");
                    message.what = 4;
                }
                handler.sendMessage(message);
            }
        });
        // 触发操作
        SMSSDK.submitVerificationCode(country, phone, code);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //用完回调要注销掉，否则可能会出现内存泄露
        SMSSDK.unregisterAllEventHandler();
    }
    //注册方法
    public void registerUser(String path,String name1,String pwd1,String mobilePhone){
        Log.i("mes","11111111111111");
        OkHttpClient okHttpClient = new OkHttpClient();
        File file = new File(path);
        if (!file.exists()){
            Toast.makeText(this, "头像不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.i("mes","22222222222222");
        RequestBody muiltipartBody = new MultipartBody.Builder()
                //一定要设置这句
                .setType(MultipartBody.FORM)
                .addFormDataPart("user_name", name1)
                .addFormDataPart("user_pwd",pwd1)
                .addFormDataPart("user_phone",mobilePhone)
                .addFormDataPart("user_image",file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file))
                .build();
        Request.Builder builder = new Request.Builder();
        Request request=builder.url(MyPathUrl.MyURL+"saveUser1.action").post(muiltipartBody).build();
        final Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Log.i("app","连接失败");
                Log.i("错误信息",e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("app","连接成功");
                String state=response.body().string();
                Log.i("app","state="+state);
                if(state.equals("success")){
                    Message message = Message.obtain();
                    message.what = 6;
                    handler.sendMessage(message);

                }else{
                    Message message = Message.obtain();
                    message.what = 5;
                    handler.sendMessage(message);


                }

            }
        });
    }
}
