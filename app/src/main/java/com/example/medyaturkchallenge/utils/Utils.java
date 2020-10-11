package com.example.medyaturkchallenge.utils;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import androidx.browser.customtabs.CustomTabColorSchemeParams;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.example.medyaturkchallenge.R;
import com.example.medyaturkchallenge.data.remote.models.DataMainPage;

import java.util.List;

public class Utils {

   /**
    * Boolean olarak aldığı görünürlük durumunu int'e çeviri
    *
    * @param isVisible bool biçiminde görünürlük durumu
    * @return integer biçiminde görünürlük durumu
    */
   public static Integer getVisibilityWithBool(boolean isVisible) {
      return isVisible ? View.VISIBLE : View.GONE;
   }

   /**
    * Data nesnesi SWIPE--NEWS gibi türlere ayrılmış durumda.
    * Bu nesneyi sectionType keyine bakarak o türe ait verileri alıyoruz.
    *
    * @param data Tüm gelen Data listesi. Bunun içinde sectionType'ı belirlenen olanı bulacağız.
    * @return SectionType'ı Swipe,News vb. olan Data nesnesi
    */
   public static DataMainPage findSwipeData(List<DataMainPage> data, String sectionType) {
      DataMainPage swipeDataMainPage = new DataMainPage();
      for (int i = 0; i < data.size(); i++) {
         if (data.get(i).getSectionType().equals(sectionType))
            swipeDataMainPage = data.get(i);
      }
      return swipeDataMainPage;
   }

   /**
    * Seçili haberin bulunduğu web sitesini browser üzerinde açar.
    */
   public static void openWebSite(Context context, String url) {

      CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
      CustomTabColorSchemeParams colorSchemeBuilder = new CustomTabColorSchemeParams.Builder()
          .setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))
          .build();

      builder.setDefaultColorSchemeParams(colorSchemeBuilder);
      // Browser açılırken oynatılacak animasyon
      builder.setStartAnimations(context, R.anim.slide_in_right, R.anim.slide_out_left);
      builder.setExitAnimations(context, R.anim.slide_in_left, R.anim.slide_out_right);

      CustomTabsIntent tabIntent = builder.build();
      tabIntent.launchUrl(context, Uri.parse(url));
   }


   /**
    * Integer değeri dp değerine çevirir.
    * Daha sonra bu dp değeri widget işlemlerinde kullanılabilir.
    *
    * @param dp      Çevrilmek istenen dp değeri (Int biçiminde)
    * @param context context Nesnesi
    * @return dp'e çevrilmiş int değeri
    */
   protected static int dpToPx(int dp, Context context) {
      float density = context.getResources().getDisplayMetrics().density;
      return Math.round((float) dp * density);
   }

   /**
    * Widget'ın sağa doğru kaç dp boşluk bırakması gerektiğini ayarlar
    *
    * @param view      kullanılacak widget
    * @param dp        sağdan boşluk bırakılacak değer
    * @param direction Boşluk bırakacak yönü belirler
    *                  0 -> Sol - Start
    *                  1 -> Sağ - End
    */
   public static void addMarginStartEnd(View view, int dp, int direction) {
      ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view.getLayoutParams();
      switch (direction) {
         case 0:
            layoutParams.setMarginStart(Utils.dpToPx(dp, view.getRootView().getContext()));
         case 1:
            layoutParams.setMarginEnd(Utils.dpToPx(dp, view.getRootView().getContext()));
      }

      view.setLayoutParams(layoutParams);
   }

}
