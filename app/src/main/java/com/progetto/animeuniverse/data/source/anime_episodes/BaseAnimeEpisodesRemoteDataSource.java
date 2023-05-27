package com.progetto.animeuniverse.data.source.anime_episodes;

public abstract class BaseAnimeEpisodesRemoteDataSource {
    protected AnimeEpisodesCallback animeEpisodesCallback;

    public void setAnimeEpisodesCallback(AnimeEpisodesCallback animeEpisodesCallback) {
        this.animeEpisodesCallback = animeEpisodesCallback;
    }

    public abstract void getAnimeEpisodes(int idAnime);
}
