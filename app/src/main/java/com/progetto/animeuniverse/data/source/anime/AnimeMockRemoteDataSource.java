package com.progetto.animeuniverse.data.source.anime;

import static com.progetto.animeuniverse.util.Constants.ANIME_API_TEST_JSON_FILE;
import static com.progetto.animeuniverse.util.Constants.API_KEY_ERROR;
import static com.progetto.animeuniverse.util.Constants.UNEXPECTED_ERROR;

import com.progetto.animeuniverse.model.AnimeApiResponse;
import com.progetto.animeuniverse.util.JSONParserUtil;

import java.io.IOException;

public class AnimeMockRemoteDataSource extends BaseAnimeRemoteDataSource{
    private final JSONParserUtil jsonParserUtil;
    private final JSONParserUtil.JsonParserType jsonParserType;

    public AnimeMockRemoteDataSource(JSONParserUtil jsonParserUtil, JSONParserUtil.JsonParserType jsonParserType) {
        this.jsonParserUtil = jsonParserUtil;
        this.jsonParserType = jsonParserType;
    }
    @Override
    public void getAnimeTop() {
        AnimeApiResponse animeApiResponse = null;
        switch (jsonParserType) {
            case GSON:
                try {
                    animeApiResponse = jsonParserUtil.parseJSONFileWithGSon(ANIME_API_TEST_JSON_FILE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case JSON_ERROR:
                animeCallback.onFailureFromRemote(new Exception(UNEXPECTED_ERROR));
                break;
        }

        if (animeApiResponse != null) {
            animeCallback.onSuccessFromRemote(animeApiResponse, System.currentTimeMillis());
        } else {
            animeCallback.onFailureFromRemote(new Exception(API_KEY_ERROR));
        }
    }

    @Override
    public void getAnimeByName(String nameAnime) {

    }

    @Override
    public void getAnimeByIdFull(String anime, int id) {

    }

    @Override
    public void getAnimeById(String anime, int id) {

    }


}
