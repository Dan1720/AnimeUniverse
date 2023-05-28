package com.progetto.animeuniverse.model;

import java.util.List;

public class AnimeTvApiResponse extends AnimeTvResponse{
    public AnimeTvApiResponse(){ super();}
    public AnimeTvApiResponse(List<AnimeTv> animeTvList){
        super(animeTvList);
    }
    @Override
    public String toString() {
        return super.toString();
    }
}
