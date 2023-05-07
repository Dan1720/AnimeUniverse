package com.progetto.animeuniverse.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnimeResponse implements Parcelable {

    private boolean isLoading;

    @SerializedName("data")
    private List<Anime> animeList;
    public AnimeResponse(){}

    public AnimeResponse(List<Anime> animeList) {
        this.animeList = animeList;
    }

    public AnimeResponse(boolean isLoading) {
        this.isLoading = isLoading;
    }

    protected AnimeResponse(Parcel in) {
        isLoading = in.readByte() != 0;
        animeList = in.createTypedArrayList(Anime.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isLoading ? 1 : 0));
        dest.writeTypedList(animeList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AnimeResponse> CREATOR = new Creator<AnimeResponse>() {
        @Override
        public AnimeResponse createFromParcel(Parcel in) {
            return new AnimeResponse(in);
        }

        @Override
        public AnimeResponse[] newArray(int size) {
            return new AnimeResponse[size];
        }
    };

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public List<Anime> getAnimeList() {
        return animeList;
    }

    public void setAnimeList(List<Anime> animeList) {
        this.animeList = animeList;
    }

    @Override
    public String toString() {
        return "AnimeResponse{" +
                "isLoading=" + isLoading +
                ", animeList=" + animeList +
                '}' + super.toString();
    }
}
