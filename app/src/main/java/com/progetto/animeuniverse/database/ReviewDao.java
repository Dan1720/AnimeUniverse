package com.progetto.animeuniverse.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.progetto.animeuniverse.model.Review;

import java.util.List;

@Dao
public interface ReviewDao {
    @Query("SELECT * FROM review")
    List<Review> getAllReviews();

    @Query("SELECT * FROM review WHERE idReview = :id")
    Review getReview(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertReviewsList(List<Review> reviewList);

    @Query("DELETE FROM review")
    int deleteAllReview();
}
