package com.progetto.animeuniverse.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.progetto.animeuniverse.repository.anime_movie.IAnimeMovieRepositoryWithLiveData;

public class AnimeMovieViewModelFactory implements ViewModelProvider.Factory {
    private final IAnimeMovieRepositoryWithLiveData iAnimeMovieRepositoryWithLiveData;


    public AnimeMovieViewModelFactory(IAnimeMovieRepositoryWithLiveData iAnimeMovieRepositoryWithLiveData) {
        this.iAnimeMovieRepositoryWithLiveData = iAnimeMovieRepositoryWithLiveData;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AnimeMovieViewModel(iAnimeMovieRepositoryWithLiveData);
    }
}

