package com.progetto.animeuniverse.data.source.reviews;

import static com.progetto.animeuniverse.util.Constants.ANIME_API_TEST_JSON_FILE;
import static com.progetto.animeuniverse.util.Constants.API_KEY_ERROR;
import static com.progetto.animeuniverse.util.Constants.REVIEWS_API_TEST_JSON_FILE;
import static com.progetto.animeuniverse.util.Constants.UNEXPECTED_ERROR;

import com.progetto.animeuniverse.model.ReviewsApiResponse;
import com.progetto.animeuniverse.util.JSONParserUtil;

import java.io.IOException;

public class ReviewsMockRemoteDataSource extends BaseReviewsRemoteDataSource{
    private final JSONParserUtil jsonParserUtil;
    private final JSONParserUtil.JsonParserType jsonParserType;

    public ReviewsMockRemoteDataSource(JSONParserUtil jsonParserUtil, JSONParserUtil.JsonParserType jsonParserType) {
        this.jsonParserUtil = jsonParserUtil;
        this.jsonParserType = jsonParserType;
    }

    @Override
    public void getReviewById(int id) {
        ReviewsApiResponse reviewsApiResponse = null;
        switch (jsonParserType) {
            case GSON:
                try {
                    reviewsApiResponse = jsonParserUtil.parseJSONFileWithGSonReviews(REVIEWS_API_TEST_JSON_FILE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case JSON_ERROR:
                reviewsCallback.onFailureFromRemote(new Exception(UNEXPECTED_ERROR));
                break;
        }

        if (reviewsApiResponse != null) {
            reviewsCallback.onSuccessFromRemote(reviewsApiResponse, System.currentTimeMillis());
        } else {
            reviewsCallback.onFailureFromRemote(new Exception(API_KEY_ERROR));
        }
    }
}
