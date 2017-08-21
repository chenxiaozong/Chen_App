package com.example.lbslocation;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 1. 显示经纬度
 * 2. 每隔5s重新扫描未知
 * 3. 显示 国家,省,市,区,街道
 * 4. 显示百度地图 mapview
 * 5. 地图显示定位的未知
 * 6. 地图显示"我"的标志
 */
public class MainActivity extends AppCompatActivity {



    @BindView(R.id.btn_current_position)
    Button btnCurrentPosition;
    private Context mContext;

    public LocationClient locationClient;

    //使用百度地图显示
    private MapView mapView;


    @BindView(R.id.tv_location_position)
    TextView tvLocationPosition;


    private BaiduMap baiduMap;
    private boolean isFirstLocation = true;

    private BDLocation current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());


        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = MainActivity.this;

        mapView = (MapView) findViewById(R.id.mv_location);
        baiduMap = mapView.getMap();//xan显示地图

        //显示我的未知
        baiduMap.setMyLocationEnabled(true);

        initData();

        checkPermissions();


    }

    private void initData() {

        locationClient = new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(new MylocationListen());


    }

    /**
     * Mapview 跳转到定位未知
     *
     * @param location
     */
    private void navigateTo(BDLocation location) {
        if (isFirstLocation) {

            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());//设置经纬度

            MapStatusUpdate update = MapStatusUpdateFactory.zoomTo(16f);//设置缩放
            baiduMap.animateMapStatus(update);

            update = MapStatusUpdateFactory.newLatLng(ll);

            baiduMap.animateMapStatus(update);
            isFirstLocation = false;
        }


        //6. 显示我的位置
//        MyLocationData.Builder builder = new MyLocationData.Builder();
//
//        builder.latitude(location.getLatitude());
//        builder.longitude(location.getLongitude());
//        MyLocationData locationData = builder.build();

        MyLocationData locationData = new MyLocationData.Builder()
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .build();


        baiduMap.setMyLocationData(locationData);
    }

    @OnClick(R.id.btn_current_position)
    void buttonOnclik(View view) {
        if (view.getId() == R.id.btn_current_position) {
            isFirstLocation = true;
            navigateTo(current);
            Toast.makeText(mContext, "当前", Toast.LENGTH_SHORT).show();
        }
    }


    private void checkPermissions() {

        List<String> permissionList = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);

        }

        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);

        }

        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!permissionList.isEmpty()) {
            String[] permisions = permissionList.toArray(new String[permissionList.size()]);

            ActivityCompat.requestPermissions((Activity) mContext, permisions, 1);

        } else {
            //权限授予成功 -> 执行定位操作
            requestLocation();

        }


    }


    /**
     * 请求定位
     */
    private void requestLocation() {
        initLocatioin();

        locationClient.start();//启动定位服务
    }


    //设置每隔5s更新location
    private void initLocatioin() {
        LocationClientOption option = new LocationClientOption();

        //1. 设置定位扫描时间
        option.setScanSpan(5000);


        //2. 设置定位模式:

        //option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);//节电模式:仅使用网络
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//高精度 : 有gps?gps:网络
//        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);//仅使用GPS


        //3. 设置使用地址
        option.setIsNeedAddress(true);//需要获取详细信息


        locationClient.setLocOption(option);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1:

                if (grantResults.length > 0) {
                    for (int result : grantResults) {

                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(mContext, "必须使用所有权限才能使用定位功能", Toast.LENGTH_SHORT).show();
                        }
                        finish();
                        return;//只要有一个权限拒绝,就终止程序


                    }
                    requestLocation();
                } else {
                    Toast.makeText(mContext, "未知错误,程序终止", Toast.LENGTH_SHORT).show();

                }

                break;
        }


    }

    private class MylocationListen implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            String latitude = "经度:" + bdLocation.getLongitude() + "\n";//纬度
            String longitude = "纬度:" + bdLocation.getLatitude() + "\n";//经度


            String country = "国家:" + bdLocation.getCountry() + "\n";
            String province = " 省:" + bdLocation.getProvince() + "\n";
            String city = "城市:" + bdLocation.getCity() + "\n";

            String district = " 区:" + bdLocation.getDistrict() + "\n";
            String street = "街道:" + bdLocation.getStreet() + "\n";


            String model = "方式:";
            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {
                model += "GPS";

            } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                model += "网络";
            }


            current = bdLocation;
            String result = latitude +
                    longitude +
                    country +
                    province +
                    city +
                    district +
                    street +
                    model;
            tvLocationPosition.setText(result +" 时间:" +System.currentTimeMillis());


            //移动地图
            if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation || bdLocation.getLocType() == BDLocation.TypeGpsLocation) {
            }
            navigateTo(bdLocation);


        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        mapView.onResume();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        locationClient.stop();//终止定位服务

        baiduMap.setMyLocationEnabled(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();

    }
}
