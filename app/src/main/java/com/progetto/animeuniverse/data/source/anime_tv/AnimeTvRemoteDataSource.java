package com.progetto.animeuniverse.data.source.anime_tv;

import static com.progetto.animeuniverse.util.Constants.RETROFIT_ERROR;

import androidx.annotation.NonNull;

import com.progetto.animeuniverse.model.AnimeTvApiResponse;
import com.progetto.animeuniverse.service.AnimeApiService;
import com.progetto.animeuniverse.util.ServiceLocator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimeTvRemoteDataSource extends  BaseAnimeTvRemoteDataSource{
    private final AnimeApiService animeApiService;

    public AnimeTvRemoteDataSource() {
        this.animeApiService = ServiceLocator.getInstance().getAnimeApiService();
    }
    @Override
    public void getAnimeTv() {
        Call<AnimeTvApiResponse> animeTvApiResponseCall = animeApiService.getAnimeTv();
        animeTvApiResponseCall.enqueue(new Callback<AnimeTvApiResponse>(){

            @Override
            public void onResponse(@NonNull Call<AnimeTvApiResponse> call,
                                   @NonNull Response<AnimeTvApiResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    animeTvCallback.onSuccessFromRemote(response.body(), System.currentTimeMillis());
                }else{
                    animeTvCallback.onFailureFromRemote(new Exception());
                }
            }

            @Override
            public void onFailure(Call<AnimeTvApiResponse> call, Throwable t) {
                animeTvCallback.onFailureFromRemote(new Exception(RETROFIT_ERROR));
            }

        });
    }
}
