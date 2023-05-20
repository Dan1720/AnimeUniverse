package com.progetto.animeuniverse.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.progetto.animeuniverse.model.Result;
import com.progetto.animeuniverse.repository.genres.IGenresRepositoryWithLiveData;


public class GenresViewModel extends ViewModel {
    private static final String TAG = GenresViewModel.class.getSimpleName();
    private final IGenresRepositoryWithLiveData genresRepositoryWithLiveData;

    private boolean isLoading;
    private boolean firstLoading;
    private int current_page;
    private int count;

    private MutableLiveData<Result> genresListLiveData;

    private int currentResults;

    public GenresViewModel(IGenresRepositoryWithLiveData iGenresRepositoryWithLiveData){
        this.genresRepositoryWithLiveData = iGenresRepositoryWithLiveData;
        this.firstLoading = true;
        this.current_page = 1;
        this.count = 0;
    }

    public MutableLiveData<Result> getGenres(long lastUpdate){
        if(genresListLiveData == null){
            fetchGenres(lastUpdate);
        }
        return genresListLiveData;
    }

    private void fetchGenres(long lastUpdate){
        genresListLiveData = genresRepositoryWithLiveData.fetchGenres(lastUpdate);
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

    public MutableLiveData<Result> getGenresListLiveData() {
        return genresListLiveData;
    }

    public void setGenresListLiveData(MutableLiveData<Result> genresListLiveData) {
        this.genresListLiveData = genresListLiveData;
    }

    public int getCurrentResults() {
        return currentResults;
    }

    public void setCurrentResults(int currentResults) {
        this.currentResults = currentResults;
    }
}
