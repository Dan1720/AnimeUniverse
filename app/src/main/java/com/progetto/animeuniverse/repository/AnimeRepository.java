package com.progetto.animeuniverse.repository;

import android.app.Application;

import com.progetto.animeuniverse.service.AnimeApiService;
import com.progetto.animeuniverse.util.ServiceLocator;

public class AnimeRepository implements IAnimeRepository{
   private final ResponseCallback responseCallback;
    private final Application application;
    private final AnimeApiService animeApiService;



    public AnimeRepository(Application application, ResponseCallback responseCallback) {
        this.application = application;
        this.animeApiService = ServiceLocator.getInstance().getAnimeApiService();
        this.responseCallback = responseCallback;
    }





   @Override
    public void fetchAnime() {


    }

}
