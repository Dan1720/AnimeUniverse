package com.progetto.animeuniverse.repository.anime_by_name;

public interface IAnimeByNameRepository {
    void fetchAnimeByName(String nameAnime, long lastUpdate);
}
