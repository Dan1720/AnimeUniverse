package com.progetto.animeuniverse.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.progetto.animeuniverse.model.Result;
import com.progetto.animeuniverse.repository.anime_specific_genres.IAnimeSpecificGenresRepositoryWithLiveData;

public class AnimeSpecificGenresViewModel extends ViewModel {
    private static final String TAG = AnimeSpecificGenresViewModel.class.getSimpleName();

    private final IAnimeSpecificGenresRepositoryWithLiveData animeSpecificGenresRepositoryWithLiveData;
    private boolean isLoading;
    private boolean firstLoading;
    private int current_page;
    private int count;
    private MutableLiveData<Result> animeSpecificGenresListLiveData;
    private int currentResults;


    public AnimeSpecificGenresViewModel(IAnimeSpecificGenresRepositoryWithLiveData animeSpecificGenresRepositoryWithLiveData) {
        this.animeSpecificGenresRepositoryWithLiveData = animeSpecificGenresRepositoryWithLiveData;
        this.firstLoading = true;
        this.current_page = 1;
        this.count = 0;
    }

    public MutableLiveData<Result> getAnimeSpecificGenres(int idGenre, long lastUpdate){
        if(animeSpecificGenresListLiveData == null){
            fetchAnimeSpecificGenres(idGenre, lastUpdate);
        }
        return animeSpecificGenresListLiveData;
    }

    private void fetchAnimeSpecificGenres(int idGenre, long lastUpdate){
        animeSpecificGenresListLiveData = animeSpecificGenresRepositoryWithLiveData.fetchAnimeSpecificGenres(idGenre, lastUpdate);
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

    public MutableLiveData<Result> getAnimeSpecificGenresListLiveData() {
        return animeSpecificGenresListLiveData;
    }

    public void setAnimeSpecificGenresListLiveData(MutableLiveData<Result> animeSpecificGenresListLiveData) {
        this.animeSpecificGenresListLiveData = animeSpecificGenresListLiveData;
    }

    public int getCurrentResults() {
        return currentResults;
    }

    public void setCurrentResults(int currentResults) {
        this.currentResults = currentResults;
    }
}