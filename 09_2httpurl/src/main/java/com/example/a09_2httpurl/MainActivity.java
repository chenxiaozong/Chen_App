package com.example.a09_2httpurl;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 1. 联网请求需要在子线程执行,更新界面在主线程,可以使用handler 通信
 *
 * 2. HttpUrlconnection conn 可以返回请求码, responsecode ==200 联网成功
 *
 * 3. 联网地址 url 格式: http://   https://
 */
public class MainActivity extends Activity {

    private static final int URL_CONNECT_OK = 1;
    private static final int URL_CONNECT_ERROR = 2;
    private static final int URL_CONNECT_URL_ERROR = 3;

    String urlStr = "https://www.baidu.com/";

    @BindView(R.id.et_url)
    EditText etUrl;
    @BindView(R.id.bt_get)
    Button btGet;
    @BindView(R.id.bt_post)
    Button btPost;
    @BindView(R.id.tv_result)
    TextView tvResult;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case URL_CONNECT_OK:
                    Toast.makeText(MainActivity.this, "联网请求成功", Toast.LENGTH_SHORT).show();

                    break;

                case URL_CONNECT_ERROR:

                    Toast.makeText(MainActivity.this, "联网请求失败", Toast.LENGTH_SHORT).show();
                    break;
                case URL_CONNECT_URL_ERROR:
                    String result = (String) msg.obj;
                    Toast.makeText(MainActivity.this, "UrlError:" + result, Toast.LENGTH_SHORT).show();
                    break;
            }


        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.bt_get, R.id.bt_post})
    void buttonOnclick(View view) {
        switch (view.getId()) {
            case R.id.bt_get:
                tvResult.setText("");

                if (!TextUtils.isEmpty(etUrl.getText().toString().trim())) {
                    urlStr = etUrl.getText().toString().trim();
                }
                requestGet(urlStr);


                break;
            case R.id.bt_post:
                tvResult.setText("");
                if (!TextUtils.isEmpty(etUrl.getText().toString().trim())) {
                    urlStr = etUrl.getText().toString().trim();
                }
                requestPost(urlStr);

                break;
        }

    }

    private void requestPost(final String path) {


        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection conn = null;
                BufferedReader reader = null;

                try {
                    URL url = new URL(path);

                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setConnectTimeout(2000);
                    conn.setReadTimeout(2000);


                    if (conn.getResponseCode() == 200) {


                        InputStream in = conn.getInputStream();

                        //对获取到的输入流读取
                        reader = new BufferedReader(new InputStreamReader(in));
                        StringBuilder response = new StringBuilder();

                        String line;
                        while ((line = reader.readLine()) != null) {

                            response.append(line);
                        }


                        //textview 显示结果
                        showResponse(response.toString());
                        handler.sendEmptyMessage(URL_CONNECT_OK);

                    } else {
                        handler.sendEmptyMessage(URL_CONNECT_ERROR);
                    }


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    Message msg = Message.obtain();
                    msg.what = URL_CONNECT_URL_ERROR;
                    msg.obj = e.getMessage();

                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {

                }

            }
        }).start();

    }

    private void showResponse(final String response) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvResult.setText(response);
            }
        });

    }

    private void requestGet(final String path) {


        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection conn = null;
                BufferedReader reader = null;

                try {
                    URL url = new URL(path);

                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(2000);
                    conn.setReadTimeout(2000);

                    if (conn.getResponseCode() == 200) {


                        InputStream in = conn.getInputStream();

                        //对获取到的输入流读取
                        reader = new BufferedReader(new InputStreamReader(in));
                        StringBuilder response = new StringBuilder();

                        String line;
                        while ((line = reader.readLine()) != null) {

                            response.append(line);
                        }


                        handler.sendEmptyMessage(URL_CONNECT_OK);

                        //textview 显示结果
                        showResponse(response.toString());
                    } else {
                        handler.sendEmptyMessage(URL_CONNECT_ERROR);
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    Message msg = Message.obtain();
                    msg.what = URL_CONNECT_URL_ERROR;
                    msg.obj = e.getMessage();
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {

                }

            }
        }).start();

    }
}
