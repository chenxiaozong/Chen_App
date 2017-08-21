package com.example.a10_service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by chen on 2017/8/20.
 * 前台服务
 */

public class FrontService extends Service {


    private FrontServieBinder binder = new FrontServieBinder();




    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();


        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this,0,intent,0);


        NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.big_image);
        style.bigPicture(bitmap);

        Notification nt = new NotificationCompat.Builder(this)
                .setContentTitle("标题")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("这是一个前台服务的通知")
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pi)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
//                .setStyle(style)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(),R.drawable.big_image)))
                .build();


        //让service 变为前台服务 1: 通知的id nt: 构建一个新的通知
        startForeground(1,nt);


    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {
        Log.d("FrontService", "启动前台服务");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("FrontService", "关闭前台服务");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }



    class  FrontServieBinder extends Binder{

        void info(){
            Toast.makeText(FrontService.this, "显示状态了", Toast.LENGTH_SHORT).show();
        }


    }


}
