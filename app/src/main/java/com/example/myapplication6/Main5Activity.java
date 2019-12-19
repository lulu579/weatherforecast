package com.example.myapplication6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Main5Activity extends AppCompatActivity {
private TextView textView;
private Button button1;
private Button button2;
private String id =null;
private static final String FLIENAME = "shoucang";
private SharedPreferences shared_prefs;
private tianqi db1 = new tianqi(this, "city1.db", null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        shared_prefs = getSharedPreferences(FLIENAME, MODE_PRIVATE);
        textView =(TextView)findViewById(R.id.tianqi);
        button1 =(Button)findViewById(R.id.refresh);
        button2=(Button)findViewById(R.id.shoucang);
        final String a =getIntent().getStringExtra("infor");
        final String b=getIntent().getStringExtra("city");
        SQLiteDatabase db= db1.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from city where city_name =?",new String[]{b});
        if(cursor.moveToFirst()) {
            do {
                id = cursor.getString(cursor.getColumnIndex("city_code"));
            } while (cursor.moveToNext());
        }
        textView.setText(a);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequsetWithOkHttp2(id);
                Toast.makeText(Main5Activity.this, "刷新成功", Toast.LENGTH_SHORT).show();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor edit = shared_prefs.edit();
                edit.putString("code",b);
                edit.apply();
                Toast.makeText(Main5Activity.this, "收藏成功", Toast.LENGTH_SHORT).show();
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
        WeatherBean wetherBean = gson.fromJson(jsonData, WeatherBean.class);
        String str1 = "地区为:" + wetherBean.getCityInfo().getCity() + "\n" + "日期为：" + wetherBean.getDate() + "\n" + "温度为：" + wetherBean.getData().getWendu() + "\n" + "更新时间为：" + wetherBean.getCityInfo().getUpdateTime() +
                "\n" + "湿度为：" + wetherBean.getData().getShidu() + "\n" + "PM2.5为：" + wetherBean.getData().getPm25();
        textView.setText(str1);
    }

}
