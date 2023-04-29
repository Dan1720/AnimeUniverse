package com.progetto.animeuniverse.model;

import android.os.Parcel;
import android.os.Parcelable;

public class AnimeStudios implements Parcelable {
    private int idStudio;
    private String typeStudio;
    private String nameStudio;
    private String urlStudio;

    public AnimeStudios(int idStudio, String typeStudio, String nameStudio, String urlStudio) {
        this.idStudio = idStudio;
        this.typeStudio = typeStudio;
        this.nameStudio = nameStudio;
        this.urlStudio = urlStudio;
    }

    protected AnimeStudios(Parcel in) {
        idStudio = in.readInt();
        typeStudio = in.readString();
        nameStudio = in.readString();
        urlStudio = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idStudio);
        dest.writeString(typeStudio);
        dest.writeString(nameStudio);
        dest.writeString(urlStudio);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public int getIdStudio() {
        return idStudio;
    }

    public void setIdStudio(int idStudio) {
        this.idStudio = idStudio;
    }

    public String getTypeStudio() {
        return typeStudio;
    }

    public void setTypeStudio(String typeStudio) {
        this.typeStudio = typeStudio;
    }

    public String getNameStudio() {
        return nameStudio;
    }

    public void setNameStudio(String nameStudio) {
        this.nameStudio = nameStudio;
    }

    public String getUrlStudio() {
        return urlStudio;
    }

    public void setUrlStudio(String urlStudio) {
        this.urlStudio = urlStudio;
    }

    @Override
    public String toString() {
        return "AnimeStudios{" +
                "idStudio=" + idStudio +
                ", typeStudio='" + typeStudio + '\'' +
                ", nameStudio='" + nameStudio + '\'' +
                ", urlStudio='" + urlStudio + '\'' +
                '}';
    }
}
