package com.example.medyaturkchallenge.base;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.medyaturkchallenge.R;
import com.example.medyaturkchallenge.ui.main.MainActivity;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.PlayerNotificationManager;

//VM extends BaseViewModel, DB extends ViewDataBinding

public abstract class BaseActivity extends AppCompatActivity {

    public PlayerNotificationManager playerNotificationManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initPlayerManager();

    }


    private void initPlayerManager() {
        playerNotificationManager = PlayerNotificationManager.createWithNotificationChannel(
                getApplicationContext(), "1", R.string.app_name, R.string.channel_desc,
                2, new PlayerNotificationManager.MediaDescriptionAdapter() {

                    @SuppressWarnings("NullableProblems")
                    @Override
                    public CharSequence getCurrentContentTitle(@Nullable Player player) {
                        return getString(R.string.channel_name);
                    }

                    @Nullable
                    @Override
                    public PendingIntent createCurrentContentIntent(@Nullable Player player) {
                        Intent resultIntent = new Intent(BaseActivity.this, MainActivity.class);
//                        resultIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//                        resultIntent.setAction(Intent.ACTION_MAIN);
//                        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        return PendingIntent.getActivity(getApplicationContext(),
                                0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    }

                    @Override
                    public CharSequence getCurrentContentText(@Nullable Player player) {
                        return getString(R.string.channel_desc);
                    }

                    @Nullable
                    @Override
                    public Bitmap getCurrentLargeIcon(@Nullable Player player,
                                                      @Nullable PlayerNotificationManager.BitmapCallback callback) {
                        return null;
                    }
                }, new PlayerNotificationManager.NotificationListener() {
                    @Override
                    public void onNotificationCancelled(int notificationId, boolean dismissedByUser) {
                        Log.e("onNotificationCancelled", "ıser : " + dismissedByUser);
                    }

                    @Override
                    public void onNotificationPosted(int notificationId, Notification notification, boolean ongoing) {
                        // startForeground(notificationId, notification);
                    }
                });
    }

   @Override
   protected void onDestroy() {
      super.onDestroy();
      if (playerNotificationManager != null) {
         playerNotificationManager.setPlayer(null);
      }
   }
}

//   private final Class<VM> mViewModelClass;
//
//   private BaseActivity(Class<VM> mViewModelClass) {
//      this.mViewModelClass = mViewModelClass;
//   }
//
//   private DB binding;
//   private VM viewModel;
//
//   private synchronized DB getBinding() {
//      if (binding == null) {
//         binding = DataBindingUtil.setContentView(this, getLayoutRes());
//      }
//      return binding;
//   }
//
//   private synchronized VM getViewModel() {
//      if (viewModel == null) {
//         viewModel = new ViewModelProvider(this).get(mViewModelClass);
//      }
//      return viewModel;
//   }
//
//
//   @LayoutRes
//   abstract public Integer getLayoutRes();
//
//   /**
//    * You need override this method.
//    * And you need to set viewModel to binding: binding.viewModel = viewModel
//    */
//   abstract public void initViewModel(VM viewModel);
