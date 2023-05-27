package com.progetto.animeuniverse.repository.anime_new;

import com.progetto.animeuniverse.model.AnimeNew;

import java.util.List;

public interface AnimeNewResponseCallback {
    void onSuccess(List<AnimeNew> animeNewList, long lastUpdate);
    void onFailure(String errorMessage);
}
