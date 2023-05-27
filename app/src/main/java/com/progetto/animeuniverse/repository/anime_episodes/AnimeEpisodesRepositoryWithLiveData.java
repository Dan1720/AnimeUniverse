package com.progetto.animeuniverse.repository.anime_episodes;

import static com.progetto.animeuniverse.util.Constants.FRESH_TIMEOUT;

import androidx.lifecycle.MutableLiveData;

import com.progetto.animeuniverse.data.source.anime_episodes.AnimeEpisodesCallback;
import com.progetto.animeuniverse.data.source.anime_episodes.BaseAnimeEpisodesLocalDataSource;
import com.progetto.animeuniverse.data.source.anime_episodes.BaseAnimeEpisodesRemoteDataSource;
import com.progetto.animeuniverse.model.AnimeByName;
import com.progetto.animeuniverse.model.AnimeEpisodes;
import com.progetto.animeuniverse.model.AnimeEpisodesApiResponse;
import com.progetto.animeuniverse.model.Result;
import com.progetto.animeuniverse.repository.anime_by_name.AnimeByNameRepositoryWithLiveData;
import com.progetto.animeuniverse.repository.anime_new.IAnimeNewRepositoryWithLiveData;

import java.util.List;

public class AnimeEpisodesRepositoryWithLiveData implements IAnimeEpisodesRepositoryWithLiveData, AnimeEpisodesCallback {

    private static final String TAG = AnimeEpisodesRepositoryWithLiveData.class.getSimpleName();

    private final MutableLiveData<Result> allAnimeEpisodesMutableLiveData;
    private final BaseAnimeEpisodesRemoteDataSource animeEpisodesRemoteDataSource;
    private final BaseAnimeEpisodesLocalDataSource animeEpisodesLocalDataSource;

    public AnimeEpisodesRepositoryWithLiveData(BaseAnimeEpisodesRemoteDataSource animeEpisodesRemoteDataSource, BaseAnimeEpisodesLocalDataSource animeEpisodesLocalDataSource) {
        allAnimeEpisodesMutableLiveData = new MutableLiveData<>();
        this.animeEpisodesRemoteDataSource = animeEpisodesRemoteDataSource;
        this.animeEpisodesLocalDataSource = animeEpisodesLocalDataSource;
        this.animeEpisodesRemoteDataSource.setAnimeEpisodesCallback(this);
        this.animeEpisodesLocalDataSource.setAnimeEpisodesCallback(this);
    }

    @Override
    public MutableLiveData<Result> fetchAnimeEpisodes(int idAnime, long lastUpdate) {
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastUpdate > FRESH_TIMEOUT){
            animeEpisodesRemoteDataSource.getAnimeEpisodes(idAnime);
        }else{
            animeEpisodesLocalDataSource.getAnimeEpisodes();
        }
        return allAnimeEpisodesMutableLiveData;
    }

    @Override
    public void fetchAnimeEpisodes(int idAnime) {
        animeEpisodesRemoteDataSource.getAnimeEpisodes(idAnime);
    }

    @Override
    public void onSuccessFromRemote(AnimeEpisodesApiResponse animeEpisodesApiResponse, long lastUpdate) {
        animeEpisodesLocalDataSource.insertAnimeEpisodes(animeEpisodesApiResponse);
    }

    @Override
    public void onFailureFromRemote(Exception exception) {
        Result.Error result = new Result.Error(exception.getMessage());
        allAnimeEpisodesMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessFromLocal(AnimeEpisodesApiResponse animeEpisodesApiResponse) {
        if(allAnimeEpisodesMutableLiveData.getValue() != null && allAnimeEpisodesMutableLiveData.getValue().isSuccess()){
            List<AnimeEpisodes> animeEpisodesList = ((Result.AnimeEpisodesSuccess) allAnimeEpisodesMutableLiveData.getValue()).getData().getAnimeEpisodesList();
            animeEpisodesApiResponse.setAnimeEpisodesList(animeEpisodesList);
            Result.AnimeEpisodesSuccess result = new Result.AnimeEpisodesSuccess(animeEpisodesApiResponse);
            allAnimeEpisodesMutableLiveData.postValue(result);
        }else {
            Result.AnimeEpisodesSuccess result = new Result.AnimeEpisodesSuccess(animeEpisodesApiResponse);
            allAnimeEpisodesMutableLiveData.postValue(result);
        }
    }

    @Override
    public void onFailureFromLocal(Exception exception) {
        Result.Error resultError = new Result.Error(exception.getMessage());
        allAnimeEpisodesMutableLiveData.postValue(resultError);
    }

    @Override
    public void onSuccessFromCloudReading(List<AnimeEpisodes> animeEpisodesList) {
        if(animeEpisodesList != null){
            for (AnimeEpisodes animeEpisodes : animeEpisodesList){
                animeEpisodes.setSynchronized(true);
            }
            animeEpisodesLocalDataSource.insertAnimeEpisodes(animeEpisodesList);
        }
    }

    @Override
    public void onSuccessFromCloudWriting(AnimeEpisodes animeEpisodes) {

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
