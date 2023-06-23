package com.progetto.animeuniverse.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.progetto.animeuniverse.model.AnimeEpisodes;
import com.progetto.animeuniverse.model.Result;
import com.progetto.animeuniverse.repository.anime_episodes.IAnimeEpisodesRepositoryWithLiveData;

public class AnimeEpisodesViewModel extends ViewModel {
    private static final String TAG = AnimeEpisodesViewModel.class.getSimpleName();

    private final IAnimeEpisodesRepositoryWithLiveData animeEpisodesRepositoryWithLiveData;
    private boolean isLoading;
    private boolean firstLoading;
    private int current_page;
    private int count;
    private MutableLiveData<Result> animeEpisodesListLiveData;
    private int currentResults;

    public AnimeEpisodesViewModel(IAnimeEpisodesRepositoryWithLiveData animeEpisodesRepositoryWithLiveData) {
        this.animeEpisodesRepositoryWithLiveData = animeEpisodesRepositoryWithLiveData;
        this.firstLoading = true;
        this.current_page = 1;
        this.count = 0;
    }

    public MutableLiveData<Result> getAnimeEpisodes(int idAnime, long lastUpdate){
        /*if(animeEpisodesListLiveData == null){
            fetchAnimeEpisodes(idAnime, lastUpdate);
        }*/
        fetchAnimeEpisodes(idAnime, lastUpdate);
        return animeEpisodesListLiveData;
    }

    private void fetchAnimeEpisodes(int idAnime, long lastUpdate){
        animeEpisodesListLiveData = animeEpisodesRepositoryWithLiveData.fetchAnimeEpisodes(idAnime, lastUpdate);
        System.out.println("animeEpisodesListLiveData: " + animeEpisodesListLiveData.toString());

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

    public MutableLiveData<Result> getAnimeEpisodesListLiveData() {
        return animeEpisodesListLiveData;
    }

    public void setAnimeEpisodesListLiveData(MutableLiveData<Result> animeEpisodesListLiveData) {
        this.animeEpisodesListLiveData = animeEpisodesListLiveData;
    }

    public int getCurrentResults() {
        return currentResults;
    }

    public void setCurrentResults(int currentResults) {
        this.currentResults = currentResults;
    }

    public void onCleared(){
        animeEpisodesListLiveData = null;
    }
}
