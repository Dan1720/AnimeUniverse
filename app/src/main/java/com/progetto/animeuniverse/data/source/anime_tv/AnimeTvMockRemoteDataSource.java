package com.progetto.animeuniverse.data.source.anime_tv;


import static com.progetto.animeuniverse.util.Constants.ANIMETV_API_TEST_JSON_FILE;
import static com.progetto.animeuniverse.util.Constants.API_KEY_ERROR;
import static com.progetto.animeuniverse.util.Constants.UNEXPECTED_ERROR;

import com.progetto.animeuniverse.model.AnimeTvApiResponse;
import com.progetto.animeuniverse.util.JSONParserUtil;

import java.io.IOException;

public class AnimeTvMockRemoteDataSource extends BaseAnimeTvRemoteDataSource {
    private final JSONParserUtil jsonParserUtil;
    private final JSONParserUtil.JsonParserType jsonParserType;

    public AnimeTvMockRemoteDataSource(JSONParserUtil jsonParserUtil, JSONParserUtil.JsonParserType jsonParserType) {
        this.jsonParserUtil = jsonParserUtil;
        this.jsonParserType = jsonParserType;
    }

    @Override
    public void getAnimeTv() {
        AnimeTvApiResponse animeTvApiResponse = null;
        switch (jsonParserType) {
            case GSON:
                try {
                    animeTvApiResponse = jsonParserUtil.parseJSONFileWithGSonAnimeTv(ANIMETV_API_TEST_JSON_FILE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case JSON_ERROR:
                animeTvCallback.onFailureFromRemote(new Exception(UNEXPECTED_ERROR));
                break;
        }

        if (animeTvApiResponse != null) {
            animeTvCallback.onSuccessFromRemote(animeTvApiResponse, System.currentTimeMillis());
        } else {
            animeTvCallback.onFailureFromRemote(new Exception(API_KEY_ERROR));
        }
    }
}
