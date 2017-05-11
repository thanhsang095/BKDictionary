package com.developer.sangbarca.bkdictionary.Helper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.developer.sangbarca.bkdictionary.Models.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nhat on 07/05/2017.
 */

public class CategoryRepo {

    public CategoryRepo() {
    }

    public long insertCategory(Category cate){
        SQLiteDatabase db = DBManger.getInstance().openDatabase();
        long id = db.insert(
                DBHelper.TABLE_CATEGORIES,
                null,
                cate.getValues()
        );
        DBManger.getInstance().closeDatabase();
        return id;
    }

    public List<Category> findByDictId(int id){
        SQLiteDatabase db = DBManger.getInstance().openDatabase();
        String selectQuery = "SELECT  ct.* FROM " + DBHelper.TABLE_CATEGORIES + " ct LEFT JOIN "
                + DBHelper.TABLE_DICTS_CATEGORIES + " a ON ct." + DBHelper.TB_CATEGORIES.PRIMARY_KEY + " = a." + DBHelper.TB_DICTS_CATEGORIES.CATEGORIES_ID
                + " WHERE " + DBHelper.TB_DICTS_CATEGORIES.DICT_ID + " = " + id;
        Log.d("Test sql ", selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        ArrayList<Category> categories =new ArrayList<>();
        if(cursor.moveToFirst()) {
            do {
                Category obj = new Category();
                obj.setPrimaryKey(cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.TB_CATEGORIES.PRIMARY_KEY)));
                obj.setId(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.TB_CATEGORIES.ID)));
                obj.setName(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.TB_CATEGORIES.NAME)));
                obj.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.TB_CATEGORIES.DESCRIPTION)));
                categories.add(obj);
            } while (cursor.moveToNext());
        }
        DBManger.getInstance().closeDatabase();
        return categories;

    }

    public Category getCategory(String key) {
        SQLiteDatabase db = DBManger.getInstance().openDatabase();
        Category obj = null;
        Cursor c = db.query(
                DBHelper.TABLE_CATEGORIES,
                null,
                DBHelper.TB_CATEGORIES.ID + DBHelper.WHERE_CONDITION,
                new String[]{key},
                null,
                null,
                null
        );
        if (c.moveToFirst()) {
            obj = new Category();
            obj.setPrimaryKey(c.getInt( c.getColumnIndexOrThrow( DBHelper.TB_CATEGORIES.PRIMARY_KEY )));
            obj.setId(c.getString( c.getColumnIndexOrThrow( DBHelper.TB_CATEGORIES.ID )));
            obj.setName(c.getString(c.getColumnIndexOrThrow(DBHelper.TB_CATEGORIES.NAME)));
            obj.setDescription(c.getString(c.getColumnIndexOrThrow(DBHelper.TB_CATEGORIES.DESCRIPTION)));
            c.close();
        }
        DBManger.getInstance().closeDatabase();
        return obj;
    }

    public int updateCategory(Category cate) {
        SQLiteDatabase db = DBManger.getInstance().openDatabase();
        int id = db.update(DBHelper.TABLE_DICTS, cate.getValues(), DBHelper.TB_DICTS.ID + DBHelper.WHERE_CONDITION,
                new String[] { cate.getId() });
        DBManger.getInstance().closeDatabase();
        return id;
    }

    public void clear(){
        SQLiteDatabase db = DBManger.getInstance().openDatabase();
        db.delete(DBHelper.TABLE_CATEGORIES, null, null);
        DBManger.getInstance().closeDatabase();
    }


}
