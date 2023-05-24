package com.progetto.animeuniverse.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;
import com.progetto.animeuniverse.util.Converter;

import java.util.List;

@Entity(tableName = "anime_recommendations")
public class AnimeRecommendations implements Parcelable {
    @PrimaryKey()
    @SerializedName("mal_id")
    private int id;

    @TypeConverters(Converter.class)
    @SerializedName("entry")
    private List<AnimeEntry> entry;

    @ColumnInfo(name = "is_synchronized")
    private boolean isSynchronized;

    public AnimeRecommendations(int id, List<AnimeEntry> entry, boolean isSynchronized) {
        this.id = id;
        this.entry = entry;
        this.isSynchronized = isSynchronized;
    }

    protected AnimeRecommendations(Parcel in) {
        id = in.readInt();
        entry = in.createTypedArrayList(AnimeEntry.CREATOR);
        isSynchronized = in.readByte() != 0;
    }

    public static final Creator<AnimeRecommendations> CREATOR = new Creator<AnimeRecommendations>() {
        @Override
        public AnimeRecommendations createFromParcel(Parcel in) {
            return new AnimeRecommendations(in);
        }

        @Override
        public AnimeRecommendations[] newArray(int size) {
            return new AnimeRecommendations[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<AnimeEntry> getEntry() {
        return entry;
    }

    public void setEntry(List<AnimeEntry> entry) {
        this.entry = entry;
    }

    public boolean isSynchronized() {
        return isSynchronized;
    }

    public void setSynchronized(boolean aSynchronized) {
        isSynchronized = aSynchronized;
    }

    @Override
    public String toString() {
        return "AnimeRecommendations{" +
                "id=" + id +
                ", entry=" + entry +
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
        dest.writeTypedList(entry);
        dest.writeByte((byte) (isSynchronized ? 1 : 0));
    }
}
