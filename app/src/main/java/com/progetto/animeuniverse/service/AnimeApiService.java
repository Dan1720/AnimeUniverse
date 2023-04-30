package com.progetto.animeuniverse.service;



import static com.progetto.animeuniverse.util.Constants.TOP_HEADLINES_ENDPOINT;
import static com.progetto.animeuniverse.util.Constants.TOP_HEADLINES_Q_PARAMETER;

import com.progetto.animeuniverse.model.Anime;
import com.progetto.animeuniverse.model.AnimeApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AnimeApiService {
    @GET(TOP_HEADLINES_ENDPOINT)
    Call<AnimeApiResponse> getAnimeByName(
            @Query(TOP_HEADLINES_Q_PARAMETER) String q);

    @GET("anime/{id}/full")
    Call<List<Anime>> groupListFull(@Path("id")int groupIdFull);

    @GET("anime/{id}")
    Call<List<Anime>> groupList(@Path("id")int groupId);


}
