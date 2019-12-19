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
import java.util.ArrayList;
public class Main6Activity extends AppCompatActivity {
    private ListView listView;
    private tianqi db1 = new tianqi(this, "city1.db", null,1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        listView=(ListView)findViewById(R.id.list1);
        SQLiteDatabase db= db1.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from city where city_code is  ''and pid =0",new String[]{});
        final ArrayList<String> list =new ArrayList<String>();
        if(cursor.moveToFirst()){
            do{
                String name = cursor.getString(cursor.getColumnIndex("city_name"));
                list.add(name);
            }while (cursor.moveToNext());
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(Main6Activity.this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String list1=list.get(position);
                Intent intent=new Intent();
                intent.setClass(com.example.myapplication6.Main6Activity.this,Main7Activity.class);
                intent.putExtra("name",list1);
                startActivity(intent);
            }
        });
    }
}