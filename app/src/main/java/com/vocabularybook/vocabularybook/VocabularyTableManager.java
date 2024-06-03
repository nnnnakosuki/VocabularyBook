package com.vocabularybook.vocabularybook;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class VocabularyTableManager {
    private DBHelper dbHelper;
    private String TBNAME;

    public VocabularyTableManager(Context context) {
        dbHelper = new DBHelper(context);
        TBNAME = DBHelper.TB_NAME;
    }
    public void add(Word item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("vocabulary", item.getVocabulary());
        values.put("paraphrase", item.getParaphrase());
        values.put("time", item.getTimes());
        db.insert(TBNAME, null, values);
        db.close();
    }

    public void addAll(List<Word> list){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (Word item : list) {
            ContentValues values = new ContentValues();
            values.put("vocabulary", item.getVocabulary());
            values.put("paraphrase", item.getParaphrase());
            values.put("time", item.getTimes());
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

    public void update(Word item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("vocabulary", item.getVocabulary());
        values.put("paraphrase", item.getParaphrase());
        values.put("time", item.getTimes());
        db.update(TBNAME, values, "ID=?", new String[]{String.valueOf(item.getId())});
        db.close();
    }

    @SuppressLint("Range")
    public List<Word> listAll(){
        List<Word> wordList = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, null, null, null, null, null);
        if(cursor!=null){
            wordList = new ArrayList<Word>();
            while(cursor.moveToNext()){
                Word item = new Word();
                item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                item.setVocabulary(cursor.getString(cursor.getColumnIndex("vocabulary")));
                item.setParaphrase(cursor.getString(cursor.getColumnIndex("paraphrase")));
                item.setTimes(cursor.getInt(cursor.getColumnIndex("time")));
                wordList.add(item);
            }
            cursor.close();
        }
        db.close();
        return wordList;
    }

    @SuppressLint("Range")
    public Word findById(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, "ID=?", new String[]{String.valueOf(id)}, null, null, null);
        Word wordItem = null;
        if(cursor!=null && cursor.moveToFirst()){
            wordItem = new Word();
            wordItem.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            wordItem.setVocabulary(cursor.getString(cursor.getColumnIndex("vocabulary")));
            wordItem.setParaphrase(cursor.getString(cursor.getColumnIndex("paraphrase")));
            wordItem.setTimes(cursor.getInt(cursor.getColumnIndex("time")));
            cursor.close();
        }
        db.close();
        return wordItem;
    }
}
