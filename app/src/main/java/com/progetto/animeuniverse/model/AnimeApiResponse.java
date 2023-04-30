package com.progetto.animeuniverse.model;

import java.util.List;
//Classe che riceve il risultato della chiamata alla Api e mette tutto in una lista
public class AnimeApiResponse extends AnimeResponse{
    private List<Anime> data;
    private String status;

    public AnimeApiResponse(){}

    public AnimeApiResponse(List<Anime>data, String status){
        this.data = data;
        this.status = status;
    }

    public List<Anime> getData() {
        return data;
    }

    public void setData(List<Anime> data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AnimeApiResponse{" +
                "data=" + data +
                ", status='" + status + '\'' +
                '}';
    }
}
