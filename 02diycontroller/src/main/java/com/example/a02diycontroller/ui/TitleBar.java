package com.example.a02diycontroller.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.a02diycontroller.R;

/**
 * Created by chen on 2017/6/27.
 */

public class TitleBar extends LinearLayout implements View.OnClickListener {
    private Button bt_title_back;
    private Button bt_title_edit;
    private Button  tv_title_text;

    private Context mycontext;
    public TitleBar(Context context) {
        super(context);
        Log.d("TitleBar", "FirsConstructor:====");
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mycontext = context;

        LayoutInflater.from(context).inflate(R.layout.diy_title,this);

        bt_title_back = (Button) findViewById(R.id.bt_title_back);
        bt_title_edit = (Button) findViewById(R.id.bt_title_edit);

        bt_title_back.setOnClickListener(this);
        bt_title_edit.setOnClickListener(this);


    }

    public TitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d("TitleBar", "3FirsConstructor:====");
    }


    @Override
    public void onClick(View v) {
        if(v==bt_title_back) {
            Toast.makeText(mycontext, "返回", Toast.LENGTH_SHORT).show();
        }else if(v ==bt_title_edit) {
            Toast.makeText(mycontext, "编辑", Toast.LENGTH_SHORT).show();
        }
    }
}
