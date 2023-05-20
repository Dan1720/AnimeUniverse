package com.progetto.animeuniverse.repository.genres;

import androidx.lifecycle.MutableLiveData;

import com.progetto.animeuniverse.model.Result;

public interface IGenresRepositoryWithLiveData {
    MutableLiveData<Result> fetchGenres(long lastUpdate);

    void fetchGenres();
}
