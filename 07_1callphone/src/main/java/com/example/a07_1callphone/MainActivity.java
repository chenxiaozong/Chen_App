package com.example.a07_1callphone;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 测试运行时权限
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_call_phone;
    private EditText et_phone_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_call_phone = (Button) findViewById(R.id.bt_call_phone);
        et_phone_number = (EditText) findViewById(R.id.et_phone_number);

        bt_call_phone.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bt_call_phone) {
            callPhone();
            Toast.makeText(this, "call", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * callphone function
     */
    private void callPhone() {

        /**
         * 运行时权限的检查与申请
         */
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);

        } else {
            Intent inten = new Intent(Intent.ACTION_CALL);
            inten.setData(Uri.parse("tel:" + et_phone_number.getText().toString()));
            startActivity(inten);
        }
    }

}
