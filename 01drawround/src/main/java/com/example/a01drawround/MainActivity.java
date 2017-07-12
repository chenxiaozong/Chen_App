package com.example.a01drawround;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button bt_1;
    private Button bt_2;
    private Button bt_3;
    private Button bt_4;
    private LinearLayout ll_roundDrawable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ll_roundDrawable = (LinearLayout)findViewById(R.id.ll_roundDrawable);

        bt_1 = (Button) findViewById(R.id.bt_1);

        bt_2 = (Button) findViewById(R.id.bt_2);
        bt_2.setOnClickListener(this);


        bt_3 = (Button) findViewById(R.id.bt_3);

        //第三种方式：使用运行是监听类
        bt_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "按钮3", Toast.LENGTH_SHORT).show();
            }
        });



        //第四种方式：创建监听类 事项监听方法
        bt_4 = (Button) findViewById(R.id.bt_4);
        bt_4.setOnClickListener(new ButtonLister());

    }


    //第一种方式响应点击事件：xml 中按钮添加onclick 属性
    public void buttonAction(View view) {
        Toast.makeText(this, "按钮1", Toast.LENGTH_SHORT).show();
    }


    //第二种方式响应点击事件 当前Activity 实现onclik 方法
    @Override
    public void onClick(View v) {
        if (v == bt_2) {
            Toast.makeText(this, "按钮2", Toast.LENGTH_SHORT).show();
        }
    }


    //4. 创建监听类

    class ButtonLister implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Toast.makeText(MainActivity.this, "按钮4", Toast.LENGTH_SHORT).show();

        }
    }

}
