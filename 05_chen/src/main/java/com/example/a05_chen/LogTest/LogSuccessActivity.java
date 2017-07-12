package com.example.a05_chen.LogTest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.a05_chen.R;


/**
 * 点击按钮，发送广播，强制登出
 * com.example.a05_chen.LogTest.ForceLogoutRecever
 *
 *
 */
public class LogSuccessActivity extends BaseActivity {


    private Button bt_logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_success);
        bt_logout = (Button)findViewById(R.id.bt_logout);

        bt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent  = new Intent("com.example.a05_chen.LogTest.ForceLogoutRecever");
                sendBroadcast(intent);


            }
        });
    }
}
