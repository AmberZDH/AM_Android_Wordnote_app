package com.example.wordnotes.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class Optsql {

    private MydatabaseHelper dbHelper;
    private SQLiteDatabase db;

    /**
     * @param context Context
     * @param name    String
     * @param factory SQLiteDatabase.CursorFactory
     * @param version Integer
     */
    public Optsql(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        this.dbHelper = new MydatabaseHelper(context, name, factory, version);
        this.db = this.dbHelper.getWritableDatabase();
    }


    /**
     * insert 操作
     * 插入一个字段 单词+翻译+例句
     *
     * @param word
     * @param translation
     * @param example
     * @return
     */
    public void insertValues(String word, String translation, String example) {

        ContentValues con = new ContentValues();

        try {

            con.put("word", word);
            con.put("translation", translation);
            con.put("example", example);
            db.insert("Notebook", null, con);
            System.out.println("Insert Successful !");


        } catch (Exception e) {
            System.out.println("ERROR : Sql Insert Failed ");
        }
    }

    /**
     * 查询某个单词字段
     *
     * @param word
     * @return
     */
    public String[] selectValues(String word) {
        String[] result = new String[3];
        try {
            Cursor cursor = db.query("Notebook", new String[]{"word", "translation", "example"}, "word=?", new String[]{word}, null, null, null);
            cursor.moveToFirst();
            result[0] = cursor.getString(cursor.getColumnIndex("word"));
            result[1] = cursor.getString(cursor.getColumnIndex("translation"));
            result[2] = cursor.getString(cursor.getColumnIndex("example"));
            cursor.close();
            System.out.println("Sql Select Successful ! ");

            return result;

        } catch (Exception e) {
            System.out.println("ERROR : Sql Select Failed ");
            return null;
        }
    }

    /**
     * 模糊查询
     * 返回值为二维数组
     * 第一维存单词，第二维度存单词详细信息
     *
     * @param fuzzyword
     * @return
     */
    public String[][] selectFuzzy(String fuzzyword) {
        String[][] result = new String[10][2];
        try {
            Cursor cursor = db.query("Notebook", new String[]{"word", "translation", "example"}, "word=?", new String[]{fuzzyword}, null, null, null);
            int i = 0;
            while (cursor.moveToNext()) {
                result[i][0] = cursor.getString(cursor.getColumnIndex("word"));
                result[i][1] = cursor.getString(cursor.getColumnIndex("translation"));
                result[i][2] = cursor.getString(cursor.getColumnIndex("example"));
                i++;
            }
            cursor.close();
            System.out.println("Sql Fuzzy Select Successful ! ");

            return result;
        } catch (Exception e) {
            System.out.println("ERROR : Sql Fuzzy Select Failed ");
            return null;
        }
    }
}