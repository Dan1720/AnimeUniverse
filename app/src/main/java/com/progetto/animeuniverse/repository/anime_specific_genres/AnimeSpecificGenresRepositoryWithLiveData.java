package com.progetto.animeuniverse.repository.anime_specific_genres;

import static com.progetto.animeuniverse.util.Constants.FRESH_TIMEOUT;

import androidx.lifecycle.MutableLiveData;

import com.progetto.animeuniverse.data.source.anime_specific_genres.AnimeSpecificGenresCallback;
import com.progetto.animeuniverse.data.source.anime_specific_genres.BaseAnimeSpecificGenresLocalDataSource;
import com.progetto.animeuniverse.data.source.anime_specific_genres.BaseAnimeSpecificGenresRemoteDataSource;
import com.progetto.animeuniverse.model.AnimeSpecificGenres;
import com.progetto.animeuniverse.model.AnimeSpecificGenresApiResponse;
import com.progetto.animeuniverse.model.Result;

import java.util.List;

public class AnimeSpecificGenresRepositoryWithLiveData implements IAnimeSpecificGenresRepositoryWithLiveData, AnimeSpecificGenresCallback {
    private static final String TAG = AnimeSpecificGenresRepositoryWithLiveData.class.getSimpleName();

    private final MutableLiveData<Result> allAnimeSpecificGenresMutableLiveData;
    private final BaseAnimeSpecificGenresRemoteDataSource animeSpecificGenresRemoteDataSource;
    private final BaseAnimeSpecificGenresLocalDataSource animeSpecificGenresLocalDataSource;

    public AnimeSpecificGenresRepositoryWithLiveData(BaseAnimeSpecificGenresRemoteDataSource animeSpecificGenresRemoteDataSource, BaseAnimeSpecificGenresLocalDataSource animeSpecificGenresLocalDataSource) {
        allAnimeSpecificGenresMutableLiveData = new MutableLiveData<>();
        this.animeSpecificGenresRemoteDataSource = animeSpecificGenresRemoteDataSource;
        this.animeSpecificGenresLocalDataSource = animeSpecificGenresLocalDataSource;
        this.animeSpecificGenresLocalDataSource.setAnimeSpecificGenresCallback(this);
        this.animeSpecificGenresRemoteDataSource.setAnimeSpecificGenresCallback(this);
    }

    @Override
    public MutableLiveData<Result> fetchAnimeSpecificGenres(long lastUpdate) {
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastUpdate > FRESH_TIMEOUT){
            animeSpecificGenresRemoteDataSource.getAnimeSpecificGenres();
        }else{
            animeSpecificGenresLocalDataSource.getAnimeSpecificGenres();
        }
        return allAnimeSpecificGenresMutableLiveData;
    }

    @Override
    public void fetchAnimeSpecificGenres() {
        animeSpecificGenresRemoteDataSource.getAnimeSpecificGenres();
    }
    @Override
    public void onSuccessFromRemote(AnimeSpecificGenresApiResponse animeSpecificGenresApiResponse, long lastUpdate) {
        animeSpecificGenresLocalDataSource.insertAnimeSpecificGenres(animeSpecificGenresApiResponse);
    }

    @Override
    public void onFailureFromRemote(Exception exception) {
        Result.Error result = new Result.Error(exception.getMessage());
        allAnimeSpecificGenresMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessFromLocal(AnimeSpecificGenresApiResponse animeSpecificGenresApiResponse) {
        if(allAnimeSpecificGenresMutableLiveData.getValue() != null && allAnimeSpecificGenresMutableLiveData.getValue().isSuccess()){
            List<AnimeSpecificGenres> animeSpecificGenresList = ((Result.AnimeSpecificGenresSuccess) allAnimeSpecificGenresMutableLiveData.getValue()).getData().getAnimeSpecificGenresList();
            animeSpecificGenresApiResponse.setAnimeSpecificGenresList(animeSpecificGenresList);
            Result.AnimeSpecificGenresSuccess result = new Result.AnimeSpecificGenresSuccess(animeSpecificGenresApiResponse);
            allAnimeSpecificGenresMutableLiveData.postValue(result);
        }else {
            Result.AnimeSpecificGenresSuccess result = new Result.AnimeSpecificGenresSuccess(animeSpecificGenresApiResponse);
            allAnimeSpecificGenresMutableLiveData.postValue(result);
        }
    }

    @Override
    public void onFailureFromLocal(Exception exception) {
        Result.Error resultError = new Result.Error(exception.getMessage());
        allAnimeSpecificGenresMutableLiveData.postValue(resultError);
    }

    @Override
    public void onSuccessFromCloudReading(List<AnimeSpecificGenres> animeSpecificGenresList) {
        if(animeSpecificGenresList != null){
            for (AnimeSpecificGenres animeSpecificGenres : animeSpecificGenresList){
                animeSpecificGenres.setSynchronized(true);
            }
            animeSpecificGenresLocalDataSource.insertAnimeSpecificGenres(animeSpecificGenresList);
        }
    }

    @Override
    public void onSuccessFromCloudWriting(AnimeSpecificGenres animeSpecificGenres) {

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