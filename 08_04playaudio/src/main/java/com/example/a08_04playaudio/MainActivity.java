package com.example.a08_04playaudio;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int UPDATE_PROGRESS = 1;
    private Button bt_play;
    private Button bt_replay;
    private Button bt_pause;

    private TextView tv_audio_info;

    private MediaPlayer mediaPlayer;

    private SeekBar  sb_audio_progress;

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    int currentPosition = mediaPlayer.getCurrentPosition();
                    sb_audio_progress.setProgress(currentPosition);
                    Log.d("MainActivity", "handler update ");
                    handler.removeMessages(UPDATE_PROGRESS);
                    handler.sendEmptyMessageDelayed(UPDATE_PROGRESS,100);
                    tv_audio_info.setText("进度："+currentPosition+"/"+mediaPlayer.getDuration());
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_play = (Button) findViewById(R.id.bt_play);
        bt_replay = (Button) findViewById(R.id.bt_replay);
        bt_pause = (Button) findViewById(R.id.bt_pause);

        sb_audio_progress = (SeekBar)findViewById(R.id.sb_audio_progress);
        tv_audio_info = (TextView) findViewById(R.id.tv_audio_info);


        bt_replay.setOnClickListener(this);
        bt_pause.setOnClickListener(this);
        bt_play.setOnClickListener(this);

        checkRunTimePermission();
    }

    /**
     * 检查权限
     */
    private void checkRunTimePermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            initAudioData();
        }

    }

    /**
     * 初始化audio数据
     */
    private void initAudioData() {
        File file = new File(Environment.getExternalStorageDirectory() + "/Download/", "live.mp3");
      //  File file = new File(Environment.getExternalStorageDirectory() , "zj.mp3");
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(file.getPath());
            tv_audio_info.setText(file.getPath());
            mediaPlayer.prepare();

            int duration = mediaPlayer.getDuration();
            sb_audio_progress.setMax(duration);


            //handler.sendEmptyMessageDelayed(UPDATE_PROGRESS,500);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_play:
                if(!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    handler.removeMessages(UPDATE_PROGRESS);
                    handler.sendEmptyMessageDelayed(UPDATE_PROGRESS,100);
                }

                break;
            case R.id.bt_pause:
                if(mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    handler.removeMessages(UPDATE_PROGRESS);
                }else {
                    mediaPlayer.start();
                    handler.removeMessages(UPDATE_PROGRESS);
                    handler.sendEmptyMessageDelayed(UPDATE_PROGRESS,100);
                }
                break;
            case R.id.bt_replay:
//                if(mediaPlayer.isPlaying()) {
//                }
                    mediaPlayer.reset();
                    initAudioData();
                    mediaPlayer.start();
                    handler.removeMessages(UPDATE_PROGRESS);
                    handler.sendEmptyMessageDelayed(UPDATE_PROGRESS,100);
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(UPDATE_PROGRESS);
    }
}
