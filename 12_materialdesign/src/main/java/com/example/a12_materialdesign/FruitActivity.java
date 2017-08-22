package com.example.a12_materialdesign;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


/**
 * 水果信息展示页面
 * 1. 使用可折叠式标题栏
 *
 */
public class FruitActivity extends AppCompatActivity {

    //fruit 信息
    public   static  final String FRUIT_NAME = "fruit_name";
    public   static  final String FRUIT_IMAGE_ID = "fruit_img_id";


    private String fruitName;
    private int fruitImgId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit);
        initData();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_fruit);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_tollbar_fruit);

        ImageView imgView = (ImageView) findViewById(R.id.iv_fruit);

        TextView textView = (TextView) findViewById(R.id.tv_fruit_content);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        collapsingToolbarLayout.setTitle(fruitName);
        Glide.with(this).load(fruitImgId).into(imgView);

        String fruitContent = generateFruitContent(fruitName);
        textView.setText(fruitContent);



    }


    /**
     * toolbar HomeAsUp按钮监听
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();

                return true;
        }

        return super.onOptionsItemSelected(item);

    }

    /**
     * 由fruitname 生成fruitcontent
     * @param fruitName
     * @return
     */
    private String generateFruitContent(String fruitName) {
        StringBuilder builder = new StringBuilder();


        for (int i=0;i<100;i++){
            builder.append(fruitName);
        }



        return builder.toString();
    }


    /**
     * 从intnt 中获取水果信息
     */
    private void initData() {
        Intent intent = getIntent();
         fruitName= intent.getStringExtra(FRUIT_NAME);
         fruitImgId =intent.getIntExtra(FRUIT_IMAGE_ID,0);

    }
}
