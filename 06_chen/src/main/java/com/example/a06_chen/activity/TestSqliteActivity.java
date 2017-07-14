package com.example.a06_chen.activity;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a06_chen.R;
import com.example.a06_chen.bean.Book;
import com.example.a06_chen.db.BookDatabaseHelper;

import org.xml.sax.ext.DeclHandler;

import java.util.ArrayList;
import java.util.List;

public class TestSqliteActivity extends AppCompatActivity implements View.OnClickListener {
    private Button bt_creat_sqdb;
    private Button bt_insert_book;
    private Button bt_delet_book;
    private Button bt_update_book;
    private Button bt_retieve_book;

    private EditText et_update_price;


    private EditText et_delete_id;
    private EditText et_update_id;
    private EditText et_insert_book_name;
    private EditText et_insert_book_author;
    private EditText et_insert_book_price;
    private EditText et_insert_book_pages;


    private TextView tv_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sqlite);

        bt_creat_sqdb = (Button) findViewById(R.id.bt_creat_sqdb);
        bt_insert_book = (Button) findViewById(R.id.bt_insert_book);
        tv_info = (TextView) findViewById(R.id.tv_info);

        bt_insert_book = (Button) findViewById(R.id.bt_insert_book);
        bt_delet_book = (Button) findViewById(R.id.bt_delete_book);
        bt_update_book = (Button) findViewById(R.id.bt_update_book);
        bt_retieve_book = (Button) findViewById(R.id.bt_retrieve_book);


        et_delete_id = (EditText) findViewById(R.id.et_delete_id);
        et_update_id = (EditText) findViewById(R.id.et_update_id);
        et_insert_book_name = (EditText) findViewById(R.id.et_insert_book_name);
        et_insert_book_author = (EditText) findViewById(R.id.et_insert_book_author);
        et_insert_book_price = (EditText) findViewById(R.id.et_insert_book_price);
        et_insert_book_pages = (EditText) findViewById(R.id.et_insert_book_pages);
        et_update_price = (EditText) findViewById(R.id.et_update_price);


        bt_creat_sqdb.setOnClickListener(this);
        bt_insert_book.setOnClickListener(this);
        bt_delet_book.setOnClickListener(this);
        bt_update_book.setOnClickListener(this);
        bt_retieve_book.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_creat_sqdb:
                createSqliteDataBase();
                break;
            case R.id.bt_insert_book:
                insertData();
                break;
            case R.id.bt_update_book:
                updateData();
                break;
            case R.id.bt_delete_book:
                deleteData();
                break;
            case R.id.bt_retrieve_book:
                retrieveData();
                break;

        }
    }


    private BookDatabaseHelper dbhelper;//数据库操作helper

    /**
     * 创建sqllite数据库
     */
    private void createSqliteDataBase() {
        Toast.makeText(this, "create", Toast.LENGTH_SHORT).show();
        dbhelper = new BookDatabaseHelper(this, "BookStore.db", null, 1);
        //dbhelper.getWritableDatabase();//当数据库不可写入时，出现异常
        dbhelper.getReadableDatabase();//当数据库不可写入时，返回对象以只读方式打开数据库

        tv_info.setText("create db:" + dbhelper.toString());

    }

    /**
     * 2. 向表中插入数据
     */
    private void insertData() {

        String bookname = et_insert_book_name.getText().toString();
        String bookauthor = et_insert_book_author.getText().toString();
        int bookpages = Integer.parseInt(et_insert_book_pages.getText().toString());
        float bookprice = Float.parseFloat(et_insert_book_price.getText().toString());

        Book book = new Book(bookname, bookauthor, bookpages, bookprice);
/*
        book.setName("The Da Vinci Code");
        book.setAuthor("DanBrown");
        book.setPages(454);
        book.setPrice((float) 23.78);
*/

        SQLiteDatabase db = null;

        if (dbhelper != null) {
            db = dbhelper.getWritableDatabase();
        } else {
            dbhelper = new BookDatabaseHelper(this, "BookStore.db", null, 1);
            db = dbhelper.getWritableDatabase();
        }


        ContentValues values = new ContentValues();
        values.put("name", book.getName());
        values.put("price", book.getPrice());
        values.put("author", book.getAuthor());
        values.put("pages", book.getPages());

        db.insert("Book", null, values);

        values.clear();

        tv_info.setText("insert data:" + book.toString());
    }


    /**
     * 更新book
     * 根据id更新book
     */
    private void updateData() {
        SQLiteDatabase db = null;

        if (dbhelper != null) {
            db = dbhelper.getWritableDatabase();
        } else {
            dbhelper = new BookDatabaseHelper(this, "BookStore.db", null, 1);
            db = dbhelper.getWritableDatabase();
        }

        //String []book_name =  new String[]{"The Da Vinci Code"};

        ContentValues values = new ContentValues();
        String price = et_update_price.getText().toString();
        values.put("price", price);


        String[] id = new String[]{et_update_id.getText().toString()};
        db.update("Book", values, "id=?", id);


        tv_info.setText("update:" + values.get("price"));
        values.clear();
    }

    /**
     * 删除book
     */
    private void deleteData() {
        SQLiteDatabase db = null;

        if (dbhelper != null) {

            db = dbhelper.getWritableDatabase();
        } else {
            dbhelper = new BookDatabaseHelper(this, "BookStore.db", null, 1);
            db = dbhelper.getWritableDatabase();
        }

        String[] id = new String[]{et_delete_id.getText().toString()};
        db.delete("Book", "id=?", id);

    }


    /**
     * 查询book
     */
    private void retrieveData() {
        SQLiteDatabase db = null;

        if (dbhelper != null) {

            db = dbhelper.getWritableDatabase();
        } else {
            dbhelper = new BookDatabaseHelper(this, "BookStore.db", null, 1);
            db = dbhelper.getWritableDatabase();
        }


        Cursor cursor = db.query("Book", null, null, null, null, null, null);

        List<Book> books = new ArrayList<>();

        String result = "";
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));

                String name = cursor.getString(cursor.getColumnIndex("name"));
                String author = cursor.getString(cursor.getColumnIndex("author"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));
                int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                Book book = new Book(name, author, pages, (float) price);
                books.add(book);

                result += id + ":" + book.toString() + "\n";


            } while (cursor.moveToNext());
        }

        tv_info.setText(result);

        Toast.makeText(this, "retrieve", Toast.LENGTH_SHORT).show();
    }
}
