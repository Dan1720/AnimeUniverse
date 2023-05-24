package com.progetto.animeuniverse.data.source.anime_by_name;

public abstract class BaseAnimeByNameRemoteDataSource {
    protected AnimeByNameCallback animeByNameCallback;

    public void setAnimeByNameCallback(AnimeByNameCallback animeByNameCallback) {
        this.animeByNameCallback = animeByNameCallback;
    }

    public abstract void getAnimeByName(String nameAnime);
}
