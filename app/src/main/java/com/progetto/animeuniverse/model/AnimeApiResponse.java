package com.progetto.animeuniverse.model;

import android.os.Parcelable;

import androidx.room.Embedded;

import com.google.gson.annotations.SerializedName;

import java.util.List;
//Classe che riceve il risultato della chiamata alla Api e mette tutto in una lista
public class AnimeApiResponse extends AnimeResponse{
    public AnimeApiResponse(){
        super();
    }

    public AnimeApiResponse(List<Anime> animeTopList) {
        super(animeTopList);
    }



    @Override
    public String toString() {
        return super.toString();
    }
}
