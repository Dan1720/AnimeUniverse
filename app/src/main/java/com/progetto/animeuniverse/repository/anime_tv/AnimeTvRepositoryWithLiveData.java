package com.progetto.animeuniverse.repository.anime_tv;

import static com.progetto.animeuniverse.util.Constants.FRESH_TIMEOUT;

import androidx.lifecycle.MutableLiveData;

import com.progetto.animeuniverse.data.source.anime_tv.AnimeTvCallback;
import com.progetto.animeuniverse.data.source.anime_tv.BaseAnimeTvLocalDataSource;
import com.progetto.animeuniverse.data.source.anime_tv.BaseAnimeTvRemoteDataSource;
import com.progetto.animeuniverse.model.AnimeTv;
import com.progetto.animeuniverse.model.AnimeTvApiResponse;
import com.progetto.animeuniverse.model.Result;

import java.util.List;

public class AnimeTvRepositoryWithLiveData implements IAnimeTvRepositoryWithLiveData, AnimeTvCallback {
    private static final String TAG = AnimeTvRepositoryWithLiveData.class.getSimpleName();

    private final MutableLiveData<Result> allAnimeTvMutableLiveData;
    private final BaseAnimeTvRemoteDataSource animeTvRemoteDataSource;
    private final BaseAnimeTvLocalDataSource animeTvLocalDataSource;

    public AnimeTvRepositoryWithLiveData(BaseAnimeTvRemoteDataSource animeTvRemoteDataSource, BaseAnimeTvLocalDataSource animeTvLocalDataSource) {
        this.allAnimeTvMutableLiveData = new MutableLiveData<>();
        this.animeTvRemoteDataSource = animeTvRemoteDataSource;
        this.animeTvLocalDataSource = animeTvLocalDataSource;
        this.animeTvRemoteDataSource.setAnimeTvCallback(this);
        this.animeTvLocalDataSource.setAnimeTvCallback(this);
    }

    @Override
    public MutableLiveData<Result> fetchAnimeTv(long lastUpdate) {
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastUpdate > FRESH_TIMEOUT){
            animeTvRemoteDataSource.getAnimeTv();
        }else{
            animeTvLocalDataSource.getAnimeTv();
        }
        return allAnimeTvMutableLiveData;
    }

    @Override
    public void fetchAnimeTv() {
        animeTvRemoteDataSource.getAnimeTv();

    }

    @Override
    public void onSuccessFromRemote(AnimeTvApiResponse animeTvApiResponse, long lastUpdate) {
        animeTvLocalDataSource.insertAnimeTv(animeTvApiResponse);

    }

    @Override
    public void onFailureFromRemote(Exception exception) {
        Result.Error result = new Result.Error(exception.getMessage());
        allAnimeTvMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessFromLocal(AnimeTvApiResponse animeTvApiResponse) {
        if(allAnimeTvMutableLiveData.getValue() != null && allAnimeTvMutableLiveData.getValue().isSuccess()){
            List<AnimeTv> animeTvList = ((Result.AnimeTvSuccess) allAnimeTvMutableLiveData.getValue()).getData().getAnimeTvList();
            animeTvApiResponse.setAnimeTvList(animeTvList);
            Result.AnimeTvSuccess result  = new Result.AnimeTvSuccess(animeTvApiResponse);
            allAnimeTvMutableLiveData.postValue(result);
        }else {
            Result.AnimeTvSuccess result = new Result.AnimeTvSuccess(animeTvApiResponse);
            allAnimeTvMutableLiveData.postValue(result);
        }
    }

    @Override
    public void onFailureFromLocal(Exception exception) {
        Result.Error resultError = new Result.Error(exception.getMessage());
        allAnimeTvMutableLiveData.postValue(resultError);
    }

    @Override
    public void onSuccessFromCloudReading(List<AnimeTv> animeTvList) {
        if(animeTvList != null){
            for(AnimeTv animeTv : animeTvList){
                animeTv.setSynchronized(true);
            }
            animeTvLocalDataSource.insertAnimeTv(animeTvList);
        }
    }

    @Override
    public void onSuccessFromCloudWriting(AnimeTv animeTv) {

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
