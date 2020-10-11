package com.example.medyaturkchallenge.data.remote.models;

import com.google.gson.annotations.SerializedName;

public class BaseModel {
   @SerializedName("errorCode")
   private Integer errorCode;
   @SerializedName("errorMessage")
   private String errorMessage;

   public Integer getErrorCode() {
      return errorCode;
   }

   public String getErrorMessage() {
      return errorMessage;
   }
}
