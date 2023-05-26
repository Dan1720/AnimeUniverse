package com.progetto.animeuniverse.repository.anime_by_name;

import androidx.lifecycle.MutableLiveData;

import com.progetto.animeuniverse.model.Result;

public interface IAnimeByNameRepositoryWithLiveData {
    MutableLiveData<Result> fetchAnimeByName(String nameAnime, long lastUpdate);
    void fetchAnimeByName(String nameAnime);
}
