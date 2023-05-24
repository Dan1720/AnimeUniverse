package com.progetto.animeuniverse.data.source.anime_by_name;

import com.progetto.animeuniverse.model.AnimeByName;
import com.progetto.animeuniverse.model.AnimeByNameApiResponse;

import java.util.List;

public abstract class BaseAnimeByNameLocalDataSource {
    protected AnimeByNameCallback animeByNameCallback;

    public void setAnimeByNameCallback(AnimeByNameCallback animeByNameCallback){
        this.animeByNameCallback = animeByNameCallback;
    }

    public abstract void getAnimeByName();
    public abstract void updateAnimeByName(AnimeByName animeByName);
    public abstract void insertAnimeByName(AnimeByNameApiResponse animeByNameApiResponse);
    public abstract void insertAnimeByName(List<AnimeByName> animeByNameList);
    public abstract void deleteAll();
}
