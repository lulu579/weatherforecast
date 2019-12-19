package com.example.myapplication6;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Main11Activity extends AppCompatActivity {
    private static final String FLIENAME = "shoucang";
    private SharedPreferences shared_prefs;
    private ListView listView;
    private tianqi db1 = new tianqi(this, "city1.db", null,1);
    private String id1=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main11);
        listView=(ListView) findViewById(R.id.text);
        shared_prefs = getSharedPreferences(FLIENAME, MODE_PRIVATE);
        SharedPreferences.Editor edit = shared_prefs.edit();
        ArrayList<String> list=new ArrayList<String>();
        final String str1 =shared_prefs.getString("code","无收藏记录");
        list.add(str1);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(Main11Activity.this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);
        SQLiteDatabase db = db1.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from city where city_name =?", new String[]{str1});
        if (cursor.moveToFirst()) {
            do {
                id1= cursor.getString(cursor.getColumnIndex("city_code"));
            } while (cursor.moveToNext());
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sendRequsetWithOkHttp2(id1);
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
        Intent intent=new Intent();
        intent.setClass(com.example.myapplication6.Main11Activity.this,MainActivity.class);
        intent.putExtra("infor",str1);
        startActivity(intent);
    }
}
