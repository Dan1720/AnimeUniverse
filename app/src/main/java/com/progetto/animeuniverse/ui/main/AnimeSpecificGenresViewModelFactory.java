package com.progetto.animeuniverse.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.progetto.animeuniverse.repository.anime_specific_genres.IAnimeSpecificGenresRepositoryWithLiveData;


public class AnimeSpecificGenresViewModelFactory implements ViewModelProvider.Factory {
    private final IAnimeSpecificGenresRepositoryWithLiveData iAnimeSpecificGenresRepositoryWithLiveData;


    public AnimeSpecificGenresViewModelFactory(IAnimeSpecificGenresRepositoryWithLiveData iAnimeSpecificGenresRepositoryWithLiveData) {
        this.iAnimeSpecificGenresRepositoryWithLiveData = iAnimeSpecificGenresRepositoryWithLiveData;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AnimeSpecificGenresViewModel(iAnimeSpecificGenresRepositoryWithLiveData);
    }
}