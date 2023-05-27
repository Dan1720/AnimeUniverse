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
import com.progetto.animeuniverse.model.Anime;
import com.progetto.animeuniverse.model.AnimeByName;
import com.progetto.animeuniverse.service.AnimeApiService;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.AnimeByNameViewHolder> {






   public interface OnItemClickListener{
        void onAnimeClick(AnimeByName anime);
    }
    private List<AnimeByName> animeList;
    private Application application;

    private OnItemClickListener onItemSelectedListener;
    public SearchListAdapter(List<AnimeByName> animeList,Application application, OnItemClickListener onItemSelectedListener){
        this.animeList = animeList;
        this.application = application;
        this.onItemSelectedListener = onItemSelectedListener;
    }

    @NonNull
    @Override
    public AnimeByNameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.search_anime_list_item,parent, false);

        return new AnimeByNameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeByNameViewHolder holder, int position) {
        //holder.bind(animeList.get(position));
        AnimeByName animeItem = animeList.get(position);
        Picasso.get()
                .load(animeItem.getImages().getJpgImages().getImageUrl())
                .into(holder.imageViewAnime);
    }

    @Override
    public int getItemCount() {
        /*if(animeList != null){
            return animeList.size();
        }
        else{
            return 0;
        }*/
        return animeList.size();
    }

    public class AnimeByNameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView textViewTitle;
        private final ImageView imageViewAnime;
        public AnimeByNameViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textview_title);
            imageViewAnime = itemView.findViewById(R.id.imageview_anime_search);
            itemView.setOnClickListener(this);
            imageViewAnime.setOnClickListener(this);
        }
        /*public void bind(Anime anime){
          textViewTitle.setText(anime.getTitle());
          //setImageViewAnimeSearch

        }*/

        @Override
        public void onClick(View v) {
            onItemSelectedListener.onAnimeClick(animeList.get(getAdapterPosition()));
        }

    }

}
