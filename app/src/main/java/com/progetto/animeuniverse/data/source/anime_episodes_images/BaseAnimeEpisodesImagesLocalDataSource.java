package com.progetto.animeuniverse.data.source.anime_episodes_images;

import com.progetto.animeuniverse.model.AnimeEpisodesImages;
import com.progetto.animeuniverse.model.AnimeEpisodesImagesApiResponse;

import java.util.List;

public abstract class BaseAnimeEpisodesImagesLocalDataSource {
    protected AnimeEpisodesImagesCallback animeEpisodesImagesCallback;

    public void setAnimeEpisodesImagesCallback(AnimeEpisodesImagesCallback animeEpisodesImagesCallback) {
        this.animeEpisodesImagesCallback = animeEpisodesImagesCallback;
    }

    public abstract void getAnimeEpisodesImages();
    public abstract void updateAnimeEpisodesImages(AnimeEpisodesImages animeEpisodesImages);
    public abstract void insertAnimeEpisodesImages(AnimeEpisodesImagesApiResponse animeEpisodesImagesApiResponse);
    public abstract void insertAnimeEpisodesImages(List<AnimeEpisodesImages> animeEpisodesImagesList);
    public abstract void deleteAll();
}
