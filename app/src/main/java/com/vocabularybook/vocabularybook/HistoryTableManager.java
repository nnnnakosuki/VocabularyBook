package com.vocabularybook.vocabularybook;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class HistoryTableManager {
    private HistoryDBHelper dbHelper;
    private String TBNAME;

    public HistoryTableManager(Context context) {
        dbHelper = new HistoryDBHelper(context);
        TBNAME = HistoryDBHelper.TB_NAME;
    }
    public void add(HistoryWord item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("vocabulary", item.getVocabulary());
        values.put("paraphrase", item.getParaphrase());
        values.put("date", item.getDate());
        db.insert(TBNAME, null, values);
        db.close();
    }

    public void addAll(List<HistoryWord> list){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (HistoryWord item : list) {
            ContentValues values = new ContentValues();
            values.put("vocabulary", item.getVocabulary());
            values.put("paraphrase", item.getParaphrase());
            values.put("date", item.getDate());
            db.insert(TBNAME, null, values);
        }
        db.close();
    }

    public void deleteAll(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME,null,null);
        db.close();
    }

    public void delete(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME, "ID=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void update(HistoryWord item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("vocabulary", item.getVocabulary());
        values.put("paraphrase", item.getParaphrase());
        values.put("date", item.getDate());
        db.update(TBNAME, values, "ID=?", new String[]{String.valueOf(item.getId())});
        db.close();
    }

    @SuppressLint("Range")
    public List<HistoryWord> listAll(){
        List<HistoryWord> wordList = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, null, null, null, null, null);
        if(cursor!=null){
            wordList = new ArrayList<HistoryWord>();
            while(cursor.moveToNext()){
                HistoryWord item = new HistoryWord();
                item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                item.setVocabulary(cursor.getString(cursor.getColumnIndex("vocabulary")));
                item.setParaphrase(cursor.getString(cursor.getColumnIndex("paraphrase")));
                item.setDate(cursor.getString(cursor.getColumnIndex("date")));
                wordList.add(item);
            }
            cursor.close();
        }
        db.close();
        return wordList;
    }

    @SuppressLint("Range")
    public HistoryWord findById(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, "ID=?", new String[]{String.valueOf(id)}, null, null, null);
        HistoryWord wordItem = null;
        if(cursor!=null && cursor.moveToFirst()){
            wordItem = new HistoryWord();
            wordItem.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            wordItem.setVocabulary(cursor.getString(cursor.getColumnIndex("vocabulary")));
            wordItem.setParaphrase(cursor.getString(cursor.getColumnIndex("paraphrase")));
            wordItem.setDate(cursor.getString(cursor.getColumnIndex("time")));
            cursor.close();
        }
        db.close();
        return wordItem;
    }
}
