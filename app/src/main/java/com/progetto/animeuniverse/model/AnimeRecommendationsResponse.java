package com.progetto.animeuniverse.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnimeRecommendationsResponse implements Parcelable {
    private boolean isLoading;
    @SerializedName("data")
    private List<AnimeRecommendations> animeRecommendationsList;

    public AnimeRecommendationsResponse(){}

    public AnimeRecommendationsResponse(List<AnimeRecommendations> animeRecommendationsList){
        this.animeRecommendationsList = animeRecommendationsList;
    }

    public AnimeRecommendationsResponse(boolean isLoading) {
        this.isLoading = isLoading;
    }

    protected AnimeRecommendationsResponse(Parcel in) {
        isLoading = in.readByte() != 0;
        animeRecommendationsList = in.createTypedArrayList(AnimeRecommendations.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isLoading ? 1 : 0));
        dest.writeTypedList(animeRecommendationsList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AnimeRecommendationsResponse> CREATOR = new Creator<AnimeRecommendationsResponse>() {
        @Override
        public AnimeRecommendationsResponse createFromParcel(Parcel in) {
            return new AnimeRecommendationsResponse(in);
        }

        @Override
        public AnimeRecommendationsResponse[] newArray(int size) {
            return new AnimeRecommendationsResponse[size];
        }
    };

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public List<AnimeRecommendations> getAnimeRecommendationsList() {
        return animeRecommendationsList;
    }

    public void setAnimeRecommendationsList(List<AnimeRecommendations> animeRecommendationsList) {
        this.animeRecommendationsList = animeRecommendationsList;
    }

    @Override
    public String toString() {
        return "AnimeRecommendationsResponse{" +
                "isLoading=" + isLoading +
                ", animeRecommendationsList=" + animeRecommendationsList +
                '}';
    }
}
