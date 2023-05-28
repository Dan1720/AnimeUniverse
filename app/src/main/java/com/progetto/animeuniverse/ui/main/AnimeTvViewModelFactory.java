package com.progetto.animeuniverse.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.progetto.animeuniverse.repository.anime_tv.IAnimeTvRepositoryWithLiveData;

public class AnimeTvViewModelFactory implements ViewModelProvider.Factory {
    private final IAnimeTvRepositoryWithLiveData iAnimeTvRepositoryWithLiveData;


    public AnimeTvViewModelFactory(IAnimeTvRepositoryWithLiveData iAnimeTvRepositoryWithLiveData) {
        this.iAnimeTvRepositoryWithLiveData = iAnimeTvRepositoryWithLiveData;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AnimeTvViewModel(iAnimeTvRepositoryWithLiveData);
    }
}
