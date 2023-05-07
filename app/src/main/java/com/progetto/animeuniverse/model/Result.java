package com.progetto.animeuniverse.model;

public abstract class Result {
    private Result(){}

    public boolean isSuccess(){
        if(this instanceof AnimeResponseSuccess || this instanceof UserResponseSuccess){
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

}
