package com.biaoke.bklive.activity;

import android.os.Bundle;

import com.biaoke.bklive.R;
import com.biaoke.bklive.base.BaseActivity;
import com.biaoke.bklive.message.AppConsts;

public class LiveCameraActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_camera);
    }

    @Override
    protected String getPowerBarColors() {
        return AppConsts.POWER_BAR_WHITE;
    }
}
