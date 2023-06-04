package com.progetto.animeuniverse.data.source.anime_specific_genres;

import static com.progetto.animeuniverse.util.Constants.RETROFIT_ERROR;

import com.progetto.animeuniverse.model.AnimeSpecificGenresApiResponse;
import com.progetto.animeuniverse.service.AnimeApiService;
import com.progetto.animeuniverse.util.ServiceLocator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimeSpecificGenresRemoteDataSource extends BaseAnimeSpecificGenresRemoteDataSource {
    private final AnimeApiService animeApiService;

    public AnimeSpecificGenresRemoteDataSource(){
        this.animeApiService = ServiceLocator.getInstance().getAnimeApiService();
    }

    @Override
    public void getAnimeSpecificGenres(int idGenre) {
        Call<AnimeSpecificGenresApiResponse> animeSpecificGenresApiResponseCall = animeApiService.getAnimeSpecificGenres(idGenre);
        animeSpecificGenresApiResponseCall.enqueue(new Callback<AnimeSpecificGenresApiResponse>() {
            @Override
            public void onResponse(Call<AnimeSpecificGenresApiResponse> call, Response<AnimeSpecificGenresApiResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    animeSpecificGenresCallback.onSuccessFromRemote(response.body(), System.currentTimeMillis());
                }else {
                    animeSpecificGenresCallback.onFailureFromRemote(new Exception());
                }

            }

            @Override
            public void onFailure(Call<AnimeSpecificGenresApiResponse> call, Throwable t) {
                animeSpecificGenresCallback.onFailureFromRemote(new Exception(RETROFIT_ERROR));
            }
        });
    }
}
