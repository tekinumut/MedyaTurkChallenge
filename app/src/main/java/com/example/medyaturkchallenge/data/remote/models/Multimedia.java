package com.example.medyaturkchallenge.data.remote.models;

import com.google.gson.annotations.SerializedName;

public class Multimedia {

   @SerializedName("sectionType")
   private String sectionType;
   @SerializedName("repeatType")
   private String repeatType;
   @SerializedName("itemCountInRow")
   private Integer itemCountInRow;
   @SerializedName("lazyLoadingEnabled")
   private Boolean lazyLoadingEnabled;
   @SerializedName("titleVisible")
   private Boolean titleVisible;
   @SerializedName("title")
   private String title;
   @SerializedName("titleColor")
   private String titleColor;
   @SerializedName("titleBgColor")
   private String titleBgColor;
   @SerializedName("sectionBgColor")
   private String sectionBgColor;

   public String getSectionType() {
      return sectionType;
   }

   public String getRepeatType() {
      return repeatType;
   }

   public Integer getItemCountInRow() {
      return itemCountInRow;
   }

   public Boolean getLazyLoadingEnabled() {
      return lazyLoadingEnabled;
   }

   public Boolean getTitleVisible() {
      return titleVisible;
   }

   public String getTitle() {
      return title;
   }

   public String getTitleColor() {
      return titleColor;
   }

   public String getTitleBgColor() {
      return titleBgColor;
   }

   public String getSectionBgColor() {
      return sectionBgColor;
   }
}
