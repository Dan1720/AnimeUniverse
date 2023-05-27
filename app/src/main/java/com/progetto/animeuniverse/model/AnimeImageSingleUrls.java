package com.progetto.animeuniverse.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class AnimeImageSingleUrls implements Parcelable {
    @SerializedName("image_url")
    private String imageUrl;

    public AnimeImageSingleUrls(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    protected AnimeImageSingleUrls(Parcel in) {
        imageUrl = in.readString();
    }

    public static final Creator<AnimeImageSingleUrls> CREATOR = new Creator<AnimeImageSingleUrls>() {
        @Override
        public AnimeImageSingleUrls createFromParcel(Parcel in) {
            return new AnimeImageSingleUrls(in);
        }

        @Override
        public AnimeImageSingleUrls[] newArray(int size) {
            return new AnimeImageSingleUrls[size];
        }
    };

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(imageUrl);
    }
}
