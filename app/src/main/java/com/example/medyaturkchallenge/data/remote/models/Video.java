package com.example.medyaturkchallenge.data.remote.models;

import com.google.gson.annotations.SerializedName;

public class Video {

   @SerializedName("shortText")
   private String shortText;
   @SerializedName("bodyText")
   private String bodyText;
   @SerializedName("videoUrl")
   private String videoUrl;
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

   public String getShortText() {
      return shortText;
   }

   public String getBodyText() {
      return bodyText;
   }

   public String getVideoUrl() {
      return videoUrl;
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
