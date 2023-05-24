package com.progetto.animeuniverse.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.progetto.animeuniverse.model.Result;
import com.progetto.animeuniverse.repository.anime_recommendations.IAnimeRecommendationsRepositoryWithLiveData;

public class AnimeRecommendationsViewModel extends ViewModel {
    private static final String TAG = AnimeRecommendationsViewModel.class.getSimpleName();
    private final IAnimeRecommendationsRepositoryWithLiveData animeRecommendationsRepositoryWithLiveData;
    private boolean isLoading;
    private boolean firstLoading;
    private int current_page;
    private int count;
    private MutableLiveData<Result> animeRecommendationsListLiveData;
    private int currentResults;


    public AnimeRecommendationsViewModel(IAnimeRecommendationsRepositoryWithLiveData animeRecommendationsRepositoryWithLiveData) {
        this.animeRecommendationsRepositoryWithLiveData = animeRecommendationsRepositoryWithLiveData;
        this.firstLoading = true;
        this.current_page = 1;
        this.count = 0;
    }

    public MutableLiveData<Result> getAnimeRecommendations(long lastUpdate){
        if(animeRecommendationsListLiveData == null){
            fetchAnimeRecommendations(lastUpdate);
        }
        return animeRecommendationsListLiveData;
    }

    public void fetchAnimeRecommendations(){
        animeRecommendationsRepositoryWithLiveData.fetchAnimeRecommendations();
    }

    public void fetchAnimeRecommendations(long lastUpdate){
        animeRecommendationsListLiveData = animeRecommendationsRepositoryWithLiveData.fetchAnimeRecommendations(lastUpdate);
    }

    public IAnimeRecommendationsRepositoryWithLiveData getAnimeRecommendationsRepositoryWithLiveData() {
        return animeRecommendationsRepositoryWithLiveData;
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

    public MutableLiveData<Result> getAnimeRecommendationsListLiveData() {
        return animeRecommendationsListLiveData;
    }

    public void setAnimeRecommendationsListLiveData(MutableLiveData<Result> animeRecommendationsListLiveData) {
        this.animeRecommendationsListLiveData = animeRecommendationsListLiveData;
    }

    public int getCurrentResults() {
        return currentResults;
    }

    public void setCurrentResults(int currentResults) {
        this.currentResults = currentResults;
    }
}
