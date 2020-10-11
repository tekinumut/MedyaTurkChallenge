package com.example.medyaturkchallenge.data.remote.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class DataMainPage {
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
   @SerializedName("itemList")
   private List<ItemList> itemList = null;
   @SerializedName("totalRecords")
   private Integer totalRecords;

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

   public List<ItemList> getItemList() {
      return itemList;
   }

   public Integer getTotalRecords() {
      return totalRecords;
   }

}
