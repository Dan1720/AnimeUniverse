package com.progetto.animeuniverse.repository.anime;

import static com.progetto.animeuniverse.util.Constants.FRESH_TIMEOUT;

import android.app.Application;
import android.util.Log;


import androidx.annotation.NonNull;

import com.progetto.animeuniverse.model.Anime;
import com.progetto.animeuniverse.model.AnimeApiResponse;
import com.progetto.animeuniverse.R;

import com.progetto.animeuniverse.database.AnimeDao;
import com.progetto.animeuniverse.database.AnimeRoomDatabase;
import com.progetto.animeuniverse.service.AnimeApiService;
import com.progetto.animeuniverse.util.ServiceLocator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimeRepository implements IAnimeRepository{

    public static final String TAG = AnimeRepository.class.getSimpleName();

    private final Application application;
    private final AnimeApiService animeApiService;
    private final AnimeDao animeDao;
    private final AnimeResponseCallback animeResponseCallback;

    public AnimeRepository(Application application, AnimeApiService animeApiService, AnimeDao animeDao, AnimeResponseCallback animeResponseCallback) {
        this.application = application;
        this.animeApiService = ServiceLocator.getInstance().getAnimeApiService();
        AnimeRoomDatabase animeRoomDatabase = ServiceLocator.getInstance().getAnimeDao(application);
        this.animeDao = animeRoomDatabase.animeDao();
        this.animeResponseCallback = animeResponseCallback;
    }

    @Override
    public void fetchAnimeByName(String q,String nameAnime, long lastUpdate) {
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastUpdate > FRESH_TIMEOUT){
            Call<AnimeApiResponse> animeResponseCall = animeApiService.getAnimeByName(q);

            animeResponseCall.enqueue(new Callback<AnimeApiResponse>() {
                @Override
                public void onResponse(Call<AnimeApiResponse> call, Response<AnimeApiResponse> response) {
                    if(response.body() != null && response.isSuccessful() && !response.body().getStatus().equals("error")){
                        List<Anime> animeList = response.body().getData();
                        saveDataInDatabase(animeList);
                    }else{
                        animeResponseCallback.onFailure(application.getString(R.string.error_retrieving_anime));
                    }
                }

                @Override
                public void onFailure(Call<AnimeApiResponse> call, @NonNull Throwable t) {
                    animeResponseCallback.onFailure(t.getMessage());
                }
            });
        }else {
            Log.d(TAG, application.getString(R.string.data_read_from_local_database));
        }
    }

    @Override
    public void fetchAnimeByIdFull(String q, int id, long lastUpdate) {
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastUpdate > FRESH_TIMEOUT){
            Call<AnimeApiResponse> animeResponseCall = animeApiService.getAnimeByIdFull(q, id);

            animeResponseCall.enqueue(new Callback<AnimeApiResponse>() {
                @Override
                public void onResponse(Call<AnimeApiResponse> call, Response<AnimeApiResponse> response) {
                    if(response.body() != null && response.isSuccessful() && !response.body().getStatus().equals("error")){
                        List<Anime> animeList = response.body().getData();
                        saveDataInDatabase(animeList);
                    }else{
                        animeResponseCallback.onFailure(application.getString(R.string.error_retrieving_anime));
                    }
                }

                @Override
                public void onFailure(Call<AnimeApiResponse> call, @NonNull Throwable t) {
                    animeResponseCallback.onFailure(t.getMessage());
                }
            });
        }else {
            Log.d(TAG, application.getString(R.string.data_read_from_local_database));
        }
    }

    @Override
    public void fetchAnimeById(String q, int id, long lastUpdate) {
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastUpdate > FRESH_TIMEOUT){
            Call<AnimeApiResponse> animeResponseCall = animeApiService.getAnimeById(q, id);

            animeResponseCall.enqueue(new Callback<AnimeApiResponse>() {
                @Override
                public void onResponse(Call<AnimeApiResponse> call, Response<AnimeApiResponse> response) {
                    if(response.body() != null && response.isSuccessful() && !response.body().getStatus().equals("error")){
                        List<Anime> animeList = response.body().getData();
                        saveDataInDatabase(animeList);
                    }else{
                        animeResponseCallback.onFailure(application.getString(R.string.error_retrieving_anime));
                    }
                }

                @Override
                public void onFailure(Call<AnimeApiResponse> call, @NonNull Throwable t) {
                    animeResponseCallback.onFailure(t.getMessage());
                }
            });
        }else {
            Log.d(TAG, application.getString(R.string.data_read_from_local_database));
        }
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

    @Override
    public void updateAnime(Anime anime) {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            animeDao.updateSingleFavoriteAnime(anime);
            animeResponseCallback.onAnimeFavoriteStatusChanged(anime);
        });
    }

    @Override
    public void getFavoriteAnime() {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            animeResponseCallback.onSuccess(animeDao.getFavoriteAnime(), System.currentTimeMillis());
        });
    }

    @Override
    public void deleteFavoriteAnime() {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            List<Anime> favoriteAnime = animeDao.getFavoriteAnime();
            for(Anime anime : favoriteAnime){
                anime.setFavorite(false);
            }
            animeDao.updateListFavoriteAnime(favoriteAnime);
            animeResponseCallback.onSuccess(animeDao.getFavoriteAnime(), System.currentTimeMillis());
        });
    }

    private void readDataFromDatabase(long lastUpdate){
        AnimeRoomDatabase.databaseWriteExecutor.execute(() ->{
            animeResponseCallback.onSuccess(animeDao.getAll(), lastUpdate);
        });
    }
}
