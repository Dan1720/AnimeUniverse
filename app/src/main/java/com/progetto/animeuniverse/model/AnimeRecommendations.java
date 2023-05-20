package com.progetto.animeuniverse.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;
import com.progetto.animeuniverse.util.Converter;

import java.util.List;

@Entity(tableName = "anime_reccomendations")
public class AnimeRecommendations implements Parcelable {
    @PrimaryKey()
    @SerializedName("mal_id")
    private int id;

    @TypeConverters(Converter.class)
    @SerializedName("entry")
    private List<AnimeEntry> entryList;

    public AnimeRecommendations(int id, List<AnimeEntry> entryList) {
        this.id = id;
        this.entryList = entryList;
    }

    protected AnimeRecommendations(Parcel in) {
        id = in.readInt();
        entryList = in.createTypedArrayList(AnimeEntry.CREATOR);
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

    public List<AnimeEntry> getEntryList() {
        return entryList;
    }

    public void setEntryList(List<AnimeEntry> entryList) {
        this.entryList = entryList;
    }

    @Override
    public String toString() {
        return "AnimeRecommendations{" +
                "id=" + id +
                ", entryList=" + entryList +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeTypedList(entryList);
    }
}
