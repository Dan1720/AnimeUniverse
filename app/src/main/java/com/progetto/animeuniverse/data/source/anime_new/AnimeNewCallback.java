package com.progetto.animeuniverse.data.source.anime_new;

import com.progetto.animeuniverse.model.AnimeNew;
import com.progetto.animeuniverse.model.AnimeNewApiResponse;
import com.progetto.animeuniverse.model.AnimeRecommendations;
import com.progetto.animeuniverse.model.AnimeRecommendationsApiResponse;

import java.util.List;

public interface AnimeNewCallback {
    void onSuccessFromRemote(AnimeNewApiResponse animeNewApiResponse, long lastUpdate);
    void onFailureFromRemote(Exception exception);
    void onSuccessFromLocal(AnimeNewApiResponse animeNewApiResponse);
    void onFailureFromLocal(Exception exception);
    void onSuccessFromCloudReading(List<AnimeNew> animeNewList);
    void onSuccessFromCloudWriting(AnimeNew animeNew);
    void onFailureFromCloud(Exception exception);
    void onSuccessSynchronization();
    void onSuccessDeletion();
}
