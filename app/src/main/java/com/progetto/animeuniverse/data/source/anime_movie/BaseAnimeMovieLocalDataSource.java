package com.progetto.animeuniverse.data.source.anime_movie;

import com.progetto.animeuniverse.model.AnimeMovie;
import com.progetto.animeuniverse.model.AnimeMovieApiResponse;

import java.util.List;

public abstract class BaseAnimeMovieLocalDataSource {
    protected AnimeMovieCallback animeMovieCallback;

    public void setAnimeMovieCallback(AnimeMovieCallback animeMovieCallback) {
        this.animeMovieCallback = animeMovieCallback;
    }

    public abstract void getAnimeMovie();
    public abstract void updateAnimeMovie(AnimeMovie animeMovie);
    public abstract void insertAnimeMovie(AnimeMovieApiResponse animeMovieApiResponse);
    public abstract void insertAnimeMovie(List<AnimeMovie> animeMovieList);
    public abstract void deleteAll();
}
