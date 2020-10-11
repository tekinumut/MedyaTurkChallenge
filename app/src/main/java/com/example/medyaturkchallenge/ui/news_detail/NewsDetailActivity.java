package com.example.medyaturkchallenge.ui.news_detail;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medyaturkchallenge.R;
import com.example.medyaturkchallenge.base.BaseActivity;
import com.example.medyaturkchallenge.databinding.ActivityNewsDetailBinding;
import com.example.medyaturkchallenge.ui.news_detail.adapter.DetailItemListAdapter;
import com.example.medyaturkchallenge.utils.Utils;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

public class NewsDetailActivity extends BaseActivity implements NewsDetailNavigator {


   // Player'da bulunan tam ekran tuşu
   ImageView mFullScreenIcon;
   NewsDetailViewModel viewModel;
   // Player'ın son çalışma durumu. Video oynatılıyor veya duraklatıldı.
   ActivityNewsDetailBinding mainBinding;
   RecyclerView recyclerItemList;
   // Video'nun oynatılacağı widget nesnesi
   private PlayerView playerView;
   // exoPlayer nesnesi tanımlanıyor.
   private SimpleExoPlayer player;
   // Player tam ekran açık olup olmama durumu
   private boolean mExoPlayerFullscreen = false;
   // Player'ın tam ekran açılacağı dialog
   private Dialog mFullScreenDialog;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      init();
      viewModel.fetchNewsDetail();
      observerViewModel();
   }

   /**
    * Gerekli tanımlamalar yapılıyor
    */
   private void init() {
      mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_news_detail);
      viewModel = new ViewModelProvider(this).get(NewsDetailViewModel.class);
      mainBinding.setNewsDetailVM(viewModel);
      mainBinding.setNewsNagivator(this);
      mainBinding.setLifecycleOwner(this);

      recyclerItemList = mainBinding.recItemList;
      LinearLayoutManager layoutManager
          = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
      recyclerItemList.setLayoutManager(layoutManager);
      recyclerItemList.setHasFixedSize(true);

      playerView = findViewById(R.id.playerViewNewsDetail);
      mFullScreenIcon = playerView.findViewById(R.id.exo_fullscreen_icon);
      playerView.findViewById(R.id.exo_live_button).setVisibility(View.GONE);
      initFullscreenDialog();

   }

   /**
    * Anasayfa API'sinden verilerin alınacağı Observer'ı takip et.
    * Bağlantının başarı durumuna göre işlemleri ele al.
    */
   private void observerViewModel() {

      viewModel.newsDetailLiveData.observe(this, detail -> {
         mainBinding.setNewsDetail(detail.getData().getNewsDetail());

         // ItemList haberleri yükle
         recyclerItemList.setAdapter(new DetailItemListAdapter(detail.getData().getItemList(), this));
      });

      viewModel.errorMessage.observe(this, errorMessage ->
          Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show());

      // Yükleme durumunu izle
      viewModel.loadingState.observe(this, isLoading -> {

      });
   }

   /**
    * Player'ın tam ekranda gösterileceği dialog tanımlaması
    */
   private void initFullscreenDialog() {
      mFullScreenDialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
         // Geri tuşuna basıldığında
         public void onBackPressed() {
            // Eğer player tam ekranda gösteriliyorsa diyaloğu kapat
            if (mExoPlayerFullscreen)
               closeFullscreenDialog();
            super.onBackPressed();
         }
      };
   }

   /**
    * Player'ı tam ekran moduna al
    */
   private void openFullscreenDialog() {
      // Mevcut playerView'ı kaldır
      ((ViewGroup) playerView.getParent()).removeView(playerView);
      // Diyaloğa tam ekran olacak şekilde playerView nesnesini ekle
      mFullScreenDialog.addContentView(playerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
      // Tam ekran ikonunu güncelle
      mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.exo_controls_fullscreen_exit));
      mExoPlayerFullscreen = true;
      // Ekranı yatay konuma zorla
      setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
      // Diyaloğu aç
      mFullScreenDialog.show();
   }

   /**
    * Player'ı tam ekran modundan çıkart
    */
   private void closeFullscreenDialog() {
      ((ViewGroup) playerView.getParent()).removeView(playerView);
      ((FrameLayout) findViewById(R.id.nonFrameLayout)).addView(playerView);
      mExoPlayerFullscreen = false;
      setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
      mFullScreenDialog.dismiss();
      mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.exo_controls_fullscreen_enter));
      releasePlayer();
   }

   /**
    * Player'ın ses açma kapama tuş kontrolleri
    *
    * @param view ses butonu view nesnesi
    */
   public void onBtnVolumeClick(View view) {
      ImageView btnVolume = (ImageView) view;
      // Oynatıcının sesi açıksa kapatıyoruz
      if (player.getVolume() == 1.0f) {
         player.setVolume(0.0f);
         btnVolume.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_volume_off));
      } else if (player.getVolume() == 0.0f) {
         player.setVolume(1.0f);
         btnVolume.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_volume_up));
      }
   }

   @Override
   public void onVideoWidgetOpen(String url) {

      Log.e("onvideoOpen", "url: " + url);
      player = new SimpleExoPlayer.Builder(this).build();
      playerView.setPlayer(player);
      // Oynatılacak bağlatıyı tanıt
      MediaItem mediaItem = MediaItem.fromUri("https://www.radiantmediaplayer.com/media/big-buck-bunny-360p.mp4");
      player.setMediaItem(mediaItem);

      playerView.clearFocus();
      player.setPlayWhenReady(true);
      player.seekTo(0);
      player.prepare();
      openFullscreenDialog();
   }

   @Override
   public void onOpenNewsClick(String url) {
      Utils.openWebSite(this, url);
   }

   /**
    * Player'ın tam ekran tuş kontolleri
    *
    * @param view tam ekran view nesnesi
    */
   public void onPlayerFullScreenClick(View view) {
      if (!mExoPlayerFullscreen)
         openFullscreenDialog();
      else
         closeFullscreenDialog();
   }

   @Override
   protected void onDestroy() {
      super.onDestroy();
      if (player !=null){
         player.release();
         player = null;
      }
   }

   /**
    * Canlı yayın oynatıcını kapat
    */
   private void releasePlayer() {
      if (player != null) {
         player.release();
         player = null;
      }
   }


}