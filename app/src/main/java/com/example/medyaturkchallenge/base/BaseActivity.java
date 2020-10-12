package com.example.medyaturkchallenge.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//VM extends BaseViewModel, DB extends ViewDataBinding

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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
