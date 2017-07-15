package com.example.a06_chen.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by chen on 2017/7/13.
 */

public class BookDatabaseHelper extends SQLiteOpenHelper {


    /**
     * 创建数据表语句；表名-Book
     */
    public  static  final  String CREATE_DATABASE_BOOK = "create table Book(" +
            "    id integer primary key autoincrement," +
            "    author text," +
            "    price real," +
            "    pages integer," +
            "    name text)";

    private Context mContext;


    public BookDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_DATABASE_BOOK);//执行sql语句，创建表
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
