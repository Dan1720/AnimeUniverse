package com.progetto.animeuniverse.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.progetto.animeuniverse.repository.reviews.IReviewsRepository;
import com.progetto.animeuniverse.repository.reviews.IReviewsRepositoryWithLiveData;

public class ReviewsViewModelFactory implements ViewModelProvider.Factory {
    private final IReviewsRepositoryWithLiveData iReviewsRepositoryWithLiveData;
    public ReviewsViewModelFactory(IReviewsRepositoryWithLiveData iReviewsRepositoryWithLiveData){
        this.iReviewsRepositoryWithLiveData = iReviewsRepositoryWithLiveData;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ReviewsViewModel(iReviewsRepositoryWithLiveData);
    }
}
