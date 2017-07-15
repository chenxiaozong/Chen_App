package com.example.a08_1notificationtest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button bt_show_notification;
    Context context = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_show_notification = (Button)findViewById(R.id.bt_show_notification);
        bt_show_notification.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Toast.makeText(this, "show", Toast.LENGTH_SHORT).show();
        //sendNotification(); //基础通知栏
        sendNotification2(); //进阶通知栏

    }


    /**
     * 2. 通知栏进阶
     * - 设置声音
     * - 设置震动
     * - 设置LED
     * - 设置默认提示
     */
    private void sendNotification2() {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle("通知")
                //.setContentText("this is a notifiacatioin form notification test, and you can see massage from the main context")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setSound(Uri.fromFile(new File("/system/media/audio/ringtones/IceLead.ogg"))) //使用声音 {静止时长，震动时长，静止时长，震动时长}

                .setLights(Color.GREEN,1000,2000)// 设置颜色， {颜色，亮的时长，暗的时长}
                .setVibrate(new long[]{0,3000,1000,2000}) //使用震动
               // .setDefaults(NotificationCompat.DEFAULT_ALL) //使用通知的默认效果
                .setStyle(new NotificationCompat.BigTextStyle().bigText("This is a notifiacatioin form notification test," +
                        " and you can see massage from the main context."))
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(),R.drawable.big_image)))
                .setPriority(NotificationCompat.PRIORITY_MAX) //设置通知等级最高
                .build();

        manager.notify(2,notification);

    }

    /**
     * 发送通知栏通知
     */
    private void sendNotification() {
        //1. 获取通知管理manager
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        Intent intent = new Intent(this,NotificationActivity.class);

        PendingIntent pi = PendingIntent.getActivity(this,0,intent,0);
//        PendingIntent pi = PendingIntent.getActivities(this,0,new Intent[]{intent},0);
        //2. 使用builder构造器创建notification对象
        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle("title")
                .setContentText("this is  notification from test")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setContentIntent(pi) //添加intent
                .setAutoCancel(true)// 点击后自动取消通知栏图标 （也可在被启动的页面取消）
                .build();

        manager.notify(1,notification);


    }

}
