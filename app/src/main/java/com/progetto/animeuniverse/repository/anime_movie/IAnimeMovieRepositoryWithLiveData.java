package com.progetto.animeuniverse.repository.anime_movie;

import androidx.lifecycle.MutableLiveData;

import com.progetto.animeuniverse.model.Result;

public interface IAnimeMovieRepositoryWithLiveData {
    MutableLiveData<Result> fetchAnimeMovie(long lastUpdate);
    void fetchAnimeMovie();
}
