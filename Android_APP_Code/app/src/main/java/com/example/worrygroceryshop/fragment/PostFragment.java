package com.example.worrygroceryshop.fragment;

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
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.worrygroceryshop.R;
import com.example.worrygroceryshop.activity.IndexActivity;
import com.example.worrygroceryshop.activity.MyInvitationActivity;
import com.example.worrygroceryshop.activity.VideoActivity;
import com.example.worrygroceryshop.bean.User;
import com.example.worrygroceryshop.common.MyPathUrl;
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

/**
 * Created by 夏天 on 2018/5/17.
 */

public class PostFragment extends Fragment {
    private SharedPreferences preferences;
    private Context context;
    private ImageButton photo;//选择图片
    private TextView send;//发送
    private ImageView img;
    private EditText content;
    private static final int IMAGE = 8888;
    String imagePath;//头像路径
    private Bitmap photoContext=null;
    private User user;
    private String data;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 10:
                    Toast.makeText(getActivity(),"连接失败",Toast.LENGTH_SHORT).show();
                    break;
                case 11:
                    Toast.makeText(getActivity(),"发布成功",Toast.LENGTH_SHORT).show();
                    //更新用户信息
                    preferences=getActivity().getSharedPreferences("userData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("userJson",data);
                    editor.commit();
                    //
                    content.setText("");
                    img.setImageBitmap(null);
                    //跳转到自己的帖子页面
                    startActivity(new Intent(context, MyInvitationActivity.class));
                    break;
                case 12:
                    Toast.makeText(getActivity(),"发布失败",Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View layout =null;
        preferences=context.getSharedPreferences("userData", Context.MODE_PRIVATE);
        String userJson=preferences.getString("userJson","不存在");
        if(userJson.equals("不存在")) {
            //没有登录的操作，跳转到4
            Intent intent=new Intent(context, IndexActivity.class);
            intent.putExtra("go",4);
            startActivity(intent);
        }else{
            layout = inflater.inflate(R.layout.fragment_post, container, false);
            getUser();
            getViews(layout);
            setListener();
        }


        //绑定监听器
        return layout;

    }

    private void setListener() {
        ClickListener listener=new ClickListener();
        photo.setOnClickListener(listener);
        send.setOnClickListener(listener);
    }

    private void getViews(View layout) {
        photo=layout.findViewById(R.id.photo);
        send=layout.findViewById(R.id.send);
        img=layout.findViewById(R.id.img);
        content=layout.findViewById(R.id.content);
    }

    @Override
    public void onAttach(Context context) {
        this.context=context;
        super.onAttach(context);
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
                        Toast.makeText(context, "图片不存在", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    RequestBody muiltipartBody = new MultipartBody.Builder()
                            //一定要设置这句
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("type_id",5+"")
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
            Cursor c = context.getContentResolver().query(selectedImage, filePathColumns, null, null, null);
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
        preferences=getActivity().getSharedPreferences("userData", Context.MODE_PRIVATE);
        String userJson=preferences.getString("userJson","不存在");
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setPrettyPrinting()
                .create();
        user = gson.fromJson(userJson, User.class);
    }
}
