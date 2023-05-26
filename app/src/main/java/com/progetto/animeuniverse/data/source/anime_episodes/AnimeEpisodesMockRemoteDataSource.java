package com.progetto.animeuniverse.data.source.anime_episodes;

import static com.progetto.animeuniverse.util.Constants.ANIMEEPISODES_API_TEST_JSON_FILE;
import static com.progetto.animeuniverse.util.Constants.API_KEY_ERROR;
import static com.progetto.animeuniverse.util.Constants.UNEXPECTED_ERROR;

import com.progetto.animeuniverse.model.AnimeEpisodesApiResponse;
import com.progetto.animeuniverse.util.JSONParserUtil;

import java.io.IOException;

public class AnimeEpisodesMockRemoteDataSource extends BaseAnimeEpisodesRemoteDataSource{
    private final JSONParserUtil jsonParserUtil;
    private final JSONParserUtil.JsonParserType jsonParserType;

    public AnimeEpisodesMockRemoteDataSource(JSONParserUtil jsonParserUtil, JSONParserUtil.JsonParserType jsonParserType) {
        this.jsonParserUtil = jsonParserUtil;
        this.jsonParserType = jsonParserType;
    }
    @Override
    public void getAnimeEpisodes(int idAnime) {
        AnimeEpisodesApiResponse animeEpisodesApiResponse = null;
        switch (jsonParserType) {
            case GSON:
                try {
                    animeEpisodesApiResponse = jsonParserUtil.parseJSONFileWithGSonAnimeEpisodes(ANIMEEPISODES_API_TEST_JSON_FILE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case JSON_ERROR:
                animeEpisodesCallback.onFailureFromRemote(new Exception(UNEXPECTED_ERROR));
                break;
        }

        if (animeEpisodesApiResponse != null) {
            animeEpisodesCallback.onSuccessFromRemote(animeEpisodesApiResponse, System.currentTimeMillis());
        } else {
            animeEpisodesCallback.onFailureFromRemote(new Exception(API_KEY_ERROR));
        }
    }
}
