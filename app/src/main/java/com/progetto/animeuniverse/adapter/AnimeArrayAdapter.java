package com.progetto.animeuniverse.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.progetto.animeuniverse.Anime;

public class AnimeArrayAdapter extends ArrayAdapter<Anime> {
    private final int layout;
    private final Anime[] animeArray;
    public AnimeArrayAdapter(@NonNull Context context, int layout,@NonNull Anime [] animeArray ) {
        super(context, layout, animeArray);
        this.layout = layout;
        this.animeArray = animeArray;
    }
}
