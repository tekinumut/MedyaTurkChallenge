package com.example.medyaturkchallenge.data.remote.models;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Category {

    @SerializedName("categoryId")
    private String categoryId;
    @SerializedName("title")
    private String title;
    @SerializedName("slug")
    private String slug;

    public String getCategoryId() {
        return categoryId;
    }

    public String getTitle() {
        return title;
    }

    public String getSlug() {
        return slug;
    }
}
