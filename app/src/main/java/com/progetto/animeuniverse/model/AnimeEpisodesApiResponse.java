package com.progetto.animeuniverse.model;

import java.util.List;

public class AnimeEpisodesApiResponse extends AnimeEpisodesResponse{
    public AnimeEpisodesApiResponse(){ super();}

    public AnimeEpisodesApiResponse(List<AnimeEpisodes> animeEpisodesList){
        super(animeEpisodesList);
    }
    @Override
    public String toString() {
        return super.toString();
    }
}
