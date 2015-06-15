package com.example.rahul.sqlitetest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.provider.SyncStateContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rahul on 11/6/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME ="DATABASE_NAME";
    private static final int DATABASE_VERSION = 1;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEPARATOR = " ,";
    private static final String TABLE_CREATE_ENTRIES = "CREATE TABLE " + Table.TABLE_NAME + " (" + Table._ID + " INTEGER PRIMARY KEY," +
            Table.NAME + TEXT_TYPE + COMMA_SEPARATOR +
            Table.NUMBER + TEXT_TYPE + " )";
    ArrayList<HashMap<String,Object>> mData;

    public static abstract class Table implements BaseColumns {

        private static final String TABLE_NAME = "RECORDS_TABLE";
        private static final String NAME = "NAME";
        private static final String NUMBER = "NUMBER";
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS" + Table.TABLE_NAME);
        onCreate(db);
    }

    public void addData(){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Table.NAME,"RAHUL");
        contentValues.put(Table.NUMBER, "00000");
        db.insert(Table.TABLE_NAME, null, contentValues);
    }

    public void addData(String columnValue){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Table.NAME,columnValue);
        contentValues.put(Table.NUMBER, "00000");
        db.update(Table.TABLE_NAME, contentValues, null, null);
    }

    public HashMap<String,String> getData(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(Table.TABLE_NAME,null,null,null,null,null,null);
        HashMap<String,String> map = new HashMap<String,String>();
        if(cursor.moveToFirst()){
            int columnLength = cursor.getColumnCount();
            for(int i=0; i<columnLength; i++){
                map.put(cursor.getColumnName(i),cursor.getString(i));
            }
        }
        Log.v("MAP", map.toString());
        return map;
    }

    public String getData(String key){

        SQLiteDatabase db = getReadableDatabase();
        //Cursor cursor = db.query(Table.TABLE_NAME,null,null,null,null,null,null);
        String[] columns = {key};
        Cursor cursor = db.query(Table.TABLE_NAME,columns,null,null,null,null,null);
        String output = "";
        if(cursor.moveToFirst()){
            int columnLength = cursor.getColumnCount();
            for(int i=0; i<columnLength; i++){
                output = cursor.getString(i);
            }
        }
        return null;
    }

    public long insertData(String name,Integer number){

        SQLiteDatabase database = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(Table.NAME,name);
        contentValues.put(Table.NUMBER,number);

        long row_index = database.insert(Table.TABLE_NAME, null,contentValues);

        return row_index;
    }// end of insert data

    public void queryData(String name, Integer number ){

        SQLiteDatabase database = getReadableDatabase();

        String[] columns = { Table.NAME, Table.NUMBER};
        String[] selectionArgs = {name, Integer.toString(number)};
        Cursor cursor = database.query(Table.TABLE_NAME, //Table Name
                columns,    //Table Number
                Table.NAME + "=?" + " AND" + Table.NUMBER + "=?",
                selectionArgs,
                null,
                null,
                null
        );

        if(cursor.moveToFirst()){

            mData = new ArrayList<HashMap<String,Object>>();
            do{

                String name_stored = cursor.getString(cursor.getColumnIndexOrThrow(Table.NAME));
                int number_stored = cursor.getInt(cursor.getColumnIndexOrThrow(Table.NUMBER));

                HashMap<String,Object> map = new HashMap<String,Object>();
                map.put(Table.NAME,name_stored);
                map.put(Table.NUMBER,number_stored);

                mData.add(map);
                map = null;
            }while(cursor.moveToNext());// end of while
        }// end of if
        if(mData != null){

            Log.v("DATA",mData.toString());
        }
        else{

            Log.v("RESULT","SORRY BUT THIS LIST IS EMPTY");
        }

    }// end of query data function


    public void queryAllData(){

        SQLiteDatabase database = getReadableDatabase();


        Cursor cursor = database.query(Table.TABLE_NAME, //Table Name
                null,    //Table Number
                null,
                null,
                null,
                null,
                null
        );

        if(cursor.moveToFirst()){

            mData = new ArrayList<HashMap<String,Object>>();
            do{

                String name_stored = cursor.getString(cursor.getColumnIndexOrThrow(Table.NAME));
                int number_stored = cursor.getInt(cursor.getColumnIndexOrThrow(Table.NUMBER));

                HashMap<String,Object> map = new HashMap<String,Object>();
                map.put(Table.NAME,name_stored);
                map.put(Table.NUMBER,number_stored);

                mData.add(map);
                map = null;
            }while(cursor.moveToNext());// end of while
        }// end of if

        if(mData != null){

            Log.v("DATA",mData.toString());
        }
        else{

            Log.v("RESULT","SORRY BUT THIS LIST IS EMPTY");
        }

    }// end of query data function

    public int deleteData(){


        int result = -1;

        SQLiteDatabase database = getWritableDatabase();

        String whereClause = Table.NAME + " LIKE ?" + " AND " + Table.NUMBER + " LIKE ?";
        String[] whereArgs = {"Ram","0"};

        result = database.delete(Table.TABLE_NAME,whereClause,whereArgs);

        return result;
    }

    public int deleteAllData(){


        int result = -1;

        SQLiteDatabase database = getWritableDatabase();

        result = database.delete(Table.TABLE_NAME,null,null);

        return result;
    }

    public int updateData(){

        int result = -1;

        SQLiteDatabase database = getWritableDatabase();

        String whereClause = Table.NAME + "LIKE ?";

        String[] selectionArgs = {"Ram"};

        ContentValues contentValues = new ContentValues();
        contentValues.put(Table.NAME,"Shyam");

        int count = database.update(Table.TABLE_NAME,contentValues,whereClause,selectionArgs);

        return result;

    }

}
