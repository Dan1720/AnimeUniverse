package com.progetto.animeuniverse.adapter;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.model.Anime;
import com.bumptech.glide.Glide;

import java.util.List;

public class AnimeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public interface OnItemClickListener{
        void onAnimeItemClick(Anime anime);
    }
    private static final int ANIME_VIEW_TYPE = 0;
    private static final int LOADING_VIEW_TYPE = 1;
    private final List<Anime> animeList;
    private final Application application;
    private final OnItemClickListener onItemClickListener;

    public AnimeRecyclerViewAdapter(List<Anime> animeList, Application application, OnItemClickListener onItemClickListener) {
        this.animeList = animeList;
        this.application = application;
        this.onItemClickListener = onItemClickListener;
    }

    public int getItemViewType(int position){
        if(animeList.get(position) == null){
            return LOADING_VIEW_TYPE;
        }else {
            return ANIME_VIEW_TYPE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if(viewType == ANIME_VIEW_TYPE){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item, parent, false);
            return new AnimeViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof AnimeViewHolder){
            ((AnimeViewHolder) holder).bind(animeList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if(animeList != null){
            return animeList.size();
        }
        return 0;
    }

    public class AnimeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final ImageView imageViewAnimeCoverCard;

        public AnimeViewHolder(@NonNull View itemView){
            super(itemView);
            imageViewAnimeCoverCard = itemView.findViewById(R.id.card);
            itemView.setOnClickListener(this);

        }

        public void bind(Anime anime){
            Glide.with(application)
                    .load(anime.getImages().get(getAdapterPosition()).getJpg().getImageUrlJpg())
                    .into(imageViewAnimeCoverCard);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
