package com.example.a06_chen.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a06_chen.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


/**
 * 保存数据到 应用下包下的文件
 */
public class FileDate extends AppCompatActivity {

    private EditText et_input_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_date);
        et_input_content = (EditText)findViewById(R.id.et_input_content);

        initData();

    }

    /**
     * 加载布局后从本地文件初始化数据
     */
    private void initData() {
        String content = readDataFromFile();
        if(!content.equals("")) {
            et_input_content.setText(content);
        }
    }

    /**
     * 从本地文件读取数据
     * @return
     */
    private String readDataFromFile() {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();

        try {
            in = openFileInput("data");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";

            while ((line = reader.readLine())!=null){
                content.append(line);
            }

            if(reader!=null) {
                reader.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



        if(content.toString().equals("")) {
            return "no data ";
        }else {
            return content.toString();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveEditDateToFile();
    }

    /**
     * 保存EditText的数据到文件
     */
    private void saveEditDateToFile() {

        String data = et_input_content.getText().toString();
        FileOutputStream out = null;
        BufferedWriter writer = null;

        //MODE_PRIVATE:同样文件名的时候，覆盖
        //MODE_APPEND:追加
        try {

            out = openFileOutput("data",MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(data);


            if(writer!=null) {
                writer.close();
            }

            Toast.makeText(this, "保存数据到'data'文件", Toast.LENGTH_SHORT).show();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
