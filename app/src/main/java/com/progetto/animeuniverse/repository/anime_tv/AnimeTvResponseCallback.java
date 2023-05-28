package com.progetto.animeuniverse.repository.anime_tv;

import com.progetto.animeuniverse.model.AnimeTv;

import java.util.List;

public interface AnimeTvResponseCallback {
    void onSuccess(List<AnimeTv> animeTvList, long lastUpdate);
    void onFailure(String errorMessage);
}
