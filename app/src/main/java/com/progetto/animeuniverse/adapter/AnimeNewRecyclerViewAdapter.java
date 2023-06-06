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
import com.progetto.animeuniverse.model.AnimeNew;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AnimeNewRecyclerViewAdapter extends RecyclerView.Adapter<AnimeNewRecyclerViewAdapter.AnimeNewRecyclerViewHolder> {



    public interface OnItemClickListener{
        void onAnimeNewClick(AnimeNew animeNew);
    }

    private final List<AnimeNew> animeNewList;
    private final Application application;
    private final AnimeNewRecyclerViewAdapter.OnItemClickListener onItemClickListener;

    public AnimeNewRecyclerViewAdapter(List<AnimeNew> animeNewList, Application application, AnimeNewRecyclerViewAdapter.OnItemClickListener onItemClickListener) {
        this.animeNewList = animeNewList;
        this.application = application;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public AnimeNewRecyclerViewAdapter.AnimeNewRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new AnimeNewRecyclerViewAdapter.AnimeNewRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeNewRecyclerViewAdapter.AnimeNewRecyclerViewHolder holder, int position) {
        if(holder instanceof AnimeNewRecyclerViewAdapter.AnimeNewRecyclerViewHolder){
            ((AnimeNewRecyclerViewAdapter.AnimeNewRecyclerViewHolder) holder).bind(animeNewList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if(animeNewList.size() > LIMIT_ITEM_RV){
            return LIMIT_ITEM_RV;
        }else{
            return animeNewList.size();
        }

    }

    public class AnimeNewRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final ImageView imageViewItem;
        public AnimeNewRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageViewItem = itemView.findViewById(R.id.img_child_item);
            itemView.setOnClickListener(this);
        }

        public void bind(AnimeNew animeNew){
            Picasso.get()
                    .load(animeNew.getEntry().getImages().getJpgImages().getImageUrl())
                    .into(imageViewItem);

        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onAnimeNewClick(animeNewList.get(getAdapterPosition()));
        }
    }
}
