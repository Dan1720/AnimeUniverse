package com.progetto.animeuniverse.repository.genres;

import com.progetto.animeuniverse.model.Genre;

import java.util.List;

public interface GenresResponseCallback {

    void onSuccess(List<Genre> genresList, long lastUpdate);
    void onFailure(String errorMessage);
}
