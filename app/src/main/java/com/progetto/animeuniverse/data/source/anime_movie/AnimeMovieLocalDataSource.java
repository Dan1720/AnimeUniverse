package com.progetto.animeuniverse.data.source.anime_movie;

import static com.progetto.animeuniverse.util.Constants.ENCRYPTED_DATA_FILE_NAME;
import static com.progetto.animeuniverse.util.Constants.ENCRYPTED_SHARED_PREFERENCES_FILE_NAME;
import static com.progetto.animeuniverse.util.Constants.LAST_UPDATE;
import static com.progetto.animeuniverse.util.Constants.SHARED_PREFERENCES_FILE_NAME;

import com.progetto.animeuniverse.data.source.anime_movie.BaseAnimeMovieLocalDataSource;
import com.progetto.animeuniverse.database.AnimeRoomDatabase;
import com.progetto.animeuniverse.database.AnimeMovieDao;
import com.progetto.animeuniverse.model.AnimeMovie;
import com.progetto.animeuniverse.model.AnimeMovieApiResponse;
import com.progetto.animeuniverse.util.DataEncryptionUtil;
import com.progetto.animeuniverse.util.SharedPreferencesUtil;

import java.util.List;

public class AnimeMovieLocalDataSource extends BaseAnimeMovieLocalDataSource {

    private final AnimeMovieDao animeMovieDao;
    private final SharedPreferencesUtil sharedPreferencesUtil;
    private final DataEncryptionUtil dataEncryptionUtil;

    public AnimeMovieLocalDataSource(AnimeRoomDatabase animeRoomDatabase, SharedPreferencesUtil sharedPreferencesUtil, DataEncryptionUtil dataEncryptionUtil) {
        this.animeMovieDao = animeRoomDatabase.animeMovieDao();
        this.sharedPreferencesUtil = sharedPreferencesUtil;
        this.dataEncryptionUtil = dataEncryptionUtil;
    }

    @Override
    public void getAnimeMovie() {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            AnimeMovieApiResponse animeMovieApiResponse = new AnimeMovieApiResponse();
            animeMovieApiResponse.setAnimeMovieList(animeMovieDao.getAll());
            animeMovieCallback.onSuccessFromLocal(animeMovieApiResponse);
        });
    }

    @Override
    public void updateAnimeMovie(AnimeMovie animeMovie) {
        if(animeMovie == null){
            List<AnimeMovie> allAnimeMovie = animeMovieDao.getAll();
            for(AnimeMovie a : allAnimeMovie){
                a.setSynchronized(false);
            }
        }
    }

    @Override
    public void insertAnimeMovie(AnimeMovieApiResponse animeMovieApiResponse) {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            List<AnimeMovie> allAnimeMovie = animeMovieDao.getAll();
            List<AnimeMovie> animeMovieList = animeMovieApiResponse.getAnimeMovieList();
            if(animeMovieList != null){
                for(AnimeMovie animeMovie : allAnimeMovie){
                    if(animeMovieList.contains(animeMovie)){
                        animeMovieList.set(animeMovieList.indexOf(animeMovie), animeMovie);
                    }
                }
                List<Long> insertedAnimeMovieIds = animeMovieDao.insertAnimeMovieList(animeMovieList);
                for(int i=0; i<animeMovieList.size(); i++){
                    animeMovieList.get(i).setId(Math.toIntExact(insertedAnimeMovieIds.get(i)));
                }
                sharedPreferencesUtil.writeStringData(SHARED_PREFERENCES_FILE_NAME, LAST_UPDATE, String.valueOf(System.currentTimeMillis()));
                animeMovieCallback.onSuccessFromLocal(animeMovieApiResponse);
            }
        });
    }

    @Override
    public void insertAnimeMovie(List<AnimeMovie> animeMovieList) {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            if(animeMovieList != null) {
                List<AnimeMovie> allAnimeMovie = animeMovieDao.getAll();
                for (AnimeMovie animeMovie : allAnimeMovie) {
                    if (animeMovieList.contains(animeMovie)) {
                        animeMovie.setSynchronized(true);
                        animeMovieList.set(animeMovieList.indexOf(animeMovie), animeMovie);
                    }
                }
                List<Long> insertedAnimeMovieIds = animeMovieDao.insertAnimeMovieList(animeMovieList);
                for (int i = 0; i < animeMovieList.size(); i++) {
                    animeMovieList.get(i).setId(Math.toIntExact(insertedAnimeMovieIds.get(i)));
                }
            }
            AnimeMovieApiResponse animeMovieApiResponse = new AnimeMovieApiResponse();
            animeMovieApiResponse.setAnimeMovieList(animeMovieList);
        });
    }

    @Override
    public void deleteAll() {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            int animeMovieCounter = animeMovieDao.getAll().size();
            int animeMovieDeleteAnimeMovie = animeMovieDao.deleteAll();
            if(animeMovieCounter == animeMovieDeleteAnimeMovie){
                sharedPreferencesUtil.deleteAll(SHARED_PREFERENCES_FILE_NAME);
                dataEncryptionUtil.deleteAll(ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, ENCRYPTED_DATA_FILE_NAME);
                animeMovieCallback.onSuccessDeletion();
            }
        });
    }
}
