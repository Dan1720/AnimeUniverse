package com.progetto.animeuniverse.data.source.anime;

import static com.progetto.animeuniverse.util.Constants.ENCRYPTED_DATA_FILE_NAME;
import static com.progetto.animeuniverse.util.Constants.ENCRYPTED_SHARED_PREFERENCES_FILE_NAME;
import static com.progetto.animeuniverse.util.Constants.LAST_UPDATE;
import static com.progetto.animeuniverse.util.Constants.SHARED_PREFERENCES_FILE_NAME;
import static com.progetto.animeuniverse.util.Constants.UNEXPECTED_ERROR;

import com.progetto.animeuniverse.model.Anime;
import com.progetto.animeuniverse.model.AnimeApiResponse;
import com.progetto.animeuniverse.database.AnimeDao;
import com.progetto.animeuniverse.database.AnimeRoomDatabase;
import com.progetto.animeuniverse.util.DataEncryptionUtil;
import com.progetto.animeuniverse.util.SharedPreferencesUtil;

import java.util.List;

public class AnimeLocalDataSource extends BaseAnimeLocalDataSource{
    private final AnimeDao animeDao;
    private final SharedPreferencesUtil sharedPreferencesUtil;
    private final DataEncryptionUtil dataEncryptionUtil;

    public AnimeLocalDataSource(AnimeRoomDatabase animeRoomDatabase,
                                SharedPreferencesUtil sharedPreferencesUtil,
                                DataEncryptionUtil dataEncryptionUtil){
        this.animeDao = animeRoomDatabase.animeDao();
        this.sharedPreferencesUtil = sharedPreferencesUtil;
        this.dataEncryptionUtil = dataEncryptionUtil;
    }
    @Override
    public void getAnime() {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            AnimeApiResponse animeApiResponse = new AnimeApiResponse();
            animeApiResponse.setAnimeList(animeDao.getAll());
            animeCallback.onSuccessFromLocal(animeApiResponse);
        });
    }

    @Override
    public void getFavoriteAnime() {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            List<Anime> favoriteAnime = animeDao.getFavoriteAnime();
            animeCallback.onAnimeFavoriteStatusChanged(favoriteAnime);
        });
    }

    @Override
    public void updateAnime(Anime anime) {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            if(anime != null){
                int rowUpdatedCounter = animeDao.updateSingleFavoriteAnime(anime);
                if(rowUpdatedCounter == 1){
                    Anime updateAnime = animeDao.getAnime(anime.getId());
                    animeCallback.onAnimeFavoriteStatusChanged(updateAnime, animeDao.getFavoriteAnime());
                }else{
                    animeCallback.onFailureFromLocal(new Exception(UNEXPECTED_ERROR));
                }
            }else{
                List<Anime> allAnime = animeDao.getAll();
                for(Anime n: allAnime){
                    n.setSynchronized(false);
                    animeDao.updateSingleFavoriteAnime(n);
                }
            }
        });
    }

    @Override
    public void deleteFavoriteAnime() {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            List<Anime> favoriteAnime = animeDao.getFavoriteAnime();
            for(Anime anime : favoriteAnime){
                anime.setFavorite(false);
            }
            int updatedRowsNumber = animeDao.updateListFavoriteAnime(favoriteAnime);
            if(updatedRowsNumber == favoriteAnime.size()){
                animeCallback.onDeleteFavoriteAnimeSuccess(favoriteAnime);
            }else{
                animeCallback.onFailureFromLocal(new Exception(UNEXPECTED_ERROR));
            }
        });
    }

    @Override
    public void insertAnime(AnimeApiResponse animeApiResponse) {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            List<Anime> allAnime = animeDao.getAll();
            List<Anime> animeList = animeApiResponse.getAnimeList();
            if(animeList != null){
                for(Anime anime : allAnime){
                    if(animeList.contains(anime)){
                        animeList.set(animeList.indexOf(anime), anime);
                    }
                }
                List<Long>insertedAnimeIds = animeDao.insertAnimeList(animeList);
                for(int i=0; i<animeList.size(); i++){
                    animeList.get(i).setId(Math.toIntExact(insertedAnimeIds.get(i)));
                }
                sharedPreferencesUtil.writeStringData(SHARED_PREFERENCES_FILE_NAME, LAST_UPDATE, String.valueOf(System.currentTimeMillis()));
                animeCallback.onSuccessFromLocal(animeApiResponse);
            }
        });
    }

    @Override
    public void insertAnime(List<Anime> animeList) {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            if(animeList != null){
                List<Anime> allAnime = animeDao.getAll();
                for (Anime anime : allAnime){
                    if(animeList.contains(anime)){
                        anime.setSynchronized(true);
                        animeList.set(animeList.indexOf(anime), anime);
                    }
                }
                List<Long> insertedAnimeIds = animeDao.insertAnimeList(animeList);
                for(int i=0; i<animeList.size(); i++){
                    animeList.get(i).setId(Math.toIntExact(insertedAnimeIds.get(i)));
                }
                AnimeApiResponse animeApiResponse = new AnimeApiResponse();
                animeApiResponse.setAnimeList(animeList);
                animeCallback.onSuccessSynchronization();
            }
        });
    }

    @Override
    public void deleteAll() {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            int animeCounter = animeDao.getAll().size();
            int animeDeletedAnime = animeDao.deleteAll();
            if(animeCounter == animeDeletedAnime){
                sharedPreferencesUtil.deleteAll(SHARED_PREFERENCES_FILE_NAME);
                dataEncryptionUtil.deleteAll(ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, ENCRYPTED_DATA_FILE_NAME);
                animeCallback.onSuccessDeletion();
            }
        });
    }
}
