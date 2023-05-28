package com.progetto.animeuniverse.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.progetto.animeuniverse.model.AnimeMovie;

import java.util.List;

@Dao
public interface AnimeMovieDao {
    @Query("SELECT * FROM anime_movie")
    List<AnimeMovie> getAll();

    @Query("SELECT * FROM anime_movie WHERE id = :id ")
    AnimeMovie getAnimeMovie(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAnimeMovieList(List<AnimeMovie> animeMovieList);

    @Query("DELETE FROM anime_movie")
    int deleteAll();
}
