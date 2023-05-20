package com.progetto.animeuniverse.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenresResponse implements Parcelable {
    private boolean isLoading;
    @SerializedName("data")
    private List<Genre> gernesList;
    public GenresResponse(){}

    public GenresResponse(List<Genre> gernesList) {
        this.gernesList = gernesList;
    }

    public GenresResponse(boolean isLoading) {
        this.isLoading = isLoading;
    }

    protected GenresResponse(Parcel in) {
        isLoading = in.readByte() != 0;
        gernesList = in.createTypedArrayList(Genre.CREATOR);
    }

    public static final Creator<GenresResponse> CREATOR = new Creator<GenresResponse>() {
        @Override
        public GenresResponse createFromParcel(Parcel in) {
            return new GenresResponse(in);
        }

        @Override
        public GenresResponse[] newArray(int size) {
            return new GenresResponse[size];
        }
    };

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public List<Genre> getGernesList() {
        return gernesList;
    }

    public void setGernesList(List<Genre> gernesList) {
        this.gernesList = gernesList;
    }

    @Override
    public String toString() {
        return "GenresResponse{" +
                "isLoading=" + isLoading +
                ", gernesList=" + gernesList +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeByte((byte) (isLoading ? 1 : 0));
        dest.writeTypedList(gernesList);
    }
}
