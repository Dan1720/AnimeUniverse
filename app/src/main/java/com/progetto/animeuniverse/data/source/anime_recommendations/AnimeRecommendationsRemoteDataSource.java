package com.progetto.animeuniverse.data.source.anime_recommendations;

import static com.progetto.animeuniverse.util.Constants.RETROFIT_ERROR;

import androidx.annotation.NonNull;

import com.progetto.animeuniverse.model.AnimeRecommendationsApiResponse;
import com.progetto.animeuniverse.service.AnimeApiService;
import com.progetto.animeuniverse.util.ServiceLocator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimeRecommendationsRemoteDataSource extends BaseAnimeRecommendationsRemoteDataSource{

    private final AnimeApiService animeApiService;

    public AnimeRecommendationsRemoteDataSource() {
        this.animeApiService = ServiceLocator.getInstance().getAnimeApiService();
    }

    @Override
    public void getAnimeRecommendations() {
        Call<AnimeRecommendationsApiResponse> animeRecommendationsApiResponseCall = animeApiService.getAnimeRecommendations();
        animeRecommendationsApiResponseCall.enqueue(new Callback<AnimeRecommendationsApiResponse>(){

            @Override
            public void onResponse(@NonNull Call<AnimeRecommendationsApiResponse> call,
                                   @NonNull Response<AnimeRecommendationsApiResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    animeRecommendationsCallback.onSuccessFromRemote(response.body(), System.currentTimeMillis());
                }else{
                    animeRecommendationsCallback.onFailureFromRemote(new Exception());
                }
            }

            @Override
            public void onFailure(Call<AnimeRecommendationsApiResponse> call, Throwable t) {
                animeRecommendationsCallback.onFailureFromRemote(new Exception(RETROFIT_ERROR));
            }

        });
    }
}
