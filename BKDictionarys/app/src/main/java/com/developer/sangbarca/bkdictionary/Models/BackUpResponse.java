package com.developer.sangbarca.bkdictionary.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nhat on 07/05/2017.
 */

public class BackUpResponse {
    @SerializedName("dicts")
    @Expose
    private List<Dict> dicts;
    @SerializedName("catogeries")
    @Expose
    private List<Category> categories;
    @SerializedName("optionals")
    @Expose
    private List<Optional> optionals;


    public List<Dict> getDicts() {
        return dicts;
    }

    public void setDicts(List<Dict> dicts) {
        this.dicts = dicts;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Optional> getOptionals() {
        return optionals;
    }

    public void setOptionals(List<Optional> optionals) {
        this.optionals = optionals;
    }
}
