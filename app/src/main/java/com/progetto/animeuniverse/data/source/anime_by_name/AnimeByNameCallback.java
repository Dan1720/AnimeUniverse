package com.progetto.animeuniverse.data.source.anime_by_name;

import com.progetto.animeuniverse.model.AnimeByName;
import com.progetto.animeuniverse.model.AnimeByNameApiResponse;

import java.util.List;

public interface AnimeByNameCallback {
    void onSuccessFromRemote(AnimeByNameApiResponse animeByNameApiResponse, long lastUpdate);
    void onFailureFromRemote(Exception exception);
    void onSuccessFromLocal(AnimeByNameApiResponse animeByNameApiResponse);
    void onFailureFromLocal(Exception exception);
    void onSuccessFromCloudReading(List<AnimeByName> animeByNameList);
    void onSuccessFromCloudWriting(AnimeByName animeByName);
    void onFailureFromCloud(Exception exception);
    void onSuccessSynchronization();
    void onSuccessDeletion();
}
