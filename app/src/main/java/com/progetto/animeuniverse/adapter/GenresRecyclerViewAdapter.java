package com.progetto.animeuniverse.adapter;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.model.Genre;

import java.util.List;

public class GenresRecyclerViewAdapter extends RecyclerView.Adapter<GenresRecyclerViewAdapter.GenresRecyclerViewHolder>{


    public interface OnItemClickListener{
        void onGenreItemClick(Genre genre);
    }

    private final List<Genre> genresList;
    private final Application application;
    private final OnItemClickListener onItemClickListener;

    public GenresRecyclerViewAdapter(List<Genre> genresList, Application application, OnItemClickListener onItemClickListener) {
        this.genresList = genresList;
        this.application = application;
        this.onItemClickListener = onItemClickListener;

    }

    @NonNull
    @Override
    public GenresRecyclerViewAdapter.GenresRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_genre, viewGroup, false);
        return new GenresRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenresRecyclerViewAdapter.GenresRecyclerViewHolder holder, int position) {
        if(holder instanceof GenresRecyclerViewHolder){
            ((GenresRecyclerViewHolder) holder).bind(genresList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return genresList.size();
    }

    public class GenresRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final TextView textViewGenreIn;


        public GenresRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewGenreIn = itemView.findViewById(R.id.textView_genreIn);
            itemView.setOnClickListener(this);
        }

        public void bind(Genre genre){
            textViewGenreIn.setText(genre.getName());
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onGenreItemClick(genresList.get(getAdapterPosition()));
        }
    }
}
