package com.progetto.animeuniverse.data.source.anime_tv;

import static com.progetto.animeuniverse.util.Constants.ENCRYPTED_DATA_FILE_NAME;
import static com.progetto.animeuniverse.util.Constants.ENCRYPTED_SHARED_PREFERENCES_FILE_NAME;
import static com.progetto.animeuniverse.util.Constants.LAST_UPDATE;
import static com.progetto.animeuniverse.util.Constants.SHARED_PREFERENCES_FILE_NAME;

import com.progetto.animeuniverse.database.AnimeRoomDatabase;
import com.progetto.animeuniverse.database.AnimeTvDao;
import com.progetto.animeuniverse.model.AnimeTv;
import com.progetto.animeuniverse.model.AnimeTvApiResponse;
import com.progetto.animeuniverse.util.DataEncryptionUtil;
import com.progetto.animeuniverse.util.SharedPreferencesUtil;

import java.util.List;

public class AnimeTvLocalDataSource extends BaseAnimeTvLocalDataSource{

    private final AnimeTvDao animeTvDao;
    private final SharedPreferencesUtil sharedPreferencesUtil;
    private final DataEncryptionUtil dataEncryptionUtil;

    public AnimeTvLocalDataSource(AnimeRoomDatabase animeRoomDatabase, SharedPreferencesUtil sharedPreferencesUtil, DataEncryptionUtil dataEncryptionUtil) {
        this.animeTvDao = animeRoomDatabase.animeTvDao();
        this.sharedPreferencesUtil = sharedPreferencesUtil;
        this.dataEncryptionUtil = dataEncryptionUtil;
    }

    @Override
    public void getAnimeTv() {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            AnimeTvApiResponse animeTvApiResponse = new AnimeTvApiResponse();
            animeTvApiResponse.setAnimeTvList(animeTvDao.getAll());
            animeTvCallback.onSuccessFromLocal(animeTvApiResponse);
        });
    }

    @Override
    public void updateAnimeTv(AnimeTv animeTv) {
        if(animeTv == null){
            List<AnimeTv> allAnimeTv = animeTvDao.getAll();
            for(AnimeTv a : allAnimeTv){
                a.setSynchronized(false);
            }
        }
    }

    @Override
    public void insertAnimeTv(AnimeTvApiResponse animeTvApiResponse) {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            List<AnimeTv> allAnimeTv = animeTvDao.getAll();
            List<AnimeTv> animeTvList = animeTvApiResponse.getAnimeTvList();
            if(animeTvList != null){
                for(AnimeTv animeTv : allAnimeTv){
                    if(animeTvList.contains(animeTv)){
                        animeTvList.set(animeTvList.indexOf(animeTv), animeTv);
                    }
                }
                List<Long> insertedAnimeTvIds = animeTvDao.insertAnimeTvList(animeTvList);
                for(int i=0; i<animeTvList.size(); i++){
                    animeTvList.get(i).setId(Math.toIntExact(insertedAnimeTvIds.get(i)));
                }
                sharedPreferencesUtil.writeStringData(SHARED_PREFERENCES_FILE_NAME, LAST_UPDATE, String.valueOf(System.currentTimeMillis()));
                animeTvCallback.onSuccessFromLocal(animeTvApiResponse);
            }
        });
    }

    @Override
    public void insertAnimeTv(List<AnimeTv> animeTvList) {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            if(animeTvList != null) {
                List<AnimeTv> allAnimeTv = animeTvDao.getAll();
                for (AnimeTv animeTv : allAnimeTv) {
                    if (animeTvList.contains(animeTv)) {
                        animeTv.setSynchronized(true);
                        animeTvList.set(animeTvList.indexOf(animeTv), animeTv);
                    }
                }
                List<Long> insertedAnimeTvIds = animeTvDao.insertAnimeTvList(animeTvList);
                for (int i = 0; i < animeTvList.size(); i++) {
                    animeTvList.get(i).setId(Math.toIntExact(insertedAnimeTvIds.get(i)));
                }
            }
            AnimeTvApiResponse animeTvApiResponse = new AnimeTvApiResponse();
            animeTvApiResponse.setAnimeTvList(animeTvList);
        });
    }

    @Override
    public void deleteAll() {
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            int animeTvCounter = animeTvDao.getAll().size();
            int animeTvDeleteAnimeTv = animeTvDao.deleteAll();
            if(animeTvCounter == animeTvDeleteAnimeTv){
                sharedPreferencesUtil.deleteAll(SHARED_PREFERENCES_FILE_NAME);
                dataEncryptionUtil.deleteAll(ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, ENCRYPTED_DATA_FILE_NAME);
                animeTvCallback.onSuccessDeletion();
            }
        });
    }
}
