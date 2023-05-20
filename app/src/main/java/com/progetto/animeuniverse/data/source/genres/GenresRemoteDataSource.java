package com.progetto.animeuniverse.data.source.genres;

import static com.progetto.animeuniverse.util.Constants.RETROFIT_ERROR;

import androidx.annotation.NonNull;

import com.progetto.animeuniverse.model.GenresApiResponse;
import com.progetto.animeuniverse.service.AnimeApiService;
import com.progetto.animeuniverse.util.ServiceLocator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenresRemoteDataSource extends BaseGenresRemoteDataSource{
    private final AnimeApiService animeApiService;

    public GenresRemoteDataSource(){
        this.animeApiService = ServiceLocator.getInstance().getAnimeApiService();
    }

    @Override
    public void getGenres() {
        Call<GenresApiResponse> genresApiResponseCall = animeApiService.getGenres();
        genresApiResponseCall.enqueue(new Callback<GenresApiResponse>(){

            @Override
            public void onResponse(@NonNull Call<GenresApiResponse> call,
                                   @NonNull Response<GenresApiResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    genresCallback.onSuccessFromRemote(response.body(), System.currentTimeMillis());
                }else{
                    genresCallback.onFailureFromRemote(new Exception());
                }
            }

            @Override
            public void onFailure(Call<GenresApiResponse> call, Throwable t) {
                genresCallback.onFailureFromRemote(new Exception(RETROFIT_ERROR));
            }

        });
    }
}
