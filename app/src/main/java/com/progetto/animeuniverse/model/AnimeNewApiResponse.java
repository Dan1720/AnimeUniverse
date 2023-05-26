package com.progetto.animeuniverse.model;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnimeNewApiResponse extends AnimeNewResponse {
    public AnimeNewApiResponse() { super();}

    public AnimeNewApiResponse(List<AnimeNew> animeNewList){
        super(animeNewList);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
