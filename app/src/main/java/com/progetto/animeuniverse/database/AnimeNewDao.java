package com.progetto.animeuniverse.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.progetto.animeuniverse.model.AnimeNew;

import java.util.List;

@Dao
public interface AnimeNewDao {
    @Query("SELECT * FROM anime_new")
    List<AnimeNew> getAllAnimeNew();

    @Query("SELECT * FROM anime_new WHERE id = :id")
    AnimeNew getAnimeNew(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAnimeNewList(List<AnimeNew> animeNewList);

    @Query("DELETE FROM anime_new")
    int deleteAll();
}
