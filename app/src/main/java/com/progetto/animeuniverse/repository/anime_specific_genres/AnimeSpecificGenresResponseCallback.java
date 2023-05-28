package com.progetto.animeuniverse.repository.anime_specific_genres;

import com.progetto.animeuniverse.model.AnimeSpecificGenres;

import java.util.List;

public interface AnimeSpecificGenresResponseCallback {
    void onSuccess(List<AnimeSpecificGenres> animeSpecificGenresList, long lastUpdate);
    void onFailure(String errorMessage);
}
