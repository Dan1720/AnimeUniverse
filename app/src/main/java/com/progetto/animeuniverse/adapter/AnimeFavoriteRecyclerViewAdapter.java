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
import com.squareup.picasso.Picasso;

import java.util.List;

public class AnimeFavoriteRecyclerViewAdapter extends RecyclerView.Adapter<AnimeFavoriteRecyclerViewAdapter.AnimeFavoriteRecyclerViewHolder> {

    public interface OnItemClickListener{
        void onAnimeFavoriteClick(Anime Anime);
    }

    private final List<Anime> animeFavoriteList;
    private final Application application;
    private final OnItemClickListener onItemClickListener;

    public AnimeFavoriteRecyclerViewAdapter(List<Anime> animeFavoriteList, Application application, AnimeFavoriteRecyclerViewAdapter.OnItemClickListener onItemClickListener) {
        this.animeFavoriteList = animeFavoriteList;
        this.application = application;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public AnimeFavoriteRecyclerViewAdapter.AnimeFavoriteRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_grid, viewGroup, false);
        return new AnimeFavoriteRecyclerViewAdapter.AnimeFavoriteRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeFavoriteRecyclerViewAdapter.AnimeFavoriteRecyclerViewHolder holder, int position) {
        if(holder instanceof AnimeFavoriteRecyclerViewHolder){
            ((AnimeFavoriteRecyclerViewHolder)holder).bind(animeFavoriteList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return animeFavoriteList.size();
    }

    public class AnimeFavoriteRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final ImageView imageViewItemGrid;
        public AnimeFavoriteRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageViewItemGrid = itemView.findViewById(R.id.item_grid);
            itemView.setOnClickListener(this);
        }

        public void bind(Anime anime){
            Picasso.get()
                    .load(anime.getImages().getJpgImages().getImageUrl())
                    .into(imageViewItemGrid);

        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onAnimeFavoriteClick(animeFavoriteList.get(getAdapterPosition()));

        }
    }


}
