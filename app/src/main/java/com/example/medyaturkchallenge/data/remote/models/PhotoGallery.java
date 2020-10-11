package com.example.medyaturkchallenge.data.remote.models;

import com.google.gson.annotations.SerializedName;

public class PhotoGallery {

   @SerializedName("itemList")
   private Object itemList;
   @SerializedName("itemId")
   private String itemId;
   @SerializedName("title")
   private String title;
   @SerializedName("imageUrl")
   private String imageUrl;
   @SerializedName("itemType")
   private String itemType;
   @SerializedName("titleVisible")
   private Boolean titleVisible;

   public Object getItemList() {
      return itemList;
   }

   public String getItemId() {
      return itemId;
   }

   public String getTitle() {
      return title;
   }

   public String getImageUrl() {
      return imageUrl;
   }

   public String getItemType() {
      return itemType;
   }

   public Boolean getTitleVisible() {
      return titleVisible;
   }
}
