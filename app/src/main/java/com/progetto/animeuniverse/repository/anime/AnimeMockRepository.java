package com.progetto.animeuniverse.repository.anime;

import static com.progetto.animeuniverse.util.Constants.ANIME_API_TEST_JSON_FILE;

import android.app.Application;

import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.database.AnimeDao;
import com.progetto.animeuniverse.database.AnimeRoomDatabase;
import com.progetto.animeuniverse.model.Anime;
import com.progetto.animeuniverse.model.AnimeApiResponse;
import com.progetto.animeuniverse.util.JSONParserUtil;
import com.progetto.animeuniverse.util.ServiceLocator;

import java.io.IOException;
import java.util.List;

public class AnimeMockRepository implements IAnimeRepository{

    private final Application application;
    private final AnimeResponseCallback animeResponseCallback;
    private final AnimeDao animeDao;
    private final JSONParserUtil.JsonParserType jsonParserType;

    public AnimeMockRepository(Application application, AnimeResponseCallback animeResponseCallback, AnimeDao animeDao,
                               JSONParserUtil.JsonParserType jsonParserType) {
        this.application = application;
        this.animeResponseCallback = animeResponseCallback;
        AnimeRoomDatabase animeRoomDatabase = ServiceLocator.getInstance().getAnimeDao(application);
        this.animeDao = animeRoomDatabase.animeDao();
        this.jsonParserType = jsonParserType;
    }


    @Override
    public void fetchAnimeTop(long lastUpdate) {
        AnimeApiResponse animeApiResponse = null;
        JSONParserUtil jsonParserUtil = new JSONParserUtil(application);

        switch (jsonParserType) {
            case GSON:
                try {
                    animeApiResponse = jsonParserUtil.parseJSONFileWithGSon(ANIME_API_TEST_JSON_FILE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case JSON_ERROR:
                animeResponseCallback.onFailure(application.getString(R.string.error_retrieving_anime));
                break;
        }

        if (animeApiResponse != null) {
            saveDataInDatabase(animeApiResponse.getAnimeList());
        } else {
            animeResponseCallback.onFailure(application.getString(R.string.error_retrieving_anime));
        }
    }

    @Override
    public void updateAnime(Anime anime) {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            animeDao.updateSingleFavoriteAnime(anime);
            animeResponseCallback.onAnimeFavoriteStatusChanged(anime);
        });
    }

    @Override
    public void getFavoriteAnime() {
        AnimeRoomDatabase.databaseWriteExecutor.execute(() -> {
            //   animeResponseCallback.onSuccess(animeDao.getFavoriteAnime(), System.currentTimeMillis());
        });
    }

    @Override
    public void deleteFavoriteAnime() {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            //    List<Anime> favoriteAnime=animeDao.getFavoriteAnime();
            //     for(Anime anime : favoriteAnime){
            //        anime.setFavorite(false);
                //    }
            //   animeDao.updateListFavoriteAnime(favoriteAnime);
            //    animeResponseCallback.onSuccess(animeDao.getFavoriteAnime(), System.currentTimeMillis());
        });
    }

    private void saveDataInDatabase(List<Anime> animeList){
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            List<Anime> allAnime = animeDao.getAll();
            for(Anime anime : allAnime){
                if(animeList.contains(anime)){
                    animeList.set(animeList.indexOf(anime), anime);
                }
            }

            List<Long> insertedAnimeIds = animeDao.insertAnimeList(animeList);
            for(int i=0; i<animeList.size(); i++){
                animeList.get(i).setId(Math.toIntExact(insertedAnimeIds.get(i)));
            }
            animeResponseCallback.onSuccess(animeList, System.currentTimeMillis());
        });
    }

    private void readDataFromDatabase(long lastUpdate){
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            animeResponseCallback.onSuccess(animeDao.getAll(), lastUpdate);
        });
    }
}
