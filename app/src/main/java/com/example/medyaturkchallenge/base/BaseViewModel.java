package com.example.medyaturkchallenge.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.medyaturkchallenge.data.remote.services.MainService;

import io.reactivex.disposables.CompositeDisposable;

public class BaseViewModel extends ViewModel {

   // Yükleniyor durumu
   public final MutableLiveData<Boolean> loadingState = new MutableLiveData<>();
   // Api'nin hata vermesi durumu
   public final MutableLiveData<String> errorMessage = new MutableLiveData<>();
   // Retrofit değişkenine erişim
   protected final MainService mainService = MainService.getInstance();
   // RxJava bağlantı nesnesi
   protected final CompositeDisposable disposable = new CompositeDisposable();

   @Override
   protected void onCleared() {
      super.onCleared();
      //RxJava bağlantısını kapat
      disposable.clear();
   }
}
