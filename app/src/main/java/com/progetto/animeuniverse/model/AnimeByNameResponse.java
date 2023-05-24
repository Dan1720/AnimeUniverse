package com.progetto.animeuniverse.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnimeByNameResponse implements Parcelable {
    private boolean isLoading;

    @SerializedName("data")
    private List<AnimeByName> animeByNameList;

    public AnimeByNameResponse(){}
    public AnimeByNameResponse(List<AnimeByName> animeByNameList){
        this.animeByNameList = animeByNameList;
    }

    public AnimeByNameResponse(boolean isLoading) {
        this.isLoading = isLoading;
    }

    protected AnimeByNameResponse(Parcel in) {
        isLoading = in.readByte() != 0;
        animeByNameList = in.createTypedArrayList(AnimeByName.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isLoading ? 1 : 0));
        dest.writeTypedList(animeByNameList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AnimeByNameResponse> CREATOR = new Creator<AnimeByNameResponse>() {
        @Override
        public AnimeByNameResponse createFromParcel(Parcel in) {
            return new AnimeByNameResponse(in);
        }

        @Override
        public AnimeByNameResponse[] newArray(int size) {
            return new AnimeByNameResponse[size];
        }
    };

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public List<AnimeByName> getAnimeByNameList() {
        return animeByNameList;
    }

    public void setAnimeByNameList(List<AnimeByName> animeByNameList) {
        this.animeByNameList = animeByNameList;
    }

    @Override
    public String toString() {
        return "AnimeByNameResponse{" +
                "isLoading=" + isLoading +
                ", animeByNameList=" + animeByNameList +
                '}' + super.toString();
    }
}
