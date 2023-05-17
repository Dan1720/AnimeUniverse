package com.progetto.animeuniverse.data.source.reviews;

import static com.progetto.animeuniverse.util.Constants.RETROFIT_ERROR;

import androidx.annotation.NonNull;

import com.progetto.animeuniverse.model.ReviewsApiResponse;
import com.progetto.animeuniverse.service.AnimeApiService;
import com.progetto.animeuniverse.util.ServiceLocator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewsRemoteDataSource extends BaseReviewsRemoteDataSource{

    private final AnimeApiService animeApiService;

    public ReviewsRemoteDataSource(){
        this.animeApiService = ServiceLocator.getInstance().getAnimeApiService();
    }
    @Override
    public void getReviewById(int id) {
        Call<ReviewsApiResponse> reviewsApiResponseCall = animeApiService.getReviewByIdAnime(id);
        reviewsApiResponseCall.enqueue(new Callback<ReviewsApiResponse>(){

            @Override
            public void onResponse(@NonNull Call<ReviewsApiResponse> call,
                                   @NonNull Response<ReviewsApiResponse> response) {
                if(response.isSuccessful()&& response.body() != null){
                    reviewsCallback.onSuccessFromRemote(response.body(), System.currentTimeMillis());
                }else{
                    reviewsCallback.onFailureFromRemote(new Exception());
                }
            }

            @Override
            public void onFailure(Call<ReviewsApiResponse> call, Throwable t) {
                reviewsCallback.onFailureFromRemote(new Exception(RETROFIT_ERROR));
            }

        });
    }
}
