package com.example.a05_chen.broadcast2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by chen on 2017/7/6.
 * 静态注册广播，监听开机完成
 */

public class BootCompleteRecevier extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "开机完成", Toast.LENGTH_LONG).show();
    }
}
