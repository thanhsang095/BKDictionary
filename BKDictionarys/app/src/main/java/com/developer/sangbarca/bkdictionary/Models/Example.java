package com.developer.sangbarca.bkdictionary.Models;

import android.content.ContentValues;

import com.developer.sangbarca.bkdictionary.Helper.DBHelper;

/**
 * Created by nhat on 07/05/2017.
 */

public class Example {
    private int primaryKey  = -1;
    private String content;
    private int dictKey = -1;

    public ContentValues getValues(){
        ContentValues cv = new ContentValues();
        if ( getPrimaryKey()!=-1) cv.put(DBHelper.TB_EXAMPLES.PRIMARY_KEY, getPrimaryKey());
        if ( getDictKey()!=-1) cv.put(DBHelper.TB_EXAMPLES.DICTS_ID, getDictKey());
        cv.put(DBHelper.TB_EXAMPLES.CONTENT, getContent());

        return cv;
    }


    public int getDictKey() {
        return dictKey;
    }

    public void setDictKey(int dictKey) {
        this.dictKey = dictKey;
    }



    public Example(String content) {
        this.content = content;
    }

    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
