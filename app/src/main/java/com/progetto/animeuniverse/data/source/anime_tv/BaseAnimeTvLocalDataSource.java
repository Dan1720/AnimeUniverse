package com.progetto.animeuniverse.data.source.anime_tv;

import com.progetto.animeuniverse.model.AnimeTv;
import com.progetto.animeuniverse.model.AnimeTvApiResponse;

import java.util.List;

public abstract class BaseAnimeTvLocalDataSource {
    protected AnimeTvCallback animeTvCallback;

    public void setAnimeTvCallback(AnimeTvCallback animeTvCallback) {
        this.animeTvCallback = animeTvCallback;
    }

    public abstract void getAnimeTv();
    public abstract void updateAnimeTv(AnimeTv animeTv);
    public abstract void insertAnimeTv(AnimeTvApiResponse animeTvApiResponse);
    public abstract void insertAnimeTv(List<AnimeTv> animeTvList);
    public abstract void deleteAll();
}
