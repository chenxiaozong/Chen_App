package com.example.a08_2camera;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int TAKE_PHOTO = 1;
    private static final int CHOSE_PHOTE = 2;

    private Button bt_get_camera;

    private Button bt_chose_album;


    private ImageView iv_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_get_camera = (Button) findViewById(R.id.bt_get_camera);
        bt_chose_album = (Button) findViewById(R.id.bt_chose_album);

        iv_img = (ImageView) findViewById(R.id.iv_img);


        bt_get_camera.setOnClickListener(this);
        bt_chose_album.setOnClickListener(this);
    }


    /**
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_get_camera:
                Toast.makeText(this, "camera", Toast.LENGTH_SHORT).show();
                takePhoto();
                break;

            case R.id.bt_chose_album:
                Toast.makeText(this, "chose ", Toast.LENGTH_SHORT).show();
                chosePhoto();

                break;
        }

    }

    /**
     * 从相册选择图片
     * 1. 检查权限
     * 2. 打开相册
     * 3.
     */
    private void chosePhoto() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        } else {

            openAlbum();
        }


    }

    /**
     * 打开相册
     */
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOSE_PHOTE);
    }

    private Uri imageUri;

    /**
     * 调用摄像头拍照
     */
    private void takePhoto() {
        File outputImage = new File(getExternalCacheDir(), "output_image.jpg");

        if (outputImage.exists()) {
            outputImage.delete();
        }
        try {
            outputImage.createNewFile();

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(MainActivity.this, "com.example.cameraalbumtest.fileprovider", outputImage);
            Log.d("MainActivity", "Build.VERSION.SDK_INT:" + Build.VERSION.SDK_INT);
        } else {
            imageUri = Uri.fromFile(outputImage);
            Log.d("MainActivity", "Build.VERSION.SDK_INT:" + Build.VERSION.SDK_INT);
        }

        Log.d("MainActivity", "outputImage:" + outputImage.toString());

        //启动相机
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {
            case TAKE_PHOTO: //请求码是TAKE_PHOTO
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));

                        iv_img.setImageBitmap(bitmap);//设置显示图片

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;


            case CHOSE_PHOTE:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {//4.4及以上系统
                        handleImageOnKitKat(data);

                    } else {//4.4  及以下系统
                        handleImageBeforeKitKat(data);

                    }
                }
                break;
        }

    }

    /**
     * 4.4 以前系统处理图片方法
     * //Intent { dat=content://com.android.providers.media.documents/document/image:11 flg=0x1 }
     * @param data
     */
    private void handleImageOnKitKat(Intent data) {

        String imagePath = null;
        Uri uri = data.getData();


        if (DocumentsContract.isDocumentUri(MainActivity.this, uri)) {//如果是doc类型的uri
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equalsIgnoreCase(uri.getAuthority())) {
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);

            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }

        } else if ("content".equalsIgnoreCase(uri.getScheme())) {//如果是content类型的uri
            imagePath = getImagePath(uri, null);

        } else if ("file".equalsIgnoreCase(uri.getScheme())) {            //如果是file 类型的uri
            imagePath = uri.getPath();

        }

        displayImage(imagePath);
    }

    /**
     * 显示图片
     *
     * @param imagePath
     */
    private void displayImage(String imagePath) {

        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            iv_img.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "fail to get image", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取图片路径
     *
     * @param uri
     * @param selection
     * @return
     */
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过uri和selection 获取真实图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

            cursor.close();
        }
        return path;
    }


    /**
     * 4.4及以上系统处理图片
     *
     * @param data
     */
    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);

        displayImage(imagePath);
    }


}
