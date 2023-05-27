package com.progetto.animeuniverse.data.source.anime_specific_genres;

import com.progetto.animeuniverse.model.AnimeSpecificGenres;
import com.progetto.animeuniverse.model.AnimeSpecificGenresApiResponse;

import java.util.List;

public abstract class BaseAnimeSpecificGenresLocalDataSource {
    protected AnimeSpecificGenresCallback animeSpecificGenresCallback;

    public void setAnimeSpecificGenresCallback(AnimeSpecificGenresCallback animeSpecificGenresCallback){
        this.animeSpecificGenresCallback = animeSpecificGenresCallback;
    }

    public abstract void getAnimeSpecificGenres();
    public abstract void updateAnimeSpecificGenres(AnimeSpecificGenres animeSpecificGenres);
    public abstract void insertAnimeSpecificGenres(AnimeSpecificGenresApiResponse animeSpecificGenresApiResponse);
    public abstract void insertAnimeSpecificGenres(List<AnimeSpecificGenres> animeSpecificGenresList);
    public abstract void deleteAll();
}
