package com.progetto.animeuniverse.data.source.anime;


import static com.progetto.animeuniverse.util.Constants.RETROFIT_ERROR;


import androidx.annotation.NonNull;

import com.progetto.animeuniverse.model.AnimeApiResponse;
import com.progetto.animeuniverse.service.AnimeApiService;
import com.progetto.animeuniverse.util.ServiceLocator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimeRemoteDataSource extends BaseAnimeRemoteDataSource{
    private final AnimeApiService animeApiService;

    public AnimeRemoteDataSource(){
        this.animeApiService = ServiceLocator.getInstance().getAnimeApiService();
    }


    @Override
    public void getAnimeTop() {
        Call<AnimeApiResponse> animeResponseCall = animeApiService.getAnimeTop();
        animeResponseCall.enqueue(new Callback<AnimeApiResponse>(){

            @Override
            public void onResponse(@NonNull Call<AnimeApiResponse> call,
                                   @NonNull Response<AnimeApiResponse> response) {
                if(response.isSuccessful()&& response.body() != null){
                    animeCallback.onSuccessFromRemote(response.body(), System.currentTimeMillis());
                }else{
                    animeCallback.onFailureFromRemote(new Exception());
                }
            }

            @Override
            public void onFailure(Call<AnimeApiResponse> call, Throwable t) {
                animeCallback.onFailureFromRemote(new Exception(RETROFIT_ERROR));
            }

        });
    }
}
