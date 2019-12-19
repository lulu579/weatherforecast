package com.example.myapplication6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Main7Activity extends AppCompatActivity {
    private ListView listView;
    private tianqi db1 = new tianqi(this, "city1.db", null, 1);
    private String id = null;
    private String code = null;
    private String str1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        listView = (ListView) findViewById(R.id.list1);
        final String a = getIntent().getStringExtra("name");
        SQLiteDatabase db = db1.getWritableDatabase();

        final ArrayList<String> list1 = new ArrayList<String>();
        Cursor cursor = db.rawQuery("select * from city where city_name =?", new String[]{a});
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getString(cursor.getColumnIndex("id"));
            } while (cursor.moveToNext());
        }
        final ArrayList<String> list = new ArrayList<String>();
        Cursor cursor1 = db.rawQuery("select * from city where pid =?", new String[]{id});
        if (cursor1.moveToFirst()) {
            do {
                String name = cursor1.getString(cursor1.getColumnIndex("city_name"));
                list.add(name);
            } while (cursor1.moveToNext());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Main7Activity.this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String list1=list.get(position);
                Intent intent=new Intent();
                intent.setClass(com.example.myapplication6.Main7Activity.this,Main3Activity.class);
                intent.putExtra("name",list1);
                startActivity(intent);
            }
        });
    }
}