package com.progetto.animeuniverse.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import com.google.firebase.database.Exclude;


public class User implements Parcelable {

    private String nomeUtente;
    private String email;
    private String idToken;
    private String urlImage;


    public User(String nomeUtente, String email, String idToken) {
        this.nomeUtente = nomeUtente;
        this.email = email;
        this.idToken = idToken;
    }

    public String getNomeUtente() {
        return nomeUtente;
    }

    public void setNomeUtente(String nomeUtente) {
        this.nomeUtente = nomeUtente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }
    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
    @Override
    public String toString() {
        return "User{" +
                "nomeUtente='" + nomeUtente + '\'' +
                ", email='" + email + '\'' +
                ", idToken='" + idToken + '\'' +
                '}';
    }

    protected User(Parcel in) {
        this.nomeUtente = in.readString();
        this.email = in.readString();
        this.idToken = in.readString();
        this.urlImage = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.nomeUtente);
        dest.writeString(this.email);
        dest.writeString(this.idToken);
        dest.writeString(this.urlImage);
    }

    public void readFromParcel(Parcel source){
        this.nomeUtente = source.readString();
        this.email = source.readString();
        this.idToken = source.readString();
        this.urlImage = source.readString();
    }
}
