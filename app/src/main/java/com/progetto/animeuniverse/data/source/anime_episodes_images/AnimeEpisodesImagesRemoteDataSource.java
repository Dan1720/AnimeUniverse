package com.progetto.animeuniverse.data.source.anime_episodes_images;

import static com.progetto.animeuniverse.util.Constants.RETROFIT_ERROR;

import androidx.annotation.NonNull;

import com.progetto.animeuniverse.model.AnimeEpisodesImagesApiResponse;
import com.progetto.animeuniverse.service.AnimeApiService;
import com.progetto.animeuniverse.util.ServiceLocator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimeEpisodesImagesRemoteDataSource extends BaseAnimeEpisodesImagesRemoteDataSource{
    private final AnimeApiService animeApiService;
    public AnimeEpisodesImagesRemoteDataSource(){
        this.animeApiService = ServiceLocator.getInstance().getAnimeApiService();
    }

    @Override
    public void getAnimeEpisodesImages(int idAnime) {
        Call<AnimeEpisodesImagesApiResponse> animeEpisodesImagesApiResponseCall = animeApiService.getAnimeEpisodesImages(idAnime);
        animeEpisodesImagesApiResponseCall.enqueue(new Callback<AnimeEpisodesImagesApiResponse>(){

            @Override
            public void onResponse(@NonNull Call<AnimeEpisodesImagesApiResponse> call,
                                   @NonNull Response<AnimeEpisodesImagesApiResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    animeEpisodesImagesCallback.onSuccessFromRemote(response.body(), System.currentTimeMillis());
                }else{
                    animeEpisodesImagesCallback.onFailureFromRemote(new Exception());
                }
            }

            @Override
            public void onFailure(Call<AnimeEpisodesImagesApiResponse> call, Throwable t) {
                animeEpisodesImagesCallback.onFailureFromRemote(new Exception(RETROFIT_ERROR));
            }

        });
    }
}
