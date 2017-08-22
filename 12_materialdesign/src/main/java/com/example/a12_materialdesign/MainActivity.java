package com.example.a12_materialdesign;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.a12_materialdesign.adapter.FruitAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    //水果数据
    private Fruit[] fruits = {new Fruit("Apple", R.drawable.apple), new Fruit("Banana", R.drawable.banana),
            new Fruit("Orange", R.drawable.orange), new Fruit("Watermelon", R.drawable.watermelon),
            new Fruit("Pear", R.drawable.pear), new Fruit("Grape", R.drawable.grape),
            new Fruit("Pineapple", R.drawable.pineapple), new Fruit("Strawberry", R.drawable.strawberry),
            new Fruit("Cherry", R.drawable.cherry), new Fruit("Mango", R.drawable.mango)};
    private List<Fruit> fruitList = new ArrayList<>();




    private DrawerLayout drawerLayout ;


    //下拉刷新
    private SwipeRefreshLayout swipeRefreshLayout ;
    private FruitAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar tool_bar = (Toolbar) findViewById(R.id.tool_bar);

        setSupportActionBar(tool_bar);//设置actionbar



        //侧滑按钮
        drawerLayout = (DrawerLayout) findViewById(R.id.dl_draylayout);


        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true);//HomeAsUp 按钮,默认图标是返回的箭头

            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);//设置按钮的图标
        }



        //侧滑menu nvagationview
        NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);


        nav_view.setCheckedItem(R.id.nva_call);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();
                return true;
            }
        });


        //初始化flatingactionButton 控件
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_main);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "FloatingActionButton", Toast.LENGTH_SHORT).show();
                //2. 使用SnackBar 提示
                Snackbar.make(view,"Delete Selected",Snackbar.LENGTH_SHORT)
                        .setAction("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this, "取消删除", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });


        //卡片布局
        initFruit();//初始化frui数据
        //设置recycleview 的adapter
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_recyclerview_main);

        //设置布局方式
        GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this,2);//按两列方式展示
        recyclerView.setLayoutManager(layoutManager);

        //设置adapter
        adapter = new FruitAdapter(MainActivity.this,fruitList);
        recyclerView.setAdapter(adapter);





        //下拉刷新
        swipeRefreshLayout  = (SwipeRefreshLayout) findViewById(R.id.srl_main_refresh);
        swipeRefreshLayout.setColorSchemeColors(Color.RED);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        });




    }


    /**
     * 下拉刷新
     */
    private void refreshFruits() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(5000);//模拟网络延迟
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //2. 刷新数据
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruit();
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);

                    }
                });


            }
        }).start();
    }


    /**
     * 初始化fruit数据
     */
    private void initFruit() {

        fruitList.clear();

        for (int i=0; i<50 ;i++){
            Random random = new Random();
            int index = random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
        }

    }


    /**
     * 加载menu布局
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.back :
                Toast.makeText(this, "back", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:
                Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home://HomeAsUp 图标的默认id永远是anroid.R.i.home
                drawerLayout.openDrawer(GravityCompat.START);

                break;

        }
        return true;
    }
}
