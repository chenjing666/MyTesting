package com.biaoke.bklive.activity;

import android.hardware.Camera;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.biaoke.bklive.R;
import com.biaoke.bklive.base.BaseActivity;
import com.biaoke.bklive.message.AppConsts;
import com.qiniu.pili.droid.streaming.AVCodecType;
import com.qiniu.pili.droid.streaming.CameraStreamingSetting;
import com.qiniu.pili.droid.streaming.MediaStreamingManager;
import com.qiniu.pili.droid.streaming.StreamingProfile;
import com.qiniu.pili.droid.streaming.StreamingState;
import com.qiniu.pili.droid.streaming.StreamingStateChangedListener;
import com.qiniu.pili.droid.streaming.widget.AspectFrameLayout;

import java.net.URISyntaxException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SWCameraStreamingActivity extends BaseActivity implements StreamingStateChangedListener {
    @BindView(R.id.start)
    Button start;
    private MediaStreamingManager mMediaStreamingManager;
    private String liveUrl = null;
    private StreamingProfile mProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swcamera_streaming);
        ButterKnife.bind(this);
        liveUrl = getIntent().getStringExtra("liveUrl");
        Log.e("--------liveUrl--------", liveUrl + "");
        initLive();
//        AspectFrameLayout afl = (AspectFrameLayout) findViewById(R.id.cameraPreview_afl);
//        // Decide FULL screen or real size
//        afl.setShowMode(AspectFrameLayout.SHOW_MODE.REAL);
//        GLSurfaceView glSurfaceView = (GLSurfaceView) findViewById(R.id.cameraPreview_surfaceView);
//        StreamingProfile.AudioProfile aProfile = new StreamingProfile.AudioProfile(44100, 66 * 1024);
//        StreamingProfile.VideoProfile vProfile = new StreamingProfile.VideoProfile(20, 500 * 1024, 32);
//        StreamingProfile.AVProfile avProfile = new StreamingProfile.AVProfile(vProfile, aProfile);
//        mProfile = new StreamingProfile();
//        try {
//            mProfile.setPublishUrl(liveUrl)
//                    .setVideoQuality(StreamingProfile.VIDEO_QUALITY_HIGH1)
//                    .setAudioQuality(StreamingProfile.AUDIO_QUALITY_MEDIUM2)
//                    .setEncodingSizeLevel(StreamingProfile.VIDEO_ENCODING_HEIGHT_480)
//                    .setEncoderRCMode(StreamingProfile.EncoderRCModes.QUALITY_PRIORITY)
//                    .setAVProfile(avProfile);
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        CameraStreamingSetting setting = new CameraStreamingSetting();
//        setting.setCameraId(Camera.CameraInfo.CAMERA_FACING_FRONT)//摄像头前置
//                .setContinuousFocusModeEnabled(true)//自动对焦
//                .setFaceBeautySetting(new CameraStreamingSetting.FaceBeautySetting(1.0f, 1.0f, 0.8f))// FaceBeautySetting 中的参数依次为：beautyLevel，whiten，redden，
//                .setVideoFilter(CameraStreamingSetting.VIDEO_FILTER_TYPE.VIDEO_FILTER_BEAUTY)//即磨皮程度、美白程度以及红润程度，取值范围为[0.0f, 1.0f]
//                .setCameraPrvSizeLevel(CameraStreamingSetting.PREVIEW_SIZE_LEVEL.MEDIUM)
//                .setCameraPrvSizeRatio(CameraStreamingSetting.PREVIEW_SIZE_RATIO.RATIO_16_9);
//        mMediaStreamingManager = new MediaStreamingManager(this, afl, glSurfaceView, AVCodecType.SW_VIDEO_WITH_SW_AUDIO_CODEC);  // soft codec
//        mMediaStreamingManager.prepare(setting, mProfile);
//        mMediaStreamingManager.setStreamingStateListener(this);

    }


    @Override
    protected String getPowerBarColors() {
        return AppConsts.POWER_BAR_WHITE;
    }

    public void initLive() {
        AspectFrameLayout afl = (AspectFrameLayout) findViewById(R.id.cameraPreview_afl);
        // Decide FULL screen or real size
        afl.setShowMode(AspectFrameLayout.SHOW_MODE.REAL);
        GLSurfaceView glSurfaceView = (GLSurfaceView) findViewById(R.id.cameraPreview_surfaceView);
        StreamingProfile.AudioProfile aProfile = new StreamingProfile.AudioProfile(44100, 66 * 1024);
        StreamingProfile.VideoProfile vProfile = new StreamingProfile.VideoProfile(20, 500 * 1024, 32);
        StreamingProfile.AVProfile avProfile = new StreamingProfile.AVProfile(vProfile, aProfile);
        mProfile = new StreamingProfile();
        try {
            mProfile.setPublishUrl(liveUrl)
                    .setVideoQuality(StreamingProfile.VIDEO_QUALITY_HIGH1)
                    .setAudioQuality(StreamingProfile.AUDIO_QUALITY_MEDIUM2)
                    .setEncodingSizeLevel(StreamingProfile.VIDEO_ENCODING_HEIGHT_480)
                    .setEncoderRCMode(StreamingProfile.EncoderRCModes.QUALITY_PRIORITY)
                    .setAVProfile(avProfile);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        CameraStreamingSetting setting = new CameraStreamingSetting();
        setting.setCameraId(Camera.CameraInfo.CAMERA_FACING_FRONT)//摄像头前置
                .setContinuousFocusModeEnabled(true)//自动对焦
                .setFaceBeautySetting(new CameraStreamingSetting.FaceBeautySetting(1.0f, 1.0f, 0.8f))// FaceBeautySetting 中的参数依次为：beautyLevel，whiten，redden，
                .setVideoFilter(CameraStreamingSetting.VIDEO_FILTER_TYPE.VIDEO_FILTER_BEAUTY)//即磨皮程度、美白程度以及红润程度，取值范围为[0.0f, 1.0f]
                .setCameraPrvSizeLevel(CameraStreamingSetting.PREVIEW_SIZE_LEVEL.MEDIUM)
                .setCameraPrvSizeRatio(CameraStreamingSetting.PREVIEW_SIZE_RATIO.RATIO_16_9);
        mMediaStreamingManager = new MediaStreamingManager(this, afl, glSurfaceView, AVCodecType.SW_VIDEO_WITH_SW_AUDIO_CODEC);  // soft codec
        mMediaStreamingManager.prepare(setting, mProfile);
        mMediaStreamingManager.setStreamingStateListener(this);
    }

    @Override
    public void onStateChanged(StreamingState streamingState, Object o) {
        switch (streamingState) {
            case PREPARING:
                break;
            case READY:
                // start streaming when READY
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (mMediaStreamingManager != null) {
                            mMediaStreamingManager.startStreaming();
                            Log.d("startStreaming---------", "startStreaming-------");
                        }
                    }
                }).start();
                break;
            case CONNECTING:
                break;
            case STREAMING:
                // The av packet had been sent.
                break;
            case SHUTDOWN:
                // The streaming had been finished.
                break;
            case IOERROR:
                // Network connect error.
                break;
            case OPEN_CAMERA_FAIL:
                // Failed to open camera.
                break;
            case DISCONNECTED:
                // The socket is broken while streaming
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMediaStreamingManager != null) {
            mMediaStreamingManager.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // You must invoke pause here.
        if (mMediaStreamingManager != null) {
            mMediaStreamingManager.pause();
        }

    }

    @OnClick(R.id.start)
    public void onClick() {
        Toast.makeText(this,"哈哈哈哈",Toast.LENGTH_SHORT).show();
    }
}
