package com.example.a06_chen.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a06_chen.MainActivity;
import com.example.a06_chen.R;


/**
 * 保存数据到sharepreference
 */
public class SaveToSP extends AppCompatActivity implements View.OnClickListener {
    private  EditText et_input_name;
    private  EditText et_input_age;
    private  EditText et_input_bl;

    private Button bt_save;
    private Button bt_load;
    private Button bt_clear;
    private Button bt_clear_sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_to_sp);
        et_input_name = (EditText)findViewById(R.id.et_input_name);
        et_input_age = (EditText)findViewById(R.id.et_input_age);
        et_input_bl = (EditText)findViewById(R.id.et_input_bl);

        bt_save = (Button)findViewById(R.id.bt_save);
        bt_load = (Button)findViewById(R.id.bt_load);
        bt_clear = (Button)findViewById(R.id.bt_clear);
        bt_clear_sp = (Button)findViewById(R.id.bt_clear_sp);

        bt_save.setOnClickListener(this);
        bt_load.setOnClickListener(this);
        bt_clear.setOnClickListener(this);
        bt_clear_sp.setOnClickListener(this);



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveDataToSP();

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_save:
                Log.d("SaveToSP", "save");
                saveDataToSP();

                break;
            case R.id.bt_load:
                loadDataFromSP();
                Log.d("SaveToSP", "load");
                break;
            case R.id.bt_clear:
                clearEditText();
                Log.d("SaveToSP", "clear");
                break;
            case R.id.bt_clear_sp:
                clearSP();
                Log.d("SaveToSP", "clear_sp");
                break;
        }

    }

    /**
     * 1. 保存数据到sp
     * > 获取sp.editor
     * > 准备数据
     * > 保存数据
     * */

    private void saveDataToSP() {

        SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
        String name= et_input_name.getText().toString();

        int age = 0;

        if(!et_input_age.getText().toString().equals("")) {
            age = Integer.parseInt( et_input_age.getText().toString());
        }
        boolean male = true;

        if(!et_input_bl.getText().toString().equals("")) {
                male = Boolean.parseBoolean(et_input_bl.getText().toString());
        }


        editor.putString("name",name);
        editor.putInt("age",age);
        editor.putBoolean("male",male);
        editor.apply();

        Toast.makeText(this, "savedata to sp", Toast.LENGTH_SHORT).show();

    }

    /**
     * 2. 从sp中读取数据
     * 1. 获取sp
     * 2. 按照键值对的方式读取数据
     */
    private void loadDataFromSP() {
        SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);
        String name = sp.getString("name","null");
        int age = sp.getInt("age", 0);
        boolean male = sp.getBoolean("male", false);

        et_input_name.setText(name);
        et_input_age.setText(age+"");
        et_input_bl.setText(male+"");

    }



    /**
     * 3. 清空文本框中的内容
     */
    private void clearEditText() {
        et_input_bl.setText("");
        et_input_age.setText("");
        et_input_name.setText("");
    }
    /**
     * 4. 清空sp中的数据
     */
    private void clearSP() {
        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        editor.clear();//clear sp
        editor.apply();

    }

}
