package com.progetto.animeuniverse.data.source.anime_by_name;

import static com.progetto.animeuniverse.util.Constants.RETROFIT_ERROR;

import com.progetto.animeuniverse.model.AnimeByNameApiResponse;
import com.progetto.animeuniverse.service.AnimeApiService;
import com.progetto.animeuniverse.util.ServiceLocator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimeByNameRemoteDataSource extends BaseAnimeByNameRemoteDataSource{
    private final AnimeApiService animeApiService;

    public AnimeByNameRemoteDataSource(){
        this.animeApiService = ServiceLocator.getInstance().getAnimeApiService();
    }

    @Override
    public void getAnimeByName(String nameAnime) {
        Call<AnimeByNameApiResponse> animeByNameApiResponseCall = animeApiService.getAnimeByName(nameAnime);
        animeByNameApiResponseCall.enqueue(new Callback<AnimeByNameApiResponse>() {
            @Override
            public void onResponse(Call<AnimeByNameApiResponse> call, Response<AnimeByNameApiResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    animeByNameCallback.onSuccessFromRemote(response.body(), System.currentTimeMillis());
                }else {
                    animeByNameCallback.onFailureFromRemote(new Exception());
                }

            }

            @Override
            public void onFailure(Call<AnimeByNameApiResponse> call, Throwable t) {
                animeByNameCallback.onFailureFromRemote(new Exception(RETROFIT_ERROR));
            }
        });
    }
}
