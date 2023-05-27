package com.progetto.animeuniverse.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.progetto.animeuniverse.model.AnimeEpisodes;

import java.util.List;

@Dao
public interface AnimeEpisodesDao {
    @Query("SELECT * FROM anime_episodes")
    List<AnimeEpisodes> getAll();

    @Query("SELECT * FROM anime_episodes WHERE id = :id")
    AnimeEpisodes getAnimeEpisodes(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertEpisodesList(List<AnimeEpisodes> animeEpisodesList);

    @Query("DELETE FROM anime_episodes")
    int deleteAll();
}
