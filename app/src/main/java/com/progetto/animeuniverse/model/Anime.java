package com.progetto.animeuniverse.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;


@Entity(tableName = "anime")
public class Anime  implements Parcelable {
    @PrimaryKey
    @SerializedName("mal_id")
    private int id;
    private String title;
    private String author;
    private String url;
    @Embedded
    private AnimeImages images;
    @Embedded
    private AnimeTrailer trailer;
    private boolean approved;
    private String type;
    private String source;

    @SerializedName("episodes")
    private int numEpisodes;
    private String status;
    private String duration;
    private String rating;
    private int year;

    @Embedded
    private AnimeProducers producers;
    @Embedded
    private AnimeStudios studios;
    @Embedded
    private AnimeGenres genres;
    @Embedded
    private AnimeStreaming streaming;
    @ColumnInfo(name = "is_favorite")
    private boolean isFavorite;
    @ColumnInfo(name = "is_synchronized")
    private boolean isSynchronized;
    private int popularity;

    public Anime(){};

    public Anime(int id, String title, String author, String url, AnimeImages images,
                 AnimeTrailer trailer, boolean approved, String type, String source,
                 int numEpisodes, String status, String duration,
                 String rating, int year, AnimeProducers producers, AnimeStudios studios,
                 AnimeGenres genres, AnimeStreaming streaming, boolean isFavorite, boolean isSynchronized, int popularity) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.url = url;
        this.images = images;
        this.trailer = trailer;
        this.approved = approved;
        this.type = type;
        this.source = source;
        this.numEpisodes = numEpisodes;
        this.status = status;
        this.duration = duration;
        this.rating = rating;
        this.year = year;
        this.producers = producers;
        this.studios = studios;
        this.genres = genres;
        this.streaming = streaming;
        this.isFavorite = isFavorite;
        this.isSynchronized = isSynchronized;
        this.popularity = popularity;
    }


    protected Anime(Parcel in) {
        id = in.readInt();
        title = in.readString();
        author = in.readString();
        url = in.readString();
        images = in.readParcelable(AnimeImages.class.getClassLoader());
        trailer = in.readParcelable(AnimeTrailer.class.getClassLoader());
        approved = in.readByte() != 0;
        type = in.readString();
        source = in.readString();
        numEpisodes = in.readInt();
        status = in.readString();
        duration = in.readString();
        rating = in.readString();
        year = in.readInt();
        producers = in.readParcelable(AnimeProducers.class.getClassLoader());
        studios = in.readParcelable(AnimeStudios.class.getClassLoader());
        genres = in.readParcelable(AnimeGenres.class.getClassLoader());
        streaming = in.readParcelable(AnimeStreaming.class.getClassLoader());
        isFavorite = in.readByte() != 0;
        isSynchronized = in.readByte() != 0;
        popularity = in.readInt();
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public AnimeImages getImages() {
        return images;
    }

    public void setImages(AnimeImages images) {
        this.images = images;
    }

    public AnimeTrailer getTrailer() {
        return trailer;
    }

    public void setTrailer(AnimeTrailer trailer) {
        this.trailer = trailer;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public AnimeProducers getProducers() {
        return producers;
    }

    public void setProducers(AnimeProducers producers) {
        this.producers = producers;
    }

    public AnimeStudios getStudios() {
        return studios;
    }

    public void setStudios(AnimeStudios studios) {
        this.studios = studios;
    }

    public AnimeGenres getGenres() {
        return genres;
    }

    public void setGenres(AnimeGenres genres) {
        this.genres = genres;
    }

    public AnimeStreaming getStreaming() {
        return streaming;
    }

    public void setStreaming(AnimeStreaming streaming) {
        this.streaming = streaming;
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

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(url);
        dest.writeParcelable(images, flags);
        dest.writeParcelable(trailer, flags);
        dest.writeByte((byte) (approved ? 1 : 0));
        dest.writeString(type);
        dest.writeString(source);
        dest.writeInt(numEpisodes);
        dest.writeString(status);
        dest.writeString(duration);
        dest.writeString(rating);
        dest.writeInt(year);
        dest.writeParcelable(producers, flags);
        dest.writeParcelable(studios, flags);
        dest.writeParcelable(genres, flags);
        dest.writeParcelable(streaming, flags);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
        dest.writeByte((byte) (isSynchronized ? 1 : 0));
        dest.writeInt(popularity);
    }
}