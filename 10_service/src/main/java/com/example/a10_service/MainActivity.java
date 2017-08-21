package com.example.a10_service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @BindView(R.id.bt_service_bind)
    Button btServiceBind;
    @BindView(R.id.bt_service_stop_unbind)
    Button btServiceStopUnbind;
    @BindView(R.id.bt_service_front_bind)
    Button btServiceFrontBind;
    @BindView(R.id.bt_service_front_ubind)
    Button btServiceFrontUbind;
    private MyService.DownloadBinder binder;


    private FrontService.FrontServieBinder fbinder;

    private ServiceConnection connection2 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            fbinder = (FrontService.FrontServieBinder) iBinder;
            fbinder.info();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };




    //Myservice 的connect
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            binder = (MyService.DownloadBinder) iBinder;
            binder.startDownload();
            binder.getProgress();

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };


    @BindView(R.id.bt_service_start)
    Button btServiceStart;
    @BindView(R.id.bt_service_stop)
    Button btServiceStop;
    @BindView(R.id.bt_service_front_start)
    Button btServiceFrontStart;
    @BindView(R.id.bt_service_front_stop)
    Button btServiceFrontStop;
    @BindView(R.id.tv_service_info)
    TextView tvServiceInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.bt_service_start, R.id.bt_service_stop, R.id.bt_service_bind, R.id.bt_service_stop_unbind, R.id.bt_service_front_start, R.id.bt_service_front_stop})
    public void buttonOnclick(View view) {

        switch (view.getId()) {
            case R.id.bt_service_start:
                Intent intent = new Intent(MainActivity.this, MyService.class);
                startService(intent);

                break;
            case R.id.bt_service_stop:

                intent = new Intent(MainActivity.this, MyService.class);
                stopService(intent);
                break;

            case R.id.bt_service_bind:
                intent = new Intent(MainActivity.this, MyService.class);

                if (connection == null) {
                    connection = new ServiceConnection() {
                        @Override
                        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                            binder = (MyService.DownloadBinder) iBinder;
                            binder.startDownload();
                            binder.getProgress();
                        }

                        @Override
                        public void onServiceDisconnected(ComponentName componentName) {

                        }
                    };
                }


                bindService(intent, connection, BIND_AUTO_CREATE);//BIND_AUTO_CREATE: 活动和服务绑定后自行创建服务
                break;

            case R.id.bt_service_stop_unbind:
                intent = new Intent(MainActivity.this, MyService.class);


                if (connection != null) {
                    unbindService(connection);
                    connection = null;
                }


                break;


            case R.id.bt_service_front_start://启动前台服务

                intent = new Intent(MainActivity.this, FrontService.class);
                startService(intent);


                break;
            case R.id.bt_service_front_stop://关闭前台服务

                intent = new Intent(MainActivity.this, FrontService.class);
                stopService(intent);
                break;
            case R.id.bt_service_front_bind://绑定前台服务

                intent = new Intent(MainActivity.this, FrontService.class);
                bindService(intent,connection2,BIND_AUTO_CREATE);


                break;
            case R.id.bt_service_front_ubind://解绑前台服务

                intent = new Intent(MainActivity.this, FrontService.class);
                bindService(intent,connection2,BIND_AUTO_CREATE);
                break;


        }

    }
}

