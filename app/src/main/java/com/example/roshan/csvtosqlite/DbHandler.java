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


    public DbHandler(Context context) {
        super(context, database_name, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + table_name + "( Company integer,Name text,Price text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists" + table_name);
       // db.execSQL("ALTER TABLE "+ table_name +" ADD "+ col_1 +" INTEGER PRIMARY KEY AUTOINCREMENT");
        onCreate(db);
    }

     public void insertData(String[] Company, String[] Name, String[] Price){
        SQLiteDatabase db=this.getWritableDatabase();
    ContentValues values=new ContentValues();
    values.put(col_2, String.valueOf(Company));
    values.put(col_3, String.valueOf(Name));
    values.put(col_4, String.valueOf(Price));
         db.insert("cart",null,values);
//    int u = db.update("cart", values, "Company=?", new String[]{String.valueOf(Company)});
//    if (u == 0) {
//        db.insertWithOnConflict("cart", null, values, SQLiteDatabase.CONFLICT_REPLACE);
//    }
}
    public Cursor viewData(){
        SQLiteDatabase db=this.getWritableDatabase();
        //Cursor cr=db.rawQuery("select distinct item_name,item_price,quantity from " + table_name,null);
     Cursor cr =  db.rawQuery( "select * from cart ",null);

        if (cr != null) {
            cr.moveToFirst();
        }
      Log.d("Cursor Size ", cr.getCount() +"");
        cr.getCount();
        return cr;

    }

//    public ArrayList<HashMap<String, String>> getAllProducts() {
//        ArrayList<HashMap<String, String>> proList;
//        proList = new ArrayList<HashMap<String, String>>();
//        String selectQuery = "select _id as _id, Company, Name, Price from cart";
//        SQLiteDatabase database = this.getWritableDatabase();
//        Cursor cursor = database.rawQuery(selectQuery, null);
//        if (cursor.moveToFirst()) {
//            do {
//                //Id, Company,Name,Price
//                HashMap<String, String> map = new HashMap<String, String>();
//                map.put("Company", cursor.getString(1));
//                map.put("Name", cursor.getString(2));
//                map.put("Price", cursor.getString(3));
//                proList.add(map);
//            } while (cursor.moveToNext());
//        }
//
//        return proList;
//    }
}