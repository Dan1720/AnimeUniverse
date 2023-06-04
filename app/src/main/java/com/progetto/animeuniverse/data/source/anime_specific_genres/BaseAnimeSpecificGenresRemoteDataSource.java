package com.progetto.animeuniverse.data.source.anime_specific_genres;


public abstract class BaseAnimeSpecificGenresRemoteDataSource {
    protected AnimeSpecificGenresCallback animeSpecificGenresCallback;

    public void setAnimeSpecificGenresCallback(AnimeSpecificGenresCallback animeSpecificGenresCallback) {
        this.animeSpecificGenresCallback = animeSpecificGenresCallback;
    }

    public abstract void getAnimeSpecificGenres(int idGenre);
}
