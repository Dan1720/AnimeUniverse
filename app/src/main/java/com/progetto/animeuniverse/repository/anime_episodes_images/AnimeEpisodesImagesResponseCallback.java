package com.progetto.animeuniverse.repository.anime_episodes_images;

import com.progetto.animeuniverse.model.AnimeEpisodesImages;

import java.util.List;

public interface AnimeEpisodesImagesResponseCallback {
    void onSuccess(List<AnimeEpisodesImages> animeEpisodesImagesList, long lastUpdate);
    void onFailure(String errorMessage);
}
