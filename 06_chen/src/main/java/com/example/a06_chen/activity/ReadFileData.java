package com.example.a06_chen.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.a06_chen.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * 从应用包下的文件中读取数据
 */

public class ReadFileData extends AppCompatActivity {

    private TextView tv_filedata_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_file_data);
        tv_filedata_show = (TextView) findViewById(R.id.tv_filedata_show);


        String content = readDataFromFile();
        tv_filedata_show.setText(content);

    }

    /**
     * 从包下的 data 文件中读取数据
     */
    private String readDataFromFile() {
        FileInputStream in = null;
        BufferedReader reader = null;

        StringBuilder content = new StringBuilder();

        try {
            in = openFileInput("data");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";

            while ((line = reader.readLine()) != null) {
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


}
