package com.developer.sangbarca.bkdictionary.Models;

import android.content.ContentValues;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.developer.sangbarca.bkdictionary.Helper.DBHelper;

/**
 * Created by nhat on 07/05/2017.
 */

public class Category {


    private int primaryKey = -1;

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;

    public ContentValues getValues(){
        ContentValues cv = new ContentValues();
        if(getPrimaryKey()!=-1)cv.put(DBHelper.TB_CATEGORIES.PRIMARY_KEY, getPrimaryKey());
        cv.put(DBHelper.TB_CATEGORIES.ID, getId());
        cv.put(DBHelper.TB_CATEGORIES.NAME, getName());
        cv.put(DBHelper.TB_CATEGORIES.DESCRIPTION, getDescription());
        return cv;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
