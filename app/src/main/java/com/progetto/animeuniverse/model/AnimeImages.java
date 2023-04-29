package com.progetto.animeuniverse.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Embedded;

public class AnimeImages implements Parcelable{
    @Embedded
    private AnimeImagesJpg jpg;
    @Embedded
    private AnimeImagesWebp webp;


    public AnimeImages(AnimeImagesJpg jpg, AnimeImagesWebp webp) {
        this.jpg = jpg;
        this.webp = webp;
    }

    protected AnimeImages(Parcel in) {
        jpg = in.readParcelable(AnimeImagesJpg.class.getClassLoader());
        webp = in.readParcelable(AnimeImagesWebp.class.getClassLoader());
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

    public AnimeImagesJpg getJpg() {
        return jpg;
    }

    public void setJpg(AnimeImagesJpg jpg) {
        this.jpg = jpg;
    }

    public AnimeImagesWebp getWebp() {
        return webp;
    }

    public void setWebp(AnimeImagesWebp webp) {
        this.webp = webp;
    }

    @Override
    public String toString() {
        return "AnimeImages{" +
                "jpg=" + jpg +
                ", webp=" + webp +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable(jpg, flags);
        dest.writeParcelable(webp, flags);
    }
}
