package com.progetto.animeuniverse.data.source.anime_recommendations;

import com.progetto.animeuniverse.model.AnimeRecommendations;
import com.progetto.animeuniverse.model.AnimeRecommendationsApiResponse;

import java.util.List;

public abstract class BaseAnimeRecommendationsLocalDataSource {
    protected AnimeRecommendationsCallback animeRecommendationsCallback;

    public void setAnimeRecommendationsCallback(AnimeRecommendationsCallback animeRecommendationsCallback){
        this.animeRecommendationsCallback = animeRecommendationsCallback;
    }

    public abstract void getAnimeRecommendations();
    public abstract void updateAnimeRecommendations(AnimeRecommendations animeRecommendations);
    public abstract void insertAnimeRecommendations(AnimeRecommendationsApiResponse animeRecommendationsApiResponse);
    public abstract void insertAnimeRecommendations(List<AnimeRecommendations> animeRecommendationsList);
    public abstract void deleteAll();
}
