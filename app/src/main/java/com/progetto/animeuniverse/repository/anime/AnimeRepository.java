package com.progetto.animeuniverse.repository.anime;

import android.app.Application;

import com.progetto.animeuniverse.Anime;
import com.progetto.animeuniverse.data.service.AnimeApiService;

public class AnimeRepository implements IAnimeRepository{

    public static final String TAG = AnimeRepository.class.getSimpleName();

    private final Application application;
    private final AnimeApiService animeApiService;

    public AnimeRepository(Application application, AnimeApiService animeApiService) {
        this.application = application;
        this.animeApiService = animeApiService;
    }

    @Override
    public void fetchAnime(String country, int page, long lastUpdate) {

    }

    @Override
    public void updateAnime(Anime anime) {

    }

    @Override
    public void getFavoriteAnime() {

    }

    @Override
    public void deleteFavoriteAnime() {

    }
}
