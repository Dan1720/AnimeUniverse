package com.progetto.animeuniverse.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnimeEpisodesImagesResponse implements Parcelable {
    private boolean isLoading;

    @SerializedName("data")
    private List<AnimeEpisodesImages> animeEpisodesImagesList;

    public AnimeEpisodesImagesResponse(){}

    public AnimeEpisodesImagesResponse(List<AnimeEpisodesImages> animeEpisodesImagesList) {
        this.animeEpisodesImagesList = animeEpisodesImagesList;
    }

    public AnimeEpisodesImagesResponse(boolean isLoading) {
        this.isLoading = isLoading;
    }

    protected AnimeEpisodesImagesResponse(Parcel in) {
        isLoading = in.readByte() != 0;
        animeEpisodesImagesList = in.createTypedArrayList(AnimeEpisodesImages.CREATOR);
    }

    public static final Creator<AnimeEpisodesImagesResponse> CREATOR = new Creator<AnimeEpisodesImagesResponse>() {
        @Override
        public AnimeEpisodesImagesResponse createFromParcel(Parcel in) {
            return new AnimeEpisodesImagesResponse(in);
        }

        @Override
        public AnimeEpisodesImagesResponse[] newArray(int size) {
            return new AnimeEpisodesImagesResponse[size];
        }
    };

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public List<AnimeEpisodesImages> getAnimeEpisodesImagesList() {
        return animeEpisodesImagesList;
    }

    public void setAnimeEpisodesImagesList(List<AnimeEpisodesImages> animeEpisodesImagesList) {
        this.animeEpisodesImagesList = animeEpisodesImagesList;
    }

    @Override
    public String toString() {
        return "AnimeEpisodesImagesResponse{" +
                "isLoading=" + isLoading +
                ", animeEpisodesImagesList=" + animeEpisodesImagesList +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeByte((byte) (isLoading ? 1 : 0));
        dest.writeTypedList(animeEpisodesImagesList);
    }
}
