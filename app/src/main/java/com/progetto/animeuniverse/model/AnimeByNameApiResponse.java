package com.progetto.animeuniverse.model;



import java.util.List;

public class AnimeByNameApiResponse extends AnimeByNameResponse {
    public AnimeByNameApiResponse(){ super();}

    public AnimeByNameApiResponse(List<AnimeByName> animeByNameList) {
        super(animeByNameList);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
