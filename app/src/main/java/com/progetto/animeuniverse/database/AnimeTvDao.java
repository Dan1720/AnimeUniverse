package com.progetto.animeuniverse.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.progetto.animeuniverse.model.AnimeTv;

import java.util.List;

@Dao
public interface AnimeTvDao {
    @Query("SELECT * FROM anime_tv")
    List<AnimeTv> getAll();

    @Query("SELECT * FROM anime_tv WHERE id = :id ")
    AnimeTv getAnimeTv(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAnimeTvList(List<AnimeTv> animeTvList);

    @Query("DELETE FROM anime_tv")
    int deleteAll();
}
