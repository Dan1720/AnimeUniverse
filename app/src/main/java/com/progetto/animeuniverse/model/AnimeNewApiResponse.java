package com.progetto.animeuniverse.model;

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
