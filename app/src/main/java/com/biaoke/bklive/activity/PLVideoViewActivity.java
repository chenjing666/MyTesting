package com.biaoke.bklive.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.biaoke.bklive.R;
import com.biaoke.bklive.base.BaseActivity;
import com.biaoke.bklive.message.AppConsts;
import com.pili.pldroid.player.widget.PLVideoView;

public class PLVideoViewActivity extends BaseActivity {
    private PLVideoView mVideoView;
    private Button btn_start, btn_pause, btn_stop;
    private String path = "http://pili-live-hls.bk5977.com/bk-test1/1174.m3u8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plvideo_view);
        mVideoView = (PLVideoView) findViewById(R.id.PLVideoView);
        mVideoView.setKeepScreenOn(true);//设置屏幕常亮
        mVideoView.requestFocus();//拿到焦点
        mVideoView.setVideoPath(path);
        mVideoView.start();
        btn_start = (Button) findViewById(R.id.start_video);
        btn_start.setOnClickListener(listen);
        btn_pause = (Button) findViewById(R.id.pause);
        btn_pause.setOnClickListener(listen);
        btn_stop = (Button) findViewById(R.id.stop);
        btn_stop.setOnClickListener(listen);
    }

    private View.OnClickListener listen = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.start_video:
                    mVideoView.start();
                    Toast.makeText(PLVideoViewActivity.this, "开始", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.pause:
                    mVideoView.pause();
                    Toast.makeText(PLVideoViewActivity.this, "暂停", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.stop:
                    mVideoView.stopPlayback();
                    finish();
                    Toast.makeText(PLVideoViewActivity.this, "结束", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        mVideoView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVideoView.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoView.stopPlayback();
    }

    @Override
    protected String getPowerBarColors() {
        return AppConsts.POWER_BAR_BACKGROUND;
    }
}
