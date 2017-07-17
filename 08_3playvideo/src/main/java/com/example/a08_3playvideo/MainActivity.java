package com.example.a08_3playvideo;

import android.Manifest;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.OnNmeaMessageListener;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_WRITE_SD = 1;
    private static final int UPDATE_INFO = 1;//发送更新进度消息
    private Button bt_play;
    private Button bt_replay;
    private Button bt_pause;
    private TextView tv_video_info;

    private VideoView vv_video_view;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_INFO:
                    int duration = vv_video_view.getDuration();
                    int currentPosition = vv_video_view.getCurrentPosition();
                    tv_video_info.setText("进度：" + currentPosition + "/" + duration);

                    handler.removeMessages(UPDATE_INFO);
                    handler.sendEmptyMessageDelayed(UPDATE_INFO, 100);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vv_video_view = (VideoView) findViewById(R.id.vv_video_view);

        bt_play = (Button) findViewById(R.id.bt_play);
        bt_replay = (Button) findViewById(R.id.bt_replay);
        bt_pause = (Button) findViewById(R.id.bt_pause);
        tv_video_info = (TextView) findViewById(R.id.tv_video_info);

        bt_pause.setOnClickListener(this);
        bt_replay.setOnClickListener(this);
        bt_play.setOnClickListener(this);


        checkRunTimePermission();


    }

    /**
     * 检查权限
     */
    private void checkRunTimePermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_SD);
        } else {
            initVideoData();//
        }
    }


    /**
     * 初始化视屏数据
     */
    private void initVideoData() {
        File move = new File(Environment.getExternalStorageDirectory() + "/Download/", "move.mp4");
        //File move = new File(Environment.getExternalStorageDirectory(),"historymap.mp4");
        String path = move.getPath();
        vv_video_view.setVideoPath(move.getPath());

        tv_video_info.setText("视屏位置：" + move.getPath());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.bt_play:
                Toast.makeText(this, "play", Toast.LENGTH_SHORT).show();
                if (!vv_video_view.isPlaying()) {
                    vv_video_view.start();
                    handler.removeMessages(UPDATE_INFO);
                    handler.sendEmptyMessageDelayed(UPDATE_INFO, 100);

                }

                break;
            case R.id.bt_pause:
                if (vv_video_view.isPlaying()) {
                    vv_video_view.pause();
                    handler.removeMessages(UPDATE_INFO);
                } else {
                    vv_video_view.start();
                    handler.removeMessages(UPDATE_INFO);
                    handler.sendEmptyMessageDelayed(UPDATE_INFO, 100);
                }


                break;
            case R.id.bt_replay:
                if (vv_video_view.isPlaying()) {
                    vv_video_view.resume();
                } else {
                    vv_video_view.resume();
                    vv_video_view.start();
                }
                handler.removeMessages(UPDATE_INFO);
                handler.sendEmptyMessageDelayed(UPDATE_INFO, 100);
                break;

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_WRITE_SD:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initVideoData();

                } else {
                    Toast.makeText(this, "申请权限被拒绝，关闭程序", Toast.LENGTH_SHORT).show();
                    finish();//关闭
                }
                break;

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //1. 释放视屏资源

        if (vv_video_view != null) {
            vv_video_view.suspend();
        }

        //2. 移除handler消息
        handler.removeCallbacksAndMessages(UPDATE_INFO);


    }
}
