package com.progetto.animeuniverse.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Embedded;

import com.google.gson.annotations.SerializedName;

public class AnimeImageSingle implements Parcelable {
    @Embedded(prefix = "jpg_")
    @SerializedName("jpg")
    private AnimeImageSingleUrls jpgImages;

    public AnimeImageSingle(AnimeImageSingleUrls jpgImages) {
        this.jpgImages = jpgImages;
    }

    protected AnimeImageSingle(Parcel in) {
    }

    public static final Creator<AnimeImageSingle> CREATOR = new Creator<AnimeImageSingle>() {
        @Override
        public AnimeImageSingle createFromParcel(Parcel in) {
            return new AnimeImageSingle(in);
        }

        @Override
        public AnimeImageSingle[] newArray(int size) {
            return new AnimeImageSingle[size];
        }
    };

    public AnimeImageSingleUrls getJpgImages() {
        return jpgImages;
    }

    public void setJpgImages(AnimeImageSingleUrls jpgImages) {
        this.jpgImages = jpgImages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
    }
}
