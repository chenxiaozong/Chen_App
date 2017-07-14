package com.example.a06_chen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.a06_chen.activity.FileDate;
import com.example.a06_chen.activity.ReadFileData;
import com.example.a06_chen.activity.SaveToSP;
import com.example.a06_chen.activity.TestSqliteActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button bt_1_file_save;
    private Button bt_2_file_save;
    private Button bt_3_file_save;
    private Button bt_4_file_save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_1_file_save = (Button) findViewById(R.id.bt_1_file_save);
        bt_2_file_save = (Button) findViewById(R.id.bt_2_file_save);
        bt_3_file_save = (Button) findViewById(R.id.bt_3_file_save);
        bt_4_file_save = (Button) findViewById(R.id.bt_4_file_save);


        bt_1_file_save.setOnClickListener(this);
        bt_2_file_save.setOnClickListener(this);
        bt_3_file_save.setOnClickListener(this);
        bt_4_file_save.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_1_file_save:
                Intent intent = new Intent(MainActivity.this, FileDate.class);
                startActivity(intent);

                break;
            case R.id.bt_2_file_save:
                intent = new Intent(MainActivity.this, ReadFileData.class);
                startActivity(intent);
                break;
            case R.id.bt_3_file_save:
                intent = new Intent(MainActivity.this, SaveToSP.class);
                startActivity(intent);
                break;
            case R.id.bt_4_file_save:
                intent = new Intent(MainActivity.this, TestSqliteActivity.class);
                startActivity(intent);
                break;

        }
    }
}
