package com.progetto.animeuniverse.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Anime  implements Parcelable {
    private String title;
    /*private String author;
    private String date;


    public Anime(String title, String author, String date){
        this.title = title;
        this.author = author;
        this.date = date;

    }



    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getAuthor(){
        return author;
    }
    public void setAuthor(String author){
        this.author = author;
    }
    public String getDate(){
        return date;
    }
    public void setDate(String date){
        this.date = date;
    }

    @Override
    public String toString() {
        return "Anime{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
    @Override
    public int describeContents(){
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(this.title);
        dest.writeString(this.author);
        dest.writeString(this.date);

    }
    public void readFromParcel(Parcel source){
        this.title = source.readString();
        this.author = source.readString();
        this.date = source.readString();

    }
    protected Anime (Parcel in){
        this.title = in.readString();
        this.author = in.readString();
        this.date = in.readString();
    }
    public static final Parcelable.Creator<Anime> CREATOR = new Parcelable.Creator<Anime>(){
        @Override
        public Anime createFromParcel(Parcel source){
            return new Anime(source);
        }
        @Override
        public Anime[] newArray(int size){
            return new Anime[size];
        }
    };
*/
    public Anime(String title) {
        this.title = title ;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    @Override
    public String toString() {
        return "Anime{" +
                "title='" + title + '\'' +
                '}';
    }
    @Override
    public int describeContents(){
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(this.title);
    }
    public void readFromParcel(Parcel source){
        this.title = source.readString();
    }
    protected Anime (Parcel in){
        this.title = in.readString();
    }
    public static final Parcelable.Creator<Anime> CREATOR = new Parcelable.Creator<Anime>(){
        @Override
        public Anime createFromParcel(Parcel source){
            return new Anime(source);
        }
        @Override
        public Anime[] newArray(int size){
            return new Anime[size];
        }
    };
}
