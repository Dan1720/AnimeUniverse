package com.progetto.animeuniverse;

import android.os.Parcel;
import android.os.Parcelable;

public class AnimeGenres implements Parcelable {
    private int id;
    private String type;
    private String name;
    private String url;

    public AnimeGenres(int id, String type, String name, String url) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        return "AnimeGenres{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    protected AnimeGenres(Parcel in) {
        id = in.readInt();
        type = in.readString();
        name = in.readString();
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(type);
        dest.writeString(name);
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AnimeGenres> CREATOR = new Creator<AnimeGenres>() {
        @Override
        public AnimeGenres createFromParcel(Parcel in) {
            return new AnimeGenres(in);
        }

        @Override
        public AnimeGenres[] newArray(int size) {
            return new AnimeGenres[size];
        }
    };
}
