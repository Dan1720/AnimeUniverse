package com.progetto.animeuniverse.adapter;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.model.AnimeEpisodesImages;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EpisodesImagesRecyclerViewAdapter extends RecyclerView.Adapter<EpisodesImagesRecyclerViewAdapter.EpisodesImagesRecyclerViewHolder> {

    public EpisodesImagesRecyclerViewAdapter(List<AnimeEpisodesImages> animeEpisodesImagesList, Application application, OnItemClickListener onItemCLickListener) {
        this.animeEpisodesImagesList = animeEpisodesImagesList;
        this.application = application;
        this.onItemCLickListener = onItemCLickListener;
    }

    public interface OnItemClickListener{
        void onEpisodeImageItemClick(AnimeEpisodesImages animeEpisodesImages);
    }

    private final List<AnimeEpisodesImages> animeEpisodesImagesList;
    private final Application application;
    private final OnItemClickListener onItemCLickListener;

    @NonNull
    @Override
    public EpisodesImagesRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_episode, viewGroup, false);
        return new EpisodesImagesRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodesImagesRecyclerViewHolder holder, int position) {
        AnimeEpisodesImages childItem = animeEpisodesImagesList.get(position);
        if(holder instanceof EpisodesImagesRecyclerViewHolder){
            ((EpisodesImagesRecyclerViewHolder) holder).bind(animeEpisodesImagesList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return animeEpisodesImagesList.size();
    }

    public class EpisodesImagesRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

       // private final ImageView imageViewEpisodeImage;

        public EpisodesImagesRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            //this.imageViewEpisodeImage = itemView.findViewById(R.id.imageView_episodeImageIn);
            itemView.setOnClickListener(this);
        }

        public void bind(AnimeEpisodesImages animeEpisodesImages){
           /*Picasso.get()
                    .load(animeEpisodesImages.getImages().getJpgImages().getImageUrl())
                    .into(imageViewEpisodeImage);*/
        }

        @Override
        public void onClick(View v) {
            onItemCLickListener.onEpisodeImageItemClick(animeEpisodesImagesList.get(getAdapterPosition()));
        }
    }
}
