package com.example.a14_weather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.a14_weather.common.AppConfige;
import com.example.a14_weather.utils.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    initData();

    }

    private void initData() {
        HttpUtil.sendOkHttpRequest(AppConfige.china+"/15/96", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(MainActivity.this, "请求数据失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //Log.d("MainActivity", response.body().string());

                String result = response.body().string();
                Log.d("MainActivity", "请求成功"+result);

                //JsonParseUtil.handlerDistrictResponse(result);
                // JsonParseUtil.handlerProvinceResponse(result);

               // JsonParseUtil.handlerCityResponse(result);
            }
        });

    }


}


