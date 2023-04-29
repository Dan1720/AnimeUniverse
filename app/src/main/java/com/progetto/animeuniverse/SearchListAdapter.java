package com.progetto.animeuniverse;
import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.progetto.animeuniverse.model.Anime;

import org.w3c.dom.Text;

import java.util.List;


public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.AnimeViewHolder> {






   public interface OnItemClickListener{
        void onAnimeClick(Anime anime);
    }
    private List<Anime> animeList;

    private OnItemClickListener onItemSelectedListener;
    public SearchListAdapter(List<Anime> animeList, OnItemClickListener onItemSelectedListener){
        this.animeList = animeList;
        this.onItemSelectedListener = onItemSelectedListener;
    }

    @NonNull
    @Override
    public AnimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.search_anime_list_item,parent, false);
        return new AnimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeViewHolder holder, int position) {
        holder.bind(animeList.get(position));
    }

    @Override
    public int getItemCount() {
        if(animeList != null){
            return animeList.size();
        }
        else{
            return 0;
        }
    }

    public class AnimeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView textViewTitle;
        public AnimeViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textview_title);
            itemView.setOnClickListener(this);
        }
        public void bind(Anime anime){
            textViewTitle.setText(anime.getTitle());

        }

        @Override
        public void onClick(View v) {
            onItemSelectedListener.onAnimeClick(animeList.get(getAdapterPosition()));
        }
    }

}
