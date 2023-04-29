package com.progetto.animeuniverse.data.source.anime;

import static com.progetto.animeuniverse.util.Constants.API_KEY_ERROR;
import static com.progetto.animeuniverse.util.Constants.RETROFIT_ERROR;
import static com.progetto.animeuniverse.util.Constants.TOP_HEADLINES_PAGE_SIZE_VALUE;

import androidx.annotation.NonNull;

import com.progetto.animeuniverse.model.AnimeApiResponse;
import com.progetto.animeuniverse.service.AnimeApiService;
import com.progetto.animeuniverse.util.ServiceLocator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimeRemoteDataSource extends BaseAnimeRemoteDataSource{
    private final AnimeApiService animeApiService;
    private final String apiKey;

    public AnimeRemoteDataSource(String apiKey){
        this.apiKey = apiKey;
        this.animeApiService = ServiceLocator.getInstance().getAnimeApiService();
    }

    @Override
    public void getAnime(String country, int page) {
        Call<AnimeApiResponse> animeResponseCall = animeApiService.getAnime(country, TOP_HEADLINES_PAGE_SIZE_VALUE, page, apiKey);
        animeResponseCall.enqueue(new Callback<AnimeApiResponse>(){
            @Override
            public void onResponse(@NonNull Call<AnimeApiResponse> call,
                                   @NonNull Response<AnimeApiResponse> response) {
                if(response.body() != null && response.isSuccessful() && !response.body().getStatus().equals("error")){
                    animeCallback.onSuccessFromRemote(response.body(), System.currentTimeMillis());
                }else{
                    animeCallback.onFailureFromRemote(new Exception(API_KEY_ERROR));
                }
            }

            @Override
            public void onFailure(Call<AnimeApiResponse> call, Throwable t) {
                animeCallback.onFailureFromRemote(new Exception(RETROFIT_ERROR));
            }

        });

    }
}
