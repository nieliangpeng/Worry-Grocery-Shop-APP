package com.example.worrygroceryshop.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.worrygroceryshop.R;
import com.example.worrygroceryshop.activity.JokActivity;
import com.example.worrygroceryshop.activity.VideoActivity;
import com.example.worrygroceryshop.activity.WordActivity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by 夏天 on 2018/5/17.
 */

public class FindFragment extends Fragment implements View.OnClickListener{
    private Context context;
    // 中心按钮
    private static ImageButton button;

    // 四个子按钮
    private static ImageButton button1;
    private static ImageButton button2;
    private static ImageButton button3;
    private static ImageButton button4;

    // 子按钮列表
    private static List<ImageButton> buttonItems = new ArrayList<ImageButton>(3);

    // 标识当前按钮弹出与否，1代表已经未弹出，-1代表已弹出
    private static int flag = 1;
    private static int r=350;
    private static View layout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if(layout==null){
            layout = inflater.inflate(R.layout.fragment_find, container,false);
            // 实例化按钮并设立监听
            button  = layout.findViewById(R.id.button);
            button.setOnClickListener(this);
            button1 = layout.findViewById(R.id.button1);
            button1.setOnClickListener(this);
            button2 = layout.findViewById(R.id.button2);
            button2.setOnClickListener(this);
            button3 = layout.findViewById(R.id.button3);
            button4 =layout.findViewById(R.id.button4);
            button4.setOnClickListener(this);
            // 将子按钮们加入列表中
            buttonItems.add(button1);
            buttonItems.add(button2);
            buttonItems.add(button3);
            buttonItems.add(button4);
        }

        //绑定监听器
        return layout;

    }
    /**
     * 按钮移动动画
     * @params 子按钮列表
     * @params 弹出时圆形半径radius
     */
    public void buttonAnimation(List<ImageButton> buttonList,int radius){

        for(int i=0;i<buttonList.size();i++){

            ObjectAnimator objAnimatorX;
            ObjectAnimator objAnimatorY;
            ObjectAnimator objAnimatorRotate;

            // 将按钮设为可见
            buttonList.get(i).setVisibility(View.VISIBLE);

            // 按钮在X、Y方向的移动距离
            float distanceX = (float) (flag*radius*(Math.cos(Util.getAngle(buttonList.size(),i))));
            float distanceY = -(float) (flag*radius*(Math.sin(Util.getAngle(buttonList.size(),i))));

            // X方向移动
            Log.i("x",buttonList.get(i).getX()+"");
            objAnimatorX = ObjectAnimator.ofFloat(buttonList.get(i), "x", buttonList.get(i).getX(),buttonList.get(i).getX()+distanceX);
            objAnimatorX.setDuration(200);
            objAnimatorX.setStartDelay(100);
            objAnimatorX.start();

            // Y方向移动
            Log.i("Y",buttonList.get(i).getY()+"");
            objAnimatorY = ObjectAnimator.ofFloat(buttonList.get(i), "y", buttonList.get(i).getY(),buttonList.get(i).getY()+distanceY);
            objAnimatorY.setDuration(200);
            objAnimatorY.setStartDelay(100);
            objAnimatorY.start();

            // 按钮旋转
            objAnimatorRotate = ObjectAnimator.ofFloat(buttonList.get(i), "rotation", 0, 360);
            objAnimatorRotate.setDuration(200);
            objAnimatorY.setStartDelay(100);
            objAnimatorRotate.start();

            if(flag==-1){
                objAnimatorX.addListener(new Animator.AnimatorListener() {

                    @Override
                    public void onAnimationStart(Animator animation) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // TODO Auto-generated method stub
                        // 将按钮设为可见
                        for (int i = 0; i < buttonItems.size(); i++) {
                            buttonItems.get(i).setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        // TODO Auto-generated method stub
                    }
                });
            }

        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                ObjectAnimator objAnimatorRotate = ObjectAnimator.ofFloat(button, "rotation", 0, 360);

                objAnimatorRotate.start();
                if(flag==1){
                    buttonAnimation(buttonItems,r);
                    flag=-1;
                }else{
                    buttonAnimation(buttonItems,r);
                    flag=1;
                }
                break;
            case R.id.button2:
                //跳转到笑话页面
                Intent intent=new Intent(context, JokActivity.class);
                startActivity(intent);
                break;
            case R.id.button4:
                Intent intent1=new Intent(context,VideoActivity.class);
                startActivity(intent1);
                break;
            case R.id.button1:
                Intent intent2=new Intent(context,WordActivity.class);
                startActivity(intent2);
                break;
        }



    }

    @Override
    public void onAttach(Context context) {
        this.context=context;
        super.onAttach(context);
    }

}
