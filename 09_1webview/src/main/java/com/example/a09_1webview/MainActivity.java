package com.example.a09_1webview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webview = (WebView)findViewById(R.id.webview);

        //设置
        webview.getSettings().setJavaScriptEnabled(true); //设置webview参数：支持js
        webview.setWebViewClient(new WebViewClient());    //当需要也没跳转时，仍在当前webview显示而不是打开系统浏览器
        webview.loadUrl("http://www.baidu.com");          //加载地址

    }
}
