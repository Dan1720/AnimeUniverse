package com.progetto.animeuniverse.repository.anime;

import androidx.lifecycle.MutableLiveData;

import com.progetto.animeuniverse.model.Anime;
import com.progetto.animeuniverse.model.Result;

public interface IAnimeRepositoryWithLiveData {

    MutableLiveData<Result> fetchAnimeTop(long lastUpdate);
    void fetchAnimeTop();
    MutableLiveData<Result> getFavoriteAnime(boolean firstLoading);
    void updateAnime(Anime anime);
    void deleteFavoriteAnime();
}
