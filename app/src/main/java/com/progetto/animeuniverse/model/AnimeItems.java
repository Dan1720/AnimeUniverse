package com.progetto.animeuniverse.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class AnimeItems implements Parcelable {
    @SerializedName("count")
    private int count;
    @SerializedName("total")
    private int total;
    @SerializedName("per_page")
    private int perPage;

    public AnimeItems(int count, int total, int perPage) {
        this.count = count;
        this.total = total;
        this.perPage = perPage;
    }

    protected AnimeItems(Parcel in) {
        count = in.readInt();
        total = in.readInt();
        perPage = in.readInt();
    }

    public static final Creator<AnimeItems> CREATOR = new Creator<AnimeItems>() {
        @Override
        public AnimeItems createFromParcel(Parcel in) {
            return new AnimeItems(in);
        }

        @Override
        public AnimeItems[] newArray(int size) {
            return new AnimeItems[size];
        }
    };

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    @Override
    public String toString() {
        return "AnimeItems{" +
                "count=" + count +
                ", total=" + total +
                ", perPage=" + perPage +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(count);
        dest.writeInt(total);
        dest.writeInt(perPage);
    }
}
