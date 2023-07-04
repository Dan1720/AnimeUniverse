package com.progetto.animeuniverse.data.source.anime_recommendations;

import com.progetto.animeuniverse.model.AnimeRecommendations;
import com.progetto.animeuniverse.model.AnimeRecommendationsApiResponse;


import java.util.List;

public interface AnimeRecommendationsCallback {
    void onSuccessFromRemote(AnimeRecommendationsApiResponse animeRecommendationsApiResponse, long lastUpdate);
    void onFailureFromRemote(Exception exception);
    void onSuccessFromLocal(AnimeRecommendationsApiResponse animeRecommendationsApiResponse);
    void onFailureFromLocal(Exception exception);
    void onSuccessFromCloudReading(List<AnimeRecommendations> animeRecommendationsList);
    void onSuccessFromCloudWriting(AnimeRecommendations animeRecommendations);
    void onFailureFromCloud(Exception exception);
    void onSuccessSynchronization();
    void onSuccessDeletion();
}
