package com.progetto.animeuniverse.repository.anime_by_name;

import android.app.Application;

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
            Call<AnimeByNameApiResponse> animeByNameResponseCall = animeApiService.getAnimeByName(nameAnime);

            animeByNameResponseCall.enqueue(new Callback<AnimeByNameApiResponse>() {
                @Override
                public void onResponse(Call<AnimeByNameApiResponse> call, Response<AnimeByNameApiResponse> response) {
                    if(response.isSuccessful()){
                        List<AnimeByName> animeByNameList = response.body().getAnimeByNameList();
                        saveDataInDatabase(animeByNameList, lastUpdate);
                    }else{
                        animeByNameResponseCallback.onFailure(application.getString(R.string.error_retrieving_anime));
                    }
                }

                @Override
                public void onFailure(Call<AnimeByNameApiResponse> call, Throwable t) {
                    animeByNameResponseCallback.onFailure(t.getMessage());
                }
            });
    }

    private void saveDataInDatabase(List<AnimeByName> animeByNameList, long lastUpdate){

        AnimeRoomDatabase.databaseWriteExecutor.execute(() -> {

            List<Long> insertedAnimeByNameIds = animeByNameDao.insertAnimeByNameList(animeByNameList);
            for (int i = 0; i < animeByNameList.size(); i++) {
                animeByNameList.get(i).setId(Math.toIntExact(insertedAnimeByNameIds.get(i)));
            }

            animeByNameResponseCallback.onSuccess(animeByNameList, System.currentTimeMillis());
        });

    }

}
