package com.progetto.animeuniverse.data.source.anime_episodes_images;

public abstract class BaseAnimeEpisodesImagesRemoteDataSource {
    protected AnimeEpisodesImagesCallback animeEpisodesImagesCallback;

    public void setAnimeEpisodesImagesCallback(AnimeEpisodesImagesCallback animeEpisodesImagesCallback) {
        this.animeEpisodesImagesCallback = animeEpisodesImagesCallback;
    }
    public abstract void getAnimeEpisodesImages(int idAnime);
}
