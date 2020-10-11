package com.example.medyaturkchallenge.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.medyaturkchallenge.base.BaseViewModel;
import com.example.medyaturkchallenge.data.remote.models.MainPage;
import com.example.medyaturkchallenge.utils.Constants;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends BaseViewModel {

   // Anasayfa adresinden alınan json nesnesi
   private final MutableLiveData<MainPage> _mainPageLiveData = new MutableLiveData<>();
   public final LiveData<MainPage> mainPageLiveData = _mainPageLiveData;

   /**
    * Anasayfa api'sine bağlantıyı başlat
    */
   public void fetchMainPage() {
      // Bağlantı başlatıldığında loadingState aktif ediliyor.
      // Bağlantı başarılı veya başarısız sonlandığında deaktif yapılacak.
      loadingState.setValue(true);

      disposable.add(
          mainService.getMainPage()
              .subscribeOn(Schedulers.newThread())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribeWith(new DisposableSingleObserver<MainPage>() {
                 @Override
                 public void onSuccess(MainPage mainPage) {
                    // Gelen nesne null olabilir. Önlem al
                    if (mainPage != null) {
                       // Bağlantı başarılı ise
                       if (mainPage.getErrorCode() == 0) {
                          _mainPageLiveData.setValue(mainPage);
                       } else { // Sunucuya bağlanırken hata verdiyse
                          String message = Constants.ServerNullResponse;
                          // Eğer sunucudan bir hata mesajı bilgisi alırsak onu kullan.
                          // Hata mesajı null gelirse varsayılan mesajı bas.
                          if (mainPage.getErrorMessage() != null) {
                             message = mainPage.getErrorMessage();
                          }
                          // Observer'ı tetikle
                          errorMessage.setValue(message);
                       }
                    } else { // Json verisi null geldiyse
                       errorMessage.setValue(Constants.ServerNullResponse);
                    }
                    // Yükleniyor durumu bitti
                    loadingState.setValue(false);
                 }

                 @Override
                 public void onError(Throwable e) {
                    // API'ye bağlanamama durumu
                    errorMessage.setValue(Constants.ServerNoConn);
                    loadingState.setValue(false);
                 }
              })
      );
   }

}
