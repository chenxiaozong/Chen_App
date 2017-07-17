package com.example.a08_3playvideo;

import android.Manifest;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_WRITE_SD = 1;
    private Button bt_play;
    private Button bt_replay;
    private Button bt_pause;

    private VideoView vv_video_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         vv_video_view= (VideoView) findViewById(R.id.vv_video_view);

        bt_play = (Button)findViewById(R.id.bt_play);
        bt_replay = (Button)findViewById(R.id.bt_replay);
        bt_pause = (Button)findViewById(R.id.bt_pause);

        bt_pause.setOnClickListener(this);
        bt_replay.setOnClickListener(this);
        bt_play.setOnClickListener(this);


        checkRunTimePermission();


    }

    /**
     * 检查权限
     */
    private void checkRunTimePermission() {
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_WRITE_SD);
        }else {
            initVideoData();//
        }
    }


    /**
     * 初始化视屏数据
     *
     */
    private void initVideoData() {
        //File move = new File(Environment.getExternalStorageDirectory()+"/Download/","move.mp4");
        File move = new File(Environment.getExternalStorageDirectory(),"historymap.mp4");
        String path = move.getPath();
        vv_video_view.setVideoPath(move.getPath());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.bt_play:
                Toast.makeText(this, "play", Toast.LENGTH_SHORT).show();
                if(!vv_video_view.isPlaying()) {
                    vv_video_view.start();                    
                }                
                
                break;
            case R.id.bt_pause:
                if(vv_video_view.isPlaying()) {
                    vv_video_view.pause();                    
                }
                
                break;
            case R.id.bt_replay:
                if(vv_video_view.isPlaying()) {
                    vv_video_view.resume();
                }
                break;

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_WRITE_SD:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                    initVideoData();
                    
                }else {
                    Toast.makeText(this, "申请权限被拒绝，关闭程序", Toast.LENGTH_SHORT).show();
                    finish();//关闭
                }
                break;
            
        }
    }
}
