package com.progetto.animeuniverse.repository.reviews;

import com.progetto.animeuniverse.model.Review;

import java.util.List;

public interface ReviewsResponseCallback {
    void onSuccess(List<Review> reviewList, long lastUpdate);
    void onFailure(String errorMessage);
}
