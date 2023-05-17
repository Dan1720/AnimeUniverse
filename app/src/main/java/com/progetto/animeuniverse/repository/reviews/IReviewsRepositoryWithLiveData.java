package com.progetto.animeuniverse.repository.reviews;

import androidx.lifecycle.MutableLiveData;

import com.progetto.animeuniverse.model.Result;

public interface IReviewsRepositoryWithLiveData {
    MutableLiveData<Result> fetchReviewById(int id, long lastUpdate);

    void fetchReviewById(int id);
}
