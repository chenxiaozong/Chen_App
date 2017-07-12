package com.example.a05_chen.LogTest;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.a05_chen.R;

public class BaseActivity extends AppCompatActivity {
    private  ForceLogoutRecever receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base2);

        ActivityController.addActivity(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.a05_chen.LogTest.ForceLogoutRecever");
        receiver = new ForceLogoutRecever();

        registerReceiver(receiver,intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(receiver!=null) {
            unregisterReceiver(receiver);

        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.removeActivity(this);
    }



    public class ForceLogoutRecever extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, final Intent intent) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Warning");
            builder.setMessage("You are forced to be offline. Please try to login again.");
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityController.finishAll(); // 销毁所有活动
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent); // 重新启动LoginActivity
                }
            });
            builder.show();


        }
    }

}
