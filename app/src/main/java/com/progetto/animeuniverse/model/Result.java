package com.progetto.animeuniverse.model;


public abstract class Result {
    private Result(){}

    public boolean isSuccess(){
        if(this instanceof AnimeResponseSuccess || this instanceof UserResponseSuccess ||
                this instanceof ReviewsResponseSuccess || this instanceof GenresResponseSuccess ||
                this instanceof AnimeRecommendationsSuccess || this instanceof AnimeByNameSuccess ||
                this instanceof AnimeNewSuccess || this instanceof AnimeEpisodesSuccess ||
                this instanceof AnimeTvSuccess || this instanceof AnimeMovieSuccess || this instanceof AnimeSpecificGenresSuccess){
            return true;
        }else{
            return false;
        }
    }

    public static final class UserResponseSuccess extends Result{
        private final User user;
        public UserResponseSuccess(User user){
            this.user = user;
        }
        public User getData(){
            return user;
        }
    }

    public static final class Error extends Result {
        private final String message;
        public Error(String message) {
            this.message = message;
        }
        public String getMessage() {
            return message;
        }
    }

    public static final class AnimeResponseSuccess extends Result{
        private final AnimeResponse animeResponse;
        public AnimeResponseSuccess(AnimeResponse animeResponse){
            this.animeResponse = animeResponse;
        }
        public AnimeResponse getData(){
            return animeResponse;
        }
    }

    public static final class ReviewsResponseSuccess extends Result{
        private final ReviewsResponse reviewsResponse;
        public ReviewsResponseSuccess(ReviewsResponse reviewsResponse){
            this.reviewsResponse = reviewsResponse;
        }
        public ReviewsResponse getData(){
            return reviewsResponse;
        }
    }

    public static final class GenresResponseSuccess extends Result{
        private final GenresResponse genresResponse;
        public GenresResponseSuccess(GenresResponse genresResponse){
            this.genresResponse = genresResponse;
        }
        public GenresResponse getData(){
            return genresResponse;
        }
    }

    public static final class AnimeRecommendationsSuccess extends Result{
        private final AnimeRecommendationsResponse animeRecommendationsResponse;
        public AnimeRecommendationsSuccess(AnimeRecommendationsResponse animeRecommendationsResponse){
            this.animeRecommendationsResponse = animeRecommendationsResponse;
        }
        public AnimeRecommendationsResponse getData(){
            return animeRecommendationsResponse;
        }
    }

    public static final class AnimeByNameSuccess extends Result{
        private final AnimeByNameResponse animeByNameResponse;
        public AnimeByNameSuccess(AnimeByNameResponse animeByNameResponse){
            this.animeByNameResponse = animeByNameResponse;
        }
        public AnimeByNameResponse getData(){return animeByNameResponse;}
    }

    public static final class AnimeNewSuccess extends Result{
        private final AnimeNewResponse animeNewResponse;

        public AnimeNewSuccess(AnimeNewResponse animeNewResponse) {
            this.animeNewResponse = animeNewResponse;
        }

        public AnimeNewResponse getData(){return animeNewResponse;}
    }

    public static final class AnimeEpisodesSuccess extends Result{
        private final AnimeEpisodesResponse animeEpisodesResponse;

        public AnimeEpisodesSuccess(AnimeEpisodesResponse animeEpisodesResponse) {
            this.animeEpisodesResponse = animeEpisodesResponse;
        }

        public AnimeEpisodesResponse getData(){ return animeEpisodesResponse; }
    }

    public static final class AnimeTvSuccess extends Result{
        private final AnimeTvResponse animeTvResponse;

        public AnimeTvSuccess(AnimeTvResponse animeTvResponse){
            this.animeTvResponse = animeTvResponse;
        }

        public AnimeTvResponse getData(){ return animeTvResponse; }
    }

    public static final class AnimeMovieSuccess extends Result{
        private final AnimeMovieResponse animeMovieResponse;

        public AnimeMovieSuccess(AnimeMovieResponse animeMovieResponse){
            this.animeMovieResponse = animeMovieResponse;
        }

        public AnimeMovieResponse getData(){ return animeMovieResponse; }
    }

    public static final class AnimeSpecificGenresSuccess extends Result{
        private final AnimeSpecificGenresResponse animeSpecificGenresResponse;

        public AnimeSpecificGenresSuccess(AnimeSpecificGenresResponse animeSpecificGenresResponse){
            this.animeSpecificGenresResponse = animeSpecificGenresResponse;
        }

        public AnimeSpecificGenresResponse getData(){ return animeSpecificGenresResponse; }
    }

}
