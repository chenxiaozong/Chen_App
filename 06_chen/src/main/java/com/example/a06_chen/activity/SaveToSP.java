package com.example.a06_chen.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a06_chen.MainActivity;
import com.example.a06_chen.R;

public class SaveToSP extends AppCompatActivity {
    private  EditText et_input_name;
    private  EditText et_input_age;
    private  EditText et_input_bl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_to_sp);
        et_input_name = (EditText)findViewById(R.id.et_input_name);
        et_input_age = (EditText)findViewById(R.id.et_input_age);
        et_input_bl = (EditText)findViewById(R.id.et_input_bl);



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        saveDataToSP();
    }

    /**
     * 保存数据到sp
     * 1. 获取sp
     * 2.
     * */

    private void saveDataToSP() {

        SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
        String content = et_input_name.getText().toString();
        String age = et_input_age.getText().toString();
        String bl = et_input_bl.getText().toString();


        editor.putString("content",content);


        Toast.makeText(this, "savedata to sp", Toast.LENGTH_SHORT).show();

    }
}
