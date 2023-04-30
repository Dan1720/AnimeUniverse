package com.progetto.animeuniverse.data.source.anime;

public abstract class BaseAnimeRemoteDataSource {
    protected AnimeCallback animeCallback;

    public void setAnimeCallback(AnimeCallback animeCallback){
        this.animeCallback = animeCallback;
    }

    public abstract void getAnimeByName(String q, String nameAnime);

}
