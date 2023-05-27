package com.progetto.animeuniverse.repository.anime_new;

import androidx.lifecycle.MutableLiveData;

import com.progetto.animeuniverse.model.Result;

public interface IAnimeNewRepositoryWithLiveData {
    MutableLiveData<Result> fetchAnimeNew(long lastUpdate);

    void fetchAnimeNew();
}
