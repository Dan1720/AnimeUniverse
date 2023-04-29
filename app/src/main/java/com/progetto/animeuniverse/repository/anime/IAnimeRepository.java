package com.progetto.animeuniverse.repository.anime;

import com.progetto.animeuniverse.Anime;

public interface IAnimeRepository {
    void fetchAnime(String country, int page, long lastUpdate);
    void updateAnime(Anime anime);
    void getFavoriteAnime();
    void deleteFavoriteAnime();
}

