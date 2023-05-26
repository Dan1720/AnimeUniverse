package com.progetto.animeuniverse.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "anime_episodes")
public class AnimeEpisodes implements Parcelable {
    @PrimaryKey()
    @SerializedName("mal_id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("duration")
    private int duration;

    @SerializedName("filler")
    private boolean filler;

    @ColumnInfo(name = "is_synchronized")
    private boolean isSynchronized;

    public AnimeEpisodes(int id, String title, int duration, boolean filler, boolean isSynchronized) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.filler = filler;
        this.isSynchronized = isSynchronized;
    }

    protected AnimeEpisodes(Parcel in) {
        id = in.readInt();
        title = in.readString();
        duration = in.readInt();
        filler = in.readByte() != 0;
        isSynchronized = in.readByte() != 0;
    }

    public static final Creator<AnimeEpisodes> CREATOR = new Creator<AnimeEpisodes>() {
        @Override
        public AnimeEpisodes createFromParcel(Parcel in) {
            return new AnimeEpisodes(in);
        }

        @Override
        public AnimeEpisodes[] newArray(int size) {
            return new AnimeEpisodes[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isFiller() {
        return filler;
    }

    public void setFiller(boolean filler) {
        this.filler = filler;
    }

    public boolean isSynchronized() {
        return isSynchronized;
    }

    public void setSynchronized(boolean aSynchronized) {
        isSynchronized = aSynchronized;
    }

    @Override
    public String toString() {
        return "AnimeEpisodes{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                ", filler=" + filler +
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
        dest.writeString(title);
        dest.writeInt(duration);
        dest.writeByte((byte) (filler ? 1 : 0));
        dest.writeByte((byte) (isSynchronized ? 1 : 0));
    }
}
