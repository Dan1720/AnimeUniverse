package com.progetto.animeuniverse.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;

public class AnimeImageUrls implements Parcelable {
    @SerializedName("image_url")
    private String imageUrl;

    @SerializedName("small_image_url")
    private String smallImageUrl;

    @SerializedName("large_image_url")
    private String largeImageUrl;

    public AnimeImageUrls(String imageUrl, String smallImageUrl, String largeImageUrl) {
        this.imageUrl = imageUrl;
        this.smallImageUrl = smallImageUrl;
        this.largeImageUrl = largeImageUrl;
    }

    protected AnimeImageUrls(Parcel in) {
        imageUrl = in.readString();
        smallImageUrl = in.readString();
        largeImageUrl = in.readString();
    }

    public static final Creator<AnimeImageUrls> CREATOR = new Creator<AnimeImageUrls>() {
        @Override
        public AnimeImageUrls createFromParcel(Parcel in) {
            return new AnimeImageUrls(in);
        }

        @Override
        public AnimeImageUrls[] newArray(int size) {
            return new AnimeImageUrls[size];
        }
    };

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSmallImageUrl() {
        return smallImageUrl;
    }

    public void setSmallImageUrl(String smallImageUrl) {
        this.smallImageUrl = smallImageUrl;
    }

    public String getLargeImageUrl() {
        return largeImageUrl;
    }

    public void setLargeImageUrl(String largeImageUrl) {
        this.largeImageUrl = largeImageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(imageUrl);
        dest.writeString(smallImageUrl);
        dest.writeString(largeImageUrl);
    }
}
