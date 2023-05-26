package com.progetto.animeuniverse.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.progetto.animeuniverse.repository.anime_new.IAnimeNewRepositoryWithLiveData;

public class AnimeNewViewModelFactory implements ViewModelProvider.Factory {
    private final IAnimeNewRepositoryWithLiveData iAnimeNewRepositoryWithLiveData;

    public AnimeNewViewModelFactory(IAnimeNewRepositoryWithLiveData iAnimeNewRepositoryWithLiveData) {
        this.iAnimeNewRepositoryWithLiveData = iAnimeNewRepositoryWithLiveData;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AnimeNewViewModel(iAnimeNewRepositoryWithLiveData);
    }
}
