package com.progetto.animeuniverse.data.source.anime_episodes;

import static com.progetto.animeuniverse.util.Constants.ENCRYPTED_DATA_FILE_NAME;
import static com.progetto.animeuniverse.util.Constants.ENCRYPTED_SHARED_PREFERENCES_FILE_NAME;
import static com.progetto.animeuniverse.util.Constants.LAST_UPDATE;
import static com.progetto.animeuniverse.util.Constants.SHARED_PREFERENCES_FILE_NAME;

import com.progetto.animeuniverse.database.AnimeEpisodesDao;
import com.progetto.animeuniverse.database.AnimeRoomDatabase;
import com.progetto.animeuniverse.model.AnimeEpisodes;
import com.progetto.animeuniverse.model.AnimeEpisodesApiResponse;
import com.progetto.animeuniverse.util.DataEncryptionUtil;
import com.progetto.animeuniverse.util.SharedPreferencesUtil;

import java.util.List;

public class AnimeEpisodesLocalDataSource extends BaseAnimeEpisodesLocalDataSource{

    private final AnimeEpisodesDao animeEpisodesDao;
    private final SharedPreferencesUtil sharedPreferencesUtil;
    private final DataEncryptionUtil dataEncryptionUtil;

    public AnimeEpisodesLocalDataSource(AnimeRoomDatabase animeRoomDatabase, SharedPreferencesUtil sharedPreferencesUtil, DataEncryptionUtil dataEncryptionUtil) {
        this.animeEpisodesDao = animeRoomDatabase.animeEpisodesDao();
        this.sharedPreferencesUtil = sharedPreferencesUtil;
        this.dataEncryptionUtil = dataEncryptionUtil;
    }

    @Override
    public void getAnimeEpisodes() {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            AnimeEpisodesApiResponse animeEpisodesApiResponse = new AnimeEpisodesApiResponse();
            animeEpisodesApiResponse.setAnimeEpisodesList(animeEpisodesDao.getAll());
            animeEpisodesCallback.onSuccessFromLocal(animeEpisodesApiResponse);
        });
    }

    @Override
    public void updateAnimeEpisodes(AnimeEpisodes animeEpisodes) {
        if(animeEpisodes == null){
            List<AnimeEpisodes> allAnimeEpisodes = animeEpisodesDao.getAll();
            for(AnimeEpisodes a : allAnimeEpisodes){
                a.setSynchronized(false);
            }
        }
    }

    @Override
    public void insertAnimeEpisodes(AnimeEpisodesApiResponse animeEpisodesApiResponse) {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            List<AnimeEpisodes> allAnimeEpisodes = animeEpisodesDao.getAll();
            List<AnimeEpisodes> animeEpisodesList = animeEpisodesApiResponse.getAnimeEpisodesList();
            if(animeEpisodesList != null){
                for(AnimeEpisodes animeEpisodes : allAnimeEpisodes){
                    if(animeEpisodesList.contains(animeEpisodes)){
                        animeEpisodesList.set(animeEpisodesList.indexOf(animeEpisodes), animeEpisodes);
                    }
                }
                List<Long> insertedAnimeEpisodesIds = animeEpisodesDao.insertEpisodesList(animeEpisodesList);
                for(int i=0; i<animeEpisodesList.size(); i++){
                    animeEpisodesList.get(i).setId(Math.toIntExact(insertedAnimeEpisodesIds.get(i)));
                }
                sharedPreferencesUtil.writeStringData(SHARED_PREFERENCES_FILE_NAME, LAST_UPDATE, String.valueOf(System.currentTimeMillis()));
                animeEpisodesCallback.onSuccessFromLocal(animeEpisodesApiResponse);
            }
        });
    }

    @Override
    public void insertAnimeEpisodes(List<AnimeEpisodes> animeEpisodesList) {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            if(animeEpisodesList != null) {
                List<AnimeEpisodes> allAnimeEpisodes = animeEpisodesDao.getAll();
                for (AnimeEpisodes animeEpisodes : allAnimeEpisodes) {
                    if (animeEpisodesList.contains(animeEpisodes)) {
                        animeEpisodes.setSynchronized(true);
                        animeEpisodesList.set(animeEpisodesList.indexOf(animeEpisodes), animeEpisodes);
                    }
                }
                List<Long> insertedAnimeEpisodesIds = animeEpisodesDao.insertEpisodesList(animeEpisodesList);
                for (int i = 0; i < animeEpisodesList.size(); i++) {
                    animeEpisodesList.get(i).setId(Math.toIntExact(insertedAnimeEpisodesIds.get(i)));
                }
            }
            AnimeEpisodesApiResponse animeEpisodesApiResponse = new AnimeEpisodesApiResponse();
            animeEpisodesApiResponse.setAnimeEpisodesList(animeEpisodesList);
        });
    }

    @Override
    public void deleteAll() {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            int animeEpisodesCounter = animeEpisodesDao.getAll().size();
            int animeEpisodesDeleteAnimeEpisodes = animeEpisodesDao.deleteAll();
            if(animeEpisodesCounter == animeEpisodesDeleteAnimeEpisodes){
                sharedPreferencesUtil.deleteAll(SHARED_PREFERENCES_FILE_NAME);
                dataEncryptionUtil.deleteAll(ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, ENCRYPTED_DATA_FILE_NAME);
                animeEpisodesCallback.onSuccessDeletion();
            }
        });
    }
}
