package com.progetto.animeuniverse.data.source.anime_episodes;

import com.progetto.animeuniverse.model.AnimeEpisodes;
import com.progetto.animeuniverse.model.AnimeEpisodesApiResponse;

import java.util.List;

public abstract class BaseAnimeEpisodesLocalDataSource {
    protected AnimeEpisodesCallback animeEpisodesCallback;

    public void setAnimeEpisodesCallback(AnimeEpisodesCallback animeEpisodesCallback) {
        this.animeEpisodesCallback = animeEpisodesCallback;
    }

    public abstract void getAnimeEpisodes();
    public abstract void updateAnimeEpisodes(AnimeEpisodes animeEpisodes);
    public abstract void insertAnimeEpisodes(AnimeEpisodesApiResponse animeEpisodesApiResponse);
    public abstract void insertAnimeEpisodes(List<AnimeEpisodes> animeEpisodesList);
    public abstract void deleteAll();
}
