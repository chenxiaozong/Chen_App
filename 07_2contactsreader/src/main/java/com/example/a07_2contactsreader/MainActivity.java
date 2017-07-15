package com.example.a07_2contactsreader;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * 读取联系人数据
 */

public class MainActivity extends AppCompatActivity {

    private ListView lv_contact_list;

    private List<String> contactsList = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv_contact_list = (ListView) findViewById(R.id.lv_contact_list);

        setListViewAdapter();

        checkRuntimePermission();

        //readContacts();

    }

    /**
     * 检查运行时权限
     */
    private void checkRuntimePermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_CONTACTS},1);
        }else {
            readContacts();
        }
    }

    /**
     * 设置listview 的adapter
     */
    private void setListViewAdapter() {
        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, contactsList);
        lv_contact_list.setAdapter(adapter);
    }

    /**
     * 读取联系人数据
     */
    private void readContacts() {
        Cursor cussor = null;


        cussor  = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        if (cussor != null) {
            while (cussor.moveToNext()) {
                String name = cussor.getString(cussor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number = cussor.getString(cussor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contactsList.add(name + "\n" + number);
            }
            adapter.notifyDataSetChanged();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                    readContacts();
                }else {
                    Toast.makeText(this, "Refused to read Contacts!!!", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
}
