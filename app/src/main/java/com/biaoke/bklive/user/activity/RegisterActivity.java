package com.biaoke.bklive.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.biaoke.bklive.R;
import com.biaoke.bklive.base.BaseActivity;
import com.biaoke.bklive.message.Api;
import com.biaoke.bklive.message.AppConsts;
import com.biaoke.bklive.user.bean.User;
import com.biaoke.bklive.utils.VerifyUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.put_phonenum)
    EditText putPhonenum;
    @BindView(R.id.pin_send)
    Button pinSend;
    @BindView(R.id.pin_put)
    EditText pinPut;
    @BindView(R.id.put_pw)
    EditText putPw;
    @BindView(R.id.confirm_pw)
    EditText confirmPw;
    @BindView(R.id.btn_register)
    Button btnRegister;
    private String name;
    private String Msg;
    String password = null;
    User user=new User();
    private String pinNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }


    Handler mHandler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    Toast.makeText(RegisterActivity.this, Msg, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(RegisterActivity.this, Msg, Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    if (VerifyUtils.verifyPassword(password)) {
                        Toast.makeText(RegisterActivity.this, "密码格式有误", Toast.LENGTH_SHORT).show();
                        return;
                    } else if ((password = putPw.getText().toString()).equals(confirmPw.getText().toString())) {
                        Toast.makeText(RegisterActivity.this, Msg, Toast.LENGTH_SHORT).show();
                        Intent intent_register = new Intent(RegisterActivity.this, HeadSetActivity.class);
                        intent_register.putExtra("password", password);
                        intent_register.putExtra("phonenum", name);
                        intent_register.putExtra("pinNum", pinNum);
                        user.setuPhoneNum(name);
                        user.setuPassWord(password);
                        startActivity(intent_register);
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "密码不一致", Toast.LENGTH_SHORT).show();
                    }

                    break;
                case 4:
                    Toast.makeText(RegisterActivity.this, Msg, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected String getPowerBarColors() {
        return AppConsts.POWER_BAR_BACKGROUND;
    }

    @OnClick({R.id.back, R.id.pin_send, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.pin_send:
                JSONObject jsonObject = new JSONObject();
                name = putPhonenum.getText().toString().trim();
                if (!VerifyUtils.verifyMobileNo(name)) {
                    Toast.makeText(this, "手机号有误！", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    jsonObject.put("Mobile", name);
                    jsonObject.put("Protocol", "Register");
                    jsonObject.put("Cmd", "1");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pinOkhttputils(Api.ENCRYPT64, jsonObject.toString());
                break;
            case R.id.btn_register:
                JSONObject jsonObject_r = new JSONObject();
                pinNum = pinPut.getText().toString().trim();
                try {
                    jsonObject_r.put("Mobile", name);
                    jsonObject_r.put("Protocol", "Register");
                    jsonObject_r.put("Cmd", "2");
                    jsonObject_r.put("Code", pinNum);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                RegisterOkhttp(Api.ENCRYPT64, jsonObject_r.toString());
                break;
        }
    }
    // 客户端把手机号码发给服务端
// {"Protocol":"Register","Cmd":"1","Mobile":"13812345678"}点击发送验证码：发手机号
// 手机号注册 客户端把短信收到的验证码发给服务端
// {"Protocol":"Register","Cmd":"2","Mobile":"13812345678","Code":"8080"}收到验证码并发送，同时记录密码

    public void RegisterOkhttp(String url, String path) {

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
                                                            Message msg = new Message();
                                                            if (Result.equals("1")) {
                                                                msg.what = 3;
                                                                mHandler.sendMessage(msg);
                                                            } else if (Result.equals("0")) {
                                                                msg.what = 4;
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


    public void pinOkhttputils(String url, String path) {

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
