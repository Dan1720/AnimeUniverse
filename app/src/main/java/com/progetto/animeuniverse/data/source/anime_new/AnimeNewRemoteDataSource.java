package com.progetto.animeuniverse.data.source.anime_new;

import static com.progetto.animeuniverse.util.Constants.RETROFIT_ERROR;

import androidx.annotation.NonNull;

import com.progetto.animeuniverse.model.AnimeNewApiResponse;
import com.progetto.animeuniverse.service.AnimeApiService;
import com.progetto.animeuniverse.util.ServiceLocator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimeNewRemoteDataSource extends BaseAnimeNewRemoteDataSource{
    private final AnimeApiService animeApiService;

    public AnimeNewRemoteDataSource() {
        this.animeApiService = ServiceLocator.getInstance().getAnimeApiService();
    }

    @Override
    public void getAnimeNew() {
        Call<AnimeNewApiResponse> animeNewApiResponseCall = animeApiService.getAnimeNew();
        animeNewApiResponseCall.enqueue(new Callback<AnimeNewApiResponse>(){

            @Override
            public void onResponse(@NonNull Call<AnimeNewApiResponse> call,
                                   @NonNull Response<AnimeNewApiResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    animeNewCallback.onSuccessFromRemote(response.body(), System.currentTimeMillis());
                }else{
                    animeNewCallback.onFailureFromRemote(new Exception());
                }
            }

            @Override
            public void onFailure(Call<AnimeNewApiResponse> call, Throwable t) {
                animeNewCallback.onFailureFromRemote(new Exception(RETROFIT_ERROR));
            }

        });
    }
}
