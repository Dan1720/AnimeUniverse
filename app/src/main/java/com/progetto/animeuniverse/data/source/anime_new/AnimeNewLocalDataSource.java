package com.progetto.animeuniverse.data.source.anime_new;

import static com.progetto.animeuniverse.util.Constants.ENCRYPTED_DATA_FILE_NAME;
import static com.progetto.animeuniverse.util.Constants.ENCRYPTED_SHARED_PREFERENCES_FILE_NAME;
import static com.progetto.animeuniverse.util.Constants.LAST_UPDATE;
import static com.progetto.animeuniverse.util.Constants.SHARED_PREFERENCES_FILE_NAME;

import com.progetto.animeuniverse.database.AnimeNewDao;
import com.progetto.animeuniverse.database.AnimeRoomDatabase;
import com.progetto.animeuniverse.model.AnimeNew;
import com.progetto.animeuniverse.model.AnimeNewApiResponse;
import com.progetto.animeuniverse.util.DataEncryptionUtil;
import com.progetto.animeuniverse.util.SharedPreferencesUtil;

import java.util.List;

public class AnimeNewLocalDataSource extends BaseAnimeNewLocalDataSource{
    private final AnimeNewDao animeNewDao;
    private final SharedPreferencesUtil sharedPreferencesUtil;
    private final DataEncryptionUtil dataEncryptionUtil;

    public AnimeNewLocalDataSource(AnimeRoomDatabase animeRoomDatabase, SharedPreferencesUtil sharedPreferencesUtil, DataEncryptionUtil dataEncryptionUtil) {
        this.animeNewDao = animeRoomDatabase.animeNewDao();
        this.sharedPreferencesUtil = sharedPreferencesUtil;
        this.dataEncryptionUtil = dataEncryptionUtil;
    }


    @Override
    public void getAnimeNew() {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            AnimeNewApiResponse animeNewApiResponse = new AnimeNewApiResponse();
            animeNewApiResponse.setAnimeNewList(animeNewDao.getAllAnimeNew());
            animeNewCallback.onSuccessFromLocal(animeNewApiResponse);
        });
    }

    @Override
    public void updateAnimeNew(AnimeNew animeNew) {
        if(animeNew == null){
            List<AnimeNew> allAnimeNew = animeNewDao.getAllAnimeNew();
            for(AnimeNew a : allAnimeNew){
                a.setSynchronized(false);
            }
        }
    }

    @Override
    public void insertAnimeNew(AnimeNewApiResponse animeNewApiResponse) {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            List<AnimeNew> allAnimeNew = animeNewDao.getAllAnimeNew();
            List<AnimeNew> animeNewList = animeNewApiResponse.getAnimeNewList();
            if(animeNewList != null){
                for(AnimeNew animeNew : allAnimeNew){
                    if(animeNewList.contains(animeNew)){
                        animeNewList.set(animeNewList.indexOf(animeNew), animeNew);
                    }
                }
                List<Long> insertedAnimeNewIds = animeNewDao.insertAnimeNewList(animeNewList);
                for(int i=0; i<animeNewList.size(); i++){
                    animeNewList.get(i).setId(Math.toIntExact(insertedAnimeNewIds.get(i)));
                }
                sharedPreferencesUtil.writeStringData(SHARED_PREFERENCES_FILE_NAME, LAST_UPDATE, String.valueOf(System.currentTimeMillis()));
                animeNewCallback.onSuccessFromLocal(animeNewApiResponse);
            }
        });
    }

    @Override
    public void insertAnimeNew(List<AnimeNew> animeNewList) {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            if(animeNewList != null) {
                List<AnimeNew> allAnimeNew = animeNewDao.getAllAnimeNew();
                for (AnimeNew animeNew : allAnimeNew) {
                    if (animeNewList.contains(animeNew)) {
                        animeNew.setSynchronized(true);
                        animeNewList.set(animeNewList.indexOf(animeNew), animeNew);
                    }
                }
                List<Long> insertedAnimeNewIds = animeNewDao.insertAnimeNewList(animeNewList);
                for (int i = 0; i < animeNewList.size(); i++) {
                    animeNewList.get(i).setId(Math.toIntExact(insertedAnimeNewIds.get(i)));
                }
            }
            AnimeNewApiResponse animeNewApiResponse = new AnimeNewApiResponse();
            animeNewApiResponse.setAnimeNewList(animeNewList);
        });
    }

    @Override
    public void deleteAll() {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            int animeNewCounter = animeNewDao.getAllAnimeNew().size();
            int animeNewDeleteAnimeNew = animeNewDao.deleteAll();
            if(animeNewCounter == animeNewDeleteAnimeNew){
                sharedPreferencesUtil.deleteAll(SHARED_PREFERENCES_FILE_NAME);
                dataEncryptionUtil.deleteAll(ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, ENCRYPTED_DATA_FILE_NAME);
                animeNewCallback.onSuccessDeletion();
            }
        });
    }
}
