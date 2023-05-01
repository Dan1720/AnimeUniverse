package com.progetto.animeuniverse.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class AnimeImagesWebp implements Parcelable {
    @SerializedName("image_url")
    @ColumnInfo(name = "image_url_webp")
    private String imageUrlWebp;
    @SerializedName("small_image_url")
    @ColumnInfo(name = "small_image_url_webp")
    private String smallImageUrlWebp;
    @SerializedName("large_image_url")
    @ColumnInfo(name = "large_image_url_webp")
    private String largeImageUrlWebp;

    public AnimeImagesWebp(){}

    public AnimeImagesWebp(String imageUrlWebp, String smallImageUrlWebp, String largeImageUrlWebp) {
        this.imageUrlWebp = imageUrlWebp;
        this.smallImageUrlWebp = smallImageUrlWebp;
        this.largeImageUrlWebp = largeImageUrlWebp;
    }

    protected AnimeImagesWebp(Parcel in) {
        imageUrlWebp = in.readString();
        smallImageUrlWebp = in.readString();
        largeImageUrlWebp = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageUrlWebp);
        dest.writeString(smallImageUrlWebp);
        dest.writeString(largeImageUrlWebp);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AnimeImagesWebp> CREATOR = new Creator<AnimeImagesWebp>() {
        @Override
        public AnimeImagesWebp createFromParcel(Parcel in) {
            return new AnimeImagesWebp(in);
        }

        @Override
        public AnimeImagesWebp[] newArray(int size) {
            return new AnimeImagesWebp[size];
        }
    };

    public String getImageUrlWebp() {
        return imageUrlWebp;
    }

    public void setImageUrlWebp(String imageUrlWebp) {
        this.imageUrlWebp = imageUrlWebp;
    }

    public String getSmallImageUrlWebp() {
        return smallImageUrlWebp;
    }

    public void setSmallImageUrlWebp(String smallImageUrlWebp) {
        this.smallImageUrlWebp = smallImageUrlWebp;
    }

    public String getLargeImageUrlWebp() {
        return largeImageUrlWebp;
    }

    public void setLargeImageUrlWebp(String largeImageUrlWebp) {
        this.largeImageUrlWebp = largeImageUrlWebp;
    }

    @Override
    public String toString() {
        return "AnimeImagesWebp{" +
                "imageUrlWebp='" + imageUrlWebp + '\'' +
                ", smallImageUrlWebp='" + smallImageUrlWebp + '\'' +
                ", largeImageUrlWebp='" + largeImageUrlWebp + '\'' +
                '}';
    }


}
