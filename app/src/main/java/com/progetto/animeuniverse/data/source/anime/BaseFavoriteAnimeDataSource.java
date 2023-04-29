package com.progetto.animeuniverse.data.source.anime;

import com.progetto.animeuniverse.model.Anime;

import java.util.List;

public abstract class BaseFavoriteAnimeDataSource {
    protected AnimeCallback animeCallback;

    public void setAnimeCallback(AnimeCallback animeCallback){
        this.animeCallback = animeCallback;
    }

    public abstract void getFavoriteAnime();
    public abstract void addFavoriteAnime(Anime anime);
    public abstract void synchronizeFavoriteAnime(List<Anime> notSynchronizedAnimeList);
    public abstract void deleteFavoriteAnime(Anime anime);
    public abstract void deleteAllFavoriteAnime();
}
