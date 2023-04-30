package com.progetto.animeuniverse.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class AnimeImagesWebp implements Parcelable {
    @SerializedName("image_url")
    @ColumnInfo(name = "image_url")
    private String imageUrl;
    @SerializedName("small_image_url")
    @ColumnInfo(name = "small_image_url")
    private String smallImageUrl;
    @SerializedName("large_image_url")
    @ColumnInfo(name = "large_image_url")
    private String largeImageUrl;


    public AnimeImagesWebp(String imageUrl, String smallImageUrl, String largeImageUrl) {
        this.imageUrl = imageUrl;
        this.smallImageUrl = smallImageUrl;
        this.largeImageUrl = largeImageUrl;
    }

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
    public String toString() {
        return "AnimeImagesWebp{" +
                "imageUrl='" + imageUrl + '\'' +
                ", smallImageUrl='" + smallImageUrl + '\'' +
                ", largeImageUrl='" + largeImageUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimeImagesWebp that = (AnimeImagesWebp) o;
        return Objects.equals(imageUrl, that.imageUrl) && Objects.equals(smallImageUrl, that.smallImageUrl) && Objects.equals(largeImageUrl, that.largeImageUrl);
    }

    protected AnimeImagesWebp(Parcel in) {
        imageUrl = in.readString();
        smallImageUrl = in.readString();
        largeImageUrl = in.readString();
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
