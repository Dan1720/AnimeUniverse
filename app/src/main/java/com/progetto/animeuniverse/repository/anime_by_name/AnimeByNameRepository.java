package com.progetto.animeuniverse.repository.anime_by_name;

import static com.progetto.animeuniverse.util.Constants.FRESH_TIMEOUT;

import android.app.Application;
import android.util.Log;

import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.database.AnimeByNameDao;
import com.progetto.animeuniverse.database.AnimeRoomDatabase;
import com.progetto.animeuniverse.model.AnimeByName;
import com.progetto.animeuniverse.model.AnimeByNameApiResponse;
import com.progetto.animeuniverse.service.AnimeApiService;
import com.progetto.animeuniverse.util.ServiceLocator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimeByNameRepository implements IAnimeByNameRepository{
    public static final String TAG = AnimeByNameRepository.class.getSimpleName();

    private final Application application;
    private final AnimeApiService animeApiService;
    private final AnimeByNameDao animeByNameDao;
    private final AnimeByNameResponseCallback animeByNameResponseCallback;

    public AnimeByNameRepository(Application application, AnimeByNameResponseCallback animeByNameResponseCallback) {
        this.application = application;
        this.animeApiService = ServiceLocator.getInstance().getAnimeApiService();
        AnimeRoomDatabase animeRoomDatabase = ServiceLocator.getInstance().getAnimeByNameDao(application);
        this.animeByNameDao = animeRoomDatabase.animeByNameDao();
        this.animeByNameResponseCallback = animeByNameResponseCallback;
    }

    @Override
    public void fetchAnimeByName(String nameAnime, long lastUpdate) {
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastUpdate > FRESH_TIMEOUT){
            Call<AnimeByNameApiResponse> animeByNameResponseCall = animeApiService.getAnimeByName(nameAnime);

            animeByNameResponseCall.enqueue(new Callback<AnimeByNameApiResponse>() {
                @Override
                public void onResponse(Call<AnimeByNameApiResponse> call, Response<AnimeByNameApiResponse> response) {
                    if(response.isSuccessful()){
                        List<AnimeByName> animeByNameList = response.body().getAnimeByNameList();
                        saveDataInDatabase(animeByNameList);
                    }else{
                        animeByNameResponseCallback.onFailure(application.getString(R.string.error_retrieving_anime));
                    }
                }

                @Override
                public void onFailure(Call<AnimeByNameApiResponse> call, Throwable t) {
                    animeByNameResponseCallback.onFailure(t.getMessage());
                }
            });

        }else {
            Log.d(TAG, application.getString(R.string.data_read_from_local_database));
        }
    }

    private void saveDataInDatabase(List<AnimeByName> animeByNameList){
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            List<AnimeByName> allAnimeByName = animeByNameDao.getAll();
            for (AnimeByName animeByName : allAnimeByName){
                if(animeByNameList.contains(animeByName)){
                    animeByNameList.set(animeByNameList.indexOf(animeByName), animeByName);
                }
            }
            List<Long> insertedAnimeByNameIds = animeByNameDao.insertAnimeByNameList(animeByNameList);
            for(int i=0; i<animeByNameList.size(); i++){
                animeByNameList.get(i).setId(Math.toIntExact(insertedAnimeByNameIds.get(i)));
            }
            animeByNameResponseCallback.onSuccess(animeByNameList,System.currentTimeMillis());
        });
    }

    private void readDataFromDatabase(long lastUpdate){
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            animeByNameResponseCallback.onSuccess(animeByNameDao.getAll(), lastUpdate);
        });
    }
}
