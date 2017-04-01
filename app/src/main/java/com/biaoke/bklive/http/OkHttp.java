package com.biaoke.bklive.http;


import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

/**
 * Created by Holiday on 2017/2/24.
 * 请求方法封装
 */

public class OkHttp {
    private static OkHttpClient okHttpClient;

    /**
     * OkHttp 发起json请求
     *
     * @param url      请求地址
     * @param object   传入的参数 JSON
     * @param callBack 自定义回调类
     */
    public static void postJson(String url, String object, Callback<String> callBack) {
        //请求日志相关
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new LoggerInterceptor("OkHttp网络请求信息"))
                    .build();
            OkHttpUtils.initClient(okHttpClient);
        }
        //TODO 请求参数加密
        OkHttpUtils
                .postString()
                .url(url)
                .content(object)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(callBack);
    }
}
