package com.example.medyaturkchallenge.data.remote.models;

import com.google.gson.annotations.SerializedName;

public class RelatedNews {

   @SerializedName("hasPhotoGallery")
   private Boolean hasPhotoGallery;
   @SerializedName("hasVideo")
   private Boolean hasVideo;
   @SerializedName("publishDate")
   private String publishDate;
   @SerializedName("shortText")
   private String shortText;
   @SerializedName("category")
   private Category category;
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

   public Boolean getHasPhotoGallery() {
      return hasPhotoGallery;
   }

   public Boolean getHasVideo() {
      return hasVideo;
   }

   public String getPublishDate() {
      return publishDate;
   }

   public String getShortText() {
      return shortText;
   }

   public Category getCategory() {
      return category;
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
