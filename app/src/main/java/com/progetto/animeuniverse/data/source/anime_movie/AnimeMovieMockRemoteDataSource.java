package com.progetto.animeuniverse.data.source.anime_movie;

import static com.progetto.animeuniverse.util.Constants.ANIMEMOVIE_API_TEST_JSON_FILE;
import static com.progetto.animeuniverse.util.Constants.API_KEY_ERROR;
import static com.progetto.animeuniverse.util.Constants.UNEXPECTED_ERROR;

import com.progetto.animeuniverse.model.AnimeMovieApiResponse;
import com.progetto.animeuniverse.util.JSONParserUtil;

import java.io.IOException;

public class AnimeMovieMockRemoteDataSource extends BaseAnimeMovieRemoteDataSource {
    private final JSONParserUtil jsonParserUtil;
    private final JSONParserUtil.JsonParserType jsonParserType;

    public AnimeMovieMockRemoteDataSource(JSONParserUtil jsonParserUtil, JSONParserUtil.JsonParserType jsonParserType) {
        this.jsonParserUtil = jsonParserUtil;
        this.jsonParserType = jsonParserType;
    }

    @Override
    public void getAnimeMovie() {
        AnimeMovieApiResponse animeMovieApiResponse = null;
        switch (jsonParserType) {
            case GSON:
                try {
                    animeMovieApiResponse = jsonParserUtil.parseJSONFileWithGSonAnimeMovie(ANIMEMOVIE_API_TEST_JSON_FILE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case JSON_ERROR:
                animeMovieCallback.onFailureFromRemote(new Exception(UNEXPECTED_ERROR));
                break;
        }

        if (animeMovieApiResponse != null) {
            animeMovieCallback.onSuccessFromRemote(animeMovieApiResponse, System.currentTimeMillis());
        } else {
            animeMovieCallback.onFailureFromRemote(new Exception(API_KEY_ERROR));
        }
    }
}
