package com.example.worrygroceryshop.fragment;

/**
 * Created by 夏天 on 2018/6/4.
 */

public class Util {
    /**
     * 返回每个按钮应该出现的角度(弧度单位)
     * @param index
     * @return double 角度(弧度单位)
     */
    public static double getAngle(int total,int index){

        return Math.toRadians(90/(total-1)*index+90);
    }
}
