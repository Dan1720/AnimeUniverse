package com.progetto.animeuniverse.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.progetto.animeuniverse.model.Result;
import com.progetto.animeuniverse.repository.anime_tv.IAnimeTvRepositoryWithLiveData;

public class AnimeTvViewModel extends ViewModel {
    private static final String TAG = AnimeTvViewModel.class.getSimpleName();
    private final IAnimeTvRepositoryWithLiveData animeTvRepositoryWithLiveData;
    private boolean isLoading;
    private boolean firstLoading;
    private int current_page;
    private int count;
    private MutableLiveData<Result> animeTvListLiveData;
    private int currentResults;


    public AnimeTvViewModel(IAnimeTvRepositoryWithLiveData animeTvRepositoryWithLiveData) {
        this.animeTvRepositoryWithLiveData = animeTvRepositoryWithLiveData;
        this.firstLoading = true;
        this.current_page = 1;
        this.count = 0;
    }

    public MutableLiveData<Result> getAnimeTv(long lastUpdate){
        if(animeTvListLiveData == null){
            fetchAnimeTv(lastUpdate);
        }
        return animeTvListLiveData;
    }

    public void fetchAnimeTv(){
        animeTvRepositoryWithLiveData.fetchAnimeTv();
    }

    public void fetchAnimeTv(long lastUpdate){
        animeTvListLiveData = animeTvRepositoryWithLiveData.fetchAnimeTv(lastUpdate);
    }

    public IAnimeTvRepositoryWithLiveData getAnimeTvRepositoryWithLiveData() {
        return animeTvRepositoryWithLiveData;
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

    public MutableLiveData<Result> getAnimeTvListLiveData() {
        return animeTvListLiveData;
    }

    public void setAnimeTvListLiveData(MutableLiveData<Result> animeTvListLiveData) {
        this.animeTvListLiveData = animeTvListLiveData;
    }

    public int getCurrentResults() {
        return currentResults;
    }

    public void setCurrentResults(int currentResults) {
        this.currentResults = currentResults;
    }
}
