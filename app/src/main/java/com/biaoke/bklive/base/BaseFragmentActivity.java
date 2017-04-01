package com.biaoke.bklive.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.biaoke.bklive.utils.PowerBarBg;


/**
 * Created by hasee on 2017/3/30.
 * 全部的FragmentActivity请继承该类
 */
public abstract class BaseFragmentActivity extends FragmentActivity {
    private Integer mColors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPowerBar(getPowerBarColors());
    }
    /**
     * 设置电量条颜色
     */
    private void setPowerBar(String color){
        mColors= Color.parseColor(color);
        if(mColors!=0){
            PowerBarBg.setPowerBarBg(this,mColors);
        }
    }
    // 初始化UI，setContentView
    protected abstract String getPowerBarColors();
}
