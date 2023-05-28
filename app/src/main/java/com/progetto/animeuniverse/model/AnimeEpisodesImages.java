package com.progetto.animeuniverse.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "anime_episodes_images")
public class AnimeEpisodesImages implements Parcelable {
    @PrimaryKey()
    @SerializedName("mal_id")
    private int id;

    @Embedded(prefix = "images_")
    @SerializedName("images")
    private AnimeImageSingle images;

    @ColumnInfo(name = "is_synchronized")
    private boolean isSynchronized;

    public AnimeEpisodesImages(int id, AnimeImageSingle images, boolean isSynchronized) {
        this.id = id;
        this.images = images;
        this.isSynchronized = isSynchronized;
    }

    protected AnimeEpisodesImages(Parcel in) {
        id = in.readInt();
        images = in.readParcelable(AnimeImageSingle.class.getClassLoader());
        isSynchronized = in.readByte() != 0;
    }

    public static final Creator<AnimeEpisodesImages> CREATOR = new Creator<AnimeEpisodesImages>() {
        @Override
        public AnimeEpisodesImages createFromParcel(Parcel in) {
            return new AnimeEpisodesImages(in);
        }

        @Override
        public AnimeEpisodesImages[] newArray(int size) {
            return new AnimeEpisodesImages[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AnimeImageSingle getImages() {
        return images;
    }

    public void setImages(AnimeImageSingle images) {
        this.images = images;
    }

    public boolean isSynchronized() {
        return isSynchronized;
    }

    public void setSynchronized(boolean aSynchronized) {
        isSynchronized = aSynchronized;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeParcelable(images, flags);
        dest.writeByte((byte) (isSynchronized ? 1 : 0));
    }
}
