package com.example.medyaturkchallenge.data.remote.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MainPage extends BaseModel {

   @SerializedName("data")
   private List<DataMainPage> data = null;

   public List<DataMainPage> getData() {
      return data;
   }
}
