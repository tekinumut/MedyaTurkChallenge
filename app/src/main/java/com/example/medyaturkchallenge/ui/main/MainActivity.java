package com.example.medyaturkchallenge.ui.main;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;
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
import com.example.medyaturkchallenge.databinding.ActivityMainBinding;
import com.example.medyaturkchallenge.ui.main.adapter.NewsRecyclerAdapter;
import com.example.medyaturkchallenge.ui.main.adapter.SwipePagerAdapter;
import com.example.medyaturkchallenge.ui.news_detail.NewsDetailActivity;
import com.example.medyaturkchallenge.utils.Constants;
import com.example.medyaturkchallenge.utils.Utils;
import com.google.android.exoplayer2.ui.PlayerView;

import static androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL;

public class MainActivity extends BaseActivity implements MainNavigator {

    // Player'da bulunan tam ekran tuşu
    ImageView mFullScreenIcon;
    private MainViewModel mainViewModel;
    private ViewPager2 viewPager2Swipe;
    private RecyclerView recyclerViewNews;
    // Video'nun oynatılacağı widget nesnesi
    private PlayerView playerView;
    // Player tam ekran açık olup olmama durumu
    private boolean mExoPlayerFullscreen = false;
    // Player'ın tam ekran açılacağı dialog
    private Dialog mFullScreenDialog;
    // Arkaplanda canlı yayını oynatan bound service
    private AudioPlayerService audioPlayerService = null;


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
        playerView.showController();
        mFullScreenIcon = playerView.findViewById(R.id.exo_fullscreen_icon);

        initFullscreenDialog();
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
     * Servisten gelen bağlantıyı okur
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder iBinder) {
            AudioPlayerService.AudioServiceBinder binder = (AudioPlayerService.AudioServiceBinder) iBinder;
            audioPlayerService = binder.getService();
            playerView.setPlayer(binder.getPlayer());
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            audioPlayerService = null;
        }
    };

    /**
     * Player güncel yayına geçer
     */
    private void jumpLiveStream() {
        audioPlayerService.jumpLiveStream();
    }

    /**
     * Player'ın ses açma kapama tuş kontrolleri
     *
     * @param view ses butonu view nesnesi
     */
    public void onBtnVolumeClick(View view) {
        audioPlayerService.changePlayerVolume((ImageView) view);
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

    private void bindService() {
        if (audioPlayerService == null) {
            Intent serviceIntent = new Intent(this, AudioPlayerService.class);
            bindService(serviceIntent, mConnection, Context.BIND_AUTO_CREATE);
        }
    }

    private void unbindAudioService() {
        if (audioPlayerService != null) {
            unbindService(mConnection);
            audioPlayerService = null;
        }
    }

    private void startAudioService() {
        Intent serviceIntent = new Intent(this, AudioPlayerService.class);
        ContextCompat.startForegroundService(this, serviceIntent);
        bindService();
    }

    private void stopAudioService() {
        unbindAudioService();
        stopService(new Intent(this, AudioPlayerService.class));
        audioPlayerService = null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        startAudioService();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindAudioService();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopAudioService();
    }

}