package com.progetto.animeuniverse.data.service;

import static com.progetto.animeuniverse.util.Constants.TOP_HEADLINES_COUNTRY_PARAMETER;
import static com.progetto.animeuniverse.util.Constants.TOP_HEADLINES_ENDPOINT;
import static com.progetto.animeuniverse.util.Constants.TOP_HEADLINES_PAGE_PARAMETER;
import static com.progetto.animeuniverse.util.Constants.TOP_HEADLINES_PAGE_SIZE_PARAMETER;

import androidx.room.Query;

import com.progetto.animeuniverse.AnimeApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface AnimeApiService {
    @GET(TOP_HEADLINES_ENDPOINT)
    Call<AnimeApiResponse> getAnime(
           // @Query(TOP_HEADLINES_COUNTRY_PARAMETER) String country,
            //@Query(TOP_HEADLINES_PAGE_SIZE_PARAMETER) int pageSize,
           // @Query(TOP_HEADLINES_PAGE_PARAMETER) int page,
            @Header("Authorization") String apiKey);


}
