package com.progetto.animeuniverse.service;



import static com.progetto.animeuniverse.util.Constants.ANIME_API_ENDPOINTS;
import static com.progetto.animeuniverse.util.Constants.TOP_HEADLINES_ANIME_NEW_PARAMETER;
import static com.progetto.animeuniverse.util.Constants.TOP_HEADLINES_ENDPOINT;
import static com.progetto.animeuniverse.util.Constants.TOP_HEADLINES_Q_PARAMETER;
import static com.progetto.animeuniverse.util.Constants.TOP_HEADLINES_TOP_PARAMETER;

import com.progetto.animeuniverse.model.Anime;
import com.progetto.animeuniverse.model.AnimeApiResponse;
import com.progetto.animeuniverse.model.ReviewsApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AnimeApiService {
    @GET(TOP_HEADLINES_ENDPOINT)
    Call<AnimeApiResponse> getAnimeByName(
            @Query(TOP_HEADLINES_Q_PARAMETER) String nameAnime);

    @GET("anime/{id}/full")
    Call<AnimeApiResponse> getAnimeByIdFull(String anime, @Path("id")int idFull);

    @GET("anime/{id}")
    Call<AnimeApiResponse> getAnimeById(String anime,@Path("id")int id);

    @GET(ANIME_API_ENDPOINTS)
    Call<AnimeApiResponse> getAnimeTop();

    @GET("anime/{id}/reviews")
    Call<ReviewsApiResponse> getReviewByIdAnime(@Path("id")int id);



}
