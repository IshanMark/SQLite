package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DBHelper extends SQLiteOpenHelper {

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Userdetail(email TEXT primary key, name TEXT, contact TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists Userdetail");
    }

    public DBHelper(Context context) {
        super(context, "Userdata", null, 1);
    }

    public Boolean intertuserdata(String email, String name, String contact){
        SQLiteDatabase DB= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("email",email);
        contentValues.put("name",name);
        contentValues.put("contact",contact);

        long result= DB.insert("Userdetail",null,contentValues);
        if (result==-1){
            return false;
        }else {
            return true;
        }
    }

    public Boolean updateuserdata(String email, String name, String contact){
        SQLiteDatabase DB= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("name",name);
        contentValues.put("contact",contact);
        Cursor cursor= DB.rawQuery("Select * from Userdetail where email=?",new String[]{email});

        if(cursor.getCount()>0){
            long result= DB.update("Userdetail",contentValues,"email=?",new String[]{email});
            if (result==-1){
                return false;
            }else {
                return true;
            }
        }else
        {
            return false;
        }
    }

    public Boolean deleteuserdata(String email){
        SQLiteDatabase DB= this.getWritableDatabase();

        Cursor cursor= DB.rawQuery("Select * from Userdetail where email=?",new String[]{email});

        if(cursor.getCount()>0){
            long result= DB.delete("Userdetail","email=?",new String[]{email});
            if (result==-1){
                return false;
            }else {
                return true;
            }
        }else
        {
            return false;
        }
    }

    public Cursor getdata(){
        SQLiteDatabase DB= this.getWritableDatabase();
        Cursor cursor= DB.rawQuery("Select * from Userdetail",null);
        return cursor;
    }




}
