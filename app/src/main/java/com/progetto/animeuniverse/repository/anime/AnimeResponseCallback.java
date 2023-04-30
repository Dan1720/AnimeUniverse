package com.progetto.animeuniverse.repository.anime;

import com.progetto.animeuniverse.model.Anime;

import java.util.List;
//Interfaccia che invia dati da repository al fragment
public interface AnimeResponseCallback {
    void onSuccess(List<Anime> animeList, long lastUpdate);
    void onFailure(String errorMessage);
    void onAnimeFavoriteStatusChanged(Anime anime);
}
