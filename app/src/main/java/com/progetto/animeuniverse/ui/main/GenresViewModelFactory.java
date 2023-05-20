package com.progetto.animeuniverse.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.progetto.animeuniverse.repository.genres.IGenresRepositoryWithLiveData;

public class GenresViewModelFactory implements ViewModelProvider.Factory {
    private final IGenresRepositoryWithLiveData iGenresRepositoryWithLiveData;


    public GenresViewModelFactory(IGenresRepositoryWithLiveData iGenresRepositoryWithLiveData) {
        this.iGenresRepositoryWithLiveData = iGenresRepositoryWithLiveData;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new GenresViewModel(iGenresRepositoryWithLiveData);
    }
}
