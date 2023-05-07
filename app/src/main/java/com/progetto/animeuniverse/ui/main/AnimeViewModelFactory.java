package com.progetto.animeuniverse.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.progetto.animeuniverse.repository.anime.IAnimeRepositoryWithLiveData;

public class AnimeViewModelFactory implements ViewModelProvider.Factory {
    private final IAnimeRepositoryWithLiveData iAnimeRepositoryWithLiveData;

    public AnimeViewModelFactory(IAnimeRepositoryWithLiveData iAnimeRepositoryWithLiveData){
        this.iAnimeRepositoryWithLiveData = iAnimeRepositoryWithLiveData;
    }
    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create (@NonNull Class <T> modelClass){
        return (T) new AnimeViewModel(iAnimeRepositoryWithLiveData);
    }
}
