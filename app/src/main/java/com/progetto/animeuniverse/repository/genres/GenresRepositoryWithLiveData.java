package com.progetto.animeuniverse.repository.genres;

import static com.progetto.animeuniverse.util.Constants.FRESH_TIMEOUT;

import androidx.lifecycle.MutableLiveData;

import com.progetto.animeuniverse.data.source.genres.BaseGenresLocalDataSource;
import com.progetto.animeuniverse.data.source.genres.BaseGenresRemoteDataSource;
import com.progetto.animeuniverse.data.source.genres.GenresCallback;
import com.progetto.animeuniverse.model.Genre;
import com.progetto.animeuniverse.model.GenresApiResponse;
import com.progetto.animeuniverse.model.Result;

import java.util.List;

public class GenresRepositoryWithLiveData implements IGenresRepositoryWithLiveData, GenresCallback {

    private static final String TAG = GenresRepositoryWithLiveData.class.getSimpleName();

    private final MutableLiveData<Result> allGenresMutableLiveData;
    private final BaseGenresRemoteDataSource genresRemoteDataSource;
    private final BaseGenresLocalDataSource genresLocalDataSource;

    public GenresRepositoryWithLiveData(BaseGenresRemoteDataSource genresRemoteDataSource, BaseGenresLocalDataSource genresLocalDataSource) {
        allGenresMutableLiveData = new MutableLiveData<>();
        this.genresRemoteDataSource = genresRemoteDataSource;
        this.genresLocalDataSource = genresLocalDataSource;
        this.genresLocalDataSource.setGenresCallback(this);
        this.genresRemoteDataSource.setGenresCallback(this);
    }
    @Override
    public MutableLiveData<Result> fetchGenres(long lastUpdate) {
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastUpdate > FRESH_TIMEOUT){
            genresRemoteDataSource.getGenres();
        }else{
            genresLocalDataSource.getGenre();
        }
        return allGenresMutableLiveData;
    }

    @Override
    public void fetchGenres() {
        genresRemoteDataSource.getGenres();
    }

    @Override
    public void onSuccessFromRemote(GenresApiResponse genresApiResponse, long lastUpdate) {
        genresLocalDataSource.insertGenres(genresApiResponse);
    }

    @Override
    public void onFailureFromRemote(Exception exception) {
        Result.Error result = new Result.Error(exception.getMessage());
        allGenresMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessFromLocal(GenresApiResponse genresApiResponse) {
        if(allGenresMutableLiveData.getValue() != null && allGenresMutableLiveData.getValue().isSuccess()){
            List<Genre> genresList = ((Result.GenresResponseSuccess) allGenresMutableLiveData.getValue()).getData().getGernesList();
            genresList.addAll(genresApiResponse.getGernesList());
            genresApiResponse.setGernesList(genresList);
            Result.GenresResponseSuccess result = new Result.GenresResponseSuccess(genresApiResponse);
            allGenresMutableLiveData.postValue(result);
        }else{
            Result.GenresResponseSuccess result = new Result.GenresResponseSuccess(genresApiResponse);
            allGenresMutableLiveData.postValue(result);
        }
    }

    @Override
    public void onFailureFromLocal(Exception exception) {
        Result.Error resultError = new Result.Error(exception.getMessage());
        allGenresMutableLiveData.postValue(resultError);
    }

    @Override
    public void onSuccessFromCloudReading(List<Genre> genresList) {
        if(genresList != null){
            for(Genre genre : genresList){
                genre.setSynchronized(true);
            }
            genresLocalDataSource.insertGenres(genresList);
        }
    }

    @Override
    public void onSuccessFromCloudWriting(Genre genre) {

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
