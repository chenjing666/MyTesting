package com.biaoke.bklive;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.biaoke.bklive.activity.LiveCameraActivity;
import com.biaoke.bklive.activity.MessageActivity;
import com.biaoke.bklive.activity.SearchActivity;
import com.biaoke.bklive.activity.ShortVedioActivity;
import com.biaoke.bklive.base.BaseActivity;
import com.biaoke.bklive.bottombar.BottomBar;
import com.biaoke.bklive.fragment.FollowFragment;
import com.biaoke.bklive.fragment.FoundFragment;
import com.biaoke.bklive.fragment.GameFragment;
import com.biaoke.bklive.fragment.SameCityFragment;
import com.biaoke.bklive.message.Api;
import com.biaoke.bklive.message.AppConsts;
import com.biaoke.bklive.user.activity.EditUserActivity;
import com.biaoke.bklive.user.activity.LoginActivity;
import com.biaoke.bklive.user.activity.SetActivity;
import com.biaoke.bklive.user.bean.User;
import com.pkmmte.view.CircularImageView;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

public class MainActivity extends BaseActivity {
    @BindViews({R.id.tv_follow, R.id.tv_game, R.id.tv_found, R.id.tv_samecity})
    TextView[] textViews;
    @BindView(R.id.iv_message)
    ImageView ivMessage;
    @BindView(R.id.main_head)
    LinearLayout mainHead;
    @BindView(R.id.mine)
    TextView mine;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.viewpager_main)
    ViewPager viewpagerMain;
    @BindView(R.id.ll_main)
    LinearLayout llMain;
    @BindView(R.id.tv_diamond_send)
    TextView tvDiamondSend;
    @BindView(R.id.diamond_send)
    TextView diamondSend;
    @BindView(R.id.iv_user_head)
    CircularImageView ivUserHead;
    @BindView(R.id.btn_edit)
    ImageView btnEdit;
    @BindView(R.id.tv_userName)
    TextView tvUserName;
    @BindView(R.id.user_id)
    TextView userId;
    @BindView(R.id.tv_live_num)
    TextView tvLiveNum;
    @BindView(R.id.tv_follow_num)
    TextView tvFollowNum;
    @BindView(R.id.tv_fan_num)
    TextView tvFanNum;
    @BindView(R.id.bk_contribution)
    LinearLayout bkContribution;
    @BindView(R.id.bk_income)
    LinearLayout bkIncome;
    @BindView(R.id.bk_mydiamond)
    LinearLayout bkMydiamond;
    @BindView(R.id.bk_level)
    LinearLayout bkLevel;
    @BindView(R.id.bk_vedio)
    LinearLayout bkVedio;
    @BindView(R.id.bk_identification)
    LinearLayout bkIdentification;
    @BindView(R.id.bk_set)
    LinearLayout bkSet;
    @BindView(R.id.ll_user)
    LinearLayout llUser;
    @BindView(R.id.ll_bottom_bar)
    BottomBar llBottomBar;
    @BindView(R.id.live_putvideo)
    ImageView livePutvideo;
    private PopupWindow popupWindow_vedio, popupWindow_login;
    private ImageView imageView_qq;
    private String APPID = "1106047080";
    Tencent mTencent;
    private BaseUiListener mIUiListener;
    private UserInfo mInfo;
    private String openID = null;
    //点击2次返回，退出程序
    private boolean isExit = false;
    User user=new User();
    private String UserId;
    private String mNickName;
    private String mLevel;
    private String mExperience;
    private String mIncome;
    private String mDiamond;
    private String mLiveNum;
    private String mVideoNum;
    private String mHeadimageUrl;
    private String mSex;
    private String mAge;
    private String mEmotion;
    private String mHometown;
    private String mWork;
    private String mFollow;
    private String mFans;
    private String mSignture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mTencent = Tencent.createInstance(APPID, MainActivity.this.getApplication());
        BottomBar bottomBar = (BottomBar) findViewById(R.id.ll_bottom_bar);
        bottomBar.setOnItemChangedListener(new BottomBar.OnItemChangedListener() {

            @Override
            public void onItemChanged(int index) {
                if (index == 0) {
                    mainHead.setVisibility(View.VISIBLE);
                    llMain.setVisibility(View.VISIBLE);
                    mine.setVisibility(View.GONE);
                    llUser.setVisibility(View.GONE);
                } else if (index == 1) {
                    SharedPreferences sharedPreferences = getSharedPreferences("isLogin", Context.MODE_PRIVATE);
                    boolean isLogin =sharedPreferences.getBoolean("isLogin",false);
                    if (!isLogin) {
                        loginPopupWindow();
                        popupWindow_login.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 260, 260);
                    } else {
                        mainHead.setVisibility(View.GONE);
                        mine.setVisibility(View.VISIBLE);
                        llUser.setVisibility(View.VISIBLE);
                        llMain.setVisibility(View.GONE);
                        SharedPreferences sharedPreferences_user = getSharedPreferences("userId", Context.MODE_PRIVATE);
                        UserId=sharedPreferences_user.getString("userId","");//如果取不到值就取后面的""
                        Log.e(UserId+"主页面获取用户名:",UserId);
                        JSONObject jsonObject_user=new JSONObject();
                        try {
                            jsonObject_user.put("Protocol","UserInfo");
                            jsonObject_user.put("Cmd","1");
                            jsonObject_user.put("UserId",UserId);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        UserInfoHttp(Api.ENCRYPT64,jsonObject_user.toString());
                    }
                }

            }
        });
        bottomBar.setSelectedState(0);
        init();//主页面
    }

    //设置电量条颜色
    @Override
    protected String getPowerBarColors() {
        return AppConsts.POWER_BAR_BACKGROUND;
    }

    @OnClick({R.id.live_putvideo, R.id.iv_message, R.id.iv_search, R.id.btn_edit, R.id.bk_contribution, R.id.bk_income, R.id.bk_mydiamond, R.id.bk_level, R.id.bk_vedio, R.id.bk_identification, R.id.bk_set})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.live_putvideo:
                llBottomBar.setVisibility(View.GONE);
                showPopWindow();
                setBackgroundAlpha(0.4f,MainActivity.this);
                popupWindow_vedio.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.iv_message:
                Intent intent_message=new Intent(this, MessageActivity.class);
                startActivity(intent_message);
                break;
            case R.id.iv_search:
                Intent intent_search=new Intent(this, SearchActivity.class);
                startActivity(intent_search);
                break;
            case R.id.btn_edit:
                Intent intent_edit = new Intent(this, EditUserActivity.class);
                startActivity(intent_edit);
                break;
            case R.id.bk_contribution:
                break;
            case R.id.bk_income:
                break;
            case R.id.bk_mydiamond:
                break;
            case R.id.bk_level:
                break;
            case R.id.bk_vedio:
                break;
            case R.id.bk_identification:
                break;
            case R.id.bk_set:
                Intent intent_set = new Intent(this, SetActivity.class);
                startActivity(intent_set);
                break;
        }
    }

    private void loginPopupWindow() {
        final View contentView = LayoutInflater.from(this).inflate(R.layout.login_style, null);
//        contentView.getBackground().setAlpha(60);
        imageView_qq = (ImageView) contentView.findViewById(R.id.qq_login);
        imageView_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIUiListener = new BaseUiListener();
                mTencent.login(MainActivity.this, "all", mIUiListener);
                popupWindow_login.dismiss();

            }
        });
//        ImageView imageView_weibo = (ImageView) contentView.findViewById(R.id.weibo_login);
//        imageView_weibo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent_test = new Intent(MainActivity.this, UserActivity.class);
//                startActivity(intent_test);
//                popupWindow.dismiss();
//            }
//        });
        ImageView imageView_phone = (ImageView) contentView.findViewById(R.id.phone_login);
        imageView_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_phone = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent_phone);
                finish();
                popupWindow_login.dismiss();
            }
        });

        popupWindow_login = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
//        ColorDrawable cd = new ColorDrawable(this.getResources().getColor(R.color.white));
//        popupWindow.setBackgroundDrawable(cd);
        //点击popwindow以外的布局让pop消失
        popupWindow_login.setOutsideTouchable(true);
        popupWindow_login.setFocusable(true);
        popupWindow_login.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                popupWindow_login.dismiss();
            }
        });

        popupWindow_login.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    popupWindow_login.dismiss();
                    return true;
                }
                return false;
            }
        });
//        setBackgroundAlpha(0.6f, contentView.getContext());//设置屏幕透明度
//        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                // popupWindow隐藏时恢复屏幕正常透明度
//                setBackgroundAlpha(1.0f, MainActivity.this);
//            }
//        });
//        contentView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (popupWindow != null && popupWindow.isShowing()) {
//                    popupWindow.dismiss();
//                    popupWindow = null;
//                }
//                return false;
//            }
//        });
    }

    private void showPopWindow() {
        final View contentView = LayoutInflater.from(this).inflate(R.layout.live_style, null);
        popupWindow_vedio = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        ImageView imageView_unput = (ImageView) contentView.findViewById(R.id.live_unputvideo);
        imageView_unput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackgroundAlpha(1.0f,MainActivity.this);
                popupWindow_vedio.dismiss();
                llBottomBar.setVisibility(View.VISIBLE);
            }
        });
        ImageView imageView_vedioshort = (ImageView) contentView.findViewById(R.id.vedio_short);
        imageView_vedioshort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackgroundAlpha(1.0f,MainActivity.this);
                startActivity(new Intent(MainActivity.this, ShortVedioActivity.class));
                popupWindow_vedio.dismiss();
                llBottomBar.setVisibility(View.VISIBLE);
            }
        });
        ImageView imageView_camera= (ImageView) contentView.findViewById(R.id.live_camera);
        imageView_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackgroundAlpha(1.0f,MainActivity.this);
                startActivity(new Intent(MainActivity.this, LiveCameraActivity.class));
                popupWindow_vedio.dismiss();
                llBottomBar.setVisibility(View.VISIBLE);
            }
        });
    }


    //初始化视图
    private void init() {
        viewpagerMain.setAdapter(unLoginAdapter);
        viewpagerMain.setCurrentItem(2);
        //刚进来默认选择游戏
        textViews[2].setSelected(true);
        //viewPager添加滑动监听，用于控制TextView的展示
        viewpagerMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                //textView全部未选择
                for (TextView textView : textViews) {
                    textView.setSelected(false);
                }
                //设置选择效果
                textViews[position].setSelected(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private FragmentStatePagerAdapter unLoginAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                //市场
                case 0:
                    return new FollowFragment();
                //消息
                case 1:
                    return new GameFragment();
                //通讯录
                case 2:
                    return new FoundFragment();
                //我的
                case 3:
                    return new SameCityFragment();
            }
            return null;
        }
    };

    //textview点击事件
    @OnClick({R.id.tv_follow, R.id.tv_game, R.id.tv_found, R.id.tv_samecity})
    public void onClick(TextView view) {
        for (int i = 0; i < textViews.length; i++) {
            textViews[i].setSelected(false);
            textViews[i].setTag(i);
        }
        //设置选择效果
        view.setSelected(true);
        //参数false代表瞬间切换，而不是平滑过渡
        viewpagerMain.setCurrentItem((Integer) view.getTag(), false);
    }


    private Handler myHandler = new Handler() {

        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    SharedPreferences sharedPreferences = getSharedPreferences("isLogin", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isLogin", true);
                    editor.commit();

                    break;
                case 2:
                    break;
            }
        }
    };

    public void UserInfoHttp(String url,String path){
        OkHttpUtils
                .postString()
                .url(url)
                .content(path)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.d("失败的返回", e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        OkHttpUtils.postString()
                                .url(Api.USERINFO_USER)
                                .content(response)
                                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                                .build()
                                .execute(new StringCallback() {
                                    @Override
                                    public void onError(Call call, Exception e, int id) {
                                        Log.d("失败的返回", e.getMessage());
                                    }

                                    @Override
                                    public void onResponse(String response, int id) {
                                        OkHttpUtils.postString()
                                                .url(Api.UNENCRYPT64)
                                                .content(response)
                                                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                                                .build()
                                                .execute(new StringCallback() {
                                                    @Override
                                                    public void onError(Call call, Exception e, int id) {
                                                        Log.d("失败的返回", e.getMessage());
                                                    }

                                                    @Override
                                                    public void onResponse(String response, int id) {
                                                        Log.d("成功===成功的返回", response);
                                                        try {
                                                            JSONObject jsonobject = new JSONObject(response);
                                                            mNickName = jsonobject.getString("昵称");
                                                            mLevel = jsonobject.getString("等级");
                                                            mExperience = jsonobject.getString("经验");
                                                            mIncome = jsonobject.getString("收益");
                                                            mDiamond = jsonobject.getString("钻石");
                                                            mLiveNum = jsonobject.getString("直播");
                                                            mVideoNum = jsonobject.getString("点播");
                                                            mHeadimageUrl = jsonobject.getString("头像");
                                                            mSex = jsonobject.getString("性别");
                                                            mAge = jsonobject.getString("年龄");
                                                            mEmotion = jsonobject.getString("情感");
                                                            mHometown = jsonobject.getString("家乡");
                                                            mWork = jsonobject.getString("职业");
                                                            mFollow = jsonobject.getString("关注"+"");
                                                            mFans = jsonobject.getString("粉丝"+"");
                                                            mSignture = jsonobject.getString("签名"+"");
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                });
                                    }
                                });
                    }
                });
    }

    //******************************                 qq                 *****************************//
    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
            Message msg = new Message();
            msg.what = 1;
            myHandler.sendMessage(msg);
            Log.e("tag", "response:" + response);
            JSONObject jo = (JSONObject) response;

            try {
                openID = jo.getString("openid");
                String accessToken = jo.getString("access_token");
                String expires = jo.getString("expires_in");
                mTencent.setOpenId(openID);
                mTencent.setAccessToken(accessToken, expires);
                QQToken qqToken = mTencent.getQQToken();
                mInfo = new UserInfo(getApplicationContext(), qqToken);
                mInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object response) {
                        Log.e("BaseUiListener", "成功" + response.toString());
                    }

                    @Override
                    public void onError(UiError uiError) {
                        Log.e("BaseUiListener", "失败" + uiError.toString());
                    }

                    @Override
                    public void onCancel() {
                        Log.e("BaseUiListener", "取消");
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(getApplicationContext(), "登录取消", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("TAG", "-->onActivityResult " + requestCode + " resultCode=" + resultCode);
        if (requestCode == Constants.REQUEST_LOGIN ||
                requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode, resultCode, data, mIUiListener);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    //点击两次返回退出程序
    @Override
    public void onBackPressed() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(this, "再摁一次退出程序", Toast.LENGTH_SHORT).show();
            //两秒内再次点击返回则退出
            //如果两秒内，用户没有再次点击，则把isExit设置为false
            viewpagerMain.postDelayed(new Runnable() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);
        } else {
            finish();
        }
    }
    //设置透明度
    public void setBackgroundAlpha(float bgAlpha, Context context) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }
}
