package com.progetto.animeuniverse.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AnimeGenres implements Parcelable {
    @SerializedName("mal_id")
    private int idGenre;
    @SerializedName("type")
    private String typeGenre;
    @SerializedName("name")
    private String nameGenre;
    @SerializedName("url")
    private String urlGenre;

    public AnimeGenres() {
    }

    public AnimeGenres(int idGenre, String typeGenre, String nameGenre, String urlGenre) {
        this.idGenre = idGenre;
        this.typeGenre = typeGenre;
        this.nameGenre = nameGenre;
        this.urlGenre = urlGenre;
    }

    public int getIdGenre() {
        return idGenre;
    }

    public void setIdGenre(int idGenre) {
        this.idGenre = idGenre;
    }

    public String getTypeGenre() {
        return typeGenre;
    }

    public void setTypeGenre(String typeGenre) {
        this.typeGenre = typeGenre;
    }

    public String getNameGenre() {
        return nameGenre;
    }

    public void setNameGenre(String nameGenre) {
        this.nameGenre = nameGenre;
    }

    public String getUrlGenre() {
        return urlGenre;
    }

    public void setUrlGenre(String urlGenre) {
        this.urlGenre = urlGenre;
    }

    @Override
    public String toString() {
        return "AnimeGenres{" +
                "idGenre=" + idGenre +
                ", typeGenre='" + typeGenre + '\'' +
                ", nameGenre='" + nameGenre + '\'' +
                ", urlGenre='" + urlGenre + '\'' +
                '}';
    }

    protected AnimeGenres(Parcel in) {
        idGenre = in.readInt();
        typeGenre = in.readString();
        nameGenre = in.readString();
        urlGenre = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idGenre);
        dest.writeString(typeGenre);
        dest.writeString(nameGenre);
        dest.writeString(urlGenre);
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
