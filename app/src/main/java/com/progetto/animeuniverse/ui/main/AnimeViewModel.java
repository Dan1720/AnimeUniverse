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
    private MutableLiveData<Result> animeListLiveData;
    private MutableLiveData<Result> favoriteAnimeListLiveData;


    public AnimeViewModel(IAnimeRepositoryWithLiveData animeRepositoryWithLiveData) {
        this.animeRepositoryWithLiveData = animeRepositoryWithLiveData;
        this.firstLoading = true;
    }

    public MutableLiveData<Result> getAnimeByName(String q, String nameAnime, long lastUpdate){
        if(animeListLiveData == null){
            fetchAnimeByName(q, nameAnime, lastUpdate);
        }
        return animeListLiveData;
    }

    public MutableLiveData<Result> getAnimeByIdFull(String q, int id, long lastUpdate){
        if(animeListLiveData == null){
            fetchAnimeByIdFull(q, id, lastUpdate);
        }
        return animeListLiveData;
    }

    public MutableLiveData<Result> getAnimeById(String q, int id, long lastUpdate){
        if(animeListLiveData == null){
            fetchAnimeById(q, id, lastUpdate);
        }
        return animeListLiveData;
    }

    public void fetchAnimeByName(String q, String nameAnime){
        animeRepositoryWithLiveData.fetchAnimeByName(q, nameAnime);
    }

    public void fetchAnimeByName(String q, String nameAnime, long lastUpdate){
        animeListLiveData = animeRepositoryWithLiveData.fetchAnimeByName(q, nameAnime, lastUpdate);
    }

    public void fetchAnimeByIdFull(String q, int id){
        animeRepositoryWithLiveData.fetchAnimeByIdFull(q, id);
    }

    public void fetchAnimeByIdFull(String q, int id, long lastUpdate){
        animeListLiveData = animeRepositoryWithLiveData.fetchAnimeByIdFull(q, id, lastUpdate);
    }

    public void fetchAnimeById(String q, int id){
        animeRepositoryWithLiveData.fetchAnimeById(q, id);
    }

    public void fetchAnimeById(String q, int id, long lastUpdate){
        animeListLiveData = animeRepositoryWithLiveData.fetchAnimeById(q, id, lastUpdate);
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

    public MutableLiveData<Result> getAnimeResponseLiveData(){
        return animeListLiveData;
    }
}
