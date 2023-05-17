package com.progetto.animeuniverse.data.source.reviews;

import com.progetto.animeuniverse.model.Review;
import com.progetto.animeuniverse.model.ReviewsApiResponse;

import java.util.List;

public abstract class BaseReviewsLocalDataSource {
    protected ReviewsCallback reviewsCallback;

    public void setReviewsCallback(ReviewsCallback reviewsCallback){
        this.reviewsCallback = reviewsCallback;
    }

    public abstract void getReview();
    public abstract void updateReview(Review review);
    public abstract void insertReviews(ReviewsApiResponse reviewsApiResponse);
    public abstract void insertReviews(List<Review> reviewList);
    public abstract void deleteAll();
}
