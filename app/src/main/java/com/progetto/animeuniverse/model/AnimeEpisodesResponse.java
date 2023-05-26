package com.progetto.animeuniverse.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnimeEpisodesResponse implements Parcelable {
    private boolean isLoading;

    @SerializedName("data")
    private List<AnimeEpisodes> animeEpisodesList;

    public AnimeEpisodesResponse(){}

    public AnimeEpisodesResponse(List<AnimeEpisodes> animeEpisodesList){
        this.animeEpisodesList = animeEpisodesList;
    }

    public AnimeEpisodesResponse(boolean isLoading) {
        this.isLoading = isLoading;
    }

    protected AnimeEpisodesResponse(Parcel in) {
        isLoading = in.readByte() != 0;
        animeEpisodesList = in.createTypedArrayList(AnimeEpisodes.CREATOR);
    }

    public static final Creator<AnimeEpisodesResponse> CREATOR = new Creator<AnimeEpisodesResponse>() {
        @Override
        public AnimeEpisodesResponse createFromParcel(Parcel in) {
            return new AnimeEpisodesResponse(in);
        }

        @Override
        public AnimeEpisodesResponse[] newArray(int size) {
            return new AnimeEpisodesResponse[size];
        }
    };

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public List<AnimeEpisodes> getAnimeEpisodesList() {
        return animeEpisodesList;
    }

    public void setAnimeEpisodesList(List<AnimeEpisodes> animeEpisodesList) {
        this.animeEpisodesList = animeEpisodesList;
    }

    @Override
    public String toString() {
        return "AnimeEpisodesResponse{" +
                "isLoading=" + isLoading +
                ", animeEpisodesList=" + animeEpisodesList +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeByte((byte) (isLoading ? 1 : 0));
        dest.writeTypedList(animeEpisodesList);
    }
}
