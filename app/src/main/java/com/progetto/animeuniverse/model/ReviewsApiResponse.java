package com.progetto.animeuniverse.model;

import java.util.List;

public class ReviewsApiResponse extends ReviewsResponse{
    public ReviewsApiResponse(){
        super();
    }

    public ReviewsApiResponse(List<Review> reviewList) {
        super(reviewList);
    }



    @Override
    public String toString() {
        return super.toString();
    }
}
