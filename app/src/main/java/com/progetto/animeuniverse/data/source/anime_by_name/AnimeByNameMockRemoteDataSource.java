package com.progetto.animeuniverse.data.source.anime_by_name;

import static com.progetto.animeuniverse.util.Constants.ANIMEBYNAME_API_TEST_JSON_FILE;
import static com.progetto.animeuniverse.util.Constants.API_KEY_ERROR;
import static com.progetto.animeuniverse.util.Constants.UNEXPECTED_ERROR;

import com.progetto.animeuniverse.model.AnimeByNameApiResponse;
import com.progetto.animeuniverse.util.JSONParserUtil;

import java.io.IOException;

public class AnimeByNameMockRemoteDataSource extends BaseAnimeByNameRemoteDataSource{

    private final JSONParserUtil jsonParserUtil;
    private final JSONParserUtil.JsonParserType jsonParserType;

    public AnimeByNameMockRemoteDataSource(JSONParserUtil jsonParserUtil, JSONParserUtil.JsonParserType jsonParserType) {
        this.jsonParserUtil = jsonParserUtil;
        this.jsonParserType = jsonParserType;
    }

    @Override
    public void getAnimeByName(String nameAnime) {
        AnimeByNameApiResponse animeByNameApiResponse = null;
        switch (jsonParserType) {
            case GSON:
                try {
                    animeByNameApiResponse = jsonParserUtil.parseJSONFileWithGSonAnimeByName(ANIMEBYNAME_API_TEST_JSON_FILE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case JSON_ERROR:
                animeByNameCallback.onFailureFromRemote(new Exception(UNEXPECTED_ERROR));
                break;
        }

        if (animeByNameApiResponse != null) {
            animeByNameCallback.onSuccessFromRemote(animeByNameApiResponse, System.currentTimeMillis());
        } else {
            animeByNameCallback.onFailureFromRemote(new Exception(API_KEY_ERROR));
        }
    }
}
