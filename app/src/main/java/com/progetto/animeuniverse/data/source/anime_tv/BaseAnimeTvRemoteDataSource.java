package com.progetto.animeuniverse.data.source.anime_tv;

public abstract class BaseAnimeTvRemoteDataSource {
    protected AnimeTvCallback animeTvCallback;

    public void setAnimeTvCallback(AnimeTvCallback animeTvCallback) {
        this.animeTvCallback = animeTvCallback;
    }

    public abstract void getAnimeTv();
}
