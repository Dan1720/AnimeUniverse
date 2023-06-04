package com.progetto.animeuniverse.adapter;

import static com.progetto.animeuniverse.util.Constants.LIMIT_ITEM_RV;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.model.AnimeRecommendations;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AnimeRecommendationsRecyclerViewAdapter extends RecyclerView.Adapter<AnimeRecommendationsRecyclerViewAdapter.AnimeRecommendationsRecyclerViewHolder> {



    public interface OnItemClickListener{
        void onAnimeRecommendationsClick(AnimeRecommendations animeRecommendations);
    }

    private final List<AnimeRecommendations> animeRecommendationsList;
    private final Application application;
    private final AnimeRecommendationsRecyclerViewAdapter.OnItemClickListener onItemClickListener;

    public AnimeRecommendationsRecyclerViewAdapter(List<AnimeRecommendations> animeRecommendationsList, Application application, AnimeRecommendationsRecyclerViewAdapter.OnItemClickListener onItemClickListener) {
        this.animeRecommendationsList = animeRecommendationsList;
        this.application = application;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public AnimeRecommendationsRecyclerViewAdapter.AnimeRecommendationsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new AnimeRecommendationsRecyclerViewAdapter.AnimeRecommendationsRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeRecommendationsRecyclerViewAdapter.AnimeRecommendationsRecyclerViewHolder holder, int position) {
        if(holder instanceof AnimeRecommendationsRecyclerViewAdapter.AnimeRecommendationsRecyclerViewHolder){
            ((AnimeRecommendationsRecyclerViewAdapter.AnimeRecommendationsRecyclerViewHolder) holder).bind(animeRecommendationsList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if(animeRecommendationsList.size() > LIMIT_ITEM_RV){
            return LIMIT_ITEM_RV;
        }else{
            return animeRecommendationsList.size();
        }

    }

    public class AnimeRecommendationsRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final ImageView imageViewItem;
        public AnimeRecommendationsRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageViewItem = itemView.findViewById(R.id.img_child_item);
            itemView.setOnClickListener(this);
        }

        public void bind(AnimeRecommendations animeRecommendations){
            Picasso.get()
                    .load(animeRecommendations.getEntry().get(0).getImages().getJpgImages().getImageUrl())
                    .into(imageViewItem);

        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onAnimeRecommendationsClick(animeRecommendationsList.get(getAdapterPosition()));
        }
    }
}
