package com.biaoke.bklive;

import android.app.Application;

import com.qiniu.pili.droid.streaming.StreamingEnv;

/**
 * Created by hasee on 2017/3/30.
 */

public class AppLaunch extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //参数初始化化。
        StreamingEnv.init(getApplicationContext());
    }
}
