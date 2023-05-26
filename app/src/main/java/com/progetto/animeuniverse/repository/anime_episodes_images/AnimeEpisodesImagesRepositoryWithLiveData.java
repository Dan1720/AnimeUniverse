package com.progetto.animeuniverse.repository.anime_episodes_images;

import static com.progetto.animeuniverse.util.Constants.FRESH_TIMEOUT;

import androidx.lifecycle.MutableLiveData;

import com.progetto.animeuniverse.data.source.anime_episodes_images.AnimeEpisodesImagesCallback;
import com.progetto.animeuniverse.data.source.anime_episodes_images.BaseAnimeEpisodesImagesLocalDataSource;
import com.progetto.animeuniverse.data.source.anime_episodes_images.BaseAnimeEpisodesImagesRemoteDataSource;
import com.progetto.animeuniverse.model.AnimeEpisodes;
import com.progetto.animeuniverse.model.AnimeEpisodesImages;
import com.progetto.animeuniverse.model.AnimeEpisodesImagesApiResponse;
import com.progetto.animeuniverse.model.Result;

import java.util.List;

public class AnimeEpisodesImagesRepositoryWithLiveData implements IAnimeEpisodesImagesRepositoryWithLiveData, AnimeEpisodesImagesCallback {
    private static final String TAG = AnimeEpisodesImagesRepositoryWithLiveData.class.getSimpleName();

    private final MutableLiveData<Result> allAnimeEpisodesImagesMutableLiveData;
    private final BaseAnimeEpisodesImagesRemoteDataSource animeEpisodesImagesRemoteDataSource;
    private final BaseAnimeEpisodesImagesLocalDataSource animeEpisodesImagesLocalDataSource;

    public AnimeEpisodesImagesRepositoryWithLiveData(BaseAnimeEpisodesImagesRemoteDataSource animeEpisodesImagesRemoteDataSource, BaseAnimeEpisodesImagesLocalDataSource animeEpisodesImagesLocalDataSource) {
        allAnimeEpisodesImagesMutableLiveData = new MutableLiveData<>();
        this.animeEpisodesImagesRemoteDataSource = animeEpisodesImagesRemoteDataSource;
        this.animeEpisodesImagesLocalDataSource = animeEpisodesImagesLocalDataSource;
        this.animeEpisodesImagesRemoteDataSource.setAnimeEpisodesImagesCallback(this);
        this.animeEpisodesImagesLocalDataSource.setAnimeEpisodesImagesCallback(this);
    }

    @Override
    public MutableLiveData<Result> fetchAnimeEpisodesImages(int idAnime, long lastUpdate) {

        long currentTime = System.currentTimeMillis();
        if(currentTime - lastUpdate > FRESH_TIMEOUT){
            animeEpisodesImagesRemoteDataSource.getAnimeEpisodesImages(idAnime);
        }else{
            animeEpisodesImagesLocalDataSource.getAnimeEpisodesImages();
        }
        return allAnimeEpisodesImagesMutableLiveData;
    }

    @Override
    public void fetchAnimeEpisodesImages(int idAnime) {
        animeEpisodesImagesRemoteDataSource.getAnimeEpisodesImages(idAnime);
    }

    @Override
    public void onSuccessFromRemote(AnimeEpisodesImagesApiResponse animeEpisodesImagesApiResponse, long lastUpdate) {
        animeEpisodesImagesLocalDataSource.insertAnimeEpisodesImages(animeEpisodesImagesApiResponse);

    }

    @Override
    public void onFailureFromRemote(Exception exception) {
        Result.Error result = new Result.Error(exception.getMessage());
        allAnimeEpisodesImagesMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessFromLocal(AnimeEpisodesImagesApiResponse animeEpisodesImagesApiResponse) {
        if(allAnimeEpisodesImagesMutableLiveData.getValue() != null && allAnimeEpisodesImagesMutableLiveData.getValue().isSuccess()){
            List<AnimeEpisodesImages> animeEpisodesImagesList = ((Result.AnimeEpisodesImagesSuccess) allAnimeEpisodesImagesMutableLiveData.getValue()).getData().getAnimeEpisodesImagesList();
            animeEpisodesImagesApiResponse.setAnimeEpisodesImagesList(animeEpisodesImagesList);
            Result.AnimeEpisodesImagesSuccess result = new Result.AnimeEpisodesImagesSuccess(animeEpisodesImagesApiResponse);
            allAnimeEpisodesImagesMutableLiveData.postValue(result);
        }else {
            Result.AnimeEpisodesImagesSuccess result = new Result.AnimeEpisodesImagesSuccess(animeEpisodesImagesApiResponse);
            allAnimeEpisodesImagesMutableLiveData.postValue(result);
        }
    }

    @Override
    public void onFailureFromLocal(Exception exception) {
        Result.Error resultError = new Result.Error(exception.getMessage());
        allAnimeEpisodesImagesMutableLiveData.postValue(resultError);
    }

    @Override
    public void onSuccessFromCloudReading(List<AnimeEpisodesImages> animeEpisodesImagesList) {
        if(animeEpisodesImagesList != null){
            for (AnimeEpisodesImages animeEpisodesImages : animeEpisodesImagesList){
                animeEpisodesImages.setSynchronized(true);
            }
            animeEpisodesImagesLocalDataSource.insertAnimeEpisodesImages(animeEpisodesImagesList);
        }
    }

    @Override
    public void onSuccessFromCloudWriting(AnimeEpisodesImages animeEpisodesImages) {

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
