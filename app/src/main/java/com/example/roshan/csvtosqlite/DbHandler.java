package com.example.roshan.csvtosqlite;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class DbHandler extends SQLiteOpenHelper {
    public static final String database_name="CSV";
    private Context context;
    //create table
    public static final String table_name="cart";
    public static final String col_2="Company";
    public static final String col_3="Name";
    public static final String col_4="Price";
    public static final String col_5="category";

    public static final String col_1=" _id";
    public DbHandler(Context context) {
        super(context, database_name, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + table_name + "( _id integer primary key autoincrement ,Company integer,Name text,Price text,category text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists" + table_name);
       // db.execSQL("ALTER TABLE "+ table_name +" ADD "+ col_1 +" INTEGER PRIMARY KEY AUTOINCREMENT");
        onCreate(db);
    }



    public long insertDB(String[] RowData)
    {

        SQLiteDatabase mDb=this.getWritableDatabase();
        long result = 0;

        ContentValues values = new ContentValues();
        values.put(col_2, RowData[0]);
        values.put(col_3, RowData[1]);
        values.put(col_4, RowData[2]);
        values.put(col_5, RowData[3]);
        result = mDb.insert(table_name, null, values);
        return result;
    }


        public Cursor viewData(){
        SQLiteDatabase db=this.getWritableDatabase();

        //Cursor cr=db.rawQuery("select distinct item_name,item_price,quantity from " + table_name,null);
   //   Cursor cr =  db.rawQuery( "select * from cart where category = '"+cat+"'",null);
        Cursor cr =  db.rawQuery( "select _id as _id, Company, Name, Price,category from cart ",null);
        if (cr != null) {
            cr.moveToFirst();
        }
      Log.d("Cursor Size ", cr.getCount() +"");
        cr.getCount();
        return cr;

    }

    public void Delete(){
        SQLiteDatabase db=this.getWritableDatabase();
       String del="delete from cart";
       db.execSQL(del);
    }


}