package com.progetto.animeuniverse.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "review")
public class Review implements Parcelable {

    @PrimaryKey()
    @SerializedName("mal_id")
    private int idReview;

    private String review;
    private int score;

    @ColumnInfo(name = "is_synchronized")
    private boolean isSynchronized;

    public Review(int idReview, String review, int score, boolean isSynchronized) {
        this.idReview = idReview;
        this.review = review;
        this.score = score;
        this.isSynchronized = isSynchronized;
    }

    protected Review(Parcel in) {
        idReview = in.readInt();
        review = in.readString();
        score = in.readInt();
        isSynchronized = in.readByte() != 0;
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public int getIdReview() {
        return idReview;
    }

    public void setIdReview(int idReview) {
        this.idReview = idReview;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isSynchronized() {
        return isSynchronized;
    }

    public void setSynchronized(boolean aSynchronized) {
        isSynchronized = aSynchronized;
    }

    @Override
    public String toString() {
        return "Review{" +
                "idReview=" + idReview +
                ", review='" + review + '\'' +
                ", score=" + score +
                ", isSynchronized=" + isSynchronized +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(idReview);
        dest.writeString(review);
        dest.writeInt(score);
        dest.writeByte((byte) (isSynchronized ? 1 : 0));
    }
}
