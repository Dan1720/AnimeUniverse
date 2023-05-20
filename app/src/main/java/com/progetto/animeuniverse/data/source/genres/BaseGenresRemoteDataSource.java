package com.progetto.animeuniverse.data.source.genres;

public abstract class BaseGenresRemoteDataSource {
    protected GenresCallback genresCallback;
    public void setGenresCallback(GenresCallback genresCallback){
        this.genresCallback = genresCallback;
    }
    public abstract void getGenres();
}
