package com.progetto.animeuniverse.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.progetto.animeuniverse.repository.anime_by_name.IAnimeByNameRepositoryWithLiveData;

public class AnimeByNameViewModelFactory implements ViewModelProvider.Factory {
    private final IAnimeByNameRepositoryWithLiveData iAnimeByNameRepositoryWithLiveData;


    public AnimeByNameViewModelFactory(IAnimeByNameRepositoryWithLiveData iAnimeByNameRepositoryWithLiveData) {
        this.iAnimeByNameRepositoryWithLiveData = iAnimeByNameRepositoryWithLiveData;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AnimeByNameViewModel(iAnimeByNameRepositoryWithLiveData);
    }
}
