package com.progetto.animeuniverse.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.progetto.animeuniverse.repository.anime_episodes.IAnimeEpisodesRepositoryWithLiveData;

public class AnimeEpisodesViewModelFactory implements ViewModelProvider.Factory{
    private final IAnimeEpisodesRepositoryWithLiveData iAnimeEpisodesRepositoryWithLiveData;


    public AnimeEpisodesViewModelFactory(IAnimeEpisodesRepositoryWithLiveData iAnimeEpisodesRepositoryWithLiveData) {
        this.iAnimeEpisodesRepositoryWithLiveData = iAnimeEpisodesRepositoryWithLiveData;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AnimeEpisodesViewModel(iAnimeEpisodesRepositoryWithLiveData);
    }
}
