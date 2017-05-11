package com.developer.sangbarca.bkdictionary.Models;

import android.content.ContentValues;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.developer.sangbarca.bkdictionary.Helper.DBHelper;

import java.util.List;

/**
 * Created by nhat on 07/05/2017.
 */

public class Dict {

    private int primaryKey = -1;

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("pronounce")
    @Expose
    private String pronounce;
    @SerializedName("category")
    @Expose
    private List<String> category = null;
    @SerializedName("example")
    @Expose
    private List<String> example = null;
    @SerializedName("optional")
    @Expose
    private List<MeanDict> optional = null;



    public ContentValues getValues(){
        ContentValues cv = new ContentValues();
        if(getPrimaryKey()!=-1)cv.put(DBHelper.TB_DICTS.ID ,getPrimaryKey());
        cv.put(DBHelper.TB_CATEGORIES.ID, getId());
        cv.put(DBHelper.TB_DICTS.NAME, getName());
        cv.put(DBHelper.TB_DICTS.PRONOUNCE, getPronounce());
        return cv;
    }


    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPronounce() {
        return pronounce;
    }

    public void setPronounce(String pronounce) {
        this.pronounce = pronounce;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public List<String> getExample() {
        return example;
    }

    public void setExample(List<String> example) {
        this.example = example;
    }

    public List<MeanDict> getOptional() {
        return optional;
    }

    public void setOptional(List<MeanDict> optional) {
        this.optional = optional;
    }

}
