package com.progetto.animeuniverse.adapter;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.model.Anime;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChildItemAdapter extends RecyclerView.Adapter<ChildItemAdapter.ChildViewHolder> {
    public interface OnItemClickListener{
        void onAnimeItemClick(Anime anime);
        void onFavoriteButtonPressed(int position);
    }

    private List<Anime> ChildItemList;
    private final Application application;
    private final OnItemClickListener onItemClickListener;



    // Constructor
    public ChildItemAdapter(List<Anime> childItemList, Application application, OnItemClickListener onItemClickListener)
    {
        this.ChildItemList = childItemList;
        this.application = application;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.child_item, viewGroup, false);

        return new ChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ChildViewHolder childViewHolder,
            int position)
    {

        if(childViewHolder instanceof ChildViewHolder){
            ((ChildViewHolder) childViewHolder).bind(ChildItemList.get(position));
        }


    }

    @Override
    public int getItemCount()
    {

        return ChildItemList.size();
    }

    public class ChildViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final ImageView ChildItemImageView;
        private final ImageView imageViewFavoriteAnime;


        public ChildViewHolder(View itemView) {
            super(itemView);

            ChildItemImageView = itemView.findViewById(R.id.img_child_item);
            imageViewFavoriteAnime = itemView.findViewById(R.id.imageView_favorite);
            itemView.setOnClickListener(this);
            imageViewFavoriteAnime.setOnClickListener(this);
        }

        public void bind(Anime anime){
            Picasso.get()
                    .load(anime.getImages().getJpgImages().getImageUrl())
                    .into(ChildItemImageView);

            setImageViewFavoriteAnime(ChildItemList.get(getAdapterPosition()).isFavorite());
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.imageView_favorite){
                setImageViewFavoriteAnime(!ChildItemList.get(getAdapterPosition()).isFavorite());
                onItemClickListener.onFavoriteButtonPressed(getAdapterPosition());
            }
            onItemClickListener.onAnimeItemClick(ChildItemList.get(getAdapterPosition()));
        }

        public void setImageViewFavoriteAnime(boolean isFavorite){
            if(isFavorite){
                imageViewFavoriteAnime.setImageDrawable(AppCompatResources.getDrawable(application, R.drawable.baseline_favorite_24));
            }else{
                imageViewFavoriteAnime.setImageDrawable(AppCompatResources.getDrawable(application, R.drawable.baseline_not_favorite_24));
            }
        }
    }


}