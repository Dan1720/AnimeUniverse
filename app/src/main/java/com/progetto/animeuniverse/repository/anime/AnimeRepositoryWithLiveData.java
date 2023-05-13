package com.progetto.animeuniverse.repository.anime;

import static com.progetto.animeuniverse.util.Constants.FRESH_TIMEOUT;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.progetto.animeuniverse.data.source.anime.AnimeCallback;
import com.progetto.animeuniverse.data.source.anime.BaseAnimeLocalDataSource;
import com.progetto.animeuniverse.data.source.anime.BaseAnimeRemoteDataSource;
import com.progetto.animeuniverse.data.source.anime.BaseFavoriteAnimeDataSource;
import com.progetto.animeuniverse.model.Anime;
import com.progetto.animeuniverse.model.AnimeApiResponse;
import com.progetto.animeuniverse.model.AnimeResponse;
import com.progetto.animeuniverse.model.Result;

import java.util.ArrayList;
import java.util.List;

public class AnimeRepositoryWithLiveData implements IAnimeRepositoryWithLiveData, AnimeCallback {

    private static final String TAG = AnimeRepositoryWithLiveData.class.getSimpleName();

    private final MutableLiveData<Result> allAnimeMutableLiveData;
    private final MutableLiveData<Result> favoriteAnimeMutableLiveData;
    private final BaseAnimeRemoteDataSource animeRemoteDataSource;
    private final BaseAnimeLocalDataSource animeLocalDataSource;
    private final BaseFavoriteAnimeDataSource favoriteAnimeDataSource;

    public AnimeRepositoryWithLiveData(BaseAnimeRemoteDataSource animeRemoteDataSource,
                                       BaseAnimeLocalDataSource animeLocalDataSource,
                                       BaseFavoriteAnimeDataSource favoriteAnimeDataSource) {
        allAnimeMutableLiveData = new MutableLiveData<>();
        favoriteAnimeMutableLiveData = new MutableLiveData<>();
        this.animeRemoteDataSource = animeRemoteDataSource;
        this.animeLocalDataSource = animeLocalDataSource;
        this.favoriteAnimeDataSource = favoriteAnimeDataSource;
        this.animeRemoteDataSource.setAnimeCallback(this);
        this.animeLocalDataSource.setAnimeCallback(this);
        this.favoriteAnimeDataSource.setAnimeCallback(this);
    }


    @Override
    public void fetchAnimeByName(String nameAnime) {
        animeRemoteDataSource.getAnimeByName(nameAnime);
    }

    @Override
    public void fetchAnimeByIdFull(String q, int id) {
        animeRemoteDataSource.getAnimeByIdFull(q, id);
    }

    @Override
    public void fetchAnimeById(String q, int id) {
        animeRemoteDataSource.getAnimeById(q, id);
    }

    @Override
    public void fetchAnimeTop() {
        animeRemoteDataSource.getAnimeTop();
    }

    @Override
    public MutableLiveData<Result> fetchAnimeByName(String nameAnime, long lastUpdate) {
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastUpdate > FRESH_TIMEOUT){
            animeRemoteDataSource.getAnimeByName(nameAnime);
        }else{
            animeLocalDataSource.getAnime();
        }
        return allAnimeMutableLiveData;
    }

    @Override
    public MutableLiveData<Result> fetchAnimeByIdFull(String q, int id, long lastUpdate) {
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastUpdate > FRESH_TIMEOUT){
            animeRemoteDataSource.getAnimeByIdFull(q, id);
        }else{
            animeLocalDataSource.getAnime();
        }
        return allAnimeMutableLiveData;
    }

    @Override
    public MutableLiveData<Result> fetchAnimeById(String q, int id, long lastUpdate) {
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastUpdate > FRESH_TIMEOUT){
            animeRemoteDataSource.getAnimeById(q, id);
        }else{
            animeLocalDataSource.getAnime();
        }
        return allAnimeMutableLiveData;
    }

    @Override
    public MutableLiveData<Result> fetchAnimeTop(long lastUpdate) {
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastUpdate > FRESH_TIMEOUT){
            animeRemoteDataSource.getAnimeTop();
            /*String animeApiResponse = new AnimeApiResponse().toString();
            System.out.println("Risposta:" + animeApiResponse);
            String animeResponse = new AnimeResponse().toString();
            System.out.println("Risposta:" + animeResponse);*/

        }else{
            animeLocalDataSource.getAnime();
        }
        return allAnimeMutableLiveData;
    }

    @Override
    public MutableLiveData<Result> getFavoriteAnime(boolean firstLoading) {
        if(firstLoading){
            favoriteAnimeDataSource.getFavoriteAnime();
        }else {
            animeLocalDataSource.getFavoriteAnime();
        }
        return favoriteAnimeMutableLiveData;
    }

    @Override
    public void updateAnime(Anime anime) {
        animeLocalDataSource.updateAnime(anime);
        //    if(anime.isFavorite()){
            favoriteAnimeDataSource.addFavoriteAnime(anime);
        //  }else {
        //    favoriteAnimeDataSource.deleteFavoriteAnime(anime);
        //  }
    }

    @Override
    public void onSuccessFromRemote(AnimeApiResponse animeApiResponse, long lastUpdate) {
        animeLocalDataSource.insertAnime(animeApiResponse);
    }

    @Override
    public void onFailureFromRemote(Exception exception) {
        Result.Error result = new Result.Error(exception.getMessage());
        allAnimeMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessFromLocal(AnimeApiResponse animeApiResponse) {
        if(allAnimeMutableLiveData.getValue() != null && allAnimeMutableLiveData.getValue().isSuccess()){
            List<Anime> animeList = ((Result.AnimeResponseSuccess)allAnimeMutableLiveData.getValue()).getData().getAnimeList();
            animeList.addAll(animeApiResponse.getAnimeList());
            animeApiResponse.setAnimeList(animeList);
            Result.AnimeResponseSuccess result = new Result.AnimeResponseSuccess(animeApiResponse);
            allAnimeMutableLiveData.postValue(result);
        }else{
            Result.AnimeResponseSuccess result = new Result.AnimeResponseSuccess(animeApiResponse);
            allAnimeMutableLiveData.postValue(result);
        }
    }

    @Override
    public void onFailureFromLocal(Exception exception) {
        Result.Error resultError = new Result.Error(exception.getMessage());
        allAnimeMutableLiveData.postValue(resultError);
        favoriteAnimeMutableLiveData.postValue(resultError);
    }

    @Override
    public void onAnimeFavoriteStatusChanged(Anime anime, List<Anime> favoriteAnime) {
        Result allAnimeResult = allAnimeMutableLiveData.getValue();

        if(allAnimeResult != null && allAnimeResult.isSuccess()){
            List<Anime> oldAllAnime = ((Result.AnimeResponseSuccess)allAnimeResult).getData().getAnimeList();
            if(oldAllAnime.contains(anime)) {
                oldAllAnime.set(oldAllAnime.indexOf(anime), anime);
                allAnimeMutableLiveData.postValue(allAnimeResult);
            }
        }
        favoriteAnimeMutableLiveData.postValue(new Result.AnimeResponseSuccess(new AnimeResponse(favoriteAnime)));
    }

    @Override
    public void onAnimeFavoriteStatusChanged(List<Anime> animeList) {
        List<Anime> notSynchronizedAnimeList = new ArrayList<>();
        for(Anime anime : animeList){
            if(!anime.isSynchronized()){
                notSynchronizedAnimeList.add(anime);
            }
          }
    //   if(!notSynchronizedAnimeList.isEmpty()){
    //      favoriteAnimeDataSource.synchronizeFavoriteAnime(notSynchronizedAnimeList);
    //    }
    //  favoriteAnimeMutableLiveData.postValue(new Result.AnimeResponseSuccess(new AnimeResponse(animeList)));
    }

    @Override
    public void onDeleteFavoriteAnimeSuccess(List<Anime> favoriteAnime) {
        Result allAnimeResult = allAnimeMutableLiveData.getValue();
        if(allAnimeResult != null && allAnimeResult.isSuccess()){
            List<Anime> oldAllAnime = ((Result.AnimeResponseSuccess)allAnimeResult).getData().getAnimeList();
            for (Anime anime : favoriteAnime){
                if(oldAllAnime.contains(anime)){
                    oldAllAnime.set(oldAllAnime.indexOf(anime), anime);
                }
            }
            allAnimeMutableLiveData.postValue(allAnimeResult);
        }

        if(favoriteAnimeMutableLiveData.getValue() != null &&
                favoriteAnimeMutableLiveData.getValue().isSuccess()){
            favoriteAnime.clear();
            Result.AnimeResponseSuccess result = new Result.AnimeResponseSuccess(new AnimeResponse( favoriteAnime));
            favoriteAnimeMutableLiveData.postValue(result);
        }
        favoriteAnimeDataSource.deleteAllFavoriteAnime();
    }

    @Override
    public void onSuccessFromCloudReading(List<Anime> animeList) {
        if(animeList != null){
            for(Anime anime : animeList){
                  anime.setSynchronized(true);
            }
            animeLocalDataSource.insertAnime(animeList);
            favoriteAnimeMutableLiveData.postValue(new Result.AnimeResponseSuccess(new AnimeResponse( animeList)));
        }
    }

    @Override
    public void onSuccessFromCloudWriting(Anime anime) {
        // if(anime != null && !anime.isFavorite()){
        //     anime.setSynchronized(false);
        //  }
    //   animeLocalDataSource.updateAnime(anime);
    //  favoriteAnimeDataSource.getFavoriteAnime();
    }

    @Override
    public void onFailureFromCloud(Exception exception) {

    }

    @Override
    public void onSuccessSynchronization() {
        Log.d(TAG, "News synchronized from remote");
    }

    @Override
    public void onSuccessDeletion() {

    }





    @Override
    public void deleteFavoriteAnime() {
        animeLocalDataSource.deleteFavoriteAnime();
    }
}
