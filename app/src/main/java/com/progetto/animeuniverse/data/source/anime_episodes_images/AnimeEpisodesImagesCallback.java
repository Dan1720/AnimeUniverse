package com.progetto.animeuniverse.data.source.anime_episodes_images;

import com.progetto.animeuniverse.model.AnimeEpisodesImages;
import com.progetto.animeuniverse.model.AnimeEpisodesImagesApiResponse;

import java.util.List;

public interface AnimeEpisodesImagesCallback {
    void onSuccessFromRemote(AnimeEpisodesImagesApiResponse animeEpisodesImagesApiResponse, long lastUpdate);
    void onFailureFromRemote(Exception exception);
    void onSuccessFromLocal(AnimeEpisodesImagesApiResponse animeEpisodesImagesApiResponse);
    void onFailureFromLocal(Exception exception);
    void onSuccessFromCloudReading(List<AnimeEpisodesImages> animeEpisodesImagesList);
    void onSuccessFromCloudWriting(AnimeEpisodesImages animeEpisodesImages);
    void onFailureFromCloud(Exception exception);
    void onSuccessSynchronization();
    void onSuccessDeletion();
}
