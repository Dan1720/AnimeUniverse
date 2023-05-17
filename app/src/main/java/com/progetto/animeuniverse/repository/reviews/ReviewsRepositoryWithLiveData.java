package com.progetto.animeuniverse.repository.reviews;

import static com.progetto.animeuniverse.util.Constants.FRESH_TIMEOUT;

import androidx.lifecycle.MutableLiveData;

import com.progetto.animeuniverse.data.source.reviews.BaseReviewsLocalDataSource;
import com.progetto.animeuniverse.data.source.reviews.BaseReviewsRemoteDataSource;
import com.progetto.animeuniverse.data.source.reviews.ReviewsCallback;
import com.progetto.animeuniverse.model.Result;
import com.progetto.animeuniverse.model.Review;
import com.progetto.animeuniverse.model.ReviewsApiResponse;

import java.util.List;

public class ReviewsRepositoryWithLiveData implements IReviewsRepositoryWithLiveData, ReviewsCallback {

    private static final String TAG = ReviewsRepositoryWithLiveData.class.getSimpleName();

    private final MutableLiveData<Result> allReviewsMutableLiveData;
    private final BaseReviewsRemoteDataSource reviewsRemoteDataSource;
    private final BaseReviewsLocalDataSource reviewsLocalDataSource;

    public ReviewsRepositoryWithLiveData(BaseReviewsRemoteDataSource reviewsRemoteDataSource, BaseReviewsLocalDataSource reviewsLocalDataSource) {
        allReviewsMutableLiveData = new MutableLiveData<>();
        this.reviewsRemoteDataSource = reviewsRemoteDataSource;
        this.reviewsLocalDataSource = reviewsLocalDataSource;
        this.reviewsLocalDataSource.setReviewsCallback(this);
        this.reviewsRemoteDataSource.setReviewsCallback(this);
    }

    @Override
    public MutableLiveData<Result> fetchReviewById(int id, long lastUpdate) {

        long currentTime = System.currentTimeMillis();
        if(currentTime - lastUpdate > FRESH_TIMEOUT){
            reviewsRemoteDataSource.getReviewById(id);
        }else{
            reviewsLocalDataSource.getReview();
        }
        return allReviewsMutableLiveData;
    }

    @Override
    public void fetchReviewById(int id) {
        reviewsRemoteDataSource.getReviewById(id);
    }

    @Override
    public void onSuccessFromRemote(ReviewsApiResponse reviewsApiResponse, long lastUpdate) {
        reviewsLocalDataSource.insertReviews(reviewsApiResponse);
    }

    @Override
    public void onFailureFromRemote(Exception exception) {
        Result.Error result = new Result.Error(exception.getMessage());
        allReviewsMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessFromLocal(ReviewsApiResponse reviewsApiResponse) {
        if(allReviewsMutableLiveData.getValue() != null && allReviewsMutableLiveData.getValue().isSuccess()){
            List<Review> reviewList = ((Result.ReviewsResponseSuccess) allReviewsMutableLiveData.getValue()).getData().getReviewList();
            reviewList.addAll(reviewsApiResponse.getReviewList());
            reviewsApiResponse.setReviewList(reviewList);
            Result.ReviewsResponseSuccess result = new Result.ReviewsResponseSuccess(reviewsApiResponse);
            allReviewsMutableLiveData.postValue(result);
        }else{
            Result.ReviewsResponseSuccess result = new Result.ReviewsResponseSuccess(reviewsApiResponse);
            allReviewsMutableLiveData.postValue(result);
        }
    }

    @Override
    public void onFailureFromLocal(Exception exception) {
        Result.Error resultError = new Result.Error(exception.getMessage());
        allReviewsMutableLiveData.postValue(resultError);
    }

    @Override
    public void onSuccessFromCloudReading(List<Review> reviewList) {
        if(reviewList != null){
            for(Review review : reviewList){
                review.setSynchronized(true);
            }
            reviewsLocalDataSource.insertReviews(reviewList);
        }
    }

    @Override
    public void onSuccessFromCloudWriting(Review review) {

    }

    @Override
    public void onFailureFromCloud(Exception exception) {

    }

    @Override
    public void onSuccessSynchronization() {

    }

    @Override
    public void onSuccessDeletion() {

    }
}
