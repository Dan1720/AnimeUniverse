package com.progetto.animeuniverse.repository.anime_episodes;

import com.progetto.animeuniverse.model.AnimeEpisodes;

import java.util.List;

public interface AnimeEpisodesResponseCallback {
    void onSuccessEpisodes(List<AnimeEpisodes> animeEpisodesList, long lastUpdate);
    void onFailure(String errorMessage);
}
