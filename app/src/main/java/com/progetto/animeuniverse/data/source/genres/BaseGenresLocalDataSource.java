package com.progetto.animeuniverse.data.source.genres;

import com.progetto.animeuniverse.model.Genre;
import com.progetto.animeuniverse.model.GenresApiResponse;

import java.util.List;

public abstract class BaseGenresLocalDataSource {
    protected GenresCallback genresCallback;

    public void setGenresCallback(GenresCallback genresCallback){
        this.genresCallback = genresCallback;
    }
    public abstract void getGenre();
    public abstract void updateGenre(Genre genre);
    public abstract void insertGenres(GenresApiResponse genresApiResponse);
    public abstract void insertGenres(List<Genre> genresList);
    public abstract void deleteAll();
}
