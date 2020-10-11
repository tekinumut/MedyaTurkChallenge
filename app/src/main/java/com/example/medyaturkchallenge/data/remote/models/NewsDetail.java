package com.example.medyaturkchallenge.data.remote.models;

import com.google.gson.annotations.SerializedName;

public class NewsDetail {

   @SerializedName("resource")
   private String resource;
   @SerializedName("bodyText")
   private String bodyText;
   @SerializedName("hasPhotoGallery")
   private Boolean hasPhotoGallery;
   @SerializedName("hasVideo")
   private Boolean hasVideo;
   @SerializedName("publishDate")
   private String publishDate;
   @SerializedName("fullPath")
   private String fullPath;
   @SerializedName("shortText")
   private String shortText;
   @SerializedName("category")
   private CategoryWithColor categoryWithColor;
   @SerializedName("itemId")
   private String itemId;
   @SerializedName("title")
   private String title;
   @SerializedName("video")
   private String video;
   @SerializedName("imageUrl")
   private String imageUrl;
   @SerializedName("itemType")
   private String itemType;

   public String getResource() {
      return resource;
   }

   public String getBodyText() {
      return bodyText;
   }

   public Boolean getHasPhotoGallery() {
      return hasPhotoGallery;
   }

   public Boolean getHasVideo() {
      return hasVideo;
   }

   public String getPublishDate() {
      return publishDate;
   }

   public String getFullPath() {
      return fullPath;
   }

   public String getShortText() {
      return shortText;
   }

   public CategoryWithColor getCategoryWithColor() {
      return categoryWithColor;
   }

   public String getItemId() {
      return itemId;
   }

   public String getTitle() {
      return title;
   }

   public String getVideo() {
      return video;
   }

   public String getImageUrl() {
      return imageUrl;
   }

   public String getItemType() {
      return itemType;
   }
}
