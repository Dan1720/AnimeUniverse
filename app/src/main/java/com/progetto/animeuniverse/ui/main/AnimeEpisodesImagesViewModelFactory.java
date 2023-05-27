package com.progetto.animeuniverse.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.progetto.animeuniverse.repository.anime_episodes_images.IAnimeEpisodesImagesRepositoryWithLiveData;

public class AnimeEpisodesImagesViewModelFactory implements ViewModelProvider.Factory{
    private final IAnimeEpisodesImagesRepositoryWithLiveData iAnimeEpisodesImagesRepositoryWithLiveData;

    public AnimeEpisodesImagesViewModelFactory(IAnimeEpisodesImagesRepositoryWithLiveData iAnimeEpisodesImagesRepositoryWithLiveData) {
        this.iAnimeEpisodesImagesRepositoryWithLiveData = iAnimeEpisodesImagesRepositoryWithLiveData;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AnimeEpisodesImagesViewModel(iAnimeEpisodesImagesRepositoryWithLiveData);
    }
}
