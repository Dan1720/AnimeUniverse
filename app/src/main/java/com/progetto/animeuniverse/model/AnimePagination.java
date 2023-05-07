package com.progetto.animeuniverse.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class AnimePagination implements Parcelable {
    @SerializedName("last_visible_page")
    private int lastVisiblePage;
    @SerializedName("has_next_page")
    private boolean hasNextPage;
    @SerializedName("current_page")
    private int currentPage;

    @SerializedName("items")
    private AnimeItems items;

    public AnimePagination(int lastVisiblePage, boolean hasNextPage, int currentPage,AnimeItems items ) {
        this.lastVisiblePage = lastVisiblePage;
        this.hasNextPage = hasNextPage;
        this.currentPage = currentPage;
        this.items = items;
    }

    protected AnimePagination(Parcel in) {
        lastVisiblePage = in.readInt();
        hasNextPage = in.readByte() != 0;
        currentPage = in.readInt();
        items = in.readParcelable(AnimeItems.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(lastVisiblePage);
        dest.writeByte((byte) (hasNextPage ? 1 : 0));
        dest.writeInt(currentPage);
        dest.writeParcelable(items, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AnimePagination> CREATOR = new Creator<AnimePagination>() {
        @Override
        public AnimePagination createFromParcel(Parcel in) {
            return new AnimePagination(in);
        }

        @Override
        public AnimePagination[] newArray(int size) {
            return new AnimePagination[size];
        }
    };

    public int getLastVisiblePage() {
        return lastVisiblePage;
    }

    public void setLastVisiblePage(int lastVisiblePage) {
        this.lastVisiblePage = lastVisiblePage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public AnimeItems getItems() {
        return items;
    }

    public void setItems(AnimeItems items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "AnimePagination{" +
                "lastVisiblePage=" + lastVisiblePage +
                ", hasNextPage=" + hasNextPage +
                ", currentPage=" + currentPage +
                ", items=" + items +
                '}';
    }
}
