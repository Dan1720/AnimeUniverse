package com.progetto.animeuniverse.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class AnimeStreaming implements Parcelable {
    @SerializedName("name")
    private String nameStreaming;
    @SerializedName("url")
    private String urlStreaming;

    public AnimeStreaming(String nameStreaming, String urlStreaming) {
        this.nameStreaming = nameStreaming;
        this.urlStreaming = urlStreaming;
    }

    protected AnimeStreaming(Parcel in) {
        nameStreaming = in.readString();
        urlStreaming = in.readString();
    }

    public static final Creator<AnimeStreaming> CREATOR = new Creator<AnimeStreaming>() {
        @Override
        public AnimeStreaming createFromParcel(Parcel in) {
            return new AnimeStreaming(in);
        }

        @Override
        public AnimeStreaming[] newArray(int size) {
            return new AnimeStreaming[size];
        }
    };

    public String getNameStreaming() {
        return nameStreaming;
    }

    public void setNameStreaming(String nameStreaming) {
        this.nameStreaming = nameStreaming;
    }

    public String getUrlStreaming() {
        return urlStreaming;
    }

    public void setUrlStreaming(String urlStreaming) {
        this.urlStreaming = urlStreaming;
    }

    @Override
    public String toString() {
        return "AnimeStreaming{" +
                "nameStreaming='" + nameStreaming + '\'' +
                ", urlStreaming='" + urlStreaming + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(nameStreaming);
        dest.writeString(urlStreaming);
    }
}
