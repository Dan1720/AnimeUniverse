package com.progetto.animeuniverse.data.source.anime_episodes;

import static com.progetto.animeuniverse.util.Constants.RETROFIT_ERROR;

import androidx.annotation.NonNull;

import com.progetto.animeuniverse.model.AnimeEpisodesApiResponse;
import com.progetto.animeuniverse.service.AnimeApiService;
import com.progetto.animeuniverse.util.ServiceLocator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimeEpisodesRemoteDataSource extends BaseAnimeEpisodesRemoteDataSource{
    private final AnimeApiService animeApiService;

    public AnimeEpisodesRemoteDataSource(){
        this.animeApiService = ServiceLocator.getInstance().getAnimeApiService();
    }
    @Override
    public void getAnimeEpisodes(int idAnime) {
        Call<AnimeEpisodesApiResponse> animeEpisodesApiResponseCall = animeApiService.getAnimeEpisodes(idAnime);
        animeEpisodesApiResponseCall.enqueue(new Callback<AnimeEpisodesApiResponse>(){

            @Override
            public void onResponse(@NonNull Call<AnimeEpisodesApiResponse> call,
                                   @NonNull Response<AnimeEpisodesApiResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    animeEpisodesCallback.onSuccessFromRemote(response.body(), System.currentTimeMillis());
                }else{
                    animeEpisodesCallback.onFailureFromRemote(new Exception());
                }
            }

            @Override
            public void onFailure(Call<AnimeEpisodesApiResponse> call, Throwable t) {
                animeEpisodesCallback.onFailureFromRemote(new Exception(RETROFIT_ERROR));
            }

        });
    }
}
