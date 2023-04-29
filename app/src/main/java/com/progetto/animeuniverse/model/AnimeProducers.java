package com.progetto.animeuniverse.model;

import android.os.Parcel;
import android.os.Parcelable;

public class AnimeProducers implements Parcelable {
    private int idProducer;
    private String typeProducer;
    private String nameProducer;
    private String urlProducer;

    public AnimeProducers(int idProducer, String typeProducer, String nameProducer, String urlProducer) {
        this.idProducer = idProducer;
        this.typeProducer = typeProducer;
        this.nameProducer = nameProducer;
        this.urlProducer = urlProducer;
    }

    public int getIdProducer() {
        return idProducer;
    }

    public void setIdProducer(int idProducer) {
        this.idProducer = idProducer;
    }

    public String getTypeProducer() {
        return typeProducer;
    }

    public void setTypeProducer(String typeProducer) {
        this.typeProducer = typeProducer;
    }

    public String getNameProducer() {
        return nameProducer;
    }

    public void setNameProducer(String nameProducer) {
        this.nameProducer = nameProducer;
    }

    public String getUrlProducer() {
        return urlProducer;
    }

    public void setUrlProducer(String urlProducer) {
        this.urlProducer = urlProducer;
    }

    @Override
    public String toString() {
        return "AnimeProducers{" +
                "idProducer=" + idProducer +
                ", typeProducer='" + typeProducer + '\'' +
                ", nameProducer='" + nameProducer + '\'' +
                ", urlProducer='" + urlProducer + '\'' +
                '}';
    }

    protected AnimeProducers(Parcel in) {
        idProducer = in.readInt();
        typeProducer = in.readString();
        nameProducer = in.readString();
        urlProducer = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idProducer);
        dest.writeString(typeProducer);
        dest.writeString(nameProducer);
        dest.writeString(urlProducer);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AnimeProducers> CREATOR = new Creator<AnimeProducers>() {
        @Override
        public AnimeProducers createFromParcel(Parcel in) {
            return new AnimeProducers(in);
        }

        @Override
        public AnimeProducers[] newArray(int size) {
            return new AnimeProducers[size];
        }
    };
}
