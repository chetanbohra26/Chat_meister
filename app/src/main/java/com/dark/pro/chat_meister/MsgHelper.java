package com.dark.pro.chat_meister;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Chetan & Rahul on 10-Nov-17.
 */

public class MsgHelper extends SQLiteOpenHelper{
    public MsgHelper(Context context) {
        super(context,"msg.db", null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table msg(id integer primary key autoincrement,name text,txtmsg text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists msg");
        onCreate(db);
    }

    public int saveData(String name,String message){
        int status=0;
        ContentValues cv=new ContentValues();
        cv.put("name",name);
        cv.put("txtmsg",message);
        SQLiteDatabase db=getWritableDatabase();
        long res=db.insert("msg",null,cv);
        if(res>0)
            status=1;
        return status;
    }

    public Cursor selectdata(String name){
        Cursor cursor;
        SQLiteDatabase db=getReadableDatabase();
        cursor=db.query("msg",new String[]{"txtmsg"},"name=?",new String[]{name},null,null,"id ASC");
        return cursor;
    }

    public void delete_all(){
        SQLiteDatabase db=getReadableDatabase();
        db.execSQL("drop table if exists msg");
        onCreate(db);
    }
}
