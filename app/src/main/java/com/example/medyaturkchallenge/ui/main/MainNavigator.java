package com.example.medyaturkchallenge.ui.main;

public interface MainNavigator {
   // Veri olmadığından haber detayına giderken
   // herhangi bir key kullanmayacağız.
   // Her sayfa aynı habere yönlendirecek
   void onMainSwipeClick();

   // İkinci recyclerView'da bulunan haberler bizi haberin web sitesine yönlendirecek.
   void onOpenNewsClick(String url);
}
