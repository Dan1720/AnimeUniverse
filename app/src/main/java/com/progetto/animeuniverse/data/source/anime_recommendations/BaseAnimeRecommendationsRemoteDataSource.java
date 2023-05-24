package com.progetto.animeuniverse.data.source.anime_recommendations;

public abstract class BaseAnimeRecommendationsRemoteDataSource {
    protected AnimeRecommendationsCallback animeRecommendationsCallback;

    public void setAnimeRecommendationsCallback(AnimeRecommendationsCallback animeRecommendationsCallback){
        this.animeRecommendationsCallback = animeRecommendationsCallback;
    }

    public abstract void getAnimeRecommendations();
}
