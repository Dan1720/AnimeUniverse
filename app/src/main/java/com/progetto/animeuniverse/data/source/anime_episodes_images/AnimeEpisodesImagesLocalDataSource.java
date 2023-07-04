package com.progetto.animeuniverse.data.source.anime_episodes_images;

import static com.progetto.animeuniverse.util.Constants.ENCRYPTED_DATA_FILE_NAME;
import static com.progetto.animeuniverse.util.Constants.ENCRYPTED_SHARED_PREFERENCES_FILE_NAME;
import static com.progetto.animeuniverse.util.Constants.LAST_UPDATE;
import static com.progetto.animeuniverse.util.Constants.SHARED_PREFERENCES_FILE_NAME;

import com.progetto.animeuniverse.database.AnimeEpisodesImagesDao;
import com.progetto.animeuniverse.database.AnimeRoomDatabase;
import com.progetto.animeuniverse.model.AnimeEpisodesImages;
import com.progetto.animeuniverse.model.AnimeEpisodesImagesApiResponse;
import com.progetto.animeuniverse.util.DataEncryptionUtil;
import com.progetto.animeuniverse.util.SharedPreferencesUtil;

import java.util.List;

public class AnimeEpisodesImagesLocalDataSource extends BaseAnimeEpisodesImagesLocalDataSource{
    private final AnimeEpisodesImagesDao animeEpisodesImagesDao;
    private final SharedPreferencesUtil sharedPreferencesUtil;
    private final DataEncryptionUtil dataEncryptionUtil;

    public AnimeEpisodesImagesLocalDataSource(AnimeRoomDatabase animeRoomDatabase, SharedPreferencesUtil sharedPreferencesUtil, DataEncryptionUtil dataEncryptionUtil) {
        this.animeEpisodesImagesDao = animeRoomDatabase.animeEpisodesImagesDao();
        this.sharedPreferencesUtil = sharedPreferencesUtil;
        this.dataEncryptionUtil = dataEncryptionUtil;
    }

    @Override
    public void getAnimeEpisodesImages() {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            AnimeEpisodesImagesApiResponse animeEpisodesImagesApiResponse = new AnimeEpisodesImagesApiResponse();
            animeEpisodesImagesApiResponse.setAnimeEpisodesImagesList(animeEpisodesImagesDao.getAll());
            animeEpisodesImagesCallback.onSuccessFromLocal(animeEpisodesImagesApiResponse);
        });
    }

    @Override
    public void updateAnimeEpisodesImages(AnimeEpisodesImages animeEpisodesImages) {
        if(animeEpisodesImages == null){
            List<AnimeEpisodesImages> allAnimeEpisodesImages = animeEpisodesImagesDao.getAll();
            for(AnimeEpisodesImages a : allAnimeEpisodesImages){
                a.setSynchronized(false);
            }
        }
    }

    @Override
    public void insertAnimeEpisodesImages(AnimeEpisodesImagesApiResponse animeEpisodesImagesApiResponse) {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            List<AnimeEpisodesImages> allAnimeEpisodesImages = animeEpisodesImagesDao.getAll();
            List<AnimeEpisodesImages> animeEpisodesImagesList = animeEpisodesImagesApiResponse.getAnimeEpisodesImagesList();
            if(animeEpisodesImagesList != null){
                for(AnimeEpisodesImages animeEpisodesImages : allAnimeEpisodesImages){
                    if(animeEpisodesImagesList.contains(animeEpisodesImages)){
                        animeEpisodesImagesList.set(animeEpisodesImagesList.indexOf(animeEpisodesImages), animeEpisodesImages);
                    }
                }
                List<Long> insertedAnimeEpisodesImagesIds = animeEpisodesImagesDao.insertEpisodesImagesList(animeEpisodesImagesList);
                for(int i=0; i<animeEpisodesImagesList.size(); i++){
                    animeEpisodesImagesList.get(i).setId(Math.toIntExact(insertedAnimeEpisodesImagesIds.get(i)));
                }
                sharedPreferencesUtil.writeStringData(SHARED_PREFERENCES_FILE_NAME, LAST_UPDATE, String.valueOf(System.currentTimeMillis()));
                animeEpisodesImagesCallback.onSuccessFromLocal(animeEpisodesImagesApiResponse);
            }
        });
    }

    @Override
    public void insertAnimeEpisodesImages(List<AnimeEpisodesImages> animeEpisodesImagesList) {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            if(animeEpisodesImagesList != null) {
                List<AnimeEpisodesImages> allAnimeEpisodesImages = animeEpisodesImagesDao.getAll();
                for (AnimeEpisodesImages animeEpisodesImages : allAnimeEpisodesImages) {
                    if (animeEpisodesImagesList.contains(animeEpisodesImages)) {
                        animeEpisodesImages.setSynchronized(true);
                        animeEpisodesImagesList.set(animeEpisodesImagesList.indexOf(animeEpisodesImages), animeEpisodesImages);
                    }
                }
                List<Long> insertedAnimeEpisodesImagesIds = animeEpisodesImagesDao.insertEpisodesImagesList(animeEpisodesImagesList);
                for (int i = 0; i < animeEpisodesImagesList.size(); i++) {
                    animeEpisodesImagesList.get(i).setId(Math.toIntExact(insertedAnimeEpisodesImagesIds.get(i)));
                }
            }
            AnimeEpisodesImagesApiResponse animeEpisodesImagesApiResponse = new AnimeEpisodesImagesApiResponse();
            animeEpisodesImagesApiResponse.setAnimeEpisodesImagesList(animeEpisodesImagesList);
        });
    }

    @Override
    public void deleteAll() {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            int animeEpisodesImagesCounter = animeEpisodesImagesDao.getAll().size();
            int animeEpisodesImagesDeleteAnimeEpisodesImages = animeEpisodesImagesDao.deleteAll();
            if(animeEpisodesImagesCounter == animeEpisodesImagesDeleteAnimeEpisodesImages){
                sharedPreferencesUtil.deleteAll(SHARED_PREFERENCES_FILE_NAME);
                dataEncryptionUtil.deleteAll(ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, ENCRYPTED_DATA_FILE_NAME);
                animeEpisodesImagesCallback.onSuccessDeletion();
            }
        });
    }
}
