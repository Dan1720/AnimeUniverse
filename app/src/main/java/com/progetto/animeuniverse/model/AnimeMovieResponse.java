package com.progetto.animeuniverse.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnimeMovieResponse implements Parcelable {
    private boolean isLoading;
    @SerializedName("data")
    private List<AnimeMovie> animeMovieList;

    public AnimeMovieResponse(){}

    public AnimeMovieResponse(List<AnimeMovie> animeMovieList){
        this.animeMovieList = animeMovieList;
    }

    public AnimeMovieResponse(boolean isLoading) {
        this.isLoading = isLoading;
    }

    protected AnimeMovieResponse(Parcel in) {
        isLoading = in.readByte() != 0;
        animeMovieList = in.createTypedArrayList(AnimeMovie.CREATOR);
    }

    public static final Creator<AnimeMovieResponse> CREATOR = new Creator<AnimeMovieResponse>() {
        @Override
        public AnimeMovieResponse createFromParcel(Parcel in) {
            return new AnimeMovieResponse(in);
        }

        @Override
        public AnimeMovieResponse[] newArray(int size) {
            return new AnimeMovieResponse[size];
        }
    };

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public List<AnimeMovie> getAnimeMovieList() {
        return animeMovieList;
    }

    public void setAnimeMovieList(List<AnimeMovie> animeMovieList) {
        this.animeMovieList = animeMovieList;
    }

    @Override
    public String toString() {
        return "AnimeMovieResponse{" +
                "isLoading=" + isLoading +
                ", animeMovieList=" + animeMovieList +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeByte((byte) (isLoading ? 1 : 0));
        dest.writeTypedList(animeMovieList);
    }
}
