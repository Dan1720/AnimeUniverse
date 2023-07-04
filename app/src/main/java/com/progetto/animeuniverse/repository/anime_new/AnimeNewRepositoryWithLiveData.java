package com.progetto.animeuniverse.repository.anime_new;

import static com.progetto.animeuniverse.util.Constants.FRESH_TIMEOUT;

import androidx.lifecycle.MutableLiveData;

import com.progetto.animeuniverse.data.source.anime_new.AnimeNewCallback;
import com.progetto.animeuniverse.data.source.anime_new.BaseAnimeNewLocalDataSource;
import com.progetto.animeuniverse.data.source.anime_new.BaseAnimeNewRemoteDataSource;
import com.progetto.animeuniverse.model.AnimeNew;
import com.progetto.animeuniverse.model.AnimeNewApiResponse;
import com.progetto.animeuniverse.model.Result;

import java.util.List;

public class AnimeNewRepositoryWithLiveData implements IAnimeNewRepositoryWithLiveData, AnimeNewCallback {

    private static final String TAG = AnimeNewRepositoryWithLiveData.class.getSimpleName();
    private final MutableLiveData<Result> allAnimeNewMutableLiveData;
    private final BaseAnimeNewRemoteDataSource animeNewRemoteDataSource;
    private final BaseAnimeNewLocalDataSource animeNewLocalDataSource;

    public AnimeNewRepositoryWithLiveData(BaseAnimeNewRemoteDataSource animeNewRemoteDataSource, BaseAnimeNewLocalDataSource animeNewLocalDataSource) {
        this.allAnimeNewMutableLiveData = new MutableLiveData<>();
        this.animeNewRemoteDataSource = animeNewRemoteDataSource;
        this.animeNewLocalDataSource = animeNewLocalDataSource;
        this.animeNewRemoteDataSource.setAnimeNewCallback(this);
        this.animeNewLocalDataSource.setAnimeNewCallback(this);
    }

    @Override
    public MutableLiveData<Result> fetchAnimeNew(long lastUpdate) {
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastUpdate > FRESH_TIMEOUT){
            animeNewRemoteDataSource.getAnimeNew();
        }else{
            animeNewLocalDataSource.getAnimeNew();
        }
        return allAnimeNewMutableLiveData;
    }

    @Override
    public void fetchAnimeNew() {
        animeNewRemoteDataSource.getAnimeNew();
    }

    @Override
    public void onSuccessFromRemote(AnimeNewApiResponse animeNewApiResponse, long lastUpdate) {
        animeNewLocalDataSource.insertAnimeNew(animeNewApiResponse);
    }

    @Override
    public void onFailureFromRemote(Exception exception) {
        Result.Error result = new Result.Error(exception.getMessage());
        allAnimeNewMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessFromLocal(AnimeNewApiResponse animeNewApiResponse) {
        if(allAnimeNewMutableLiveData.getValue() != null && allAnimeNewMutableLiveData.getValue().isSuccess()){
            List<AnimeNew> animeNewList = ((Result.AnimeNewSuccess) allAnimeNewMutableLiveData.getValue()).getData().getAnimeNewList();
            animeNewApiResponse.setAnimeNewList(animeNewList);
            Result.AnimeNewSuccess result  = new Result.AnimeNewSuccess(animeNewApiResponse);
            allAnimeNewMutableLiveData.postValue(result);
        }else {
            Result.AnimeNewSuccess result = new Result.AnimeNewSuccess(animeNewApiResponse);
            allAnimeNewMutableLiveData.postValue(result);
        }
    }

    @Override
    public void onFailureFromLocal(Exception exception) {
        Result.Error resultError = new Result.Error(exception.getMessage());
        allAnimeNewMutableLiveData.postValue(resultError);
    }

    @Override
    public void onSuccessFromCloudReading(List<AnimeNew> animeNewList) {
        if(animeNewList != null){
            for(AnimeNew animeNew : animeNewList){
                animeNew.setSynchronized(true);
            }
            animeNewLocalDataSource.insertAnimeNew(animeNewList);
        }
    }

    @Override
    public void onSuccessFromCloudWriting(AnimeNew animeNew) {

    }

    @Override
    public void onFailureFromCloud(Exception exception) {

    }

    @Override
    public void onSuccessSynchronization() {

    }

    @Override
    public void onSuccessDeletion() {

    }


}
