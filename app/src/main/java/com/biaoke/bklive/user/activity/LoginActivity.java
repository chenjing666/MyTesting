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
import android.widget.TextView;
import android.widget.Toast;

import com.biaoke.bklive.MainActivity;
import com.biaoke.bklive.R;
import com.biaoke.bklive.base.BaseActivity;
import com.biaoke.bklive.message.Api;
import com.biaoke.bklive.message.AppConsts;
import com.biaoke.bklive.user.bean.User;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.put_phone_num)
    EditText putPhoneNum;
    @BindView(R.id.put_password)
    EditText putPassword;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_forgotpassword)
    TextView tvForgotpassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    private String Msg;
    User user=new User();
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }



    Handler mHandler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    Log.d("登录","————————————————");
                    SharedPreferences sharedPreferences = getSharedPreferences("isLogin", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isLogin", true);
                    user.setuId(userId);
                    editor.putString("userId",userId);
                    editor.commit();
                    Log.d("LoginActivity----useId",user.getuId()+"");
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                    break;
                case 2:
                    Toast.makeText(LoginActivity.this, Msg, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected String getPowerBarColors() {
        return AppConsts.POWER_BAR_BACKGROUND;
    }

    @OnClick({R.id.back, R.id.tv_register, R.id.tv_forgotpassword, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_register:
                Intent intent_register = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent_register);
                finish();
                break;
            case R.id.tv_forgotpassword:
                Intent intent_forgetpwd = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent_forgetpwd);
                finish();
                break;
            case R.id.btn_login:
                JSONObject jsonObject = new JSONObject();
                String name = putPhoneNum.getText().toString().trim();
                String password = putPassword.getText().toString().trim();
                try {
                    jsonObject.put("Name", name);
                    jsonObject.put("Password", password);
                    jsonObject.put("Protocol", "Logging");
                    jsonObject.put("PwdModel", "1");
                    jsonObject.put("Sys", "android");
                    jsonObject.put("Version", "BK_V1_20130313");
                    jsonObject.put("Imei", "0");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Okhttputils(Api.ENCRYPT64, jsonObject.toString());
                Log.d("LoginActivity",jsonObject.toString());
                break;
        }
    }

//    private String path = "{"Protocol":"Logging","Name":"13812345678","Password":"pwd","PwdModel":"1","Sys":"ios","Version":"BK_V1_20130313","Imei":"0"}";

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
//                        Log.d("成功的返回", response);
//                        Okhttputils(Api.LOGIN,response);
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
//                                        Log.d("成功的返回", response);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
