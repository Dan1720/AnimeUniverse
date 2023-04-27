package com.progetto.animeuniverse;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class AnimeStudios implements Parcelable {
    private int id;
    private String type;
    private String name;
    private String url;

    public AnimeStudios(int id, String type, String name, String url) {
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
        return "AnimeStudios{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    protected AnimeStudios(Parcel in) {
        id = in.readInt();
        type = in.readString();
        name = in.readString();
        url = in.readString();
    }

    public static final Creator<AnimeStudios> CREATOR = new Creator<AnimeStudios>() {
        @Override
        public AnimeStudios createFromParcel(Parcel in) {
            return new AnimeStudios(in);
        }

        @Override
        public AnimeStudios[] newArray(int size) {
            return new AnimeStudios[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(type);
        dest.writeString(name);
        dest.writeString(url);
    }
}
