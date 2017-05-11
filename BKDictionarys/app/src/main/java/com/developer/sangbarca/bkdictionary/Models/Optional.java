package com.developer.sangbarca.bkdictionary.Models;

import android.content.ContentValues;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.developer.sangbarca.bkdictionary.Helper.DBHelper;

/**
 * Created by nhat on 07/05/2017.
 */

public class Optional {

    private int primaryKey = -1;

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("nameVi")
    @Expose
    private String nameVi;

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

    public ContentValues getValues(){
        ContentValues cv = new ContentValues();
        if ( getPrimaryKey()!=-1) cv.put(DBHelper.TB_EXAMPLES.PRIMARY_KEY, getPrimaryKey());
        cv.put(DBHelper.TB_OPTIONALS.ID, getId());
        cv.put(DBHelper.TB_OPTIONALS.NAME, getName());
        cv.put(DBHelper.TB_OPTIONALS.NAME_VI, getNameVi());


        return cv;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameVi() {
        return nameVi;
    }

    public void setNameVi(String nameVi) {
        this.nameVi = nameVi;
    }
}
