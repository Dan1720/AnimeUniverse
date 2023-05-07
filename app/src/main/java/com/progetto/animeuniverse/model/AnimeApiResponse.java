package com.progetto.animeuniverse.model;

import android.os.Parcelable;

import androidx.room.Embedded;

import com.google.gson.annotations.SerializedName;

import java.util.List;
//Classe che riceve il risultato della chiamata alla Api e mette tutto in una lista
public class AnimeApiResponse extends AnimeResponse{
    @SerializedName("pagination")
    private AnimePagination pagination;
    public AnimeApiResponse(){
        super();
    }

    public AnimeApiResponse(AnimePagination pagination, List<Anime> animeTopList) {
        super(animeTopList);
        this.pagination = pagination;
    }

    public AnimePagination getPagination() {
        return pagination;
    }

    public void setPagination(AnimePagination pagination) {
        this.pagination = pagination;
    }


    @Override
    public String toString() {
        super.toString();
        return "AnimeApiResponse{" +
                "pagination=" + pagination +
                '}';
    }
}
