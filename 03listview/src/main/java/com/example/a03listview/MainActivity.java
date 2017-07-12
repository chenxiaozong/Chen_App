package com.example.a03listview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a03listview.adapter.Fruit;
import com.example.a03listview.bean.FruitAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private List<Fruit> fruitList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


/* 方式一
        String []data = {"item1","item2","item3","item4","item5","item6"};

        ListView lv_list = (ListView) findViewById(R.id.lv_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,data);


        lv_list.setAdapter(adapter);

        float xdpi = getResources().getDisplayMetrics().xdpi;
        float ydpi = getResources().getDisplayMetrics().ydpi;

        Toast.makeText(this, "xdpi:" + xdpi, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "ydpi:" + ydpi, Toast.LENGTH_SHORT).show();
*/



/*使用自定义adapter
* */

        //1. 出事話數據
        initFruits();
        ListView lv_list = (ListView) findViewById(R.id.lv_list);

        //2 初始化adapter
        FruitAdapter fruitadapter = new FruitAdapter(MainActivity.this,R.layout.fruit_item,fruitList);



        //3. 设置adapter
        lv_list.setAdapter(fruitadapter);

    }

    /**
     * 初始化list数据
     */

    private void initFruits() {
        Fruit apple = new Fruit("Apple", R.drawable.apple_pic);
        fruitList.add(apple);
        Fruit banana = new Fruit("Banana", R.drawable.banana_pic);
        fruitList.add(banana);
        Fruit orange = new Fruit("Orange", R.drawable.orange_pic);
        fruitList.add(orange);
        Fruit watermelon = new Fruit("Watermelon", R.drawable.watermelon_pic);
        fruitList.add(watermelon);
        Fruit pear = new Fruit("Pear", R.drawable.pear_pic);
        fruitList.add(pear);
        Fruit grape = new Fruit("Grape", R.drawable.grape_pic);
        fruitList.add(grape);
        Fruit pineapple = new Fruit("Pineapple", R.drawable.pineapple_pic);
        fruitList.add(pineapple);
        Fruit strawberry = new Fruit("Strawberry", R.drawable.strawberry_pic);
        fruitList.add(strawberry);
        Fruit cherry = new Fruit("Cherry", R.drawable.cherry_pic);
        fruitList.add(cherry);
        Fruit mango = new Fruit("Mango", R.drawable.mango_pic);
        fruitList.add(mango);

    }
}
