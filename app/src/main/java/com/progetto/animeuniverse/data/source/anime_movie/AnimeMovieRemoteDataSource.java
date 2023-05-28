package com.progetto.animeuniverse.data.source.anime_movie;

import static com.progetto.animeuniverse.util.Constants.RETROFIT_ERROR;

import androidx.annotation.NonNull;

import com.progetto.animeuniverse.model.AnimeMovieApiResponse;
import com.progetto.animeuniverse.service.AnimeApiService;
import com.progetto.animeuniverse.util.ServiceLocator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimeMovieRemoteDataSource extends BaseAnimeMovieRemoteDataSource {
    private final AnimeApiService animeApiService;

    public AnimeMovieRemoteDataSource() {
        this.animeApiService = ServiceLocator.getInstance().getAnimeApiService();
    }
    @Override
    public void getAnimeMovie() {
        Call<AnimeMovieApiResponse> animeMovieApiResponseCall = animeApiService.getAnimeMovie();
        animeMovieApiResponseCall.enqueue(new Callback<AnimeMovieApiResponse>(){

            @Override
            public void onResponse(@NonNull Call<AnimeMovieApiResponse> call,
                                   @NonNull Response<AnimeMovieApiResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    animeMovieCallback.onSuccessFromRemote(response.body(), System.currentTimeMillis());
                }else{
                    animeMovieCallback.onFailureFromRemote(new Exception());
                }
            }

            @Override
            public void onFailure(Call<AnimeMovieApiResponse> call, Throwable t) {
                animeMovieCallback.onFailureFromRemote(new Exception(RETROFIT_ERROR));
            }

        });
    }
}