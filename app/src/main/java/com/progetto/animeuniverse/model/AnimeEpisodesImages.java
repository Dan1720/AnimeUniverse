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
    private AnimeImages images;

    @ColumnInfo(name = "is_synchronized")
    private boolean isSynchronized;

    public AnimeEpisodesImages(int id, AnimeImages images, boolean isSynchronized) {
        this.id = id;
        this.images = images;
        this.isSynchronized = isSynchronized;
    }

    protected AnimeEpisodesImages(Parcel in) {
        id = in.readInt();
        images = in.readParcelable(AnimeImages.class.getClassLoader());
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

    public AnimeImages getImages() {
        return images;
    }

    public void setImages(AnimeImages images) {
        this.images = images;
    }

    public boolean isSynchronized() {
        return isSynchronized;
    }

    public void setSynchronized(boolean aSynchronized) {
        isSynchronized = aSynchronized;
    }

    @Override
    public String toString() {
        return "AnimeEpisodesImages{" +
                "id=" + id +
                ", images=" + images +
                ", isSynchronized=" + isSynchronized +
                '}';
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
