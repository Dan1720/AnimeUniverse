package com.progetto.animeuniverse.repository.reviews;

import static com.progetto.animeuniverse.util.Constants.FRESH_TIMEOUT;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.database.AnimeRoomDatabase;
import com.progetto.animeuniverse.database.ReviewDao;
import com.progetto.animeuniverse.model.Anime;
import com.progetto.animeuniverse.model.AnimeApiResponse;
import com.progetto.animeuniverse.model.Review;
import com.progetto.animeuniverse.model.ReviewsApiResponse;
import com.progetto.animeuniverse.service.AnimeApiService;
import com.progetto.animeuniverse.util.ServiceLocator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewsRepository implements IReviewsRepository{

    public static final String TAG = ReviewsRepository.class.getSimpleName();

    private final Application application;
    private final AnimeApiService animeApiService;
    private final ReviewDao reviewDao;
    private final ReviewsResponseCallback reviewsResponseCallback;

    public ReviewsRepository(Application application, ReviewsResponseCallback reviewsResponseCallback) {
        this.application = application;
        this.animeApiService = ServiceLocator.getInstance().getAnimeApiService();
        AnimeRoomDatabase animeRoomDatabase = ServiceLocator.getInstance().getReviewsDao(application);
        this.reviewDao = animeRoomDatabase.reviewDao();
        this.reviewsResponseCallback = reviewsResponseCallback;
    }

    @Override
    public void fetchReviewsById(int id, long lastUpdate) {
            Call<ReviewsApiResponse> reviewResponseCall = animeApiService.getReviewByIdAnime(id);

            reviewResponseCall.enqueue(new Callback<ReviewsApiResponse>() {
                @Override
                public void onResponse(Call<ReviewsApiResponse> call, Response<ReviewsApiResponse> response) {
                    if(response.isSuccessful()){
                        List<Review> reviewList = response.body().getReviewList();
                        saveDataInDatabase(reviewList);
                    }else{
                        reviewsResponseCallback.onFailure(application.getString(R.string.error_retrieving_anime));
                    }
                }

                @Override
                public void onFailure(Call<ReviewsApiResponse> call, @NonNull Throwable t) {
                    reviewsResponseCallback.onFailure(t.getMessage());
                }
            });

    }



    private void saveDataInDatabase(List<Review> reviewList){
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            List<Review> allReviews = reviewDao.getAllReviews();
            for(Review review : allReviews){
                if(reviewList.contains(review)){
                    reviewList.set(reviewList.indexOf(review), review);
                }
            }
            List<Long> insertedReviewIds = reviewDao.insertReviewsList(reviewList);
            for(int i=0; i<reviewList.size(); i++){
                reviewList.get(i).setIdReview(Math.toIntExact(insertedReviewIds.get(i)));
            }
            reviewsResponseCallback.onSuccess(reviewList, System.currentTimeMillis());
        });
    }

    private void readDataFromDatabase(long lastUpdate){
        AnimeRoomDatabase.databaseWriteExecutor.execute(() ->{
            reviewsResponseCallback.onSuccess(reviewDao.getAllReviews(), lastUpdate);
        });
    }
}
