package com.progetto.animeuniverse.data.source.anime_recommendations;

import static com.progetto.animeuniverse.util.Constants.ANIMERECOMMENDATIONS_API_TEST_JSON_FILE;
import static com.progetto.animeuniverse.util.Constants.API_KEY_ERROR;
import static com.progetto.animeuniverse.util.Constants.UNEXPECTED_ERROR;

import com.progetto.animeuniverse.model.AnimeRecommendationsApiResponse;
import com.progetto.animeuniverse.util.JSONParserUtil;

import java.io.IOException;

public class AnimeRecommendationsMockRemoteDataSource extends BaseAnimeRecommendationsRemoteDataSource{
    private final JSONParserUtil jsonParserUtil;
    private final JSONParserUtil.JsonParserType jsonParserType;

    public AnimeRecommendationsMockRemoteDataSource(JSONParserUtil jsonParserUtil, JSONParserUtil.JsonParserType jsonParserType) {
        this.jsonParserUtil = jsonParserUtil;
        this.jsonParserType = jsonParserType;
    }

    @Override
    public void getAnimeRecommendations() {
        AnimeRecommendationsApiResponse animeRecommendationsApiResponse = null;
        switch (jsonParserType) {
            case GSON:
                try {
                    animeRecommendationsApiResponse = jsonParserUtil.parseJSONFileWithGSonAnimeRecommendations(ANIMERECOMMENDATIONS_API_TEST_JSON_FILE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case JSON_ERROR:
                animeRecommendationsCallback.onFailureFromRemote(new Exception(UNEXPECTED_ERROR));
                break;
        }

        if (animeRecommendationsApiResponse != null) {
            animeRecommendationsCallback.onSuccessFromRemote(animeRecommendationsApiResponse, System.currentTimeMillis());
        } else {
            animeRecommendationsCallback.onFailureFromRemote(new Exception(API_KEY_ERROR));
        }
    }
}
