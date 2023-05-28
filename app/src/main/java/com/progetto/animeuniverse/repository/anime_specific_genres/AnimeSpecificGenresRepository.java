package com.progetto.animeuniverse.repository.anime_specific_genres;

import static com.progetto.animeuniverse.util.Constants.FRESH_TIMEOUT;

import android.app.Application;
import android.util.Log;

import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.database.AnimeSpecificGenresDao;
import com.progetto.animeuniverse.database.AnimeRoomDatabase;
import com.progetto.animeuniverse.model.AnimeSpecificGenres;
import com.progetto.animeuniverse.model.AnimeSpecificGenresApiResponse;
import com.progetto.animeuniverse.service.AnimeApiService;
import com.progetto.animeuniverse.util.ServiceLocator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimeSpecificGenresRepository implements IAnimeSpecificGenresRepository {
    public static final String TAG = AnimeSpecificGenresRepository.class.getSimpleName();

    private final Application application;
    private final AnimeApiService animeApiService;
    private final AnimeSpecificGenresDao animeSpecificGenresDao;
    private final AnimeSpecificGenresResponseCallback animeSpecificGenresResponseCallback;

    public AnimeSpecificGenresRepository(Application application, AnimeSpecificGenresResponseCallback animeSpecificGenresResponseCallback) {
        this.application = application;
        this.animeApiService = ServiceLocator.getInstance().getAnimeApiService();
        AnimeRoomDatabase animeRoomDatabase = ServiceLocator.getInstance().getAnimeSpecificGenresDao(application);
        this.animeSpecificGenresDao = animeRoomDatabase.animeSpecificGenresDao();
        this.animeSpecificGenresResponseCallback = animeSpecificGenresResponseCallback;
    }

    @Override
    public void fetchAnimeSpecificGenres(int idGenre, long lastUpdate) {
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastUpdate > FRESH_TIMEOUT){
            Call<AnimeSpecificGenresApiResponse> animeSpecificGenresResponseCall = animeApiService.getAnimeSpecificGenres(idGenre);

            animeSpecificGenresResponseCall.enqueue(new Callback<AnimeSpecificGenresApiResponse>() {
                @Override
                public void onResponse(Call<AnimeSpecificGenresApiResponse> call, Response<AnimeSpecificGenresApiResponse> response) {
                    if(response.isSuccessful()){
                        List<AnimeSpecificGenres> animeSpecificGenresList = response.body().getAnimeSpecificGenresList();
                        saveDataInDatabase(animeSpecificGenresList);
                    }else{
                        animeSpecificGenresResponseCallback.onFailure(application.getString(R.string.error_retrieving_anime));
                    }
                }

                @Override
                public void onFailure(Call<AnimeSpecificGenresApiResponse> call, Throwable t) {
                    animeSpecificGenresResponseCallback.onFailure(t.getMessage());
                }
            });

        }else {
            Log.d(TAG, application.getString(R.string.data_read_from_local_database));
        }
    }

    private void saveDataInDatabase(List<AnimeSpecificGenres> animeSpecificGenresList){
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            List<AnimeSpecificGenres> allAnimeSpecificGenres = animeSpecificGenresDao.getAll();
            for (AnimeSpecificGenres animeSpecificGenres : allAnimeSpecificGenres){
                if(animeSpecificGenresList.contains(animeSpecificGenres)){
                    animeSpecificGenresList.set(animeSpecificGenresList.indexOf(animeSpecificGenres), animeSpecificGenres);
                }
            }
            List<Long> insertedAnimeSpecificGenresIds = animeSpecificGenresDao.insertAnimeSpecificGenresList(animeSpecificGenresList);
            for(int i=0; i<animeSpecificGenresList.size(); i++){
                animeSpecificGenresList.get(i).setId(Math.toIntExact(insertedAnimeSpecificGenresIds.get(i)));
            }
            animeSpecificGenresResponseCallback.onSuccess(animeSpecificGenresList,System.currentTimeMillis());
        });
    }

    private void readDataFromDatabase(long lastUpdate){
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            animeSpecificGenresResponseCallback.onSuccess(animeSpecificGenresDao.getAll(), lastUpdate);
        });
    }
}
