package com.progetto.animeuniverse.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnimeNewResponse implements Parcelable {
    private boolean isLoading;
    @SerializedName("data")
    private List<AnimeNew> animeNewList;

    public AnimeNewResponse(){}

    public AnimeNewResponse(List<AnimeNew> animeNewList) {
        this.animeNewList = animeNewList;
    }

    public AnimeNewResponse(boolean isLoading) {
        this.isLoading = isLoading;
    }

    protected AnimeNewResponse(Parcel in) {
        isLoading = in.readByte() != 0;
        animeNewList = in.createTypedArrayList(AnimeNew.CREATOR);
    }

    public static final Creator<AnimeNewResponse> CREATOR = new Creator<AnimeNewResponse>() {
        @Override
        public AnimeNewResponse createFromParcel(Parcel in) {
            return new AnimeNewResponse(in);
        }

        @Override
        public AnimeNewResponse[] newArray(int size) {
            return new AnimeNewResponse[size];
        }
    };

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public List<AnimeNew> getAnimeNewList() {
        return animeNewList;
    }

    public void setAnimeNewList(List<AnimeNew> animeNewList) {
        this.animeNewList = animeNewList;
    }

    @Override
    public String toString() {
        return "AnimeNewResponse{" +
                "isLoading=" + isLoading +
                ", animeNewList=" + animeNewList +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeByte((byte) (isLoading ? 1 : 0));
        dest.writeTypedList(animeNewList);
    }
}
