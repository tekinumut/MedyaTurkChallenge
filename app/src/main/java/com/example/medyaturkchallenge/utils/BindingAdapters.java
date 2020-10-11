package com.example.medyaturkchallenge.utils;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.medyaturkchallenge.R;
import com.example.medyaturkchallenge.data.remote.models.DataMainPage;
import com.example.medyaturkchallenge.data.remote.models.MainPage;

/**
 * Databinding kullanılan yerlerdeki daha karmaşık istekleri
 * karşılamak adına oluşturulan sınıf
 */
public class BindingAdapters {

   /**
    * Haber başlığının tasarım ve içerik özelliği
    * API'den gelen içerik ile oluşturulur
    *
    * @param textView başlığın nesnesi
    * @param mainPage API'den gelen veri
    */
   @BindingAdapter(value = {"titleSettings"})
   public static void getTitleView(TextView textView, MainPage mainPage) {
      if (mainPage != null) {
         // SWIPE türündeki haberleri çek
         DataMainPage swipeDataMainPage = Utils.findSwipeData(mainPage.getData(), textView.getTag().toString());

         textView.setText(swipeDataMainPage.getTitle());
         textView.setVisibility(Utils.getVisibilityWithBool(swipeDataMainPage.getTitleVisible()));
         if (swipeDataMainPage.getTitleColor() != null)
            textView.setTextColor(Color.parseColor(swipeDataMainPage.getTitleColor()));
         // Background color rengi hoş durmuyordu :) Sunucudan gelen veriyi kullanmayalım
         //  if (swipeData.getTitleBgColor() != null)
         //    textView.setBackgroundColor(Color.parseColor(swipeData.getTitleBgColor()));
      }
   }

   /**
    * İlgili URL'deki resmi metodun tanımlandığı imageview nesnesine aktarır
    *
    * @param imageView resmin gösterileceği widget
    * @param swipeUrl  resmin alınacağı url
    */
   @BindingAdapter(value = {"swipeUrl"})
   public static void setImageSwipe(ImageView imageView, String swipeUrl) {

      // Glide bağlandığı web adresindeki resmi ilgili ImageView'a basar
      Glide
          .with(imageView.getContext())
          .load(swipeUrl)
          // Resim yüklenene kadar gösterilecek resim.
          .placeholder(R.drawable.progress_animation)
          // Resmin yüklenememesi durumunda gösterilecek resim
          .error(ContextCompat.getDrawable(imageView.getContext(), R.drawable.image_error))
          .into(imageView);
   }

   /**
    * Aldığı hex renk koduna göre view'ın background rengini değiştirir
    *
    * @param view        view nesnesi
    * @param setColorHex Hex biçiminde renk kodu exp : #FF0000
    */
   @BindingAdapter(value = {"setColorHex"})
   public static void setColorHex(View view, String setColorHex) {
      if (setColorHex != null) {
         view.setBackgroundColor(Color.parseColor(setColorHex));
      } else {
         view.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.colorPrimary));
      }

   }


}
