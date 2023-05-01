package com.progetto.animeuniverse.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class AnimeImagesJpg implements Parcelable {
    @SerializedName("image_url")
    @ColumnInfo(name = "image_url_jpg")
    private String imageUrlJpg;
    @SerializedName("small_image_url")
    @ColumnInfo(name = "small_image_url_jpg")
    private String smallImageUrlJpg;
    @SerializedName("large_image_url")
    @ColumnInfo(name = "large_image_url_jpg")
    private String largeImageUrlJpg;

    public AnimeImagesJpg(){}
    public AnimeImagesJpg(String imageUrlJpg, String smallImageUrlJpg, String largeImageUrlJpg) {
        this.imageUrlJpg = imageUrlJpg;
        this.smallImageUrlJpg = smallImageUrlJpg;
        this.largeImageUrlJpg = largeImageUrlJpg;
    }

    protected AnimeImagesJpg(Parcel in) {
        imageUrlJpg = in.readString();
        smallImageUrlJpg = in.readString();
        largeImageUrlJpg = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageUrlJpg);
        dest.writeString(smallImageUrlJpg);
        dest.writeString(largeImageUrlJpg);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AnimeImagesJpg> CREATOR = new Creator<AnimeImagesJpg>() {
        @Override
        public AnimeImagesJpg createFromParcel(Parcel in) {
            return new AnimeImagesJpg(in);
        }

        @Override
        public AnimeImagesJpg[] newArray(int size) {
            return new AnimeImagesJpg[size];
        }
    };

    public String getImageUrlJpg() {
        return imageUrlJpg;
    }

    public void setImageUrlJpg(String imageUrlJpg) {
        this.imageUrlJpg = imageUrlJpg;
    }

    public String getSmallImageUrlJpg() {
        return smallImageUrlJpg;
    }

    public void setSmallImageUrlJpg(String smallImageUrlJpg) {
        this.smallImageUrlJpg = smallImageUrlJpg;
    }

    public String getLargeImageUrlJpg() {
        return largeImageUrlJpg;
    }

    public void setLargeImageUrlJpg(String largeImageUrlJpg) {
        this.largeImageUrlJpg = largeImageUrlJpg;
    }

    @Override
    public String toString() {
        return "AnimeImagesJpg{" +
                "imageUrlJpg='" + imageUrlJpg + '\'' +
                ", smallImageUrlJpg='" + smallImageUrlJpg + '\'' +
                ", largeImageUrlJpg='" + largeImageUrlJpg + '\'' +
                '}';
    }

}
