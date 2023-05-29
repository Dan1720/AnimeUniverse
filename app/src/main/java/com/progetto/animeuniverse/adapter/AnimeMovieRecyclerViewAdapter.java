package com.progetto.animeuniverse.adapter;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.model.AnimeMovie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AnimeMovieRecyclerViewAdapter extends RecyclerView.Adapter<AnimeMovieRecyclerViewAdapter.AnimeMovieRecyclerViewHolder> {



    public interface OnItemClickListener{
        void onAnimeMovieClick(AnimeMovie animeMovie);
    }

    private final List<AnimeMovie> animeMovieList;
    private final Application application;
    private final AnimeMovieRecyclerViewAdapter.OnItemClickListener onItemClickListener;

    public AnimeMovieRecyclerViewAdapter(List<AnimeMovie> animeMovieList, Application application, AnimeMovieRecyclerViewAdapter.OnItemClickListener onItemClickListener) {
        this.animeMovieList = animeMovieList;
        this.application = application;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public AnimeMovieRecyclerViewAdapter.AnimeMovieRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_grid, viewGroup, false);
        return new AnimeMovieRecyclerViewAdapter.AnimeMovieRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeMovieRecyclerViewAdapter.AnimeMovieRecyclerViewHolder holder, int position) {
        if(holder instanceof AnimeMovieRecyclerViewAdapter.AnimeMovieRecyclerViewHolder){
            ((AnimeMovieRecyclerViewAdapter.AnimeMovieRecyclerViewHolder) holder).bind(animeMovieList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return animeMovieList.size();
    }

    public class AnimeMovieRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final ImageView imageViewItemGrid;
        public AnimeMovieRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageViewItemGrid = itemView.findViewById(R.id.item_grid);
            itemView.setOnClickListener(this);
        }

        public void bind(AnimeMovie animeMovie){
            Picasso.get()
                    .load(animeMovie.getImages().getJpgImages().getImageUrl())
                    .into(imageViewItemGrid);

        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onAnimeMovieClick(animeMovieList.get(getAdapterPosition()));
        }
    }
}

