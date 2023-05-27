package com.progetto.animeuniverse.model;

import java.util.List;

public class AnimeEpisodesImagesApiResponse extends AnimeEpisodesImagesResponse{
    public AnimeEpisodesImagesApiResponse(){ super(); }

    public AnimeEpisodesImagesApiResponse(List<AnimeEpisodesImages> animeEpisodesImagesList){
        super(animeEpisodesImagesList);
    }
    @Override
    public String toString() {
        return super.toString();
    }
}
