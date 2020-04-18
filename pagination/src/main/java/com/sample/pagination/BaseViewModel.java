package com.sample.pagination;

import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;

public class BaseViewModel extends ViewModel {

    protected CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.dispose();
    }
}
