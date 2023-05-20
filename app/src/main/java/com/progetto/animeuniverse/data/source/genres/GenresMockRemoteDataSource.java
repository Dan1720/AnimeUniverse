package com.progetto.animeuniverse.data.source.genres;

import static com.progetto.animeuniverse.util.Constants.API_KEY_ERROR;
import static com.progetto.animeuniverse.util.Constants.GENRES_API_TEST_JSON_FILE;
import static com.progetto.animeuniverse.util.Constants.REVIEWS_API_TEST_JSON_FILE;
import static com.progetto.animeuniverse.util.Constants.UNEXPECTED_ERROR;

import com.progetto.animeuniverse.model.GenresApiResponse;
import com.progetto.animeuniverse.util.JSONParserUtil;

import java.io.IOException;

public class GenresMockRemoteDataSource extends BaseGenresRemoteDataSource{
    private final JSONParserUtil jsonParserUtil;
    private final JSONParserUtil.JsonParserType jsonParserType;

    public GenresMockRemoteDataSource(JSONParserUtil jsonParserUtil, JSONParserUtil.JsonParserType jsonParserType) {
        this.jsonParserUtil = jsonParserUtil;
        this.jsonParserType = jsonParserType;
    }

    @Override
    public void getGenres() {
        GenresApiResponse genresApiResponse = null;
        switch (jsonParserType) {
            case GSON:
                try {
                    genresApiResponse = jsonParserUtil.parseJSONFileWithGSonGenres(GENRES_API_TEST_JSON_FILE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case JSON_ERROR:
                genresCallback.onFailureFromRemote(new Exception(UNEXPECTED_ERROR));
                break;
        }

        if (genresApiResponse != null) {
            genresCallback.onSuccessFromRemote(genresApiResponse, System.currentTimeMillis());
        } else {
            genresCallback.onFailureFromRemote(new Exception(API_KEY_ERROR));
        }
    }
}
