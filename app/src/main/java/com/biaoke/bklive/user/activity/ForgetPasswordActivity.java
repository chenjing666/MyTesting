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
import android.widget.Toast;

import com.biaoke.bklive.MainActivity;
import com.biaoke.bklive.R;
import com.biaoke.bklive.base.BaseActivity;
import com.biaoke.bklive.message.Api;
import com.biaoke.bklive.message.AppConsts;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

public class ForgetPasswordActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.put_phone_num)
    EditText putPhoneNum;
    @BindView(R.id.send_pin)
    Button sendPin;
    @BindView(R.id.put_pin)
    EditText putPin;
    @BindView(R.id.btn_login)
    Button btnLogin;
    private String Msg;
    private String phonenum;
    private String Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
    }

    @Override
    protected String getPowerBarColors() {
        return AppConsts.POWER_BAR_BACKGROUND;
    }

    Handler mHandler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    Toast.makeText(ForgetPasswordActivity.this, Msg, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(ForgetPasswordActivity.this, Msg, Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    SharedPreferences sharedPreferences = getSharedPreferences("isLogin", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isLogin", true);
                    editor.commit();
                    startActivity(new Intent(ForgetPasswordActivity.this, MainActivity.class));
                    finish();
                    break;
            }
        }
    };

    @OnClick({R.id.back, R.id.send_pin, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.send_pin:
                JSONObject jsonObject_pin = new JSONObject();
                phonenum = putPhoneNum.getText().toString().trim();
                try {
                    jsonObject_pin.put("Mobile", phonenum);
                    jsonObject_pin.put("Protocol", "Register");
                    jsonObject_pin.put("Cmd", "4");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                PinOkhttputils(Api.ENCRYPT64, jsonObject_pin.toString());
                break;
            case R.id.btn_login:
                JSONObject jsonObject_login = new JSONObject();
                phonenum = putPhoneNum.getText().toString().trim();
                String pinNum = putPin.getText().toString().trim();
                try {
                    jsonObject_login.put("Mobile", phonenum);
                    jsonObject_login.put("Code", pinNum);
                    jsonObject_login.put("Protocol", "LoggingCode");
                    jsonObject_login.put("PwdModel", "1");
                    jsonObject_login.put("Sys", "android");
                    jsonObject_login.put("Version", "BK_V1_20130313");
                    jsonObject_login.put("Imei", "0");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                LoginOkhttputils(Api.ENCRYPT64, jsonObject_login.toString());
                break;
        }
    }


    public void PinOkhttputils(String url, String path) {

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
//                                                        Log.d("成功的返回", response);
                                                        try {
                                                            JSONObject jsonobject = new JSONObject(response);
                                                            Msg = jsonobject.getString("Msg");
                                                            String Result = jsonobject.getString("Result");
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
    public void LoginOkhttputils(String url, String path) {

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
                                .url(Api.LOGIN)
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
                                                        Log.d("成功的返回", response);
                                                        try {
                                                            JSONObject jsonobject = new JSONObject(response);
                                                            Msg = jsonobject.getString("Msg");
                                                            Id = jsonobject.getString("Id");
                                                            String Result = jsonobject.getString("Result");
                                                            Message msg = new Message();
                                                            if (Result.equals("1")) {
                                                                msg.what = 3;
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
