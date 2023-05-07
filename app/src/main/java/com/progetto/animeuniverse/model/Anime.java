package com.progetto.animeuniverse.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


@Entity(tableName = "anime")
public class Anime  implements Parcelable {
    @PrimaryKey()
    @SerializedName("mal_id")
    private int id;
    @SerializedName("url")
    private String url;

    private boolean approved;
    @SerializedName("title")
    private String title;
    @SerializedName("type")
    private String type;
    @SerializedName("source")
    private String source;
    @SerializedName("episodes")
    private int numEpisodes;
    @SerializedName("status")
    private String status;
    @SerializedName("duration")
    private String duration;
    @SerializedName("rating")
    private String rating;
    @SerializedName("popularity")
    private int popularity;
    @SerializedName("year")
    private int year;
    @ColumnInfo(name = "is_favorite")
    private boolean isFavorite;
    @ColumnInfo(name = "is_synchronized")
    private boolean isSynchronized;
    @SerializedName("synopsis")
    private String synopsis;

    public Anime(int id, String url, boolean approved, String title, String type, String source,
                 int numEpisodes, String status, String duration, String rating, int popularity,
                 int year, boolean isFavorite, boolean isSynchronized, String synopsis) {
        this.id = id;
        this.url = url;
        this.approved = approved;
        this.title = title;
        this.type = type;
        this.source = source;
        this.numEpisodes = numEpisodes;
        this.status = status;
        this.duration = duration;
        this.rating = rating;
        this.popularity = popularity;
        this.year = year;
        this.isFavorite = isFavorite;
        this.isSynchronized = isSynchronized;
        this.synopsis = synopsis;
    }

    protected Anime(Parcel in) {
        id = in.readInt();
        url = in.readString();
        approved = in.readByte() != 0;
        title = in.readString();
        type = in.readString();
        source = in.readString();
        numEpisodes = in.readInt();
        status = in.readString();
        duration = in.readString();
        rating = in.readString();
        popularity = in.readInt();
        year = in.readInt();
        isFavorite = in.readByte() != 0;
        isSynchronized = in.readByte() != 0;
        synopsis = in.readString();
    }

    public static final Creator<Anime> CREATOR = new Creator<Anime>() {
        @Override
        public Anime createFromParcel(Parcel in) {
            return new Anime(in);
        }

        @Override
        public Anime[] newArray(int size) {
            return new Anime[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getNumEpisodes() {
        return numEpisodes;
    }

    public void setNumEpisodes(int numEpisodes) {
        this.numEpisodes = numEpisodes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public boolean isSynchronized() {
        return isSynchronized;
    }

    public void setSynchronized(boolean aSynchronized) {
        isSynchronized = aSynchronized;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(url);
        dest.writeByte((byte) (approved ? 1 : 0));
        dest.writeString(title);
        dest.writeString(type);
        dest.writeString(source);
        dest.writeInt(numEpisodes);
        dest.writeString(status);
        dest.writeString(duration);
        dest.writeString(rating);
        dest.writeInt(popularity);
        dest.writeInt(year);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
        dest.writeByte((byte) (isSynchronized ? 1 : 0));
        dest.writeString(synopsis);
    }
}