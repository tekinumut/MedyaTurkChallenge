package com.example.medyaturkchallenge.data.remote.models;

import com.google.gson.annotations.SerializedName;

/**
 * Category with extra color element
 */
public class CategoryWithColor extends Category {

   @SerializedName("color")
   private String color;

   public String getColor() {
      return color;
   }
}
