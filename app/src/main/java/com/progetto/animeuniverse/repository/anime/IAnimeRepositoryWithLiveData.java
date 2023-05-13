package com.progetto.animeuniverse.repository.anime;

import androidx.lifecycle.MutableLiveData;

import com.progetto.animeuniverse.model.Anime;
import com.progetto.animeuniverse.model.Result;

public interface IAnimeRepositoryWithLiveData {

    MutableLiveData<Result> fetchAnimeByName(String nameAnime, long lastUpdate);
    MutableLiveData<Result> fetchAnimeByIdFull(String q, int id, long lastUpdate);
    MutableLiveData<Result> fetchAnimeById(String q, int id, long lastUpdate);

    MutableLiveData<Result> fetchAnimeTop(long lastUpdate);

    void fetchAnimeByName(String nameAnime);
    void fetchAnimeByIdFull(String q, int id);
    void fetchAnimeById(String q, int id);
    void fetchAnimeTop();
    MutableLiveData<Result> getFavoriteAnime(boolean firstLoading);
    void updateAnime(Anime anime);
    void deleteFavoriteAnime();
}
