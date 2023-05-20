package com.progetto.animeuniverse.repository.genres;

import static com.progetto.animeuniverse.util.Constants.FRESH_TIMEOUT;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.database.AnimeRoomDatabase;
import com.progetto.animeuniverse.database.GenreDao;
import com.progetto.animeuniverse.database.ReviewDao;
import com.progetto.animeuniverse.model.Genre;
import com.progetto.animeuniverse.model.GenresApiResponse;
import com.progetto.animeuniverse.model.Review;
import com.progetto.animeuniverse.model.ReviewsApiResponse;
import com.progetto.animeuniverse.service.AnimeApiService;
import com.progetto.animeuniverse.util.ServiceLocator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenresRepository implements IGenresRepository{

    public static final String TAG = GenresRepository.class.getSimpleName();

    private final Application application;
    private final AnimeApiService animeApiService;
    private final GenreDao genreDao;
    private final GenresResponseCallback genresResponseCallback;

    public GenresRepository(Application application, GenresResponseCallback genresResponseCallback){
        this.application = application;
        this.animeApiService = ServiceLocator.getInstance().getAnimeApiService();
        AnimeRoomDatabase animeRoomDatabase = ServiceLocator.getInstance().getGenresDao(application);
        this.genreDao = animeRoomDatabase.genreDao();
        this.genresResponseCallback = genresResponseCallback;
    }
    @Override
    public void fetchGenres(long lastUpdate) {
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastUpdate > FRESH_TIMEOUT){
            Call<GenresApiResponse> genresResponseCall = animeApiService.getGenres();

            genresResponseCall.enqueue(new Callback<GenresApiResponse>() {
                @Override
                public void onResponse(Call<GenresApiResponse> call, Response<GenresApiResponse> response) {
                    if(response.isSuccessful()){
                        List<Genre> genresList = response.body().getGernesList();
                        saveDataInDatabase(genresList);
                    }else{
                        genresResponseCallback.onFailure(application.getString(R.string.error_retrieving_anime));
                    }
                }

                @Override
                public void onFailure(Call<GenresApiResponse> call, Throwable t) {
                    genresResponseCallback.onFailure(t.getMessage());
                }

            });
        }else {
            Log.d(TAG, application.getString(R.string.data_read_from_local_database));
        }
    }

    private void saveDataInDatabase(List<Genre> genresList){
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            List<Genre> allGenres = genreDao.getAllGenres();
            for(Genre genre : allGenres){
                if(genresList.contains(genre)){
                    genresList.set(genresList.indexOf(genre), genre);
                }
            }
            List<Long> insertedGenreIds = genreDao.insertGenreList(genresList);
            for(int i=0; i<genresList.size(); i++){
                genresList.get(i).setId(Math.toIntExact(insertedGenreIds.get(i)));
            }
            genresResponseCallback.onSuccess(genresList, System.currentTimeMillis());
        });
    }

    private void readDataFromDatabase(long lastUpdate){
        AnimeRoomDatabase.databaseWriteExecutor.execute(() ->{
            genresResponseCallback.onSuccess(genreDao.getAllGenres(), lastUpdate);
        });
    }
}
