package com.progetto.animeuniverse.data.source.anime;

import com.progetto.animeuniverse.model.Anime;
import com.progetto.animeuniverse.model.AnimeApiResponse;

import java.util.List;

public abstract class BaseAnimeLocalDataSource {
    protected AnimeCallback animeCallback;

    public void setAnimeCallback(AnimeCallback animeCallback){
        this.animeCallback = animeCallback;
    }

    public abstract void getAnime();
    public abstract void getFavoriteAnime();
    public abstract void updateAnime(Anime anime);
    public abstract void deleteFavoriteAnime();
    public abstract void insertAnime(AnimeApiResponse animeApiResponse);
    public abstract void insertAnime(List<Anime> animeList);
    public abstract void deleteAll();
}
