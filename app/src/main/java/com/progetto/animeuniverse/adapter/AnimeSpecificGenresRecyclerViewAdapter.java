package com.progetto.animeuniverse.adapter;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.model.AnimeSpecificGenres;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AnimeSpecificGenresRecyclerViewAdapter extends RecyclerView.Adapter<AnimeSpecificGenresRecyclerViewAdapter.AnimeSpecificGenresRecyclerViewHolder> {



    public interface OnItemClickListener{
        void onAnimeSpecificGenresClick(AnimeSpecificGenres animeSpecificGenres);
    }

    private final List<AnimeSpecificGenres> animeSpecificGenresList;
    private final Application application;
    private final AnimeSpecificGenresRecyclerViewAdapter.OnItemClickListener onItemClickListener;

    public AnimeSpecificGenresRecyclerViewAdapter(List<AnimeSpecificGenres> animeSpecificGenresList, Application application, AnimeSpecificGenresRecyclerViewAdapter.OnItemClickListener onItemClickListener) {
        this.animeSpecificGenresList = animeSpecificGenresList;
        this.application = application;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public AnimeSpecificGenresRecyclerViewAdapter.AnimeSpecificGenresRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_grid, viewGroup, false);
        return new AnimeSpecificGenresRecyclerViewAdapter.AnimeSpecificGenresRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeSpecificGenresRecyclerViewAdapter.AnimeSpecificGenresRecyclerViewHolder holder, int position) {
        if(holder instanceof AnimeSpecificGenresRecyclerViewAdapter.AnimeSpecificGenresRecyclerViewHolder){
            ((AnimeSpecificGenresRecyclerViewAdapter.AnimeSpecificGenresRecyclerViewHolder) holder).bind(animeSpecificGenresList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return animeSpecificGenresList.size();
    }

    public class AnimeSpecificGenresRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final ImageView imageViewItemGrid;
        public AnimeSpecificGenresRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageViewItemGrid = itemView.findViewById(R.id.item_grid);
            itemView.setOnClickListener(this);
        }

        public void bind(AnimeSpecificGenres animeSpecificGenres){
            Picasso.get()
                    .load(animeSpecificGenres.getImages().getJpgImages().getImageUrl())
                    .into(imageViewItemGrid);

        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onAnimeSpecificGenresClick(animeSpecificGenresList.get(getAdapterPosition()));
        }
    }
}
