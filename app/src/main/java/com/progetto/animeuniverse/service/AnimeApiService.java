package com.progetto.animeuniverse.service;



import static com.progetto.animeuniverse.util.Constants.ANIME_API_ENDPOINTS;
import static com.progetto.animeuniverse.util.Constants.TOP_HEADLINES_ENDPOINT;
import static com.progetto.animeuniverse.util.Constants.TOP_HEADLINES_Q_PARAMETER;

import com.progetto.animeuniverse.model.AnimeApiResponse;
import com.progetto.animeuniverse.model.AnimeByNameApiResponse;
import com.progetto.animeuniverse.model.AnimeEpisodesApiResponse;
import com.progetto.animeuniverse.model.AnimeEpisodesImagesApiResponse;
import com.progetto.animeuniverse.model.AnimeMovieApiResponse;
import com.progetto.animeuniverse.model.AnimeNewApiResponse;
import com.progetto.animeuniverse.model.AnimeRecommendationsApiResponse;
import com.progetto.animeuniverse.model.AnimeSpecificGenresApiResponse;
import com.progetto.animeuniverse.model.AnimeTvApiResponse;
import com.progetto.animeuniverse.model.GenresApiResponse;
import com.progetto.animeuniverse.model.ReviewsApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AnimeApiService {
    @GET(TOP_HEADLINES_ENDPOINT)
    Call<AnimeByNameApiResponse> getAnimeByName(
            @Query(TOP_HEADLINES_Q_PARAMETER) String nameAnime);

    @GET(TOP_HEADLINES_ENDPOINT)
    Call<AnimeSpecificGenresApiResponse> getAnimeSpecificGenres(
            @Query("genres") int idGenre);


    @GET("anime/{id}/full")
    Call<AnimeApiResponse> getAnimeByIdFull(String anime, @Path("id")int idFull);

    @GET("anime/{id}")
    Call<AnimeApiResponse> getAnimeById(String anime,@Path("id")int id);

    @GET(ANIME_API_ENDPOINTS)
    Call<AnimeApiResponse> getAnimeTop();

    @GET("anime/{id}/reviews")
    Call<ReviewsApiResponse> getReviewByIdAnime(@Path("id")int id);

    @GET("genres/anime")
    Call<GenresApiResponse> getGenres();

    @GET("recommendations/anime")
    Call<AnimeRecommendationsApiResponse> getAnimeRecommendations();

    @GET("watch/promos")
    Call<AnimeNewApiResponse> getAnimeNew();

    @GET("anime/{id}/episodes")
    Call<AnimeEpisodesApiResponse> getAnimeEpisodes(@Path("id")int id);

    @GET("anime/{id}/videos/episodes")
    Call<AnimeEpisodesImagesApiResponse> getAnimeEpisodesImages(@Path("id")int id);

    @GET("anime?type=tv")
    Call<AnimeTvApiResponse> getAnimeTv();

    @GET("anime?type=movie")
    Call<AnimeMovieApiResponse> getAnimeMovie();


}
