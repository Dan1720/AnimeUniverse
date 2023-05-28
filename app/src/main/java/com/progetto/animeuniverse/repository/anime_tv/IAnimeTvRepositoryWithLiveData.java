package com.progetto.animeuniverse.repository.anime_tv;

import androidx.lifecycle.MutableLiveData;

import com.progetto.animeuniverse.model.Result;

public interface IAnimeTvRepositoryWithLiveData {
    MutableLiveData<Result> fetchAnimeTv(long lastUpdate);
    void fetchAnimeTv();
}
