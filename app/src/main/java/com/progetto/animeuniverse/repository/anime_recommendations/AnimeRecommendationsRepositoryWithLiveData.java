package com.progetto.animeuniverse.repository.anime_recommendations;

import static com.progetto.animeuniverse.util.Constants.FRESH_TIMEOUT;

import androidx.lifecycle.MutableLiveData;

import com.progetto.animeuniverse.data.source.anime_recommendations.AnimeRecommendationsCallback;
import com.progetto.animeuniverse.data.source.anime_recommendations.BaseAnimeRecommendationsLocalDataSource;
import com.progetto.animeuniverse.data.source.anime_recommendations.BaseAnimeRecommendationsRemoteDataSource;
import com.progetto.animeuniverse.model.AnimeRecommendations;
import com.progetto.animeuniverse.model.AnimeRecommendationsApiResponse;
import com.progetto.animeuniverse.model.Result;

import java.util.List;

public class AnimeRecommendationsRepositoryWithLiveData implements IAnimeRecommendationsRepositoryWithLiveData, AnimeRecommendationsCallback {
    private static final String TAG = AnimeRecommendationsRepositoryWithLiveData.class.getSimpleName();

    private final MutableLiveData<Result> allAnimeRecommendationsMutableLiveData;
    private final BaseAnimeRecommendationsRemoteDataSource animeRecommendationsRemoteDataSource;
    private final BaseAnimeRecommendationsLocalDataSource animeRecommendationsLocalDataSource;


    public AnimeRecommendationsRepositoryWithLiveData(BaseAnimeRecommendationsRemoteDataSource animeRecommendationsRemoteDataSource, BaseAnimeRecommendationsLocalDataSource animeRecommendationsLocalDataSource) {
        this.allAnimeRecommendationsMutableLiveData = new MutableLiveData<>();
        this.animeRecommendationsRemoteDataSource = animeRecommendationsRemoteDataSource;
        this.animeRecommendationsLocalDataSource = animeRecommendationsLocalDataSource;
        this.animeRecommendationsLocalDataSource.setAnimeRecommendationsCallback(this);
        this.animeRecommendationsRemoteDataSource.setAnimeRecommendationsCallback(this);
    }

    @Override
    public MutableLiveData<Result> fetchAnimeRecommendations(long lastUpdate) {
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastUpdate > FRESH_TIMEOUT){
            animeRecommendationsRemoteDataSource.getAnimeRecommendations();
        }else{
            animeRecommendationsLocalDataSource.getAnimeRecommendations();
        }
        return allAnimeRecommendationsMutableLiveData;
    }

    @Override
    public void fetchAnimeRecommendations() {
        animeRecommendationsRemoteDataSource.getAnimeRecommendations();
    }


    @Override
    public void onSuccessFromRemote(AnimeRecommendationsApiResponse animeRecommendationsApiResponse, long lastUpdate) {
        animeRecommendationsLocalDataSource.insertAnimeRecommendations(animeRecommendationsApiResponse);
    }

    @Override
    public void onFailureFromRemote(Exception exception) {
        Result.Error result = new Result.Error(exception.getMessage());
        allAnimeRecommendationsMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessFromLocal(AnimeRecommendationsApiResponse animeRecommendationsApiResponse) {
        if(allAnimeRecommendationsMutableLiveData.getValue() != null & allAnimeRecommendationsMutableLiveData.getValue().isSuccess()){
            List<AnimeRecommendations> animeRecommendationsList = ((Result.AnimeRecommendationsSuccess) allAnimeRecommendationsMutableLiveData.getValue()).getData().getAnimeRecommendationsList();
            animeRecommendationsApiResponse.setAnimeRecommendationsList(animeRecommendationsList);
            Result.AnimeRecommendationsSuccess result  = new Result.AnimeRecommendationsSuccess(animeRecommendationsApiResponse);
            allAnimeRecommendationsMutableLiveData.postValue(result);
        }else {
            Result.AnimeRecommendationsSuccess result = new Result.AnimeRecommendationsSuccess(animeRecommendationsApiResponse);
            allAnimeRecommendationsMutableLiveData.postValue(result);
        }
    }

    @Override
    public void onFailureFromLocal(Exception exception) {
        Result.Error resultError = new Result.Error(exception.getMessage());
        allAnimeRecommendationsMutableLiveData.postValue(resultError);
    }

    @Override
    public void onSuccessFromCloudReading(List<AnimeRecommendations> animeRecommendationsList) {
        if(animeRecommendationsList != null){
            for(AnimeRecommendations animeRecommendations : animeRecommendationsList){
                animeRecommendations.setSynchronized(true);
            }
            animeRecommendationsLocalDataSource.insertAnimeRecommendations(animeRecommendationsList);
        }
    }

    @Override
    public void onSuccessFromCloudWriting(AnimeRecommendations animeRecommendations) {

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
