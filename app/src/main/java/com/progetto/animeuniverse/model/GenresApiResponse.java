package com.progetto.animeuniverse.model;

import java.util.List;

public class GenresApiResponse extends GenresResponse{
    public GenresApiResponse(){
        super();
    }
    public GenresApiResponse(List<Genre> genresList){
        super(genresList);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
