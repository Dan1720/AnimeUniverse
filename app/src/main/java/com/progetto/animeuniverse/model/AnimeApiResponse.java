package com.progetto.animeuniverse.model;

import java.util.List;

public class AnimeApiResponse extends AnimeResponse{
    private String status;
    private int totalResults;

    public AnimeApiResponse(){
        super();
    }

    public AnimeApiResponse(String status, int totalResults, List<Anime> anime) {
        super(anime);
        this.status = status;
        this.totalResults = totalResults;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    @Override
    public String toString() {
        return "AnimeApiResponse{" +
                "status='" + status + '\'' +
                ", totalResults=" + totalResults +
                '}';
    }
}
