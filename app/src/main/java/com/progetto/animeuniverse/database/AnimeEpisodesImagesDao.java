package com.progetto.animeuniverse.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.progetto.animeuniverse.model.AnimeEpisodesImages;

import java.util.List;

@Dao
public interface AnimeEpisodesImagesDao {
    @Query("SELECT * FROM anime_episodes_images")
    List<AnimeEpisodesImages> getAll();

    @Query("SELECT * FROM anime_episodes_images WHERE id = :id")
    AnimeEpisodesImages getAnimeEpisodesImages(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertEpisodesImagesList(List<AnimeEpisodesImages> animeEpisodesImagesList);

    @Query("DELETE FROM anime_episodes_images")
    int deleteAll();
}
