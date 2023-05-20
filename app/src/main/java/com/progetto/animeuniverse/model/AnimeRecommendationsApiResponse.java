package com.progetto.animeuniverse.model;

import java.util.List;

public class AnimeRecommendationsApiResponse extends AnimeRecommendationsResponse{
    public AnimeRecommendationsApiResponse(){
        super();
    }
    public AnimeRecommendationsApiResponse(List<AnimeRecommendations> animeRecommendationsList){
        super(animeRecommendationsList);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
