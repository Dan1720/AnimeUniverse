package com.progetto.animeuniverse.repository.anime_recommendations;

import androidx.lifecycle.MutableLiveData;

import com.progetto.animeuniverse.model.Result;

public interface IAnimeRecommendationsRepositoryWithLiveData {
    MutableLiveData<Result> fetchAnimeRecommendations(long lastUpdate);
    void fetchAnimeRecommendations();
}
