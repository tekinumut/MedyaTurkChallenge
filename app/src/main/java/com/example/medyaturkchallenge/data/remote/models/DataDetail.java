package com.example.medyaturkchallenge.data.remote.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataDetail {
   @SerializedName("headerAd")
   private HeaderAd headerAd;
   @SerializedName("newsDetail")
   private NewsDetail newsDetail;
   @SerializedName("footerAd")
   private FooterAd footerAd;
   @SerializedName("multimedia")
   private Multimedia multimedia;
   @SerializedName("itemList")
   private List<ItemListDetail> itemList = null;
   @SerializedName("relatedNews")
   private RelatedNews relatedNews;
   @SerializedName("video")
   private Video video;
   @SerializedName("photoGallery")
   private PhotoGallery photoGallery;

   public HeaderAd getHeaderAd() {
      return headerAd;
   }

   public NewsDetail getNewsDetail() {
      return newsDetail;
   }

   public FooterAd getFooterAd() {
      return footerAd;
   }

   public Multimedia getMultimedia() {
      return multimedia;
   }

   public List<ItemListDetail> getItemList() {
      return itemList;
   }

   public RelatedNews getRelatedNews() {
      return relatedNews;
   }

   public Video getVideo() {
      return video;
   }

   public PhotoGallery getPhotoGallery() {
      return photoGallery;
   }
}
