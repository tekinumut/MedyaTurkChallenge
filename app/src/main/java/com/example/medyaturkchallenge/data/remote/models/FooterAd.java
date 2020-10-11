package com.example.medyaturkchallenge.data.remote.models;

import com.google.gson.annotations.SerializedName;

public class FooterAd {

   @SerializedName("itemType")
   private String itemType;
   @SerializedName("adUnit")
   private String adUnit;
   @SerializedName("itemWidth")
   private Integer itemWidth;
   @SerializedName("itemHeight")
   private Integer itemHeight;

   public String getItemType() {
      return itemType;
   }

   public String getAdUnit() {
      return adUnit;
   }

   public Integer getItemWidth() {
      return itemWidth;
   }

   public Integer getItemHeight() {
      return itemHeight;
   }
}
