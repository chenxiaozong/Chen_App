Android数据存储  

[TOC]  
---

> 简介：数据持久化就是将内存中的瞬时数据保存到存储设备中，保证及时在设备关机的情况下数据仍然不丢失。


## 1. 文件存储

> 是Android设备中最基本的存储方式，不对存储的内容进行任何格式化处理，所有数据原封不动的保存到文件中。

### 1.1 将数据保存到文件中

```java
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
```
>效果：

![Image Title](save1.gif) 

### 1.2 从文件中读取数据

>从文件中读取数据: 将第一步保存的数据读取出来显示到textview中


>code

```java

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

        if(content!=null&&!content.equals("")) {
            return content.toString();
        }else {
            return "no data";
        }

   
}

```


>效果

![Image Title](read1.gif) 


### 1.3 加载布局时初始化数据

> 加载布局时，从文件中加载数据

```java

    /**
     * 加载布局后从本地文件初始化数据
     */
    private void initData() {
        String content = readDataFromFile();
        if(!content.equals("")) {
            et_input_content.setText(content);
        }
    }
```


```java
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
```

>效果：

![Image Title](re_read.gif) 



## 2. SharePrefrence

>简介：
sharepreferences 是采用键-值对的方式保存数据,保存数据时需要同时提供数据的值和其对应的键， 读取数据时同样根据数据的键读取数据的值。

### 1. 使用sp保存数据

> 步骤：
1. 获取sp.editor
2. 执行editor.putxxx操作
3. 执行editor.apply操作

```java
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
```


### 2. 读取sp中的数据

>步骤：
1. 获取sp
2. 执行sp.getXXX方法



```java
    private void loadDataFromSP() {
        SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);
        String name = sp.getString("name","null");
        int age = sp.getInt("age", 0);
        boolean male = sp.getBoolean("male", false);

        et_input_name.setText(name);
        et_input_age.setText(age+"");
        et_input_bl.setText(male+"");
    }
```


### 3. 清空sp文件中的数据

>步骤：
1. 获取sp
2. 执行clear操作
3. 执行apply操作

```java
    private void clearSP() {
        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        editor.clear();//clear sp
        editor.apply();
    }
```




### 4. 效果

![Image Title](sp_01.gif) 


## 3. 使用Sqlite数据库

>创建表语句

```sql
create table Book(
    id integer primary key autoincrement,
    author text,
    price real, 
    pages integer,
    name text)

```

### 1. android原生sqlite操作

#### 1. 创建helper类
>1. BookDatabaseHelper extends SQLiteOpenHelper 
2. 定义构造方法 BookDatabaseHelper（）
3. 在onCreate() 中执行创建表的sql语句


```java
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
```


#### 2. 创建数据库

>1. 创建dbhelper类
2. 执行getReadableDatabase()/getWriteableDatabase();

```java
    private BookDatabaseHelper dbhelper;//数据库操作helper
    private void createSqliteDataBase() {
        Toast.makeText(this, "create", Toast.LENGTH_SHORT).show();
        dbhelper = new BookDatabaseHelper(this,"BookStore.db",null,1);
        //dbhelper.getWritableDatabase();//当数据库不可写入时，出现异常
        dbhelper.getReadableDatabase();//当数据库不可写入时，返回对象以只读方式打开数据库

        tv_info.setText("create db:"+dbhelper.toString());

    }
```


#### 3. 向表中插入数据
> 

```java

    /**
     * 2. 向表中插入数据
     */
    private void insertData() {

        String bookname = et_insert_book_name.getText().toString();
        String bookauthor = et_insert_book_author.getText().toString();
        int bookpages = Integer.parseInt(et_insert_book_pages.getText().toString());
        float bookprice = Float.parseFloat(et_insert_book_price.getText().toString());

        Book book = new Book(bookname,bookauthor,bookpages,bookprice);

        SQLiteDatabase db = null;

        if(dbhelper!=null) {
                db = dbhelper.getWritableDatabase();
        }else {
            dbhelper = new BookDatabaseHelper(this,"BookStore.db",null,1);
            db = dbhelper.getWritableDatabase();
        }

        ContentValues values = new ContentValues();
        values.put("name",book.getName());
        values.put("price",book.getPrice());
        values.put("author",book.getAuthor());
        values.put("pages",book.getPages());

        db.insert("Book",null,values);
        values.clear();
    }
```



#### 4. 更新数据

```java


    /**
     * 更新book
     * 根据id更新book
     */
    private void updateData() {
        SQLiteDatabase db = null;
        if(dbhelper!=null) {
            db = dbhelper.getWritableDatabase();
        }else {
            dbhelper = new BookDatabaseHelper(this,"BookStore.db",null,1);
            db = dbhelper.getWritableDatabase();
        }

        ContentValues values = new ContentValues();
        String price = et_update_price.getText().toString();
        values.put("price",price);
        String[] id = new String[]{et_update_id.getText().toString()};
        db.update("Book",values,"id=?",id);
        values.clear();
    }
```





#### 5. 删除数据

```java
    private void deleteData() {
        SQLiteDatabase db= null;
        if(dbhelper!=null) {
        db = dbhelper.getWritableDatabase();
        }else {
            dbhelper = new BookDatabaseHelper(this,"BookStore.db",null,1);
            db = dbhelper.getWritableDatabase();
        }

        String [] id = new String[]{et_delete_id.getText().toString()};
        db.delete("Book","id=?",id);
    }
```

#### 6. 查询数据

```java
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

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));

                String name = cursor.getString(cursor.getColumnIndex("name"));
                String author = cursor.getString(cursor.getColumnIndex("author"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));
                int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                Book book = new Book(name, author, pages, (float) price);
                books.add(book);
            } while (cursor.moveToNext());
        }
    }
```


### 2. 使用dbhelper 执行sql语句 实现CRUD
> 添加数据

```java
 db.execSQL("insert into Book(name,author,pages,price)values(?,?,?,?)",new String[]{"BookName","author","pages","price"});
```

> 更新数据

```java
db.execSQL("update  Book set price =?where name = ?",new String[] {"10.99"},"BookName" );
```

>删除数据 

```java
db.execSQL("delete from Book where pages >？"，new String[]{"500"});
```

> 查询数据

```java
db.rawQuery("select * from Book",null);
```

### 3. 使用LitePal 操作数据库

> 简介： 
使用LitePal 是一个开源第三方数据库框架，采用了对象关系映射（ORM）的模式，并将我们平时开发常用的一些数据库功能进行封装，可以不用编写sql语句，就可以完成各种建表和CRUD操作；
项目主页地址：https://github.com/LitePalFramework/LitePal

#### 1. 使用步骤：
##### 1 添加依赖
>编辑app/build.gradle 文件，在dependencies 中添加如下内容：

```java
dependencies {
    compile 'org.litepal.android:core:1.5.1'
}
```

##### 2 配置 litepal.xml 文件

> 创建： app/src/main/assets/litepal.xml 

```xml
<?xml version="1.0" encoding="utf-8" ?>

<litepal>
    <dbname value="BookStore"></dbname>
    <version value="1"></version>
    <list></list>
</litepal>
```

##### 3 修改AndroidManifest.xml

....




