package com.progetto.animeuniverse.model;

import java.util.List;

public class AnimeMovieApiResponse extends AnimeMovieResponse{
    public AnimeMovieApiResponse(){ super();}
    public AnimeMovieApiResponse(List<AnimeMovie> animeMovieList){
        super(animeMovieList);
    }
    @Override
    public String toString() {
        return super.toString();
    }
}
