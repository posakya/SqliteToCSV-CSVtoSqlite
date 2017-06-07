package com.example.roshan.csvtosqlite;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;


import au.com.bytecode.opencsv.CSVWriter;

import static com.example.roshan.csvtosqlite.DbHandler.col_2;
import static com.example.roshan.csvtosqlite.DbHandler.col_3;
import static com.example.roshan.csvtosqlite.DbHandler.col_4;
import static com.example.roshan.csvtosqlite.DbHandler.table_name;

public class MainActivity extends AppCompatActivity {

    TextView txt_cursor;
    DbHandler db1 ;
//    Button btnimport;
//    ListView lv;
//    final Context context = this;
//    ListAdapter adapter;
//    ArrayList<HashMap<String, String>> myList;
//    public static final int requestcode = 1;
public static final int requestcode = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_cursor=(TextView)findViewById(R.id.txt_cursor);
        db1=new DbHandler(getApplicationContext());
        System.out.println("Table :"+table_name);
        exportDataBaseIntoCSV();
//        try {
//
//            startActivityForResult();
//
//        } catch (ActivityNotFoundException e) {
//
//            lbl.setText("No activity can handle picking a file. Showing alternatives.");
//
//        }
        db1 = new DbHandler(getApplicationContext());
        String mCSVfile = "text.csv";
        System.out.println("Text file :" +mCSVfile);
        AssetManager manager = MainActivity.this.getAssets();

        InputStream  inputStream = null;
        try {
            inputStream = manager.open(mCSVfile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert inputStream != null;

        BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";

        try {
            while ((line = buffer.readLine()) != null) {
                String[] colums = line.split(",");
                System.out.println("Column :"+colums);
                db1.insertData(colums,colums,colums);
                System.out.println("Inserting :");
                Cursor cursor=db1.viewData();
                System.out.println("Cursor size :"+cursor.getCount());

                txt_cursor.setText("Cursor Size :"+cursor.getCount());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //sqlite file to CSV

    public void exportDataBaseIntoCSV(){


        DbHandler db = new DbHandler(getApplicationContext());//here Dbhandler is my database. you can create your db object.
        File exportDir = new File(Environment.getExternalStorageDirectory(), "");

        if (!exportDir.exists())
        {
            exportDir.mkdirs();
        }

        File file = new File(exportDir, "csvfilename.csv"+System.currentTimeMillis());

        try
        {
            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
            SQLiteDatabase sql_db = db.getReadableDatabase();//here create a method ,and return SQLiteDatabaseObject.getReadableDatabase();
            Cursor curCSV = sql_db.rawQuery("SELECT * FROM "+DbHandler.table_name,null);
            csvWrite.writeNext(curCSV.getColumnNames());

            while(curCSV.moveToNext())
            {
                //Which column you want to export you can add over here...
                String arrStr[] ={curCSV.getString(0),curCSV.getString(1), curCSV.getString(2)};
                csvWrite.writeNext(arrStr);
            }

            csvWrite.close();
            curCSV.close();
        }
        catch(Exception sqlEx)
        {
            Log.e("Error:", sqlEx.getMessage(), sqlEx);
        }
    }
    }
