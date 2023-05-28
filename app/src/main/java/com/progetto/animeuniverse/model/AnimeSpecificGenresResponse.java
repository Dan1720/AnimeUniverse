package com.progetto.animeuniverse.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnimeSpecificGenresResponse implements Parcelable {
    private boolean isLoading;

    @SerializedName("data")
    private List<AnimeSpecificGenres> animeSpecificGenresList;

    public AnimeSpecificGenresResponse(){}
    public AnimeSpecificGenresResponse(List<AnimeSpecificGenres> animeSpecificGenresList){
        this.animeSpecificGenresList = animeSpecificGenresList;
    }

    public AnimeSpecificGenresResponse(boolean isLoading) {
        this.isLoading = isLoading;
    }

    protected AnimeSpecificGenresResponse(Parcel in) {
        isLoading = in.readByte() != 0;
        animeSpecificGenresList = in.createTypedArrayList(AnimeSpecificGenres.CREATOR);
    }

    public static final Creator<AnimeSpecificGenresResponse> CREATOR = new Creator<AnimeSpecificGenresResponse>() {
        @Override
        public AnimeSpecificGenresResponse createFromParcel(Parcel in) {
            return new AnimeSpecificGenresResponse(in);
        }

        @Override
        public AnimeSpecificGenresResponse[] newArray(int size) {
            return new AnimeSpecificGenresResponse[size];
        }
    };

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public List<AnimeSpecificGenres> getAnimeSpecificGenresList() {
        return animeSpecificGenresList;
    }

    public void setAnimeSpecificGenresList(List<AnimeSpecificGenres> animeSpecificGenresList) {
        this.animeSpecificGenresList = animeSpecificGenresList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeByte((byte) (isLoading ? 1 : 0));
        dest.writeTypedList(animeSpecificGenresList);
    }
}
