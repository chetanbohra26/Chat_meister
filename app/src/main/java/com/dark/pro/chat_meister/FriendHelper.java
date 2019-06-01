package com.dark.pro.chat_meister;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Chetan & Rahul on 10-Nov-17.
 */

public class FriendHelper extends SQLiteOpenHelper {
    public FriendHelper(Context context) {
        super(context, "friends.db",null ,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table friends(id integer primary key autoincrement,name text,address text unique)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists friends");
    }

    public int saveData(String name,String address){
        int status=0;
        ContentValues cv=new ContentValues();
        cv.put("name",name);
        cv.put("address",address);
        SQLiteDatabase db=getWritableDatabase();
        long res=db.insert("friends",null,cv);
        if(res>0)
            status=1;
        return status;
    }

    public Cursor selectdata(){
        Cursor cursor;
        SQLiteDatabase db=getReadableDatabase();
        cursor=db.query("friends",new String[]{"name"},null,null,null,null,null);

        return cursor;
    }

    public void delete_all() {
        SQLiteDatabase db=getReadableDatabase();
        db.execSQL("drop table if exists friends");
        onCreate(db);
    }
}
