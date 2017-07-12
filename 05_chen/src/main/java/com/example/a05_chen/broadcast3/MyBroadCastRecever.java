package com.example.a05_chen.broadcast3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by chen on 2017/7/6.
 */

public class MyBroadCastRecever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "receve my braodcast ", Toast.LENGTH_SHORT).show();
    }
}
