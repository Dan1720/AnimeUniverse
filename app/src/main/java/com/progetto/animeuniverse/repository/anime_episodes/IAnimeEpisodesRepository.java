package com.progetto.animeuniverse.repository.anime_episodes;

public interface IAnimeEpisodesRepository {
    void fetchAnimeEpisodes(int idAnime, long lastUpdate);
}
