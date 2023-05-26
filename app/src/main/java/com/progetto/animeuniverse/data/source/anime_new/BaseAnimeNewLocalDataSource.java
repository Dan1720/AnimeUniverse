package com.progetto.animeuniverse.data.source.anime_new;

import com.progetto.animeuniverse.model.AnimeNew;
import com.progetto.animeuniverse.model.AnimeNewApiResponse;

import java.util.List;

public abstract class BaseAnimeNewLocalDataSource {
    protected AnimeNewCallback animeNewCallback;

    public void setAnimeNewCallback(AnimeNewCallback animeNewCallback) {
        this.animeNewCallback = animeNewCallback;
    }

    public abstract void getAnimeNew();
    public abstract void updateAnimeNew(AnimeNew animeNew);
    public abstract void insertAnimeNew(AnimeNewApiResponse animeNewApiResponse);
    public abstract void insertAnimeNew(List<AnimeNew> animeNewList);
    public abstract void deleteAll();
}
