package com.progetto.animeuniverse.database;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Dao;
import androidx.room.Update;

import com.progetto.animeuniverse.model.Anime;

import java.util.List;
@Dao
public interface AnimeDao {
    @Query("SELECT * FROM anime")
    List<Anime> getAll();

    @Query("SELECT * FROM anime WHERE id = :id")
    Anime getAnime(long id);

    @Query("SELECT * FROM anime WHERE is_favorite = 1 ORDER BY rating DESC")
    List<Anime> getFavoriteAnime();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAnimeList(List<Anime> animeList);

    @Insert
    void insertAll(Anime ... anime);

    @Update
    int updateSingleFavoriteAnime(Anime anime);

    @Update
    int updateListFavoriteAnime(List<Anime> anime);

    @Delete
    void delete(Anime anime);

    @Delete
    void deleteAllWithoutQuery(Anime ... anime);

    @Query("DELETE FROM anime")
    int deleteAll();

    @Query("DELETE FROM anime WHERE is_favorite = 0")
    void deleteNotFavoriteNews();
}
