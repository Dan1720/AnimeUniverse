package com.progetto.animeuniverse;

import android.os.Parcel;
import android.os.Parcelable;

public class AnimeStreaming implements Parcelable {
    private String name;
    private String url;

    public AnimeStreaming(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "AnimeStreaming{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    protected AnimeStreaming(Parcel in) {
        name = in.readString();
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
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
}
