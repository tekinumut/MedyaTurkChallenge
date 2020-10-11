package com.example.medyaturkchallenge.ui.news_detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.medyaturkchallenge.base.BaseViewModel;
import com.example.medyaturkchallenge.data.remote.models.Detail;
import com.example.medyaturkchallenge.utils.Constants;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class NewsDetailViewModel extends BaseViewModel {

   // Detay adresinden alınan json nesnesi
   private final MutableLiveData<Detail> _newsDetailLiveData = new MutableLiveData<>();
   public final LiveData<Detail> newsDetailLiveData = _newsDetailLiveData;

   /**
    * Detay api'sine bağlantıyı başlat
    */
   public void fetchNewsDetail() {
      // Bağlantı başlatıldığında loadingState aktif ediliyor.
      // Bağlantı başarılı veya başarısız sonlandığında deaktif yapılacak.
      loadingState.setValue(true);

      disposable.add(
          mainService.getNewsDetail()
              .subscribeOn(Schedulers.newThread())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribeWith(new DisposableSingleObserver<Detail>() {
                 @Override
                 public void onSuccess(Detail detail) {
                    // Gelen nesne null olabilir. Önlem al
                    if (detail != null) {
                       // Bağlantı başarılı ise
                       if (detail.getErrorCode() == 0) {
                          _newsDetailLiveData.setValue(detail);
                       } else { // Sunucuya bağlanırken hata verdiyse
                          String message = Constants.ServerNullResponse;
                          // Eğer sunucudan bir hata mesajı bilgisi alırsak onu kullan.
                          // Hata mesajı null gelirse varsayılan mesajı bas.
                          if (detail.getErrorMessage() != null) {
                             message = detail.getErrorMessage();
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
