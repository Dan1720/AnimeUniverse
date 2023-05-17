package com.progetto.animeuniverse.data.source.reviews;


import com.progetto.animeuniverse.model.Review;
import com.progetto.animeuniverse.model.ReviewsApiResponse;


import java.util.List;

public interface ReviewsCallback {
    void onSuccessFromRemote(ReviewsApiResponse reviewsApiResponse, long lastUpdate);
    void onFailureFromRemote(Exception exception);
    void onSuccessFromLocal(ReviewsApiResponse reviewsApiResponse);
    void onFailureFromLocal(Exception exception);
    void onSuccessFromCloudReading(List<Review> reviewList);
    void onSuccessFromCloudWriting(Review review);
    void onFailureFromCloud(Exception exception);
    void onSuccessSynchronization();
    void onSuccessDeletion();
}
