package com.developer.sangbarca.bkdictionary.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nhat on 07/05/2017.
 */

public class DictResponse {

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
    private List<Category> category = null;
    @SerializedName("example")
    @Expose
    private List<String> example = null;
    @SerializedName("optional")
    @Expose
    private List<MeanDictResponse> optional = null;

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

    public List<String> getExample() {
        return example;
    }

    public void setExample(List<String> example) {
        this.example = example;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public List<MeanDictResponse> getOptional() {
        return optional;
    }

    public void setOptional(List<MeanDictResponse> optional) {
        this.optional = optional;
    }
}

