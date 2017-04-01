package com.biaoke.bklive.user.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.biaoke.bklive.MainActivity;
import com.biaoke.bklive.R;
import com.biaoke.bklive.base.BaseActivity;
import com.biaoke.bklive.message.Api;
import com.biaoke.bklive.message.AppConsts;
import com.biaoke.bklive.user.bean.User;
import com.pkmmte.view.CircularImageView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

public class HeadSetActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.iv_user_head)
    CircularImageView ivUserHead;
    @BindView(R.id.et_nickName)
    EditText etNickName;
    @BindView(R.id.male)
    RadioButton male;
    @BindView(R.id.female)
    RadioButton female;
    @BindView(R.id.sex)
    RadioGroup sex;
    @BindView(R.id.btn_finish)
    Button btnFinish;
    private String Msg = null;
    private String nickName = null;
    private String sexes = null;
    private String phonenum = null;
    private String password = null;
    private String pinNum;
    private String userId = null;
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_set);
        ButterKnife.bind(this);
        phonenum = getIntent().getStringExtra("phonenum");
        password = getIntent().getStringExtra("password");
        pinNum = getIntent().getStringExtra("pinNum");
    }

    Handler mHandler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    Toast.makeText(HeadSetActivity.this, Msg, Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = getSharedPreferences("isLogin", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    user.setuId(userId);
                    editor.putString("userId",user.getuId());
                    editor.putBoolean("isLogin", true);
                    editor.commit();
                    Intent intent=new Intent(HeadSetActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case 2:
                    Toast.makeText(HeadSetActivity.this, Msg, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    @Override
    protected String getPowerBarColors() {
        return AppConsts.POWER_BAR_BACKGROUND;
    }

    @OnClick({R.id.back, R.id.iv_user_head, R.id.btn_finish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.iv_user_head:

                break;
            case R.id.btn_finish:
                nickName = etNickName.getText().toString().trim();
                if (male.getId() == sex.getCheckedRadioButtonId()) {
                    sexes = "男";
                } else {
                    sexes = "女";
                }
                JSONObject jsonObject_r = new JSONObject();
                try {
                    jsonObject_r.put("Mobile", phonenum);
                    jsonObject_r.put("Password", password);
                    jsonObject_r.put("NickName", nickName);
                    jsonObject_r.put("Gender", sexes);
                    jsonObject_r.put("Code", pinNum);
//                    jsonObject_r.put("PwdModel", "1");
                    jsonObject_r.put("Protocol", "Register");
                    jsonObject_r.put("Cmd", "3");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Log.d("pppppppppppppp", jsonObject_r.toString() + "");
                Okhttputils(Api.ENCRYPT64, jsonObject_r.toString());
                break;
        }
    }

    // 完成页 客户端回：手机号、密码、昵称、性别
// {"Protocol":"Register","Cmd":"3","Mobile":"13812345678","Password":"pwd","NickName":"你好骠客","Gender":"男","Code":"8080"}最后完整信息发送
    public void Okhttputils(String url, String path) {

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
                                .url(Api.REGISTER)
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
                                        Log.d("成功的返回", response);
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
                                                        Log.d("成功的返回", response);
                                                        try {
                                                            JSONObject jsonobject = new JSONObject(response);
                                                            Msg = jsonobject.getString("Msg");
                                                            String Result = jsonobject.getString("Result");
                                                            user.setuId(jsonobject.getString("Id"));
                                                            userId = jsonobject.getString("Id");
                                                            Message msg = new Message();
                                                            if (Result.equals("1")) {
                                                                msg.what = 1;
                                                                mHandler.sendMessage(msg);
                                                            } else if (Result.equals("0")) {
                                                                msg.what = 2;
                                                                mHandler.sendMessage(msg);
                                                            }
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
}
