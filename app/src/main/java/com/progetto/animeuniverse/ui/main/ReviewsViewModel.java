package com.progetto.animeuniverse.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.progetto.animeuniverse.model.Result;
import com.progetto.animeuniverse.repository.reviews.IReviewsRepositoryWithLiveData;

public class ReviewsViewModel extends ViewModel {
    private static final String TAG = ReviewsViewModel.class.getSimpleName();
    private final IReviewsRepositoryWithLiveData reviewsRepositoryWithLiveData;
    private int idAnime;
    private boolean isLoading;
    private boolean firstLoading;
    private int current_page;
    private int count;
    private MutableLiveData<com.progetto.animeuniverse.model.Result> reviewsListLiveData;
    private int currentResults;

    public ReviewsViewModel(IReviewsRepositoryWithLiveData iReviewsRepositoryWithLiveData){
        this.reviewsRepositoryWithLiveData = iReviewsRepositoryWithLiveData;
        this.firstLoading = true;
        this.current_page = 1;
        this.count = 0;
    }

    public MutableLiveData<Result> getReviewsByIdAnime(int idAnime, long lastUpdate){
        if(reviewsListLiveData == null){
            fetchReviewByIdAnime(idAnime, lastUpdate);
        }
        return reviewsListLiveData;
    }

    public void fetchReviewByIdAnime(int idAnime){
        reviewsRepositoryWithLiveData.fetchReviewById(idAnime);
    }
    public void fetchReviewByIdAnime(int idAnime, long lastUpdate){
        reviewsListLiveData = reviewsRepositoryWithLiveData.fetchReviewById(idAnime, lastUpdate);
    }

    public int getIdAnime() {
        return idAnime;
    }

    public void setIdAnime(int idAnime) {
        this.idAnime = idAnime;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public boolean isFirstLoading() {
        return firstLoading;
    }

    public void setFirstLoading(boolean firstLoading) {
        this.firstLoading = firstLoading;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCurrentResults() {
        return currentResults;
    }

    public void setCurrentResults(int currentResults) {
        this.currentResults = currentResults;
    }

    public MutableLiveData<Result> getReviewsListLiveData() {
        return reviewsListLiveData;
    }
}
