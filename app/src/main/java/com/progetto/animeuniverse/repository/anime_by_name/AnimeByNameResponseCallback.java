package com.progetto.animeuniverse.repository.anime_by_name;

import com.progetto.animeuniverse.model.AnimeByName;

import java.util.List;

public interface AnimeByNameResponseCallback {
    void onSuccess(List<AnimeByName> animeByNameList, long lastUpdate);
    void onFailure(String errorMessage);
}
