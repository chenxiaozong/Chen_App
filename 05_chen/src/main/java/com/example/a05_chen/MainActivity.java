package com.example.a05_chen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.a05_chen.LogTest.LoginActivity;
import com.example.a05_chen.broadcast1.BroadCast1Activity;
import com.example.a05_chen.broadcast2.BootCompleteRecevier;
import com.example.a05_chen.broadcast3.BroadCast3Activity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_button1;
    private Button bt_button2;
    private Button bt_button3;
    private Button bt_button4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_button1 = (Button) findViewById(R.id.bt_button1);
        bt_button2 = (Button) findViewById(R.id.bt_button2);
        bt_button3 = (Button) findViewById(R.id.bt_button3);
        bt_button4 = (Button) findViewById(R.id.bt_button4);

        bt_button1.setOnClickListener(this);
        bt_button2.setOnClickListener(this);
        bt_button3.setOnClickListener(this);
        bt_button4.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){

        case R.id.bt_button1:
            Intent intent = new Intent(MainActivity.this,BroadCast1Activity.class);

            startActivity(intent);

        Toast.makeText(this, "BUTTON1", Toast.LENGTH_SHORT).show();

        break;
        case R.id.bt_button2:
        Toast.makeText(this, "BUTTON2", Toast.LENGTH_SHORT).show();

        break;
        case R.id.bt_button3:
            intent = new Intent(MainActivity.this, BroadCast3Activity.class);
            startActivity(intent);
        Toast.makeText(this, "BUTTON3", Toast.LENGTH_SHORT).show();

        break;
        case R.id.bt_button4:
            intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);

        break;
        }
    }
}
