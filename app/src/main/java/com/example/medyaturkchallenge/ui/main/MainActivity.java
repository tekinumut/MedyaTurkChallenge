package com.example.medyaturkchallenge.ui.main;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.medyaturkchallenge.R;
import com.example.medyaturkchallenge.base.BaseActivity;
import com.example.medyaturkchallenge.data.remote.models.DataMainPage;
import com.example.medyaturkchallenge.data.remote.services.ApiInterface;
import com.example.medyaturkchallenge.databinding.ActivityMainBinding;
import com.example.medyaturkchallenge.ui.main.adapter.NewsRecyclerAdapter;
import com.example.medyaturkchallenge.ui.main.adapter.SwipePagerAdapter;
import com.example.medyaturkchallenge.ui.news_detail.NewsDetailActivity;
import com.example.medyaturkchallenge.utils.Constants;
import com.example.medyaturkchallenge.utils.Utils;
import com.google.android.exoplayer2.DefaultControlDispatcher;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.util.Util;

import static androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL;

public class MainActivity extends BaseActivity implements MainNavigator {

    // Player'da bulunan tam ekran tuşu
    ImageView mFullScreenIcon;
    private MainViewModel mainViewModel;
    private ViewPager2 viewPager2Swipe;
    private RecyclerView recyclerViewNews;
    // Video'nun oynatılacağı widget nesnesi
    private PlayerView playerView;
    // exoPlayer nesnesi tanımlanıyor.
    private SimpleExoPlayer player;
    // Player'ın son çalışma durumu. Video oynatılıyor veya duraklatıldı.
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    // Player tam ekran açık olup olmama durumu
    private boolean mExoPlayerFullscreen = false;
    // Player'ın tam ekran açılacağı dialog
    private Dialog mFullScreenDialog;
    // Player'ın Bildirim alanında çıkmasını sağlayan sınıf


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        mainViewModel.fetchMainPage();
        observerViewModel();
    }

    /**
     * Gerekli tanımlamalar yapılıyor
     */
    private void init() {
        ActivityMainBinding mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainBinding.setMainPageVM(mainViewModel);
        mainBinding.setLifecycleOwner(this);

        viewPager2Swipe = mainBinding.pager2Swipe;
        viewPager2Swipe.setOffscreenPageLimit(2);
        viewPagerWithMultiplePage();

        recyclerViewNews = mainBinding.recNews;
        recyclerViewNews.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewNews.setHasFixedSize(true);

        playerView = findViewById(R.id.playerView);
        mFullScreenIcon = playerView.findViewById(R.id.exo_fullscreen_icon);

        initFullscreenDialog();
    }

    /**
     * Canlı yayın oynatıcını tanımla
     */
    private void initializePlayer() {
        player = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(player);
        // Oynatılacak bağlatıyı tanıt
        MediaItem mediaItem = MediaItem.fromUri(ApiInterface.Channel24LiveURL);
        player.setMediaItem(mediaItem);

        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        player.prepare();
        playerNotificationManager.setPlayer(player);

        // Player çalışma durumları
        playerView.setControlDispatcher(new DefaultControlDispatcher() {
            @Override
            public boolean dispatchSetPlayWhenReady(Player player, boolean playWhenReady) {
                // Eğer player kapanmış ise (internet kopması gibi)
                // Duraklat-Başlat butonuna player'ı sıfırlama yetkisi veriyoruz.
                if (player.getPlaybackState() == Player.STATE_IDLE ||
                        player.getPlaybackState() == Player.STATE_ENDED) {
                    releasePlayer();
                    initializePlayer();
                }

                return super.dispatchSetPlayWhenReady(player, playWhenReady);
            }
        });
    }

    /**
     * Canlı yayın oynatıcını kapat
     */
    private void releasePlayer() {
        if (player != null) {
            playWhenReady = player.getPlayWhenReady();
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player.release();
            player = null;
        }
        if (playerNotificationManager != null) {
            playerNotificationManager.setPlayer(null);
        }
    }

    /**
     * Anasayfa API'sinden verilerin alınacağı Observer'ı takip et.
     * Bağlantının başarı durumuna göre işlemleri ele al.
     */
    private void observerViewModel() {

        mainViewModel.mainPageLiveData.observe(this, mainPage -> {
            // SWIPE haberleri yükle
            DataMainPage swipeDataMainPage = Utils.findSwipeData(mainPage.getData(), Constants.SectionTypes.Swipe);
            viewPager2Swipe.setAdapter(new SwipePagerAdapter(swipeDataMainPage.getItemList(), this));

            // NEWS haberleri yükle
            DataMainPage newsDataMainPage = Utils.findSwipeData(mainPage.getData(), Constants.SectionTypes.News);
            recyclerViewNews.setAdapter(new NewsRecyclerAdapter(newsDataMainPage.getItemList(), this));
        });

        mainViewModel.errorMessage.observe(this, errorMessage ->
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show());

        // Yükleme durumunu izle
        mainViewModel.loadingState.observe(this, isLoading -> {

        });
    }

    /**
     * Haber nesnesine tıklanma durumunu kontrol eder
     *
     * @param url Açılacak olan web sitesi
     */
    @Override
    public void onOpenNewsClick(String url) {
        Utils.openWebSite(this, url);
    }

    /**
     * Swipe haberlere tıklama durumunda tetiklenecek fonksiyon
     */
    @Override
    public void onMainSwipeClick() {
        startActivity(new Intent(this, NewsDetailActivity.class));
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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // Diyaloğu aç
        mFullScreenDialog.show();
    }

    /**
     * Player'ı tam ekran modundan çıkart
     */
    private void closeFullscreenDialog() {
        ((ViewGroup) playerView.getParent()).removeView(playerView);
        ((FrameLayout) findViewById(R.id.nonCardView)).addView(playerView);
        mExoPlayerFullscreen = false;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mFullScreenDialog.dismiss();
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.exo_controls_fullscreen_enter));
    }

    /**
     * Viewpager2'nin aynı ekranda birden fazla içerik göstermesine sağlar.
     * 2. sayfanın 50dp uzunluğunda alanı gösterilir.
     */
    private void viewPagerWithMultiplePage() {
        int pageMarginPx = getResources().getDimensionPixelOffset(R.dimen.pageMargin);
        int offsetPx = getResources().getDimensionPixelOffset(R.dimen.offset);
        viewPager2Swipe.setPageTransformer((page, position) -> {
            ViewPager2 viewPager2 = (ViewPager2) page.getParent().getParent();
            float offset = position * -(2 * offsetPx + pageMarginPx);
            if (viewPager2.getOrientation() == ORIENTATION_HORIZONTAL) {
                if (ViewCompat.getLayoutDirection(viewPager2) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                    page.setTranslationX(-offset);
                } else {
                    page.setTranslationX(offset);
                }
            } else {
                page.setTranslationY(offset);
            }
        });
    }

    /**
     * Player'ı duraklat veya başlat
     *
     * @param playWhenReady başlatma veya duraklatma seçimi
     *                      true ->  başlat
     *                      false -> durdur
     */
    private void startOrStopPlayer(boolean playWhenReady) {
        player.setPlayWhenReady(playWhenReady);
    }

    /**
     * Player güncel yayına geçer
     */
    private void jumpLiveStream() {
        MediaItem mediaItem = MediaItem.fromUri(ApiInterface.Channel24LiveURL);
        player.setMediaItem(mediaItem);
        startOrStopPlayer(true);
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

    /**
     * Canlı Yayın butonunu kontrol eden fonsiyon
     *
     * @param view Canlı yayın butonu
     */
    public void onPlayerLiveClick(View view) {
        jumpLiveStream();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 24) {
            if (player == null) {
                initializePlayer();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT < 24 || player == null)) {
            if (player == null) {
                initializePlayer();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

}