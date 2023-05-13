package com.progetto.animeuniverse.data.source.anime;

public abstract class BaseAnimeRemoteDataSource {
    protected AnimeCallback animeCallback;

    public void setAnimeCallback(AnimeCallback animeCallback){
        this.animeCallback = animeCallback;
    }

    public abstract void getAnimeByName(String nameAnime);
    public abstract void getAnimeByIdFull(String anime, int id);
    public abstract void getAnimeById(String anime, int id);
    public abstract void getAnimeTop();

}
