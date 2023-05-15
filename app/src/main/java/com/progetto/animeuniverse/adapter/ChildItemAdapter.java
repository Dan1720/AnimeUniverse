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

public class ChildItemAdapter extends RecyclerView.Adapter<ChildItemAdapter.ChildViewHolder> {
    public interface OnItemClickListener{
        void onAnimeItemClick(Anime anime);
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

            Anime childItem = ChildItemList.get(position);

            Picasso.get()
                    .load(childItem.getImages().getJpgImages().getImageUrl())
                    .into(childViewHolder.ChildItemImageView);
        }

        @Override
        public int getItemCount()
        {

            return ChildItemList.size();
        }

        public class ChildViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            ImageView ChildItemImageView;

            public ChildViewHolder(View itemView) {
                super(itemView);

                ChildItemImageView = itemView.findViewById(R.id.img_child_item);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                onItemClickListener.onAnimeItemClick(ChildItemList.get(getAdapterPosition()));
            }
        }
}