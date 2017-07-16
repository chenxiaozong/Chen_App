package com.example.a08_2camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
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
    private Button bt_get_camera;
    private ImageView iv_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_get_camera = (Button) findViewById(R.id.bt_get_camera);
        iv_img = (ImageView) findViewById(R.id.iv_img);


        bt_get_camera.setOnClickListener(this);
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
        }

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
        }

    }
}
