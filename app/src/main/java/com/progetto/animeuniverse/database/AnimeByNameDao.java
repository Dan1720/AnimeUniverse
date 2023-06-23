package com.progetto.animeuniverse.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.progetto.animeuniverse.model.Anime;
import com.progetto.animeuniverse.model.AnimeByName;

import java.util.List;

@Dao
public interface AnimeByNameDao {
    @Query("SELECT * FROM animeByName")
    List<AnimeByName> getAll();

    @Query("SELECT * FROM animeByName WHERE id = :id")
    AnimeByName getAnimeByName(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAnimeByNameList(List<AnimeByName> animeByNameList);

    @Query("DELETE FROM animeByName")
    int deleteAll();
}
