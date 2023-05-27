package com.progetto.animeuniverse.database;

import static com.progetto.animeuniverse.util.Constants.ANIMEUNIVERSE_DATABASE_NAME;
import static com.progetto.animeuniverse.util.Constants.DATABASE_VERSION;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.progetto.animeuniverse.model.Anime;
import com.progetto.animeuniverse.model.AnimeByName;
import com.progetto.animeuniverse.model.AnimeEpisodes;
import com.progetto.animeuniverse.model.AnimeEpisodesImages;
import com.progetto.animeuniverse.model.AnimeMovie;
import com.progetto.animeuniverse.model.AnimeNew;
import com.progetto.animeuniverse.model.AnimeRecommendations;
import com.progetto.animeuniverse.model.AnimeTv;
import com.progetto.animeuniverse.model.Genre;
import com.progetto.animeuniverse.model.Review;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Anime.class, Review.class, Genre.class, AnimeRecommendations.class,
        AnimeByName.class, AnimeNew.class, AnimeEpisodes.class, AnimeEpisodesImages.class,
        AnimeTv.class, AnimeMovie.class}, version = DATABASE_VERSION)
public abstract class AnimeRoomDatabase extends RoomDatabase {
    public abstract AnimeDao animeDao();
    public abstract ReviewDao reviewDao();
    public abstract GenreDao genreDao();
    public abstract AnimeRecommendationsDao animeRecommendationsDao();
    public abstract AnimeByNameDao animeByNameDao();
    public abstract AnimeNewDao animeNewDao();
    public abstract AnimeEpisodesDao animeEpisodesDao();
    public abstract AnimeEpisodesImagesDao animeEpisodesImagesDao();
    public abstract AnimeTvDao animeTvDao();
    public abstract AnimeMovieDao animeMovieDao();

    private static volatile AnimeRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AnimeRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (AnimeRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AnimeRoomDatabase.class, ANIMEUNIVERSE_DATABASE_NAME).fallbackToDestructiveMigration().build();
                }

            }
        }
        return INSTANCE;
    }
}
