package com.example.medyaturkchallenge.data.remote.services;

import com.example.medyaturkchallenge.data.remote.models.Detail;
import com.example.medyaturkchallenge.data.remote.models.MainPage;

import io.reactivex.Single;
import retrofit2.http.GET;


public interface ApiInterface {

   // Kanal 24 Canlı yayın bağlantı adresi
   String Channel24LiveURL = "https://mn-nl.mncdn.com/kanal24/smil:kanal24.smil/playlist.m3u8";

   String MainPageURL = "anasayfa.json";

   String DetailPageURL = "detay.json";

   /**
    * AnaSayfa verilerini al
    *
    * @return MainPage nesnesi
    */
   @GET(MainPageURL)
   Single<MainPage> getMainPage();


   /**
    * Detay sayfasının verilerini al
    *
    * @return Detay nesnesi
    */
   @GET(DetailPageURL)
   Single<Detail> getNewsDetailPage();

}
