package com.progetto.animeuniverse.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.lang.reflect.Array;
import java.util.List;

public class AnimeApiResponse implements Parcelable {
    private String status;
    private int totalResults;
    private List<Anime> anime;

    private UrlAnime[] data;

    public UrlAnime[] getData() {
        return data;
    }

    public void setData(UrlAnime[] data) {
        this.data = data;
    }

    public AnimeApiResponse() {}

    public AnimeApiResponse(String status, int totalResults, List<Anime> anime){
        this.status = status;
        this.totalResults = totalResults;
        this.anime = anime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<Anime> getAnime() {
        return anime;
    }

    public void setAnime(List<Anime> anime) {
        this.anime = anime;
    }

    @Override
    public String toString() {
        return "AnimeApiResponse{" +
                "status='" + status + '\'' +
                ", totalResults=" + totalResults +
                ", anime=" + anime +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.status);
        dest.writeInt(this.totalResults);
        dest.writeTypedList(this.anime);
    }
    public void readFromParcel(Parcel source) {
        this.status = source.readString();
        this.totalResults = source.readInt();
        this.anime = source.createTypedArrayList(Anime.CREATOR);
    }

    protected AnimeApiResponse(Parcel in) {
        this.status = in.readString();
        this.totalResults = in.readInt();
        this.anime = in.createTypedArrayList(Anime.CREATOR);
    }

    public static final Parcelable.Creator<AnimeApiResponse> CREATOR = new Parcelable.Creator<AnimeApiResponse>() {
        @Override
        public AnimeApiResponse createFromParcel(Parcel source) {
            return new AnimeApiResponse(source);
        }

        @Override
        public AnimeApiResponse[] newArray(int size) {
            return new AnimeApiResponse[size];
        }
    };
}
