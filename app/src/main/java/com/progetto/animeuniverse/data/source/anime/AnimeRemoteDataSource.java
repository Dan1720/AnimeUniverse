package com.progetto.animeuniverse.data.source.anime;

import static com.progetto.animeuniverse.util.Constants.API_KEY_ERROR;
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
    public void getAnimeByName(String q, String nameAnime) {

    }

    @Override
    public void getAnimeByIdFull(String anime, int id) {

    }

    @Override
    public void getAnimeById(String anime, int id) {

    }

    @Override
    public void getAnimeTop() {
        Call<AnimeApiResponse> animeResponseCall = animeApiService.getAnimeTop();

        System.out.println(" CIAONE " + animeResponseCall.toString());

        animeResponseCall.enqueue(new Callback<AnimeApiResponse>(){

            @Override
            public void onResponse(@NonNull Call<AnimeApiResponse> call,
                                   @NonNull Response<AnimeApiResponse> response) {
                if(response.isSuccessful()&& response.body() != null){
                    System.out.println("HO STAMPATO " + response.body());
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
