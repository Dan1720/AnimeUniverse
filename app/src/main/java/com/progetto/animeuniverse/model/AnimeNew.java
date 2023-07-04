package com.progetto.animeuniverse.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


import com.google.gson.annotations.SerializedName;


@Entity(tableName = "anime_new")
public class AnimeNew implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @Embedded(prefix = "entry_")
    @SerializedName("entry")
    private AnimeEntry entry;

    @ColumnInfo(name = "is_synchronized")
    private boolean isSynchronized;

    public AnimeNew(int id, AnimeEntry entry, boolean isSynchronized) {
        this.id = id;
        this.entry = entry;
        this.isSynchronized = isSynchronized;
    }

    protected AnimeNew(Parcel in) {
        id = in.readInt();
        entry = in.readParcelable(AnimeEntry.class.getClassLoader());
        isSynchronized = in.readByte() != 0;
    }

    public static final Creator<AnimeNew> CREATOR = new Creator<AnimeNew>() {
        @Override
        public AnimeNew createFromParcel(Parcel in) {
            return new AnimeNew(in);
        }

        @Override
        public AnimeNew[] newArray(int size) {
            return new AnimeNew[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AnimeEntry getEntry() {
        return entry;
    }

    public void setEntry(AnimeEntry entry) {
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
        return "AnimeNew{" +
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
        dest.writeParcelable(entry, flags);
        dest.writeByte((byte) (isSynchronized ? 1 : 0));
    }
}
