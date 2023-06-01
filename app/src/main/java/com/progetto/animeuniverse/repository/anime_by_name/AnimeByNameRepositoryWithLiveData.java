package com.progetto.animeuniverse.repository.anime_by_name;

import static com.progetto.animeuniverse.util.Constants.FRESH_TIMEOUT;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.progetto.animeuniverse.data.source.anime_by_name.AnimeByNameCallback;
import com.progetto.animeuniverse.data.source.anime_by_name.BaseAnimeByNameLocalDataSource;
import com.progetto.animeuniverse.data.source.anime_by_name.BaseAnimeByNameRemoteDataSource;
import com.progetto.animeuniverse.database.AnimeByNameDao;
import com.progetto.animeuniverse.model.Anime;
import com.progetto.animeuniverse.model.AnimeByName;
import com.progetto.animeuniverse.model.AnimeByNameApiResponse;
import com.progetto.animeuniverse.model.Result;

import java.util.List;

public class AnimeByNameRepositoryWithLiveData implements IAnimeByNameRepositoryWithLiveData, AnimeByNameCallback {
    private static final String TAG = AnimeByNameRepositoryWithLiveData.class.getSimpleName();

    private final MutableLiveData<Result> allAnimeByNameMutableLiveData;
    private final BaseAnimeByNameRemoteDataSource animeByNameRemoteDataSource;
    private final BaseAnimeByNameLocalDataSource animeByNameLocalDataSource;
    private AnimeByNameDao animeByNameDao;


    public AnimeByNameRepositoryWithLiveData(BaseAnimeByNameRemoteDataSource animeByNameRemoteDataSource, BaseAnimeByNameLocalDataSource animeByNameLocalDataSource) {
        allAnimeByNameMutableLiveData = new MutableLiveData<>();
        this.animeByNameRemoteDataSource = animeByNameRemoteDataSource;
        this.animeByNameLocalDataSource = animeByNameLocalDataSource;
        this.animeByNameLocalDataSource.setAnimeByNameCallback(this);
        this.animeByNameRemoteDataSource.setAnimeByNameCallback(this);
    }
    public AnimeByNameRepositoryWithLiveData(AnimeByNameDao animeByNameDao){
        this.animeByNameDao = animeByNameDao;

    }


    @Override
    public MutableLiveData<Result> fetchAnimeByName(String nameAnime,long lastUpdate) {
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastUpdate > FRESH_TIMEOUT){
            animeByNameRemoteDataSource.getAnimeByName(nameAnime);
        }else{
            animeByNameLocalDataSource.getAnimeByName();
        }
        return allAnimeByNameMutableLiveData;
    }

    @Override
    public void fetchAnimeByName(String nameAnime) {
        animeByNameRemoteDataSource.getAnimeByName(nameAnime);
    }
    @Override
    public void onSuccessFromRemote(AnimeByNameApiResponse animeByNameApiResponse, long lastUpdate) {
        animeByNameLocalDataSource.insertAnimeByName(animeByNameApiResponse);
    }

    @Override
    public void onFailureFromRemote(Exception exception) {
        Result.Error result = new Result.Error(exception.getMessage());
        allAnimeByNameMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessFromLocal(AnimeByNameApiResponse animeByNameApiResponse) {
        if(allAnimeByNameMutableLiveData.getValue() != null && allAnimeByNameMutableLiveData.getValue().isSuccess()){
            List<AnimeByName> animeByNameList = ((Result.AnimeByNameSuccess) allAnimeByNameMutableLiveData.getValue()).getData().getAnimeByNameList();
            animeByNameApiResponse.setAnimeByNameList(animeByNameList);
            Result.AnimeByNameSuccess result = new Result.AnimeByNameSuccess(animeByNameApiResponse);
            allAnimeByNameMutableLiveData.postValue(result);
        }else {
            Result.AnimeByNameSuccess result = new Result.AnimeByNameSuccess(animeByNameApiResponse);
            allAnimeByNameMutableLiveData.postValue(result);
        }
    }

    @Override
    public void onFailureFromLocal(Exception exception) {
        Result.Error resultError = new Result.Error(exception.getMessage());
        allAnimeByNameMutableLiveData.postValue(resultError);
    }

    @Override
    public void onSuccessFromCloudReading(List<AnimeByName> animeByNameList) {
        if(animeByNameList != null){
            for (AnimeByName animeByName : animeByNameList){
                animeByName.setSynchronized(true);
            }
            animeByNameLocalDataSource.insertAnimeByName(animeByNameList);
        }
    }

    @Override
    public void onSuccessFromCloudWriting(AnimeByName animeByName) {

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
    private static class deleteAllWordsAsyncTask extends AsyncTask<Void, Void, Void> {
        private AnimeByNameDao mAsyncTaskDao;

        deleteAllWordsAsyncTask(AnimeByNameDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
    public void deleteAll()  {
        new deleteAllWordsAsyncTask(animeByNameDao).execute();
    }



}
