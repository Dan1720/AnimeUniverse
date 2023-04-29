package com.progetto.animeuniverse.repository;

import com.progetto.animeuniverse.model.Anime;

public interface IAnimeRepository {
    enum JsonParserType{
        JSON_READER,
        JSON_OBJECT_ARRAY,
        GSON,
        JSON_ERROR
    };

    void fetchAnime();
   /* void updateAnime(Anime anime);

    void getFavoriteAnime();

    void deleteFavoriteAnime();*/
}
