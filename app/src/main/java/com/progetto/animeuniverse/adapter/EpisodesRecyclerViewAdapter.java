package com.progetto.animeuniverse.adapter;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.model.AnimeEpisodes;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EpisodesRecyclerViewAdapter extends RecyclerView.Adapter<EpisodesRecyclerViewAdapter.EpisodesRecyclerViewHolder> {
    
    public interface OnItemClickListener{
        void onEpisodeItemClick(AnimeEpisodes animeEpisodes);
    }
    
    private final List<AnimeEpisodes> animeEpisodesList;
    private final Application application;
    private final OnItemClickListener onItemClickListener;

    public EpisodesRecyclerViewAdapter(List<AnimeEpisodes> animeEpisodesList, Application application, OnItemClickListener onItemClickListener) {
        this.animeEpisodesList = animeEpisodesList;
        this.application = application;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public EpisodesRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_episode, viewGroup, false);
        return new EpisodesRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodesRecyclerViewHolder holder, int position) {
        AnimeEpisodes childItem = animeEpisodesList.get(position);
        if(holder instanceof EpisodesRecyclerViewHolder){
            ((EpisodesRecyclerViewHolder) holder).bind(animeEpisodesList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return animeEpisodesList.size();
    }

    public class EpisodesRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        
        private final TextView textViewTitleIn;
        private final TextView textViewEpisodeNumberIn;
        private final TextView textViewEpisodeFiller;
        public EpisodesRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewTitleIn = itemView.findViewById(R.id.textView_episodeTitleIn);
            this.textViewEpisodeNumberIn = itemView.findViewById(R.id.textView_episodeNumberIn);
            this.textViewEpisodeFiller = itemView.findViewById(R.id.textView_episodeFiller);
            itemView.setOnClickListener(this);
        }

        public void bind(AnimeEpisodes animeEpisodes){
            textViewTitleIn.setText(animeEpisodes.getTitle());
            textViewEpisodeNumberIn.setText(String.valueOf(animeEpisodes.getId()));
            if(animeEpisodes.isFiller()){
                textViewEpisodeFiller.setText("Filler");
            }else{
                textViewEpisodeFiller.setText("Story");
            }


        }
        
        @Override
        public void onClick(View v) {
            onItemClickListener.onEpisodeItemClick(animeEpisodesList.get(getAdapterPosition()));
        }
    }
}
