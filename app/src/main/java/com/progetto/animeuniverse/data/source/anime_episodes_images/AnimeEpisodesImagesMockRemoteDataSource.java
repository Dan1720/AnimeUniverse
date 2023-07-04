package com.progetto.animeuniverse.data.source.anime_episodes_images;

import static com.progetto.animeuniverse.util.Constants.ANIMEEPISODESIMAGES_API_TEST_JSON_FILE;
import static com.progetto.animeuniverse.util.Constants.API_KEY_ERROR;
import static com.progetto.animeuniverse.util.Constants.UNEXPECTED_ERROR;

import com.progetto.animeuniverse.model.AnimeEpisodesImagesApiResponse;
import com.progetto.animeuniverse.util.JSONParserUtil;

import java.io.IOException;

public class AnimeEpisodesImagesMockRemoteDataSource extends BaseAnimeEpisodesImagesRemoteDataSource{
    private final JSONParserUtil jsonParserUtil;
    private final JSONParserUtil.JsonParserType jsonParserType;

    public AnimeEpisodesImagesMockRemoteDataSource(JSONParserUtil jsonParserUtil, JSONParserUtil.JsonParserType jsonParserType) {
        this.jsonParserUtil = jsonParserUtil;
        this.jsonParserType = jsonParserType;
    }
    @Override
    public void getAnimeEpisodesImages(int idAnime) {
        AnimeEpisodesImagesApiResponse animeEpisodesImagesApiResponse = null;
        switch (jsonParserType) {
            case GSON:
                try {
                    animeEpisodesImagesApiResponse = jsonParserUtil.parseJSONFileWithGSonAnimeEpisodesImages(ANIMEEPISODESIMAGES_API_TEST_JSON_FILE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case JSON_ERROR:
                animeEpisodesImagesCallback.onFailureFromRemote(new Exception(UNEXPECTED_ERROR));
                break;
        }

        if (animeEpisodesImagesApiResponse != null) {
            animeEpisodesImagesCallback.onSuccessFromRemote(animeEpisodesImagesApiResponse, System.currentTimeMillis());
        } else {
            animeEpisodesImagesCallback.onFailureFromRemote(new Exception(API_KEY_ERROR));
        }
    }
}
