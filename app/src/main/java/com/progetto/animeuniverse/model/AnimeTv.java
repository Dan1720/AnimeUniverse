package com.progetto.animeuniverse.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;
import com.progetto.animeuniverse.util.Converter;

import java.util.List;

@Entity(tableName = "anime_tv")
public class AnimeTv implements Parcelable {
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

    @Embedded(prefix = "trailer_")
    @SerializedName("trailer")
    private AnimeTrailer trailer;
    @TypeConverters(Converter.class)
    @SerializedName("producers")
    private List<AnimeProducers> producers;

    @TypeConverters(Converter.class)
    @SerializedName("studios")
    private List<AnimeStudios> studios;

    @TypeConverters(Converter.class)
    @SerializedName("genres")
    private List<AnimeGenres> genres;
    @Embedded(prefix = "images_")
    @SerializedName("images")
    private AnimeImages images;

    public AnimeTv(int id, String url, boolean approved, String title, String type, String source, int numEpisodes, String status, String duration, String rating, int popularity, int year, boolean isFavorite, boolean isSynchronized, String synopsis, AnimeTrailer trailer, List<AnimeProducers> producers, List<AnimeStudios> studios, List<AnimeGenres> genres, AnimeImages images) {
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
        this.trailer = trailer;
        this.producers = producers;
        this.studios = studios;
        this.genres = genres;
        this.images = images;
    }

    protected AnimeTv(Parcel in) {
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
        trailer = in.readParcelable(AnimeTrailer.class.getClassLoader());
        producers = in.createTypedArrayList(AnimeProducers.CREATOR);
        studios = in.createTypedArrayList(AnimeStudios.CREATOR);
        genres = in.createTypedArrayList(AnimeGenres.CREATOR);
        images = in.readParcelable(AnimeImages.class.getClassLoader());
    }

    public static final Creator<AnimeTv> CREATOR = new Creator<AnimeTv>() {
        @Override
        public AnimeTv createFromParcel(Parcel in) {
            return new AnimeTv(in);
        }

        @Override
        public AnimeTv[] newArray(int size) {
            return new AnimeTv[size];
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

    public AnimeTrailer getTrailer() {
        return trailer;
    }

    public void setTrailer(AnimeTrailer trailer) {
        this.trailer = trailer;
    }

    public List<AnimeProducers> getProducers() {
        return producers;
    }

    public void setProducers(List<AnimeProducers> producers) {
        this.producers = producers;
    }

    public List<AnimeStudios> getStudios() {
        return studios;
    }

    public void setStudios(List<AnimeStudios> studios) {
        this.studios = studios;
    }

    public List<AnimeGenres> getGenres() {
        return genres;
    }

    public void setGenres(List<AnimeGenres> genres) {
        this.genres = genres;
    }

    public AnimeImages getImages() {
        return images;
    }

    public void setImages(AnimeImages images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "AnimeTv{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", approved=" + approved +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", source='" + source + '\'' +
                ", numEpisodes=" + numEpisodes +
                ", status='" + status + '\'' +
                ", duration='" + duration + '\'' +
                ", rating='" + rating + '\'' +
                ", popularity=" + popularity +
                ", year=" + year +
                ", isFavorite=" + isFavorite +
                ", isSynchronized=" + isSynchronized +
                ", synopsis='" + synopsis + '\'' +
                ", trailer=" + trailer +
                ", producers=" + producers +
                ", studios=" + studios +
                ", genres=" + genres +
                ", images=" + images +
                '}';
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
        dest.writeParcelable(trailer, flags);
        dest.writeTypedList(producers);
        dest.writeTypedList(studios);
        dest.writeTypedList(genres);
        dest.writeParcelable(images, flags);
    }
}
