package com.progetto.animeuniverse.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.model.Anime;
import com.progetto.animeuniverse.model.Result;
import com.progetto.animeuniverse.repository.anime.IAnimeRepositoryWithLiveData;

public class AnimeViewModel extends ViewModel {
    private static final String TAG = AnimeViewModel.class.getSimpleName();

    private final IAnimeRepositoryWithLiveData animeRepositoryWithLiveData;
    private String nameAnime;
    private boolean isLoading;
    private boolean firstLoading;
    private int current_page;
    private int count;

    private MutableLiveData<Result> animeListLiveData;
    private MutableLiveData<Result> favoriteAnimeListLiveData;

    private int currentResults;


    public AnimeViewModel(IAnimeRepositoryWithLiveData iAnimeRepositoryWithLiveData) {
        this.animeRepositoryWithLiveData = iAnimeRepositoryWithLiveData;
        this.firstLoading = true;
        this.current_page = 1;
        this.count = 0;
    }

    public MutableLiveData<Result> getAnimeTop(long lastUpdate){
        if(animeListLiveData == null){
            fetchAnimeTop(lastUpdate);
        }
        return animeListLiveData;
    }

    public void fetchAnimeTop(){
        animeRepositoryWithLiveData.fetchAnimeTop();
    }

    private void fetchAnimeTop(long lastUpdate){
        animeListLiveData = animeRepositoryWithLiveData.fetchAnimeTop(lastUpdate);
    }



    public MutableLiveData<Result> getFavoriteAnimeLiveData(boolean isFirstLoading){
        if(favoriteAnimeListLiveData == null){
            getFavoriteAnime(isFirstLoading);
        }
        return favoriteAnimeListLiveData;
    }

    private void getFavoriteAnime(boolean firstLoading)  {
        favoriteAnimeListLiveData = animeRepositoryWithLiveData.getFavoriteAnime(firstLoading);
    }

    public int getCurrentResults() {
        return currentResults;
    }

    public void setCurrentResults(int currentResults) {
        this.currentResults = currentResults;
    }

    public void updateAnime(Anime anime){
        animeRepositoryWithLiveData.updateAnime(anime);
    }

    public void removeFromFavorite(Anime anime){
        animeRepositoryWithLiveData.updateAnime(anime);
    }

    public void deleteAllFavoriteAnime(){
        animeRepositoryWithLiveData.deleteFavoriteAnime();
    }

    public String getNameAnime() {
        return nameAnime;
    }

    public void setNameAnime(String nameAnime) {
        this.nameAnime = nameAnime;
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

    public MutableLiveData<Result> getAnimeResponseLiveData(){
        return animeListLiveData;
    }
}
