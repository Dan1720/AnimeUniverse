package com.progetto.animeuniverse.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.progetto.animeuniverse.model.Result;
import com.progetto.animeuniverse.repository.anime_episodes_images.IAnimeEpisodesImagesRepositoryWithLiveData;

public class AnimeEpisodesImagesViewModel extends ViewModel {
    private static final String TAG = AnimeEpisodesImagesViewModel.class.getSimpleName();

    private final IAnimeEpisodesImagesRepositoryWithLiveData animeEpisodesImagesRepositoryWithLiveData;
    private boolean isLoading;
    private boolean firstLoading;
    private int current_page;
    private int count;
    private MutableLiveData<Result> animeEpisodesImagesListLiveData;
    private int currentResults;

    public AnimeEpisodesImagesViewModel(IAnimeEpisodesImagesRepositoryWithLiveData animeEpisodesImagesRepositoryWithLiveData) {
        this.animeEpisodesImagesRepositoryWithLiveData = animeEpisodesImagesRepositoryWithLiveData;
        this.firstLoading = true;
        this.current_page = 1;
        this.count = 0;
    }

    public MutableLiveData<Result> getAnimeEpisodesImages(int idAnime, long lastUpdate){
        if(animeEpisodesImagesListLiveData == null){
            fetchAnimeEpisodesImages(idAnime, lastUpdate);
        }
        return animeEpisodesImagesListLiveData;
    }

    private void fetchAnimeEpisodesImages(int idAnime, long lastUpdate){
        animeEpisodesImagesListLiveData = animeEpisodesImagesRepositoryWithLiveData.fetchAnimeEpisodesImages(idAnime, lastUpdate);
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

    public MutableLiveData<Result> getAnimeEpisodesImagesListLiveData() {
        return animeEpisodesImagesListLiveData;
    }

    public void setAnimeEpisodesImagesListLiveData(MutableLiveData<Result> animeEpisodesImagesListLiveData) {
        this.animeEpisodesImagesListLiveData = animeEpisodesImagesListLiveData;
    }

    public int getCurrentResults() {
        return currentResults;
    }

    public void setCurrentResults(int currentResults) {
        this.currentResults = currentResults;
    }
}
