package com.progetto.animeuniverse.data.source.anime_specific_genres;

import static com.progetto.animeuniverse.util.Constants.ANIMEGENRES_API_TEST_JSON_FILE;
import static com.progetto.animeuniverse.util.Constants.API_KEY_ERROR;
import static com.progetto.animeuniverse.util.Constants.UNEXPECTED_ERROR;

import com.progetto.animeuniverse.model.AnimeSpecificGenresApiResponse;
import com.progetto.animeuniverse.util.JSONParserUtil;

import java.io.IOException;

public class AnimeSpecificGenresMockRemoteDataSource extends BaseAnimeSpecificGenresRemoteDataSource {

    private final JSONParserUtil jsonParserUtil;
    private final JSONParserUtil.JsonParserType jsonParserType;

    public AnimeSpecificGenresMockRemoteDataSource(JSONParserUtil jsonParserUtil, JSONParserUtil.JsonParserType jsonParserType) {
        this.jsonParserUtil = jsonParserUtil;
        this.jsonParserType = jsonParserType;
    }

    @Override
    public void getAnimeSpecificGenres() {
        AnimeSpecificGenresApiResponse animeSpecificGenresApiResponse = null;
        switch (jsonParserType) {
            case GSON:
                try {
                    animeSpecificGenresApiResponse = jsonParserUtil.parseJSONFileWithGSonAnimeSpecificGenres(ANIMEGENRES_API_TEST_JSON_FILE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case JSON_ERROR:
                animeSpecificGenresCallback.onFailureFromRemote(new Exception(UNEXPECTED_ERROR));
                break;
        }

        if (animeSpecificGenresApiResponse != null) {
            animeSpecificGenresCallback.onSuccessFromRemote(animeSpecificGenresApiResponse, System.currentTimeMillis());
        } else {
            animeSpecificGenresCallback.onFailureFromRemote(new Exception(API_KEY_ERROR));
        }
    }
}
