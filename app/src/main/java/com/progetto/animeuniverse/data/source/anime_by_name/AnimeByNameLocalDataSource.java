package com.progetto.animeuniverse.data.source.anime_by_name;

import static com.progetto.animeuniverse.util.Constants.ENCRYPTED_DATA_FILE_NAME;
import static com.progetto.animeuniverse.util.Constants.ENCRYPTED_SHARED_PREFERENCES_FILE_NAME;
import static com.progetto.animeuniverse.util.Constants.LAST_UPDATE;
import static com.progetto.animeuniverse.util.Constants.SHARED_PREFERENCES_FILE_NAME;

import com.progetto.animeuniverse.database.AnimeByNameDao;
import com.progetto.animeuniverse.database.AnimeRoomDatabase;
import com.progetto.animeuniverse.model.AnimeByName;
import com.progetto.animeuniverse.model.AnimeByNameApiResponse;
import com.progetto.animeuniverse.util.DataEncryptionUtil;
import com.progetto.animeuniverse.util.SharedPreferencesUtil;

import java.util.List;

public class AnimeByNameLocalDataSource extends BaseAnimeByNameLocalDataSource{

    private final AnimeByNameDao animeByNameDao;
    private final SharedPreferencesUtil sharedPreferencesUtil;
    private final DataEncryptionUtil dataEncryptionUtil;

    public AnimeByNameLocalDataSource(AnimeRoomDatabase animeRoomDatabase, SharedPreferencesUtil sharedPreferencesUtil, DataEncryptionUtil dataEncryptionUtil) {
        this.animeByNameDao = animeRoomDatabase.animeByNameDao();
        this.sharedPreferencesUtil = sharedPreferencesUtil;
        this.dataEncryptionUtil = dataEncryptionUtil;
    }

    @Override
    public void getAnimeByName() {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            AnimeByNameApiResponse animeByNameApiResponse = new AnimeByNameApiResponse();
            animeByNameApiResponse.setAnimeByNameList(animeByNameDao.getAll());
            animeByNameCallback.onSuccessFromLocal(animeByNameApiResponse);
        });
    }

    @Override
    public void updateAnimeByName(AnimeByName animeByName) {
        if(animeByName == null){
            List<AnimeByName> allAnimeByName = animeByNameDao.getAll();
            for(AnimeByName a : allAnimeByName){
                a.setSynchronized(false);
            }
        }
    }

    @Override
    public void insertAnimeByName(AnimeByNameApiResponse animeByNameApiResponse) {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            List<AnimeByName> allAnimeByName = animeByNameDao.getAll();
            List<AnimeByName> animeByNameList = animeByNameApiResponse.getAnimeByNameList();
            if(animeByNameList != null){
                for(AnimeByName animeByName : allAnimeByName){
                    if(animeByNameList.contains(animeByName)){
                        animeByNameList.set(animeByNameList.indexOf(animeByName), animeByName);
                    }
                }
                List<Long> insertedAnimeByNameIds = animeByNameDao.insertAnimeByNameList(animeByNameList);
                for(int i=0; i<animeByNameList.size(); i++){
                    animeByNameList.get(i).setId(Math.toIntExact(insertedAnimeByNameIds.get(i)));
                }
                sharedPreferencesUtil.writeStringData(SHARED_PREFERENCES_FILE_NAME, LAST_UPDATE, String.valueOf(System.currentTimeMillis()));
                animeByNameCallback.onSuccessFromLocal(animeByNameApiResponse);
            }
        });
    }

    @Override
    public void insertAnimeByName(List<AnimeByName> animeByNameList) {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            if(animeByNameList != null){
                List<AnimeByName> allAnimeByName = animeByNameDao.getAll();
                for(AnimeByName animeByName : allAnimeByName){
                    if(animeByNameList.contains(animeByName)){
                        animeByName.setSynchronized(true);
                        animeByNameList.set(animeByNameList.indexOf(animeByName), animeByName);
                    }
                }
                List<Long> insertedAnimeByNameIds = animeByNameDao.insertAnimeByNameList(animeByNameList);
                for(int i = 0; i<animeByNameList.size(); i++){
                    animeByNameList.get(i).setId(Math.toIntExact(insertedAnimeByNameIds.get(i)));
                }
                AnimeByNameApiResponse animeByNameApiResponse = new AnimeByNameApiResponse();
                animeByNameApiResponse.setAnimeByNameList(animeByNameList);
            }
        });
    }

    @Override
    public void deleteAll() {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            int animeByNameCounter = animeByNameDao.getAll().size();
            int animeByNameDeleteAnimeByName = animeByNameDao.deleteAll();
            if(animeByNameCounter == animeByNameDeleteAnimeByName){
                sharedPreferencesUtil.deleteAll(SHARED_PREFERENCES_FILE_NAME);
                dataEncryptionUtil.deleteAll(ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, ENCRYPTED_DATA_FILE_NAME);
                animeByNameCallback.onSuccessDeletion();
            }
        });
    }
}
