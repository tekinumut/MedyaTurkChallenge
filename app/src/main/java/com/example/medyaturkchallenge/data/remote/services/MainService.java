package com.example.medyaturkchallenge.data.remote.services;

import com.example.medyaturkchallenge.BuildConfig;
import com.example.medyaturkchallenge.data.remote.models.Detail;
import com.example.medyaturkchallenge.data.remote.models.MainPage;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

// Singleton Retrofit Service Class
public class MainService {

   private static MainService instance;

   private final ApiInterface api = new Retrofit.Builder()
       .baseUrl(BuildConfig.baseURL)
       .addConverterFactory(GsonConverterFactory.create())
       .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
       .build()
       .create(ApiInterface.class);

   private MainService() {
   }

   public static MainService getInstance() {
      if (instance == null) {
         instance = new MainService();
      }
      return instance;
   }

   public Single<MainPage> getMainPage() {
      return api.getMainPage();
   }

   public Single<Detail> getNewsDetail() {
      return api.getNewsDetailPage();
   }

}
