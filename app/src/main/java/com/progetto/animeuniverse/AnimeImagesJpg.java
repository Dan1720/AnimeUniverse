package com.progetto.animeuniverse;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

import java.util.Objects;

public class AnimeImagesJpg implements Parcelable {

    @ColumnInfo(name = "image_url")
    private String imageUrl;
    @ColumnInfo(name = "small_image_url")
    private String smallImageUrl;
    @ColumnInfo(name = "large_image_url")
    private String largeImageUrl;

    public AnimeImagesJpg(String imageUrl, String smallImageUrl, String largeImageUrl) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimeImagesJpg that = (AnimeImagesJpg) o;
        return Objects.equals(imageUrl, that.imageUrl) && Objects.equals(smallImageUrl, that.smallImageUrl) && Objects.equals(largeImageUrl, that.largeImageUrl);
    }


    @Override
    public String toString() {
        return "AnimeImagesJpg{" +
                "imageUrl='" + imageUrl + '\'' +
                ", smallImageUrl='" + smallImageUrl + '\'' +
                ", largeImageUrl='" + largeImageUrl + '\'' +
                '}';
    }

    protected AnimeImagesJpg(Parcel in) {
        imageUrl = in.readString();
        smallImageUrl = in.readString();
        largeImageUrl = in.readString();
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