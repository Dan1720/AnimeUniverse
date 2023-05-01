package com.progetto.animeuniverse.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class AnimeSource implements Parcelable {
    private String name;

    public AnimeSource(){}

    public AnimeSource(String name){
        this.name = name;
    }

    protected AnimeSource(Parcel in) {
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AnimeSource> CREATOR = new Creator<AnimeSource>() {
        @Override
        public AnimeSource createFromParcel(Parcel in) {
            return new AnimeSource(in);
        }

        @Override
        public AnimeSource[] newArray(int size) {
            return new AnimeSource[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AnimeSource{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimeSource that = (AnimeSource) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
