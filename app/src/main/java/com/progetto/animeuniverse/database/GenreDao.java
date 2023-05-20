package com.progetto.animeuniverse.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.progetto.animeuniverse.model.Genre;

import java.util.List;

@Dao
public interface GenreDao {
    @Query("SELECT * FROM genre")
    List<Genre> getAllGenres();

    @Query("SELECT * FROM genre WHERE id = :id")
    Genre getGenre(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertGenreList(List<Genre> genreList);

    @Query("DELETE FROM genre")
    int deleteAll();
}
