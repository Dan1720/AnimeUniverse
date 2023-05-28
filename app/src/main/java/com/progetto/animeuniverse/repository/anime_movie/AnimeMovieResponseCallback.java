package com.progetto.animeuniverse.repository.anime_movie;

import com.progetto.animeuniverse.model.AnimeMovie;

import java.util.List;

public interface AnimeMovieResponseCallback {
    void onSuccess(List<AnimeMovie> animeMovieList, long lastUpdate);
    void onFailure(String errorMessage);
}
