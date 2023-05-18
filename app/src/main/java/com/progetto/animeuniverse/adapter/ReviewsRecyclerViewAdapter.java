package com.progetto.animeuniverse.adapter;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.model.Review;
import com.progetto.animeuniverse.util.DateTimeUtil;


import java.util.List;

public class ReviewsRecyclerViewAdapter extends RecyclerView.Adapter<ReviewsRecyclerViewAdapter.ReviewsRecyclerViewHolder> {

    private final List<Review> reviewList;
    private final Application application;

    public ReviewsRecyclerViewAdapter(List<Review> reviewList, Application application) {
        this.reviewList = reviewList;
        this.application = application;
    }

    @NonNull
    @Override
    public ReviewsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_review, viewGroup, false);
        return new ReviewsRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsRecyclerViewHolder holder, int position) {
        Review childItem = reviewList.get(position);
        if(holder instanceof ReviewsRecyclerViewHolder){
            ((ReviewsRecyclerViewHolder) holder).bind(reviewList.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public class ReviewsRecyclerViewHolder extends RecyclerView.ViewHolder{
        private final TextView textViewScoreIn;
        private final TextView textViewDateIn;
        private final TextView textViewReviewIn;

        public ReviewsRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewScoreIn = itemView.findViewById(R.id.textView_scoreIn);
            this.textViewDateIn = itemView.findViewById(R.id.textView_dateIn);
            this.textViewReviewIn = itemView.findViewById(R.id.textView_reviewIn);
        }

        public void bind(Review review) {
            textViewScoreIn.setText(String.valueOf(review.getScore()));
            textViewDateIn.setText(DateTimeUtil.getDate(review.getDate()));
            textViewReviewIn.setText(review.getReview());

        }
    }
}
