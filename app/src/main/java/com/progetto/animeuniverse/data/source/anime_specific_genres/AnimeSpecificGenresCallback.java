package com.progetto.animeuniverse.data.source.anime_specific_genres;

import com.progetto.animeuniverse.model.AnimeSpecificGenres;
import com.progetto.animeuniverse.model.AnimeSpecificGenresApiResponse;

import java.util.List;

public interface AnimeSpecificGenresCallback {
    void onSuccessFromRemote(AnimeSpecificGenresApiResponse animeSpecificGenresApiResponse, long lastUpdate);
    void onFailureFromRemote(Exception exception);
    void onSuccessFromLocal(AnimeSpecificGenresApiResponse animeSpecificGenresApiResponse);
    void onFailureFromLocal(Exception exception);
    void onSuccessFromCloudReading(List<AnimeSpecificGenres> animeSpecificGenresList);
    void onSuccessFromCloudWriting(AnimeSpecificGenres animeSpecificGenres);
    void onFailureFromCloud(Exception exception);
    void onSuccessSynchronization();
    void onSuccessDeletion();
}
