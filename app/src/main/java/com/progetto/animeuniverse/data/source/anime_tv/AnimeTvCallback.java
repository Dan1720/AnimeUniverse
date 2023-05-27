package com.progetto.animeuniverse.data.source.anime_tv;

import com.progetto.animeuniverse.model.AnimeTv;
import com.progetto.animeuniverse.model.AnimeTvApiResponse;

import java.util.List;

public interface AnimeTvCallback {
    void onSuccessFromRemote(AnimeTvApiResponse animeTvApiResponse, long lastUpdate);
    void onFailureFromRemote(Exception exception);
    void onSuccessFromLocal(AnimeTvApiResponse animeTvApiResponse);
    void onFailureFromLocal(Exception exception);
    void onSuccessFromCloudReading(List<AnimeTv> animeTvList);
    void onSuccessFromCloudWriting(AnimeTv animeTv);
    void onFailureFromCloud(Exception exception);
    void onSuccessSynchronization();
    void onSuccessDeletion();
}
