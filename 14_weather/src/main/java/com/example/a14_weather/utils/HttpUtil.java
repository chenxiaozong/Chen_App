package com.example.a14_weather.utils;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by chen on 2017/8/22.
 * 联网获取数据工具类
 */

public class HttpUtil {

    public  static void sendOkHttpRequest(String address,Callback callback ){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();

        client.newCall(request).enqueue(callback);


    }
}
