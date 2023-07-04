package com.progetto.animeuniverse.repository.anime_movie;

import static com.progetto.animeuniverse.util.Constants.FRESH_TIMEOUT;

import androidx.lifecycle.MutableLiveData;

import com.progetto.animeuniverse.data.source.anime_movie.AnimeMovieCallback;
import com.progetto.animeuniverse.data.source.anime_movie.BaseAnimeMovieLocalDataSource;
import com.progetto.animeuniverse.data.source.anime_movie.BaseAnimeMovieRemoteDataSource;
import com.progetto.animeuniverse.model.AnimeMovie;
import com.progetto.animeuniverse.model.AnimeMovieApiResponse;
import com.progetto.animeuniverse.model.Result;

import java.util.List;

public class AnimeMovieRepositoryWithLiveData implements IAnimeMovieRepositoryWithLiveData, AnimeMovieCallback {
    private static final String TAG = AnimeMovieRepositoryWithLiveData.class.getSimpleName();

    private final MutableLiveData<Result> allAnimeMovieMutableLiveData;
    private final BaseAnimeMovieRemoteDataSource animeMovieRemoteDataSource;
    private final BaseAnimeMovieLocalDataSource animeMovieLocalDataSource;

    public AnimeMovieRepositoryWithLiveData(BaseAnimeMovieRemoteDataSource animeMovieRemoteDataSource, BaseAnimeMovieLocalDataSource animeMovieLocalDataSource) {
        this.allAnimeMovieMutableLiveData = new MutableLiveData<>();
        this.animeMovieRemoteDataSource = animeMovieRemoteDataSource;
        this.animeMovieLocalDataSource = animeMovieLocalDataSource;
        this.animeMovieRemoteDataSource.setAnimeMovieCallback(this);
        this.animeMovieLocalDataSource.setAnimeMovieCallback(this);
    }

    @Override
    public MutableLiveData<Result> fetchAnimeMovie(long lastUpdate) {
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastUpdate > FRESH_TIMEOUT){
            animeMovieRemoteDataSource.getAnimeMovie();
        }else{
            animeMovieLocalDataSource.getAnimeMovie();
        }
        return allAnimeMovieMutableLiveData;
    }

    @Override
    public void fetchAnimeMovie() {
        animeMovieRemoteDataSource.getAnimeMovie();

    }

    @Override
    public void onSuccessFromRemote(AnimeMovieApiResponse animeMovieApiResponse, long lastUpdate) {
        animeMovieLocalDataSource.insertAnimeMovie(animeMovieApiResponse);

    }

    @Override
    public void onFailureFromRemote(Exception exception) {
        Result.Error result = new Result.Error(exception.getMessage());
        allAnimeMovieMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessFromLocal(AnimeMovieApiResponse animeMovieApiResponse) {
        if(allAnimeMovieMutableLiveData.getValue() != null && allAnimeMovieMutableLiveData.getValue().isSuccess()){
            List<AnimeMovie> animeMovieList = ((Result.AnimeMovieSuccess) allAnimeMovieMutableLiveData.getValue()).getData().getAnimeMovieList();
            animeMovieApiResponse.setAnimeMovieList(animeMovieList);
            Result.AnimeMovieSuccess result  = new Result.AnimeMovieSuccess(animeMovieApiResponse);
            allAnimeMovieMutableLiveData.postValue(result);
        }else {
            Result.AnimeMovieSuccess result = new Result.AnimeMovieSuccess(animeMovieApiResponse);
            allAnimeMovieMutableLiveData.postValue(result);
        }
    }

    @Override
    public void onFailureFromLocal(Exception exception) {
        Result.Error resultError = new Result.Error(exception.getMessage());
        allAnimeMovieMutableLiveData.postValue(resultError);
    }

    @Override
    public void onSuccessFromCloudReading(List<AnimeMovie> animeMovieList) {
        if(animeMovieList != null){
            for(AnimeMovie animeMovie : animeMovieList){
                animeMovie.setSynchronized(true);
            }
            animeMovieLocalDataSource.insertAnimeMovie(animeMovieList);
        }
    }

    @Override
    public void onSuccessFromCloudWriting(AnimeMovie animeMovie) {

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

