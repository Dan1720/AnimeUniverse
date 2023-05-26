package com.progetto.animeuniverse.data.source.anime_new;

public abstract class BaseAnimeNewRemoteDataSource {
    protected AnimeNewCallback animeNewCallback;

    public void setAnimeNewCallback(AnimeNewCallback animeNewCallback) {
        this.animeNewCallback = animeNewCallback;
    }
    public abstract void getAnimeNew();
}
