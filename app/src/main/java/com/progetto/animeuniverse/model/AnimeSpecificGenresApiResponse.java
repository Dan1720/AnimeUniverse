package com.progetto.animeuniverse.model;

import java.util.List;

public class AnimeSpecificGenresApiResponse extends AnimeSpecificGenresResponse {
    public AnimeSpecificGenresApiResponse(){super();}
    public AnimeSpecificGenresApiResponse(List<AnimeSpecificGenres> animeSpecificGenresList){super(animeSpecificGenresList);}
    @Override
    public String toString() {
        return super.toString();
    }
}

