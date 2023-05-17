package com.progetto.animeuniverse.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewsResponse implements Parcelable {

    private boolean isLoading;

    @SerializedName("data")
    private List<Review> reviewList;
    public ReviewsResponse(){}

    public ReviewsResponse(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    public ReviewsResponse(boolean isLoading) {
        this.isLoading = isLoading;
    }

    protected ReviewsResponse(Parcel in) {
        isLoading = in.readByte() != 0;
        reviewList = in.createTypedArrayList(Review.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isLoading ? 1 : 0));
        dest.writeTypedList(reviewList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ReviewsResponse> CREATOR = new Creator<ReviewsResponse>() {
        @Override
        public ReviewsResponse createFromParcel(Parcel in) {
            return new ReviewsResponse(in);
        }

        @Override
        public ReviewsResponse[] newArray(int size) {
            return new ReviewsResponse[size];
        }
    };

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    @Override
    public String toString() {
        return "ReviewsResponse{" +
                "isLoading=" + isLoading +
                ", reviewList=" + reviewList +
                '}';
    }
}
