package com.progetto.animeuniverse.data.source.anime_new;

import static com.progetto.animeuniverse.util.Constants.ANIMENEW_API_TEST_JSON_FILE;
import static com.progetto.animeuniverse.util.Constants.ANIMERECOMMENDATIONS_API_TEST_JSON_FILE;
import static com.progetto.animeuniverse.util.Constants.API_KEY_ERROR;
import static com.progetto.animeuniverse.util.Constants.UNEXPECTED_ERROR;

import com.progetto.animeuniverse.model.AnimeNewApiResponse;
import com.progetto.animeuniverse.model.AnimeRecommendationsApiResponse;
import com.progetto.animeuniverse.util.JSONParserUtil;

import java.io.IOException;

public class AnimeNewMockRemoteDataSource extends BaseAnimeNewRemoteDataSource{

    private final JSONParserUtil jsonParserUtil;
    private final JSONParserUtil.JsonParserType jsonParserType;

    public AnimeNewMockRemoteDataSource(JSONParserUtil jsonParserUtil, JSONParserUtil.JsonParserType jsonParserType) {
        this.jsonParserUtil = jsonParserUtil;
        this.jsonParserType = jsonParserType;
    }
    @Override
    public void getAnimeNew() {
        AnimeNewApiResponse animeNewApiResponse = null;
        switch (jsonParserType) {
            case GSON:
                try {
                    animeNewApiResponse = jsonParserUtil.parseJSONFileWithGSonAnimeNew(ANIMENEW_API_TEST_JSON_FILE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case JSON_ERROR:
                animeNewCallback.onFailureFromRemote(new Exception(UNEXPECTED_ERROR));
                break;
        }

        if (animeNewApiResponse != null) {
            animeNewCallback.onSuccessFromRemote(animeNewApiResponse, System.currentTimeMillis());
        } else {
            animeNewCallback.onFailureFromRemote(new Exception(API_KEY_ERROR));
        }
    }
}
