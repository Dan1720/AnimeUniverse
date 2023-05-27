package com.progetto.animeuniverse.data.source.anime_movie;

public abstract class BaseAnimeMovieRemoteDataSource {
    protected AnimeMovieCallback animeMovieCallback;

    public void setAnimeMovieCallback(AnimeMovieCallback animeMovieCallback) {
        this.animeMovieCallback = animeMovieCallback;
    }

    public abstract void getAnimeMovie();
}
