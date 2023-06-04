package com.progetto.animeuniverse.repository.anime_specific_genres;

import androidx.lifecycle.MutableLiveData;

import com.progetto.animeuniverse.model.Result;

public interface IAnimeSpecificGenresRepositoryWithLiveData {
    MutableLiveData<Result> fetchAnimeSpecificGenres(long lastUpdate);
    void fetchAnimeSpecificGenres();

}
