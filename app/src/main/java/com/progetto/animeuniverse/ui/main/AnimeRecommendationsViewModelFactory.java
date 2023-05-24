package com.progetto.animeuniverse.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.progetto.animeuniverse.repository.anime_recommendations.IAnimeRecommendationsRepositoryWithLiveData;

public class AnimeRecommendationsViewModelFactory implements ViewModelProvider.Factory {
    private final IAnimeRecommendationsRepositoryWithLiveData iAnimeRecommendationsRepositoryWithLiveData;

    public AnimeRecommendationsViewModelFactory(IAnimeRecommendationsRepositoryWithLiveData iAnimeRecommendationsRepositoryWithLiveData) {
        this.iAnimeRecommendationsRepositoryWithLiveData = iAnimeRecommendationsRepositoryWithLiveData;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AnimeRecommendationsViewModel(iAnimeRecommendationsRepositoryWithLiveData);
    }
}
