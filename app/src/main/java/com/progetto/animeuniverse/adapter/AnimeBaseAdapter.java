package com.progetto.animeuniverse.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.progetto.animeuniverse.Anime;

import java.util.List;

public class AnimeBaseAdapter extends BaseAdapter {
    private final List<Anime> animeList;

    public AnimeBaseAdapter(List<Anime> animeList){
        this.animeList = animeList;
    }

    @Override
    public int getCount() {
        if(animeList != null){
            return animeList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return animeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
