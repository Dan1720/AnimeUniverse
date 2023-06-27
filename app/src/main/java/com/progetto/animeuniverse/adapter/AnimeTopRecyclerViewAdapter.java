package com.progetto.animeuniverse.adapter;

import static com.progetto.animeuniverse.util.Constants.LIMIT_ITEM_RV;

import android.app.Application;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.model.Anime;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AnimeTopRecyclerViewAdapter extends RecyclerView.Adapter<AnimeTopRecyclerViewAdapter.AnimeTopRecyclerViewHolder> {



    public interface OnItemClickListener{
        void onAnimeClick(Anime anime);
        void onFavoriteButtonPressed(int position);
    }

    private final List<Anime> animeList;
    private final Application application;
    private final AnimeTopRecyclerViewAdapter.OnItemClickListener onItemClickListener;

    public AnimeTopRecyclerViewAdapter(List<Anime> animeList, Application application, AnimeTopRecyclerViewAdapter.OnItemClickListener onItemClickListener) {
        this.animeList = animeList;
        this.application = application;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public AnimeTopRecyclerViewAdapter.AnimeTopRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new AnimeTopRecyclerViewAdapter.AnimeTopRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeTopRecyclerViewAdapter.AnimeTopRecyclerViewHolder holder, int position) {
        if(holder instanceof AnimeTopRecyclerViewAdapter.AnimeTopRecyclerViewHolder){
            ((AnimeTopRecyclerViewAdapter.AnimeTopRecyclerViewHolder) holder).bind(animeList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if(animeList.size() > LIMIT_ITEM_RV){
            return LIMIT_ITEM_RV;
        }else{
            return animeList.size();
        }
    }

    public class AnimeTopRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final ImageView imageViewItem;
        private final ImageView imageViewFavoriteAnime;
        private final TextView textViewTitle;
        public AnimeTopRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageViewItem = itemView.findViewById(R.id.img_child_item);
            imageViewFavoriteAnime = itemView.findViewById(R.id.imageView_favorite);
            textViewTitle = itemView.findViewById(R.id.textView_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onAnimeClick(animeList.get(getAdapterPosition()));
                }
            });
            imageViewFavoriteAnime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    setImageViewFavoriteAnime(!animeList.get(getAdapterPosition()).isFavorite());
                    onItemClickListener.onFavoriteButtonPressed(getAdapterPosition());

                }
            });

        }

        public void bind(Anime anime){
            Picasso.get()
                    .load(anime.getImages().getJpgImages().getImageUrl())
                    .into(imageViewItem);
            textViewTitle.setText(animeList.get(getAdapterPosition()).getTitle());
            setImageViewFavoriteAnime(animeList.get(getAdapterPosition()).isFavorite());

        }
        private long mLastClickTime = 0;
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.imageView_favorite){
                setImageViewFavoriteAnime(!animeList.get(getAdapterPosition()).isFavorite());
                onItemClickListener.onFavoriteButtonPressed(getAdapterPosition());
            }
            onItemClickListener.onAnimeClick(animeList.get(getAdapterPosition()));
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
