package com.progetto.animeuniverse.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

import com.google.gson.annotations.SerializedName;

public class AnimeTrailer implements Parcelable {
    @SerializedName("youtube_id")
    @ColumnInfo(name = "youtube_id")
    private String youtubeId;
    @SerializedName("url")
    private String urlTrailer;
    @ColumnInfo(name = "embed_url")
    @SerializedName("embed_url")
    private String embedUrl;

    public AnimeTrailer() {
    }

    public AnimeTrailer(String youtubeId, String urlTrailer, String embedUrl) {
        this.youtubeId = youtubeId;
        this.urlTrailer = urlTrailer;
        this.embedUrl = embedUrl;
    }

    public String getYoutubeId() {
        return youtubeId;
    }

    public void setYoutubeId(String youtubeId) {
        this.youtubeId = youtubeId;
    }

    public String getUrlTrailer() {
        return urlTrailer;
    }

    public void setUrlTrailer(String url) {
        this.urlTrailer = urlTrailer;
    }

    public String getEmbedUrl() {
        return embedUrl;
    }

    public void setEmbedUrl(String embedUrl) {
        this.embedUrl = embedUrl;
    }

    @Override
    public String toString() {
        return "AnimeTrailer{" +
                "youtubeId='" + youtubeId + '\'' +
                ", urlTrailer='" + urlTrailer + '\'' +
                ", embedUrl='" + embedUrl + '\'' +
                '}';
    }

    protected AnimeTrailer(Parcel in) {
        youtubeId = in.readString();
        urlTrailer = in.readString();
        embedUrl = in.readString();
    }

    public static final Creator<AnimeTrailer> CREATOR = new Creator<AnimeTrailer>() {
        @Override
        public AnimeTrailer createFromParcel(Parcel in) {
            return new AnimeTrailer(in);
        }

        @Override
        public AnimeTrailer[] newArray(int size) {
            return new AnimeTrailer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(youtubeId);
        dest.writeString(urlTrailer);
        dest.writeString(embedUrl);
    }
}
