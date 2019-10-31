package com.example.wordnotes.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;


public class MydatabaseHelper extends SQLiteOpenHelper {

    public static final String CREAT_NOTEBOOK = "create table Notebook ("
            + "id integer primary key autoincrement,"
            + "word text,"
            + "translation text,"
            + "example text)";

    Context mContext;

    public MydatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAT_NOTEBOOK);
        Toast.makeText(mContext, "Create Successful", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }


}
