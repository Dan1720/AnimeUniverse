package com.progetto.animeuniverse.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.progetto.animeuniverse.model.AnimeRecommendations;

import java.util.List;

@Dao
public interface AnimeRecommendationsDao {
    @Query("SELECT * FROM anime_recommendations")
    List<AnimeRecommendations> getAllAnimeRecommendations();

    @Query("SELECT * FROM anime_recommendations WHERE id = :id")
    AnimeRecommendations getAnimeRecommendations(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAnimeRecommendationsList(List<AnimeRecommendations> animeRecommendationsList);

    @Query("DELETE FROM anime_recommendations")
    int deleteAll();
}
