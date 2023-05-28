package com.progetto.animeuniverse.repository.anime_movie;

import static com.progetto.animeuniverse.util.Constants.FRESH_TIMEOUT;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.database.AnimeRoomDatabase;
import com.progetto.animeuniverse.database.AnimeMovieDao;
import com.progetto.animeuniverse.model.AnimeMovie;
import com.progetto.animeuniverse.model.AnimeMovieApiResponse;
import com.progetto.animeuniverse.repository.anime_movie.AnimeMovieRepository;
import com.progetto.animeuniverse.repository.anime_movie.AnimeMovieResponseCallback;
import com.progetto.animeuniverse.repository.anime_movie.IAnimeMovieRepository;
import com.progetto.animeuniverse.service.AnimeApiService;
import com.progetto.animeuniverse.util.ServiceLocator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimeMovieRepository implements IAnimeMovieRepository {
    private static final String TAG = AnimeMovieRepository.class.getSimpleName();

    private final Application application;
    private final AnimeApiService animeApiService;
    private final AnimeMovieDao animeMovieDao;
    private final AnimeMovieResponseCallback animeMovieResponseCallback;

    public AnimeMovieRepository(Application application, AnimeMovieResponseCallback animeMovieResponseCallback) {
        this.application = application;
        this.animeApiService = ServiceLocator.getInstance().getAnimeApiService();
        AnimeRoomDatabase animeRoomDatabase = ServiceLocator.getInstance().getAnimeMovieDao(application);
        this.animeMovieDao = animeRoomDatabase.animeMovieDao();
        this.animeMovieResponseCallback = animeMovieResponseCallback;
    }

    @Override
    public void fetchAnimeMovie(long lastUpdate) {
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastUpdate > FRESH_TIMEOUT){
            Call<AnimeMovieApiResponse> animeMovieApiResponseCall = animeApiService.getAnimeMovie();
            animeMovieApiResponseCall.enqueue(new Callback<AnimeMovieApiResponse>() {
                @Override
                public void onResponse(Call<AnimeMovieApiResponse> call, Response<AnimeMovieApiResponse> response) {
                    if(response.isSuccessful()){
                        List<AnimeMovie> animeMovieList = response.body().getAnimeMovieList();
                        saveDataInDatabase(animeMovieList);
                    }else{
                        animeMovieResponseCallback.onFailure(application.getString(R.string.error_retrieving_anime));
                    }
                }

                @Override
                public void onFailure(Call<AnimeMovieApiResponse> call, @NonNull Throwable t) {
                    animeMovieResponseCallback.onFailure(t.getMessage());
                }
            });
        }else {
            Log.d(TAG, application.getString(R.string.data_read_from_local_database));
        }
    }

    private void saveDataInDatabase(List<AnimeMovie> animeMovieList){
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            List<AnimeMovie> allAnimeMovie = animeMovieDao.getAll();
            for(AnimeMovie animeMovie : allAnimeMovie){
                if(animeMovieList.contains(animeMovie)){
                    animeMovieList.set(animeMovieList.indexOf(animeMovie), animeMovie);
                }
            }
            List<Long> insertedAnimeMovieIds = animeMovieDao.insertAnimeMovieList(animeMovieList);
            for(int i=0; i<animeMovieList.size(); i++){
                animeMovieList.get(i).setId(Math.toIntExact(insertedAnimeMovieIds.get(i)));
            }
            animeMovieResponseCallback.onSuccess(animeMovieList, System.currentTimeMillis());
        });
    }

    private void readDataFromDatabase(long lastUpdate){
        AnimeRoomDatabase.databaseWriteExecutor.execute(() ->{
            animeMovieResponseCallback.onSuccess(animeMovieDao.getAll(), lastUpdate);
        });
    }
}
