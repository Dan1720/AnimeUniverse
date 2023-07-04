package com.progetto.animeuniverse.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.progetto.animeuniverse.model.Result;
import com.progetto.animeuniverse.repository.anime_new.IAnimeNewRepositoryWithLiveData;

public class AnimeNewViewModel extends ViewModel {

    private static final String TAG = AnimeNewViewModel.class.getSimpleName();

    private final IAnimeNewRepositoryWithLiveData animeNewRepositoryWithLiveData;
    private boolean isLoading;
    private boolean firstLoading;
    private int current_page;
    private int count;
    private MutableLiveData<Result> animeNewListLiveData;
    private int currentResults;

    public AnimeNewViewModel(IAnimeNewRepositoryWithLiveData animeNewRepositoryWithLiveData) {
        this.animeNewRepositoryWithLiveData = animeNewRepositoryWithLiveData;
        this.firstLoading = true;
        this.current_page = 1;
        this.count = 0;
    }

    public MutableLiveData<Result> getAnimeNew(long lastUpdate){
        if(animeNewListLiveData == null){
            fetchAnimeNew(lastUpdate);
        }
        return animeNewListLiveData;
    }

    public void fetchAnimeNew(){
        animeNewRepositoryWithLiveData.fetchAnimeNew();
    }

    public void fetchAnimeNew(long lastUpdate){
        animeNewListLiveData = animeNewRepositoryWithLiveData.fetchAnimeNew(lastUpdate);
    }

    public IAnimeNewRepositoryWithLiveData getAnimeNewRepositoryWithLiveData() {
        return animeNewRepositoryWithLiveData;
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

    public MutableLiveData<Result> getAnimeNewListLiveData() {
        return animeNewListLiveData;
    }

    public void setAnimeNewListLiveData(MutableLiveData<Result> animeNewListLiveData) {
        this.animeNewListLiveData = animeNewListLiveData;
    }

    public int getCurrentResults() {
        return currentResults;
    }

    public void setCurrentResults(int currentResults) {
        this.currentResults = currentResults;
    }
}
