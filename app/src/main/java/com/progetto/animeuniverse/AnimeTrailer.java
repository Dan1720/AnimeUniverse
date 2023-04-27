package com.progetto.animeuniverse;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

public class AnimeTrailer implements Parcelable {

    @ColumnInfo(name = "youtube_id")
    private String youtubeId;
    private String url;
    @ColumnInfo(name = "embed_url")
    private String embedUrl;

    public AnimeTrailer(String youtubeId, String url, String embedUrl) {
        this.youtubeId = youtubeId;
        this.url = url;
        this.embedUrl = embedUrl;
    }

    public String getYoutubeId() {
        return youtubeId;
    }

    public void setYoutubeId(String youtubeId) {
        this.youtubeId = youtubeId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
                ", url='" + url + '\'' +
                ", embedUrl='" + embedUrl + '\'' +
                '}';
    }

    protected AnimeTrailer(Parcel in) {
        youtubeId = in.readString();
        url = in.readString();
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
        dest.writeString(url);
        dest.writeString(embedUrl);
    }
}
