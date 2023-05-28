package com.progetto.animeuniverse.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.progetto.animeuniverse.model.AnimeByName;
import com.progetto.animeuniverse.model.AnimeSpecificGenres;

import java.util.List;
@Dao
public interface AnimeSpecificGenresDao {
    @Query("SELECT * FROM anime_genres_specific")
    List<AnimeSpecificGenres> getAll();

    @Query("SELECT * FROM anime_genres_specific WHERE id = :id")
    AnimeSpecificGenres getAnimeSpecificGenres(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAnimeSpecificGenresList(List<AnimeSpecificGenres> animeSpecificGenresList);

    @Query("DELETE FROM anime_genres_specific")
    int deleteAll();
}
