package com.example.medyaturkchallenge.data.remote.models;

import com.google.gson.annotations.SerializedName;

public class Detail extends BaseModel {

   @SerializedName("data")
   private DataDetail dataDetail;

   public DataDetail getData() {
      return dataDetail;
   }

}
