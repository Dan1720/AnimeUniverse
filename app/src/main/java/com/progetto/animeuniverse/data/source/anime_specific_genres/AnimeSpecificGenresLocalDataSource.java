package com.progetto.animeuniverse.data.source.anime_specific_genres;

import static com.progetto.animeuniverse.util.Constants.ENCRYPTED_DATA_FILE_NAME;
import static com.progetto.animeuniverse.util.Constants.ENCRYPTED_SHARED_PREFERENCES_FILE_NAME;
import static com.progetto.animeuniverse.util.Constants.LAST_UPDATE;
import static com.progetto.animeuniverse.util.Constants.SHARED_PREFERENCES_FILE_NAME;


import com.progetto.animeuniverse.database.AnimeRoomDatabase;
import com.progetto.animeuniverse.database.AnimeSpecificGenresDao;
import com.progetto.animeuniverse.model.AnimeSpecificGenres;
import com.progetto.animeuniverse.model.AnimeSpecificGenresApiResponse;
import com.progetto.animeuniverse.util.DataEncryptionUtil;
import com.progetto.animeuniverse.util.SharedPreferencesUtil;

import java.util.List;

public class AnimeSpecificGenresLocalDataSource extends BaseAnimeSpecificGenresLocalDataSource {

    private final AnimeSpecificGenresDao animeSpecificGenresDao;
    private final SharedPreferencesUtil sharedPreferencesUtil;
    private final DataEncryptionUtil dataEncryptionUtil;

    public AnimeSpecificGenresLocalDataSource(AnimeRoomDatabase animeRoomDatabase, SharedPreferencesUtil sharedPreferencesUtil, DataEncryptionUtil dataEncryptionUtil) {
        this.animeSpecificGenresDao = animeRoomDatabase.animeSpecificGenresDao();
        this.sharedPreferencesUtil = sharedPreferencesUtil;
        this.dataEncryptionUtil = dataEncryptionUtil;
    }

    @Override
    public void getAnimeSpecificGenres() {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            AnimeSpecificGenresApiResponse animeSpecificGenresApiResponse = new AnimeSpecificGenresApiResponse();
            animeSpecificGenresApiResponse.setAnimeSpecificGenresList(animeSpecificGenresDao.getAll());
            animeSpecificGenresCallback.onSuccessFromLocal(animeSpecificGenresApiResponse);
        });
    }

    @Override
    public void updateAnimeSpecificGenres(AnimeSpecificGenres animeSpecificGenres) {
        if(animeSpecificGenres == null){
            List<AnimeSpecificGenres> allAnimeSpecificGenres = animeSpecificGenresDao.getAll();
            for(AnimeSpecificGenres a : allAnimeSpecificGenres){
                a.setSynchronized(false);
            }
        }
    }

    @Override
    public void insertAnimeSpecificGenres(AnimeSpecificGenresApiResponse animeSpecificGenresApiResponse) {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            List<AnimeSpecificGenres> allAnimeSpecificGenres = animeSpecificGenresDao.getAll();
            List<AnimeSpecificGenres> animeSpecificGenresList = animeSpecificGenresApiResponse.getAnimeSpecificGenresList();
            if(animeSpecificGenresList != null){
                for(AnimeSpecificGenres animeSpecificGenres : allAnimeSpecificGenres){
                    if(animeSpecificGenresList.contains(animeSpecificGenres)){
                        animeSpecificGenresList.set(animeSpecificGenresList.indexOf(animeSpecificGenres), animeSpecificGenres);
                    }
                }
                List<Long> insertedAnimeSpecificGenresIds = animeSpecificGenresDao.insertAnimeSpecificGenresList(animeSpecificGenresList);
                for(int i=0; i<animeSpecificGenresList.size(); i++){
                    animeSpecificGenresList.get(i).setId(Math.toIntExact(insertedAnimeSpecificGenresIds.get(i)));
                }
                sharedPreferencesUtil.writeStringData(SHARED_PREFERENCES_FILE_NAME, LAST_UPDATE, String.valueOf(System.currentTimeMillis()));
                animeSpecificGenresCallback.onSuccessFromLocal(animeSpecificGenresApiResponse);
            }
        });
    }

    @Override
    public void insertAnimeSpecificGenres(List<AnimeSpecificGenres> animeSpecificGenresList) {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            if(animeSpecificGenresList != null){
                List<AnimeSpecificGenres> allAnimeSpecificGenres = animeSpecificGenresDao.getAll();
                for(AnimeSpecificGenres animeSpecificGenres : allAnimeSpecificGenres){
                    if(animeSpecificGenresList.contains(animeSpecificGenres)){
                        animeSpecificGenres.setSynchronized(true);
                        animeSpecificGenresList.set(animeSpecificGenresList.indexOf(animeSpecificGenres), animeSpecificGenres);
                    }
                }
                List<Long> insertedAnimeSpecificGenresIds = animeSpecificGenresDao.insertAnimeSpecificGenresList(animeSpecificGenresList);
                for(int i = 0; i<animeSpecificGenresList.size(); i++){
                    animeSpecificGenresList.get(i).setId(Math.toIntExact(insertedAnimeSpecificGenresIds.get(i)));
                }
                AnimeSpecificGenresApiResponse animeSpecificGenresApiResponse = new AnimeSpecificGenresApiResponse();
                animeSpecificGenresApiResponse.setAnimeSpecificGenresList(animeSpecificGenresList);
            }
        });
    }

    @Override
    public void deleteAll() {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            int animeSpecificGenresCounter = animeSpecificGenresDao.getAll().size();
            int animeSpecificGenresDeleteAnimeSpecificGenres = animeSpecificGenresDao.deleteAll();
            if(animeSpecificGenresCounter == animeSpecificGenresDeleteAnimeSpecificGenres){
                sharedPreferencesUtil.deleteAll(SHARED_PREFERENCES_FILE_NAME);
                dataEncryptionUtil.deleteAll(ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, ENCRYPTED_DATA_FILE_NAME);
                animeSpecificGenresCallback.onSuccessDeletion();
            }
        });
    }
}
