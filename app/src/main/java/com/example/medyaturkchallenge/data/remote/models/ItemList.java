package com.example.medyaturkchallenge.data.remote.models;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ItemList {
   @SerializedName("hasPhotoGallery")
   private Boolean hasPhotoGallery;
   @SerializedName("hasVideo")
   private Boolean hasVideo;
   @SerializedName("titleVisible")
   private Boolean titleVisible;
   @SerializedName("fLike")
   private String fLike;
   @SerializedName("publishDate")
   private String publishDate;
   @SerializedName("shortText")
   private String shortText;
   @SerializedName("fullPath")
   private String fullPath;
   @SerializedName("category")
   private Category category;
   @SerializedName("videoUrl")
   private String videoUrl;
   @SerializedName("externalUrl")
   private String externalUrl;
   @SerializedName("columnistName")
   private String columnistName;
   @SerializedName("itemId")
   private String itemId;
   @SerializedName("title")
   private String title;
   @SerializedName("imageUrl")
   private String imageUrl;
   @SerializedName("itemType")
   private String itemType;

   public Boolean getHasPhotoGallery() {
      return hasPhotoGallery;
   }

   public Boolean getHasVideo() {
      return hasVideo;
   }

   public Boolean getTitleVisible() {
      return titleVisible;
   }

   public String getfLike() {
      return fLike;
   }

   public String getPublishDate() {
      return publishDate;
   }

   public String getShortText() {
      return shortText;
   }

   public String getFullPath() {
      return fullPath;
   }

   public Category getCategory() {
      return category;
   }

   public String getVideoUrl() {
      return videoUrl;
   }

   public String getExternalUrl() {
      return externalUrl;
   }

   public String getColumnistName() {
      return columnistName;
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
}

