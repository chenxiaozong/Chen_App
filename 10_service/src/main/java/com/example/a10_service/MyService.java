package com.example.a10_service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

public class MyService extends Service{

    private DownloadBinder downloadBinder = new DownloadBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyService", "onCreate");
        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this,0,intent,0);

        Notification nt = new NotificationCompat.Builder(this)
                .setContentTitle("标题")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("这是一个前台服务的通知")
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pi)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .build();


        //让service 变为前台服务 1: 通知的id nt: 构建一个新的通知
        startForeground(1,nt);



    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MyService", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyService", "onDestroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        Log.d("MyService", "绑定服务");
        return downloadBinder;
    }


    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
        Log.d("MyService", "解绑服务");
    }




    //自定义一个ibinde 类
    class  DownloadBinder extends Binder{

        public void  startDownload(){
            Log.d("DownloadBinder", "Myservice:start download");
        }

        public int getProgress(){
            Log.d("DownloadBinder", "Myservice:get progress ");
            return 0;
        }


    }
}
