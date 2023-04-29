package com.progetto.animeuniverse.service;

import static com.progetto.animeuniverse.util.Constants.ANIME_API_ENDPOINTS;
import static com.progetto.animeuniverse.util.Constants.ANIME_API_SEARCH;

import com.progetto.animeuniverse.model.Anime;
import com.progetto.animeuniverse.model.AnimeApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AnimeApiService {
    @GET(ANIME_API_ENDPOINTS)
    /*Call<AnimeApiResponse> getAnime(
      @Query(ANIME_API_SEARCH) String searched_anime
    );*/
    Call<AnimeApiResponse> getAnime();

}
