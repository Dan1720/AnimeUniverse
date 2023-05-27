package com.progetto.animeuniverse.repository.anime_episodes_images;

import androidx.lifecycle.MutableLiveData;

import com.progetto.animeuniverse.model.Result;

public interface IAnimeEpisodesImagesRepositoryWithLiveData {
    MutableLiveData<Result> fetchAnimeEpisodesImages(int idAnime, long lastUpdate);
    void fetchAnimeEpisodesImages(int idAnime);
}
