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

public class Main3Activity extends AppCompatActivity {
 private ListView listView;
 private tianqi db1 = new tianqi(this, "city1.db", null,1);
 private String id =null;
 private String code=null;
 private String str1=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        listView=(ListView)findViewById(R.id.list1);
        final String a =getIntent().getStringExtra("name");
        SQLiteDatabase db= db1.getWritableDatabase();

        final ArrayList<String> list1 =new ArrayList<String>();
        Cursor cursor = db.rawQuery("select * from city where city_name =?",new String[]{a});
        if(cursor.moveToFirst()) {
            do {
                 id = cursor.getString(cursor.getColumnIndex("id"));
            } while (cursor.moveToNext());
        }
        final ArrayList<String> list =new ArrayList<String>();
        Cursor cursor1 = db.rawQuery("select * from city where pid =?",new String[]{id});
        if(cursor1.moveToFirst()){
            do{
                String name=cursor1.getString(cursor1.getColumnIndex("city_name"));
                list.add(name);
            }while (cursor1.moveToNext());
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(Main3Activity.this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SQLiteDatabase db= db1.getWritableDatabase();
                final ArrayList<String> list2 =new ArrayList<String>();
                final String list1=list.get(position);
                Cursor cursor2 = db.rawQuery("select city_code from city where city_name =?",new String[]{list1});
                if(cursor2.moveToFirst()){
                    do{
                         code=cursor2.getString(cursor2.getColumnIndex("city_code"));
                    }while (cursor2.moveToNext());
                }
                sendRequsetWithOkHttp2(code);

            }
        });
    }
    private void sendRequsetWithOkHttp2(final String code) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    String res = null;
                    OkHttpClient okHttpClient1 = new OkHttpClient();
                    String strur2 = " http://t.weather.sojson.com/api/weather/city/" + code;
                    Request request1 = new Request.Builder().url(strur2).build();
                    Response response1 = okHttpClient1.newCall(request1).execute();
                    String responseData = response1.body().string();
                    parseJSONWithGSON1(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithGSON1(String jsonData) {
        Gson gson = new Gson();
        SQLiteDatabase db2= db1.getWritableDatabase();
        WeatherBean wetherBean = gson.fromJson(jsonData, WeatherBean.class);
        String str1 = "地区为:" + wetherBean.getCityInfo().getCity() + "\n" + "日期为：" + wetherBean.getDate() + "\n" + "温度为：" + wetherBean.getData().getWendu() + "\n" + "更新时间为：" + wetherBean.getCityInfo().getUpdateTime() +
                "\n" + "湿度为：" + wetherBean.getData().getShidu() + "\n" + "PM2.5为：" + wetherBean.getData().getPm25();
        String ste3=wetherBean.getCityInfo().getCity();
        Intent intent=new Intent();
        intent.setClass(com.example.myapplication6.Main3Activity.this,Main5Activity.class);
        intent.putExtra("infor",str1);
        intent.putExtra("city",ste3);
        startActivity(intent);
    }
}
