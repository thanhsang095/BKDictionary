package com.developer.sangbarca.bkdictionary.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nhat on 07/05/2017.
 */

public class MeanDict {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("content")
    @Expose
    private List<String> content = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }
}
