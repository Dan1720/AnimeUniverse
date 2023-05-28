package com.progetto.animeuniverse.data.source.anime_movie;

import com.progetto.animeuniverse.model.AnimeMovie;
import com.progetto.animeuniverse.model.AnimeMovieApiResponse;

import java.util.List;

public interface AnimeMovieCallback {
    void onSuccessFromRemote(AnimeMovieApiResponse animeMovieApiResponse, long lastUpdate);
    void onFailureFromRemote(Exception exception);
    void onSuccessFromLocal(AnimeMovieApiResponse animeMovieApiResponse);
    void onFailureFromLocal(Exception exception);
    void onSuccessFromCloudReading(List<AnimeMovie> animeMovieList);
    void onSuccessFromCloudWriting(AnimeMovie animeMovie);
    void onFailureFromCloud(Exception exception);
    void onSuccessSynchronization();
    void onSuccessDeletion();
}
