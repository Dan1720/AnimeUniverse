package com.progetto.animeuniverse.data.source.anime;

import com.progetto.animeuniverse.model.Anime;
import com.progetto.animeuniverse.model.AnimeApiResponse;

import java.util.List;

public interface AnimeCallback {
    void onSuccessFromRemote(AnimeApiResponse animeApiResponse, long lastUpdate);
    void onFailureFromRemote(Exception exception);
    void onSuccessFromLocal(AnimeApiResponse animeApiResponse);
    void onFailureFromLocal(Exception exception);
    void onAnimeFavoriteStatusChanged(Anime anime, List<Anime> favoriteAnime);
    void onAnimeFavoriteStatusChanged(List<Anime> anime);
    void onDeleteFavoriteAnimeSuccess(List<Anime> favoriteAnime);
    void onSuccessFromCloudReading(List<Anime> animeList);
    void onSuccessFromCloudWriting(Anime anime);
    void onFailureFromCloud(Exception exception);
    void onSuccessSynchronization();
    void onSuccessDeletion();
}
