package com.progetto.animeuniverse.repository.anime_recommendations;

import com.progetto.animeuniverse.model.AnimeRecommendations;

import java.util.List;

public interface AnimeRecommendationResponseCallback {
    void onSuccess(List<AnimeRecommendations> animeRecommendationsList, long lastUpdate);
    void onFailure(String errorMessage);
}
