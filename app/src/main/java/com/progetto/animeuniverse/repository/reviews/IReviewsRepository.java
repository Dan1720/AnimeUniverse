package com.progetto.animeuniverse.repository.reviews;

import com.progetto.animeuniverse.model.Review;

public interface IReviewsRepository {

    void fetchReviewsById(int id, long lastUpdate);

}
