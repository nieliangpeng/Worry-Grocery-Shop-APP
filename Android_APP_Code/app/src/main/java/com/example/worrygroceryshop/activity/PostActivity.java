package com.example.worrygroceryshop.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.worrygroceryshop.R;
import com.example.worrygroceryshop.bean.User;
import com.example.worrygroceryshop.common.MyPathUrl;
import com.example.worrygroceryshop.fragment.PostFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PostActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    private ImageButton photo;//选择图片
    private TextView send;//发送
    private ImageView img;
    private EditText content;
    private TextView typename;
    private static final int IMAGE = 8888;
    String imagePath;//头像路径
    private Bitmap photoContext=null;
    private User user;
    private String data;
    private int type_id;
    private String type_name;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 10:
                    Toast.makeText(PostActivity.this,"连接失败",Toast.LENGTH_SHORT).show();
                    break;
                case 11:
                    Toast.makeText(PostActivity.this,"发布成功",Toast.LENGTH_SHORT).show();
                    //更新用户信息
                    preferences=getSharedPreferences("userData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("userJson",data);
                    editor.commit();
                    //
                    content.setText("");
                    img.setImageBitmap(null);
                    //跳转到自己的帖子页面
                    startActivity(new Intent(PostActivity.this, MyInvitationActivity.class));
                    break;
                case 12:
                    Toast.makeText(PostActivity.this,"发布失败",Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences=getSharedPreferences("userData", Context.MODE_PRIVATE);
        String userJson=preferences.getString("userJson","不存在");
        if(userJson.equals("不存在")) {
            //没有登录的操作，跳转到4
            Intent intent=new Intent(this, IndexActivity.class);
            intent.putExtra("go",4);
            startActivity(intent);
        }else{
            setContentView(R.layout.activity_post);
            Intent intent=getIntent();

            type_id=intent.getIntExtra("type_id",5);
            type_name=intent.getStringExtra("type_name");

            getUser();
            getViews();
            setListener();
        }
    }
    private void setListener() {
        ClickListener listener=new ClickListener();
        photo.setOnClickListener(listener);
        send.setOnClickListener(listener);
    }

    private void getViews() {
        photo=findViewById(R.id.photo);
        send=findViewById(R.id.send);
        img=findViewById(R.id.img);
        content=findViewById(R.id.content);
        typename=findViewById(R.id.typename);
        typename.setText(type_name);
    }
    class ClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.photo:
                    //选取头像
                    Intent imageIntent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(imageIntent, IMAGE);
                    break;
                case R.id.send:
                    String txt=content.getText().toString().trim();//内容
                    OkHttpClient okHttpClient = new OkHttpClient();
                    File file = new File(imagePath);
                    if (!file.exists()){
                        Toast.makeText(PostActivity.this, "图片不存在", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    RequestBody muiltipartBody = new MultipartBody.Builder()
                            //一定要设置这句
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("type_id",type_id+"")
                            .addFormDataPart("user_id",user.getUser_id()+"")
                            .addFormDataPart("Ivtn_content", txt)
                            .addFormDataPart("file",file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file))
                            .build();
                    Request.Builder builder = new Request.Builder();
                    Request request=builder.url(MyPathUrl.MyURL+"invitation.action").post(muiltipartBody).build();
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
                            if(!data.equals("failure")){
                                Message message = Message.obtain();
                                message.what = 11;
                                handler.sendMessage(message);
                            }else{
                                Message message = Message.obtain();
                                message.what = 12;
                                handler.sendMessage(message);
                            }
                        }
                    });
                    break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //需要有读取手机存储的权限
        if(requestCode==IMAGE && resultCode == Activity.RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            if(c.moveToFirst()){
                int columnIndex = c.getColumnIndex(filePathColumns[0]);
                imagePath = c.getString(columnIndex);
                //Toast.makeText(this,imagePath,Toast.LENGTH_SHORT).show();
                photoContext= BitmapFactory.decodeFile(imagePath);
                img.setImageBitmap(photoContext);
            }

        }
    }
    private void getUser() {
        preferences=getSharedPreferences("userData", Context.MODE_PRIVATE);
        String userJson=preferences.getString("userJson","不存在");
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setPrettyPrinting()
                .create();
        user = gson.fromJson(userJson, User.class);
    }
}
