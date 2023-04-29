package com.progetto.animeuniverse.service;



import static com.progetto.animeuniverse.util.Constants.TOP_HEADLINES_COUNTRY_PARAMETER;
import static com.progetto.animeuniverse.util.Constants.TOP_HEADLINES_ENDPOINT;
import static com.progetto.animeuniverse.util.Constants.TOP_HEADLINES_PAGE_PARAMETER;
import static com.progetto.animeuniverse.util.Constants.TOP_HEADLINES_PAGE_SIZE_PARAMETER;

import com.progetto.animeuniverse.model.Anime;
import com.progetto.animeuniverse.model.AnimeApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface AnimeApiService {
    @GET(TOP_HEADLINES_ENDPOINT)
    Call<AnimeApiResponse> getAnime(
            @Query(TOP_HEADLINES_COUNTRY_PARAMETER) String country,
            @Query(TOP_HEADLINES_PAGE_SIZE_PARAMETER) int pageSize,
            @Query(TOP_HEADLINES_PAGE_PARAMETER) int page,
            @Header("Authorization") String apiKey);


}
