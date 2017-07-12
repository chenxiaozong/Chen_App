package com.example.a05_chen.broadcast3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.a05_chen.R;

public class BroadCast3Activity extends AppCompatActivity {

    private Button bt_sendBroadCast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad_cast3);
        bt_sendBroadCast = (Button)findViewById(R.id.bt_sendBroadCast);
        bt_sendBroadCast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.example.a05_chen.broadcast3.MY_BROADCAST");
                sendBroadcast(intent);
            }
        });
    }




}
