package com.progetto.animeuniverse.database;

import static com.progetto.animeuniverse.util.Constants.ANIMEUNIVERSE_DATABASE_NAME;
import static com.progetto.animeuniverse.util.Constants.DATABASE_VERSION;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.progetto.animeuniverse.Anime;

import java.lang.reflect.Executable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Anime.class}, version = DATABASE_VERSION)
public abstract class AnimeRoomDatabase extends RoomDatabase {
    public abstract AnimeDao animeDao();

    private static volatile AnimeRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();
    private static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AnimeRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (AnimeRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AnimeRoomDatabase.class, ANIMEUNIVERSE_DATABASE_NAME).build();
                }

            }
        }
        return INSTANCE;
    }
}