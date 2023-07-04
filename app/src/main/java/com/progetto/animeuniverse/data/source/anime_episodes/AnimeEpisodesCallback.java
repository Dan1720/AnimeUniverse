package com.progetto.animeuniverse.data.source.anime_episodes;

import com.progetto.animeuniverse.model.AnimeEpisodes;
import com.progetto.animeuniverse.model.AnimeEpisodesApiResponse;

import java.util.List;

public interface AnimeEpisodesCallback {
    void onSuccessFromRemote(AnimeEpisodesApiResponse animeEpisodesApiResponse, long lastUpdate);
    void onFailureFromRemote(Exception exception);
    void onSuccessFromLocal(AnimeEpisodesApiResponse animeEpisodesApiResponse);
    void onFailureFromLocal(Exception exception);
    void onSuccessFromCloudReading(List<AnimeEpisodes> animeEpisodesList);
    void onSuccessFromCloudWriting(AnimeEpisodes animeEpisodes);
    void onFailureFromCloud(Exception exception);
    void onSuccessSynchronization();
    void onSuccessDeletion();
}
