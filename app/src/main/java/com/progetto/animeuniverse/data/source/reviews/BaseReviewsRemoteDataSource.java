package com.progetto.animeuniverse.data.source.reviews;

public abstract class BaseReviewsRemoteDataSource {
    protected ReviewsCallback reviewsCallback;
    public void setReviewsCallback(ReviewsCallback reviewsCallback){
        this.reviewsCallback = reviewsCallback;
    }
    public abstract void getReviewById(int id);
}
