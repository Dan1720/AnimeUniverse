package com.progetto.animeuniverse.repository;

import static com.progetto.animeuniverse.util.Constants.FRESH_TIMEOUT;

import android.app.Application;

import androidx.annotation.NonNull;

import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.model.Anime;
import com.progetto.animeuniverse.model.AnimeApiResponse;
import com.progetto.animeuniverse.model.UrlAnime;
import com.progetto.animeuniverse.service.AnimeApiService;
import com.progetto.animeuniverse.util.ServiceLocator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimeRepository implements IAnimeRepository{
   private final ResponseCallback responseCallback;
    private final Application application;
    private final AnimeApiService animeApiService;



    public AnimeRepository(Application application, ResponseCallback responseCallback) {
        this.application = application;
        this.animeApiService = ServiceLocator.getInstance().getAnimeApiService();
        this.responseCallback = responseCallback;
    }





   @Override
    public void fetchAnime() {
       /* long currentTime = System.currentTimeMillis();

        if(currentTime - lastUpdate > FRESH_TIMEOUT){
            Call<AnimeApiResponse> animeApiResponseCall = animeApiService.getAnime(searched_anime);

            //chiamata asincrona
            animeApiResponseCall.enqueue(new Callback<AnimeApiResponse>() {
                @Override
                public void onResponse(@NonNull Call<AnimeApiResponse> call, @NonNull Response<AnimeApiResponse> response) {
                    if(response.body() != null && response.isSuccessful() &&
                    !response.body().getStatus().equals("error")){
                        List<Anime> animeList = response.body().getAnime();
                        //Salvataggio nel database???
                    } else{
                        responseCallback.onFailure(application.getString(R.string.error_retrieving_anime));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<AnimeApiResponse> call,@NonNull Throwable t) {
                    responseCallback.onFailure(t.getMessage());
                }
            });

        }*/
        //Call<AnimeApiResponse> animeApiResponseCall = animeApiService.getAnime(searched_anime);

        Call<AnimeApiResponse> animeApiResponseCall = animeApiService.getAnime();
        //chiamata asincrona
        animeApiResponseCall.enqueue(new Callback<AnimeApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<AnimeApiResponse> call, @NonNull Response<AnimeApiResponse> response) {
                if(response.body() != null && response.isSuccessful()){
                    System.out.println("chiamata andata a buon fine");
                    //List<Anime> animeList = response.body().getAnime();
                    UrlAnime urlAnime = response.body().getData()[0];
                    System.out.println(urlAnime.getUrl());
                    //Salvataggio nel database???
                } else{
                    responseCallback.onFailure(application.getString(R.string.error_retrieving_anime));
                }
            }

            @Override
            public void onFailure(@NonNull Call<AnimeApiResponse> call,@NonNull Throwable t) {
                responseCallback.onFailure(t.getMessage());
            }
        });

    }

}
