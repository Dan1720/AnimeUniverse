package com.progetto.animeuniverse.repository;

import com.progetto.animeuniverse.model.Anime;

import java.util.List;

public interface ResponseCallback {
    void onSuccess(List<Anime> animeList);
    void onFailure(String errorMessage);
    void onAnimeFavoriteStatusChanged(Anime anime);
}
