package com.progetto.animeuniverse.repository.anime_new;

import static com.progetto.animeuniverse.util.Constants.FRESH_TIMEOUT;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.database.AnimeNewDao;
import com.progetto.animeuniverse.database.AnimeRoomDatabase;
import com.progetto.animeuniverse.model.AnimeNew;
import com.progetto.animeuniverse.model.AnimeNewApiResponse;
import com.progetto.animeuniverse.model.AnimeRecommendations;
import com.progetto.animeuniverse.model.AnimeRecommendationsApiResponse;
import com.progetto.animeuniverse.service.AnimeApiService;
import com.progetto.animeuniverse.util.ServiceLocator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimeNewRepository implements IAnimeNewRepository{
    private static final String TAG = AnimeNewRepository.class.getSimpleName();

    private final Application application;
    private final AnimeApiService animeApiService;
    private final AnimeNewDao animeNewDao;
    private final AnimeNewResponseCallback animeNewResponseCallback;

    public AnimeNewRepository(Application application, AnimeNewResponseCallback animeNewResponseCallback) {
        this.application = application;
        this.animeApiService = ServiceLocator.getInstance().getAnimeApiService();
        AnimeRoomDatabase animeRoomDatabase = ServiceLocator.getInstance().getAnimeNewDao(application);
        this.animeNewDao = animeRoomDatabase.animeNewDao();
        this.animeNewResponseCallback = animeNewResponseCallback;
    }


    @Override
    public void fetchAnimeNew(long lastUpdate) {
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastUpdate > FRESH_TIMEOUT){
            Call<AnimeNewApiResponse> animeNewApiResponseCall = animeApiService.getAnimeNew();
            animeNewApiResponseCall.enqueue(new Callback<AnimeNewApiResponse>() {
                @Override
                public void onResponse(Call<AnimeNewApiResponse> call, Response<AnimeNewApiResponse> response) {
                    if(response.isSuccessful()){
                        List<AnimeNew> animeNewList = response.body().getAnimeNewList();
                        saveDataInDatabase(animeNewList);
                    }else{
                        animeNewResponseCallback.onFailure(application.getString(R.string.error_retrieving_anime));
                    }
                }

                @Override
                public void onFailure(Call<AnimeNewApiResponse> call, @NonNull Throwable t) {
                    animeNewResponseCallback.onFailure(t.getMessage());
                }
            });
        }else {
            Log.d(TAG, application.getString(R.string.data_read_from_local_database));
        }
    }

    private void saveDataInDatabase(List<AnimeNew> animeNewList){
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            List<AnimeNew> allAnimeNew = animeNewDao.getAllAnimeNew();
            for(AnimeNew animeNew : allAnimeNew){
                if(animeNewList.contains(animeNew)){
                    animeNewList.set(animeNewList.indexOf(animeNew), animeNew);
                }
            }
            List<Long> insertedAnimeNewIds = animeNewDao.insertAnimeNewList(animeNewList);
            for(int i=0; i<animeNewList.size(); i++){
                animeNewList.get(i).setId(Math.toIntExact(insertedAnimeNewIds.get(i)));
            }
            animeNewResponseCallback.onSuccess(animeNewList, System.currentTimeMillis());
        });
    }

    private void readDataFromDatabase(long lastUpdate){
        AnimeRoomDatabase.databaseWriteExecutor.execute(() ->{
            animeNewResponseCallback.onSuccess(animeNewDao.getAllAnimeNew(), lastUpdate);
        });
    }
}
