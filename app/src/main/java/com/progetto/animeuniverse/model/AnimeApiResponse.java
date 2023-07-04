package com.progetto.animeuniverse.model;


import java.util.List;

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
