package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.material.internal.NavigationMenu;

public class DataBase extends SQLiteOpenHelper {

    private static final String dbName = "mydb.db";
    private static final int version = 1;

    public DataBase(Context context){
        super(context, dbName, null, version);

    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql  = "CREATE TABLE PRODUCTS(NAME TEXT, PHONENUMBER TEXT, CALENDAR TEXT, TIME TEXT, E1 TEXT, E2 TEXT, E3 TEXT)";
        sqLiteDatabase.execSQL(sql);
    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists PRODUCTS");
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String name, String phoneNumber, String calendar, String time, String e1, String e2, String e3){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NAME",name);
        values.put("PHONENUMBER", phoneNumber);
        values.put("CALENDAR", calendar);
        values.put("TIME",time);
        values.put("E1",e1);
        values.put("E2",e2);
        values.put("E3",e3);
        long e= sqLiteDatabase.insert("PRODUCTS", null, values);
        if (e == -1){
            return false;
        }
        else{
            return true;
        }
    }
    public Cursor readall(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from PRODUCTS", null);
        return cursor;
    }
}
