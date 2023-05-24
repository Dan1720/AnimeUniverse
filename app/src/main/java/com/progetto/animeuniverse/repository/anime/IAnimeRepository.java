package com.progetto.animeuniverse.repository.anime;

import com.progetto.animeuniverse.model.Anime;

public interface IAnimeRepository {

    void fetchAnimeTop(long lastUpdate);

    void updateAnime(Anime anime);
    void getFavoriteAnime();
    void deleteFavoriteAnime();
}

