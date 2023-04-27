package com.progetto.animeuniverse.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.progetto.animeuniverse.Anime;

public class AnimeArrayAdapter extends ArrayAdapter<Anime> {
    public AnimeArrayAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
}
