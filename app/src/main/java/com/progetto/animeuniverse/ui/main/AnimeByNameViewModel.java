package com.progetto.animeuniverse.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.progetto.animeuniverse.model.Result;
import com.progetto.animeuniverse.repository.anime_by_name.IAnimeByNameRepositoryWithLiveData;

public class AnimeByNameViewModel extends ViewModel {
    private static final String TAG = AnimeByNameViewModel.class.getSimpleName();

    private final IAnimeByNameRepositoryWithLiveData animeByNameRepositoryWithLiveData;
    private boolean isLoading;
    private boolean firstLoading;
    private int current_page;
    private int count;
    private MutableLiveData<Result> animeByNameListLiveData;
    private int currentResults;


    public AnimeByNameViewModel(IAnimeByNameRepositoryWithLiveData animeByNameRepositoryWithLiveData) {
        this.animeByNameRepositoryWithLiveData = animeByNameRepositoryWithLiveData;
        this.firstLoading = true;
        this.current_page = 1;
        this.count = 0;
    }

    public MutableLiveData<Result> getAnimeByName(String nameAnime, long lastUpdate){
        if(animeByNameListLiveData == null){
            fetchAnimeByName(nameAnime, lastUpdate);
        }
        return animeByNameListLiveData;
    }

    private void fetchAnimeByName(String nameAnime, long lastUpdate){
        animeByNameListLiveData = animeByNameRepositoryWithLiveData.fetchAnimeByName(nameAnime, lastUpdate);
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

    public MutableLiveData<Result> getAnimeByNameListLiveData() {
        return animeByNameListLiveData;
    }

    public void setAnimeByNameListLiveData(MutableLiveData<Result> animeByNameListLiveData) {
        this.animeByNameListLiveData = animeByNameListLiveData;
    }

    public int getCurrentResults() {
        return currentResults;
    }

    public void setCurrentResults(int currentResults) {
        this.currentResults = currentResults;
    }
}
