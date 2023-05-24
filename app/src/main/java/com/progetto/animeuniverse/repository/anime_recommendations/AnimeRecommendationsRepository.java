package com.progetto.animeuniverse.repository.anime_recommendations;

import static com.progetto.animeuniverse.util.Constants.FRESH_TIMEOUT;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.database.AnimeRecommendationsDao;
import com.progetto.animeuniverse.database.AnimeRoomDatabase;
import com.progetto.animeuniverse.model.AnimeRecommendations;
import com.progetto.animeuniverse.model.AnimeRecommendationsApiResponse;
import com.progetto.animeuniverse.model.Genre;
import com.progetto.animeuniverse.model.Review;
import com.progetto.animeuniverse.model.ReviewsApiResponse;
import com.progetto.animeuniverse.service.AnimeApiService;
import com.progetto.animeuniverse.util.ServiceLocator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimeRecommendationsRepository implements IAnimeRecommendationsRepository{

    private static final String TAG = AnimeRecommendationsRepository.class.getSimpleName();
    private final Application application;
    private final AnimeApiService animeApiService;
    private final AnimeRecommendationsDao animeRecommendationsDao;
    private final AnimeRecommendationResponseCallback animeRecommendationResponseCallback;

    public AnimeRecommendationsRepository(Application application, AnimeRecommendationResponseCallback animeRecommendationResponseCallback) {
        this.application = application;
        this.animeApiService = ServiceLocator.getInstance().getAnimeApiService();
        AnimeRoomDatabase animeRoomDatabase = ServiceLocator.getInstance().getAnimeRecommendationsDao(application);
        this.animeRecommendationsDao = animeRoomDatabase.animeRecommendationsDao();
        this.animeRecommendationResponseCallback = animeRecommendationResponseCallback;
    }

    @Override
    public void fetchAnimeRecommendations(long lastUpdate) {
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastUpdate > FRESH_TIMEOUT){
            Call<AnimeRecommendationsApiResponse> animeRecommendationsResponseCall = animeApiService.getAnimeRecommendations();
            animeRecommendationsResponseCall.enqueue(new Callback<AnimeRecommendationsApiResponse>() {
                @Override
                public void onResponse(Call<AnimeRecommendationsApiResponse> call, Response<AnimeRecommendationsApiResponse> response) {
                    if(response.isSuccessful()){
                        List<AnimeRecommendations> animeRecommendationsList = response.body().getAnimeRecommendationsList();
                        saveDataInDatabase(animeRecommendationsList);
                    }else{
                        animeRecommendationResponseCallback.onFailure(application.getString(R.string.error_retrieving_anime));
                    }
                }

                @Override
                public void onFailure(Call<AnimeRecommendationsApiResponse> call, @NonNull Throwable t) {
                    animeRecommendationResponseCallback.onFailure(t.getMessage());
                }
            });
        }else {
            Log.d(TAG, application.getString(R.string.data_read_from_local_database));
        }
    }

    private void saveDataInDatabase(List<AnimeRecommendations> animeRecommendationsList){
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            List<AnimeRecommendations> allAnimeRecommendations = animeRecommendationsDao.getAllAnimeRecommendations();
            for(AnimeRecommendations animeRecommendations : allAnimeRecommendations){
                if(animeRecommendationsList.contains(animeRecommendations)){
                    animeRecommendationsList.set(animeRecommendationsList.indexOf(animeRecommendations), animeRecommendations);
                }
            }
            List<Long> insertedAnimeRecommendationsIds = animeRecommendationsDao.insertAnimeRecommendationsList(animeRecommendationsList);
            for(int i=0; i<animeRecommendationsList.size(); i++){
                animeRecommendationsList.get(i).setId(Math.toIntExact(insertedAnimeRecommendationsIds.get(i)));
            }
            animeRecommendationResponseCallback.onSuccess(animeRecommendationsList, System.currentTimeMillis());
        });
    }

    private void readDataFromDatabase(long lastUpdate){
        AnimeRoomDatabase.databaseWriteExecutor.execute(() ->{
            animeRecommendationResponseCallback.onSuccess(animeRecommendationsDao.getAllAnimeRecommendations(), lastUpdate);
        });
    }
}
