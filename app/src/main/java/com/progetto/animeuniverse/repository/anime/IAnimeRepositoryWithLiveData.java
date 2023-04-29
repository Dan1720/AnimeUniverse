package com.progetto.animeuniverse.repository.anime;

import androidx.lifecycle.MutableLiveData;

import com.progetto.animeuniverse.Anime;
import com.progetto.animeuniverse.Result;

public interface IAnimeRepositoryWithLiveData {
    MutableLiveData<Result> fetchAnime(String country, int page, long lastUpdate);
    void fetchAnime(String country, int page);
    MutableLiveData<Result> getFavoriteAnime(boolean firstLoading);
    void updateAnime(Anime anime);
    void deleteFavoriteAnime();
}