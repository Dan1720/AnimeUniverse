package com.progetto.animeuniverse.data.source.genres;

import com.progetto.animeuniverse.model.Genre;
import com.progetto.animeuniverse.model.GenresApiResponse;

import java.util.List;

public interface GenresCallback {
    void onSuccessFromRemote(GenresApiResponse genresApiResponse, long lastUpdate);
    void onFailureFromRemote(Exception exception);
    void onSuccessFromLocal(GenresApiResponse genresApiResponse);
    void onFailureFromLocal(Exception exception);
    void onSuccessFromCloudReading(List<Genre> genresList);
    void onSuccessFromCloudWriting(Genre genre);
    void onFailureFromCloud(Exception exception);
    void onSuccessSynchronization();
    void onSuccessDeletion();
}
