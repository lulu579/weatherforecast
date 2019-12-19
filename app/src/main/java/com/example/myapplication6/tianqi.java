package com.example.myapplication6;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class tianqi extends SQLiteOpenHelper {
        private static final String TABLE_NAME = "city1";
        private Context sContext;
        public tianqi(Context context,String name,SQLiteDatabase.CursorFactory favtory,int version) {
            super(context, name, favtory, version);
            sContext=context;
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table " + TABLE_NAME + " ("
                    + "id text primary key,"
                    + "pid text,"
                    + "city_code text,"
                    + "city_name text,"
                    + "area_cod text,"
                    + "ctime text,"
                    + "post_code text)");
        }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
