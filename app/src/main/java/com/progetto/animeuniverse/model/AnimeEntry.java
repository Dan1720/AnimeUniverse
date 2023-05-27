package com.progetto.animeuniverse.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Embedded;

import com.google.gson.annotations.SerializedName;

public class AnimeEntry implements Parcelable {
    @SerializedName("mal_id")
    private int idAnime;

    @Embedded(prefix = "images_")
    @SerializedName("images")
    private AnimeImages images;

    @SerializedName("title")
    private String title;

    public AnimeEntry(int idAnime, AnimeImages images, String title) {
        this.idAnime = idAnime;
        this.images = images;
        this.title = title;
    }

    protected AnimeEntry(Parcel in) {
        idAnime = in.readInt();
        images = in.readParcelable(AnimeImages.class.getClassLoader());
        title = in.readString();
    }

    public static final Creator<AnimeEntry> CREATOR = new Creator<AnimeEntry>() {
        @Override
        public AnimeEntry createFromParcel(Parcel in) {
            return new AnimeEntry(in);
        }

        @Override
        public AnimeEntry[] newArray(int size) {
            return new AnimeEntry[size];
        }
    };

    public int getIdAnime() {
        return idAnime;
    }

    public void setIdAnime(int idAnime) {
        this.idAnime = idAnime;
    }

    public AnimeImages getImages() {
        return images;
    }

    public void setImages(AnimeImages images) {
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "AnimeEntry{" +
                "idAnime=" + idAnime +
                ", images=" + images +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(idAnime);
        dest.writeParcelable(images, flags);
        dest.writeString(title);
    }
}
