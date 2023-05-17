package com.progetto.animeuniverse.data.source.reviews;

import static com.progetto.animeuniverse.util.Constants.ENCRYPTED_DATA_FILE_NAME;
import static com.progetto.animeuniverse.util.Constants.ENCRYPTED_SHARED_PREFERENCES_FILE_NAME;
import static com.progetto.animeuniverse.util.Constants.LAST_UPDATE;
import static com.progetto.animeuniverse.util.Constants.SHARED_PREFERENCES_FILE_NAME;

import com.progetto.animeuniverse.database.AnimeRoomDatabase;
import com.progetto.animeuniverse.database.ReviewDao;
import com.progetto.animeuniverse.model.Review;
import com.progetto.animeuniverse.model.ReviewsApiResponse;
import com.progetto.animeuniverse.util.DataEncryptionUtil;
import com.progetto.animeuniverse.util.SharedPreferencesUtil;

import java.util.List;

public class ReviewsLocalDataSource extends BaseReviewsLocalDataSource{
    private final ReviewDao reviewDao;
    private final SharedPreferencesUtil sharedPreferencesUtil;
    private final DataEncryptionUtil dataEncryptionUtil;

    public ReviewsLocalDataSource(AnimeRoomDatabase animeRoomDatabase, SharedPreferencesUtil sharedPreferencesUtil, DataEncryptionUtil dataEncryptionUtil) {
        this.reviewDao = animeRoomDatabase.reviewDao();
        this.sharedPreferencesUtil = sharedPreferencesUtil;
        this.dataEncryptionUtil = dataEncryptionUtil;
    }

    @Override
    public void getReview() {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            ReviewsApiResponse reviewsApiResponse = new ReviewsApiResponse();
            reviewsApiResponse.setReviewList(reviewDao.getAllReviews());
            reviewsCallback.onSuccessFromLocal(reviewsApiResponse);
        });
    }

    @Override
    public void updateReview(Review review) {
        if(review == null){
            List<Review> allReviews = reviewDao.getAllReviews();
            for(Review r : allReviews){
                r.setSynchronized(false);
            }
        }
    }

    @Override
    public void insertReviews(ReviewsApiResponse reviewsApiResponse) {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            List<Review> allReviews = reviewDao.getAllReviews();
            List<Review> reviewList = reviewsApiResponse.getReviewList();
            if(reviewList != null){
                for(Review review : allReviews){
                    if(reviewList.contains(review)){
                        reviewList.set(reviewList.indexOf(review), review);
                    }
                }
                List<Long> insertedReviewsIds = reviewDao.insertReviewsList(reviewList);
                for(int i=0; i<reviewList.size(); i++){
                    reviewList.get(i).setIdReview(Math.toIntExact(insertedReviewsIds.get(i)));
                }
                sharedPreferencesUtil.writeStringData(SHARED_PREFERENCES_FILE_NAME, LAST_UPDATE, String.valueOf(System.currentTimeMillis()));
                reviewsCallback.onSuccessFromLocal(reviewsApiResponse);
            }
        });
    }

    @Override
    public void insertReviews(List<Review> reviewList) {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            if(reviewList != null){
                List<Review> allReviews = reviewDao.getAllReviews();
                for(Review review : allReviews){
                    if(reviewList.contains(review)){
                        review.setSynchronized(true);
                        reviewList.set(reviewList.indexOf(review), review);
                    }
                }
                List<Long> insertedReviewsIds = reviewDao.insertReviewsList(reviewList);
                for (int i=0; i<reviewList.size(); i++){
                    reviewList.get(i).setIdReview(Math.toIntExact(insertedReviewsIds.get(i)));
                }
                ReviewsApiResponse reviewsApiResponse = new ReviewsApiResponse();
                reviewsApiResponse.setReviewList(reviewList);
                reviewsCallback.onSuccessSynchronization();
            }
        });
    }

    @Override
    public void deleteAll() {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            int reviewsCounter = reviewDao.getAllReviews().size();
            int reviewsDeleteReview = reviewDao.deleteAllReview();
            if(reviewsCounter == reviewsDeleteReview){
                sharedPreferencesUtil.deleteAll(SHARED_PREFERENCES_FILE_NAME);
                dataEncryptionUtil.deleteAll(ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, ENCRYPTED_DATA_FILE_NAME);
                reviewsCallback.onSuccessDeletion();
            }
        });
    }
}
