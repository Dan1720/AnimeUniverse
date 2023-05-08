package com.progetto.animeuniverse.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Embedded;

public class AnimeImages implements Parcelable {
    @Embedded(prefix = "jpg_images")
    private AnimeImageUrls jpgImages;
    @Embedded(prefix = "webp_images")
    private AnimeImageUrls webpImages;

    public AnimeImages(AnimeImageUrls jpgImages, AnimeImageUrls webpImages) {
        this.jpgImages = jpgImages;
        this.webpImages = webpImages;
    }

    protected AnimeImages(Parcel in) {
        jpgImages = in.readParcelable(AnimeImageUrls.class.getClassLoader());
        webpImages = in.readParcelable(AnimeImageUrls.class.getClassLoader());
    }

    public static final Creator<AnimeImages> CREATOR = new Creator<AnimeImages>() {
        @Override
        public AnimeImages createFromParcel(Parcel in) {
            return new AnimeImages(in);
        }

        @Override
        public AnimeImages[] newArray(int size) {
            return new AnimeImages[size];
        }
    };

    public AnimeImageUrls getJpgImages() {
        return jpgImages;
    }

    public void setJpgImages(AnimeImageUrls jpgImages) {
        this.jpgImages = jpgImages;
    }

    public AnimeImageUrls getWebpImages() {
        return webpImages;
    }

    public void setWebpImages(AnimeImageUrls webpImages) {
        this.webpImages = webpImages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable(jpgImages, flags);
        dest.writeParcelable(webpImages, flags);
    }
}
