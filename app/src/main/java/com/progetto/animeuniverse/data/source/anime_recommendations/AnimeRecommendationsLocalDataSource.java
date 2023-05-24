package com.progetto.animeuniverse.data.source.anime_recommendations;

import static com.progetto.animeuniverse.util.Constants.ENCRYPTED_DATA_FILE_NAME;
import static com.progetto.animeuniverse.util.Constants.ENCRYPTED_SHARED_PREFERENCES_FILE_NAME;
import static com.progetto.animeuniverse.util.Constants.LAST_UPDATE;
import static com.progetto.animeuniverse.util.Constants.SHARED_PREFERENCES_FILE_NAME;

import com.progetto.animeuniverse.database.AnimeRecommendationsDao;
import com.progetto.animeuniverse.database.AnimeRoomDatabase;
import com.progetto.animeuniverse.model.AnimeRecommendations;
import com.progetto.animeuniverse.model.AnimeRecommendationsApiResponse;
import com.progetto.animeuniverse.util.DataEncryptionUtil;
import com.progetto.animeuniverse.util.SharedPreferencesUtil;

import java.util.List;

public class AnimeRecommendationsLocalDataSource extends BaseAnimeRecommendationsLocalDataSource{
    private final AnimeRecommendationsDao animeRecommendationsDao;
    private final SharedPreferencesUtil sharedPreferencesUtil;
    private final DataEncryptionUtil dataEncryptionUtil;

    public AnimeRecommendationsLocalDataSource(AnimeRoomDatabase animeRoomDatabase, SharedPreferencesUtil sharedPreferencesUtil, DataEncryptionUtil dataEncryptionUtil) {
        this.animeRecommendationsDao = animeRoomDatabase.animeRecommendationsDao();
        this.sharedPreferencesUtil = sharedPreferencesUtil;
        this.dataEncryptionUtil = dataEncryptionUtil;
    }


    @Override
    public void getAnimeRecommendations() {
       AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
           AnimeRecommendationsApiResponse animeRecommendationsApiResponse = new AnimeRecommendationsApiResponse();
           animeRecommendationsApiResponse.setAnimeRecommendationsList(animeRecommendationsDao.getAllAnimeRecommendations());
           animeRecommendationsCallback.onSuccessFromLocal(animeRecommendationsApiResponse);
       });
    }

    @Override
    public void updateAnimeRecommendations(AnimeRecommendations animeRecommendations) {
        if(animeRecommendations == null){
            List<AnimeRecommendations> allAnimeRecommendations = animeRecommendationsDao.getAllAnimeRecommendations();
            for(AnimeRecommendations a : allAnimeRecommendations){
                a.setSynchronized(false);
            }
        }
    }

    @Override
    public void insertAnimeRecommendations(AnimeRecommendationsApiResponse animeRecommendationsApiResponse) {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            List<AnimeRecommendations> allAnimeRecommendations = animeRecommendationsDao.getAllAnimeRecommendations();
            List<AnimeRecommendations> animeRecommendationsList = animeRecommendationsApiResponse.getAnimeRecommendationsList();
            if(animeRecommendationsList != null){
                for(AnimeRecommendations animeRecommendations : allAnimeRecommendations){
                    if(animeRecommendationsList.contains(animeRecommendations)){
                        animeRecommendationsList.set(animeRecommendationsList.indexOf(animeRecommendations), animeRecommendations);
                    }
                }
                List<Long> insertedAnimeRecommendationsIds = animeRecommendationsDao.insertAnimeRecommendationsList(animeRecommendationsList);
                for(int i=0; i<animeRecommendationsList.size(); i++){
                    animeRecommendationsList.get(i).setId(Math.toIntExact(insertedAnimeRecommendationsIds.get(i)));
                }
                sharedPreferencesUtil.writeStringData(SHARED_PREFERENCES_FILE_NAME, LAST_UPDATE, String.valueOf(System.currentTimeMillis()));
                animeRecommendationsCallback.onSuccessFromLocal(animeRecommendationsApiResponse);
            }
        });
    }

    @Override
    public void insertAnimeRecommendations(List<AnimeRecommendations> animeRecommendationsList) {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            if(animeRecommendationsList != null) {
                List<AnimeRecommendations> allAnimeRecommendations = animeRecommendationsDao.getAllAnimeRecommendations();
                for (AnimeRecommendations animeRecommendations : allAnimeRecommendations) {
                    if (animeRecommendationsList.contains(animeRecommendations)) {
                        animeRecommendations.setSynchronized(true);
                        animeRecommendationsList.set(animeRecommendationsList.indexOf(animeRecommendations), animeRecommendations);
                    }
                }
                List<Long> insertedAnimeRecommendationsIds = animeRecommendationsDao.insertAnimeRecommendationsList(animeRecommendationsList);
                for (int i = 0; i < animeRecommendationsList.size(); i++) {
                    animeRecommendationsList.get(i).setId(Math.toIntExact(insertedAnimeRecommendationsIds.get(i)));
                }
            }
                AnimeRecommendationsApiResponse animeRecommendationsApiResponse = new AnimeRecommendationsApiResponse();
                animeRecommendationsApiResponse.setAnimeRecommendationsList(animeRecommendationsList);
        });
    }


    @Override
    public void deleteAll() {
       AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
           int animeRecommendationsCounter = animeRecommendationsDao.getAllAnimeRecommendations().size();
           int animeRecommendationsDeleteAnimeRecommendations = animeRecommendationsDao.deleteAll();
           if(animeRecommendationsCounter == animeRecommendationsDeleteAnimeRecommendations){
               sharedPreferencesUtil.deleteAll(SHARED_PREFERENCES_FILE_NAME);
               dataEncryptionUtil.deleteAll(ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, ENCRYPTED_DATA_FILE_NAME);
               animeRecommendationsCallback.onSuccessDeletion();
           }
       });
    }
}
