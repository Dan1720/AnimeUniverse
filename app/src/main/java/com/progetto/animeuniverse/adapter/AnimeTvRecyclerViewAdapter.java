package com.progetto.animeuniverse.adapter;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.model.AnimeTv;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AnimeTvRecyclerViewAdapter extends RecyclerView.Adapter<AnimeTvRecyclerViewAdapter.AnimeTvRecyclerViewHolder> {



    public interface OnItemClickListener{
        void onAnimeTvClick(AnimeTv animeTv);
    }

    private final List<AnimeTv> animeTvList;
    private final Application application;
    private final OnItemClickListener onItemClickListener;

    public AnimeTvRecyclerViewAdapter(List<AnimeTv> animeTvList, Application application, OnItemClickListener onItemClickListener) {
        this.animeTvList = animeTvList;
        this.application = application;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public AnimeTvRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_grid, viewGroup, false);
        return new AnimeTvRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeTvRecyclerViewHolder holder, int position) {
        if(holder instanceof AnimeTvRecyclerViewHolder){
            ((AnimeTvRecyclerViewHolder) holder).bind(animeTvList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return animeTvList.size();
    }

    public class AnimeTvRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final ImageView imageViewItemGrid;
        public AnimeTvRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageViewItemGrid = itemView.findViewById(R.id.item_grid);
            itemView.setOnClickListener(this);
        }

        public void bind(AnimeTv animeTv){
            Picasso.get()
                    .load(animeTv.getImages().getJpgImages().getImageUrl())
                    .into(imageViewItemGrid);

        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onAnimeTvClick(animeTvList.get(getAdapterPosition()));
        }
    }
}
