package com.progetto.animeuniverse.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.progetto.animeuniverse.model.Result;
import com.progetto.animeuniverse.repository.anime_movie.IAnimeMovieRepositoryWithLiveData;

public class AnimeMovieViewModel extends ViewModel {
    private static final String TAG = AnimeMovieViewModel.class.getSimpleName();
    private final IAnimeMovieRepositoryWithLiveData animeMovieRepositoryWithLiveData;
    private boolean isLoading;
    private boolean firstLoading;
    private int current_page;
    private int count;
    private MutableLiveData<Result> animeMovieListLiveData;
    private int currentResults;


    public AnimeMovieViewModel(IAnimeMovieRepositoryWithLiveData animeMovieRepositoryWithLiveData) {
        this.animeMovieRepositoryWithLiveData = animeMovieRepositoryWithLiveData;
        this.firstLoading = true;
        this.current_page = 1;
        this.count = 0;
    }

    public MutableLiveData<Result> getAnimeMovie(long lastUpdate){
        if(animeMovieListLiveData == null){
            fetchAnimeMovie(lastUpdate);
        }
        return animeMovieListLiveData;
    }

    public void fetchAnimeMovie(){
        animeMovieRepositoryWithLiveData.fetchAnimeMovie();
    }

    public void fetchAnimeMovie(long lastUpdate){
        animeMovieListLiveData = animeMovieRepositoryWithLiveData.fetchAnimeMovie(lastUpdate);
    }

    public IAnimeMovieRepositoryWithLiveData getAnimeMovieRepositoryWithLiveData() {
        return animeMovieRepositoryWithLiveData;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public boolean isFirstLoading() {
        return firstLoading;
    }

    public void setFirstLoading(boolean firstLoading) {
        this.firstLoading = firstLoading;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public MutableLiveData<Result> getAnimeMovieListLiveData() {
        return animeMovieListLiveData;
    }

    public void setAnimeMovieListLiveData(MutableLiveData<Result> animeMovieListLiveData) {
        this.animeMovieListLiveData = animeMovieListLiveData;
    }

    public int getCurrentResults() {
        return currentResults;
    }

    public void setCurrentResults(int currentResults) {
        this.currentResults = currentResults;
    }
}