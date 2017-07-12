package com.example.a05_chen.broadcast1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.a05_chen.R;

public class BroadCast1Activity extends AppCompatActivity {


    private  IntentFilter intentFilter ;
    private NetChangeRecever netChangeRecever;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad_cast1);

                                //android.net.conn.CONNECTIVITY_CHANGE

        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        netChangeRecever = new NetChangeRecever();

        registerReceiver(netChangeRecever,intentFilter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(netChangeRecever);
    }

    //使用内部类动态注册广播
    class NetChangeRecever extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
        Toast.makeText(BroadCast1Activity.this, "网络状态改变", Toast.LENGTH_SHORT).show();
        }

    }
}
