package com.progetto.animeuniverse.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.model.Anime;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.List;

public class ChildItemAdapter
            extends RecyclerView
            .Adapter<ChildItemAdapter.ChildViewHolder> {

        private List<Anime> ChildItemList;

        // Constructor
        ChildItemAdapter(List<Anime> childItemList)
        {
            this.ChildItemList = childItemList;
        }

        @NonNull
        @Override
        public ChildViewHolder onCreateViewHolder(
                @NonNull ViewGroup viewGroup,
                int i)
        {


            View view = LayoutInflater
                    .from(viewGroup.getContext())
                    .inflate(
                            R.layout.child_item,
                            viewGroup, false);

            return new ChildViewHolder(view);
        }

        @Override
        public void onBindViewHolder(
                @NonNull ChildViewHolder childViewHolder,
                int position)
        {

            Anime childItem
                    = ChildItemList.get(position);

            Picasso.get()
                    .load(childItem.getImages().getJpgImages().getImageUrl())
                    .into(childViewHolder.ChildItemImageView);
        }

        @Override
        public int getItemCount()
        {

            return ChildItemList.size();
        }

        class ChildViewHolder
                extends RecyclerView.ViewHolder {

            ImageView ChildItemImageView;

            ChildViewHolder(View itemView) {
                super(itemView);

                ChildItemImageView = itemView.findViewById(R.id.img_child_item);
            }

        }
}