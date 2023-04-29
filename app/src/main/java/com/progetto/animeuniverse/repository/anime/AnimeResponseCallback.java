package com.progetto.animeuniverse.repository.anime;

import com.progetto.animeuniverse.Anime;

import java.util.List;

public interface AnimeResponseCallback {
    void onSuccess(List<Anime> animeList, long lastUpdate);
    void onFailure(String errorMessage);
    void onAnimeFavoriteStatusChanged(Anime anime);
}
