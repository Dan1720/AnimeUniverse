package com.progetto.animeuniverse.repository.anime_episodes;

import androidx.lifecycle.MutableLiveData;

import com.progetto.animeuniverse.model.Result;

public interface IAnimeEpisodesRepositoryWithLiveData {
    MutableLiveData<Result> fetchAnimeEpisodes(int idAnime, long lastUpdate);

    void fetchAnimeEpisodes(int idAnime);
}
