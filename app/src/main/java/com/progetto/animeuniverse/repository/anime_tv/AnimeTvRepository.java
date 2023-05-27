package com.progetto.animeuniverse.repository.anime_tv;

import static com.progetto.animeuniverse.util.Constants.FRESH_TIMEOUT;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.database.AnimeRoomDatabase;
import com.progetto.animeuniverse.database.AnimeTvDao;

import com.progetto.animeuniverse.model.AnimeTv;
import com.progetto.animeuniverse.model.AnimeTvApiResponse;
import com.progetto.animeuniverse.service.AnimeApiService;
import com.progetto.animeuniverse.util.ServiceLocator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimeTvRepository implements IAnimeTvRepository{
    private static final String TAG = AnimeTvRepository.class.getSimpleName();

    private final Application application;
    private final AnimeApiService animeApiService;
    private final AnimeTvDao animeTvDao;
    private final AnimeTvResponseCallback animeTvResponseCallback;

    public AnimeTvRepository(Application application, AnimeTvResponseCallback animeTvResponseCallback) {
        this.application = application;
        this.animeApiService = ServiceLocator.getInstance().getAnimeApiService();
        AnimeRoomDatabase animeRoomDatabase = ServiceLocator.getInstance().getAnimeTvDao(application);
        this.animeTvDao = animeRoomDatabase.animeTvDao();
        this.animeTvResponseCallback = animeTvResponseCallback;
    }

    @Override
    public void fetchAnimeTv(long lastUpdate) {
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastUpdate > FRESH_TIMEOUT){
            Call<AnimeTvApiResponse> animeTvApiResponseCall = animeApiService.getAnimeTv();
            animeTvApiResponseCall.enqueue(new Callback<AnimeTvApiResponse>() {
                @Override
                public void onResponse(Call<AnimeTvApiResponse> call, Response<AnimeTvApiResponse> response) {
                    if(response.isSuccessful()){
                        List<AnimeTv> animeTvList = response.body().getAnimeTvList();
                        saveDataInDatabase(animeTvList);
                    }else{
                        animeTvResponseCallback.onFailure(application.getString(R.string.error_retrieving_anime));
                    }
                }

                @Override
                public void onFailure(Call<AnimeTvApiResponse> call, @NonNull Throwable t) {
                    animeTvResponseCallback.onFailure(t.getMessage());
                }
            });
        }else {
            Log.d(TAG, application.getString(R.string.data_read_from_local_database));
        }
    }

    private void saveDataInDatabase(List<AnimeTv> animeTvList){
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            List<AnimeTv> allAnimeTv = animeTvDao.getAll();
            for(AnimeTv animeTv : allAnimeTv){
                if(animeTvList.contains(animeTv)){
                    animeTvList.set(animeTvList.indexOf(animeTv), animeTv);
                }
            }
            List<Long> insertedAnimeTvIds = animeTvDao.insertAnimeTvList(animeTvList);
            for(int i=0; i<animeTvList.size(); i++){
                animeTvList.get(i).setId(Math.toIntExact(insertedAnimeTvIds.get(i)));
            }
            animeTvResponseCallback.onSuccess(animeTvList, System.currentTimeMillis());
        });
    }

    private void readDataFromDatabase(long lastUpdate){
        AnimeRoomDatabase.databaseWriteExecutor.execute(() ->{
            animeTvResponseCallback.onSuccess(animeTvDao.getAll(), lastUpdate);
        });
    }
}
