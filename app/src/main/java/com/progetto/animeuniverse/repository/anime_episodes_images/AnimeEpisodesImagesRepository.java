package com.progetto.animeuniverse.repository.anime_episodes_images;

import static com.progetto.animeuniverse.util.Constants.FRESH_TIMEOUT;

import android.app.Application;
import android.util.Log;

import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.database.AnimeEpisodesDao;
import com.progetto.animeuniverse.database.AnimeEpisodesImagesDao;
import com.progetto.animeuniverse.database.AnimeRoomDatabase;
import com.progetto.animeuniverse.model.AnimeEpisodes;
import com.progetto.animeuniverse.model.AnimeEpisodesApiResponse;
import com.progetto.animeuniverse.model.AnimeEpisodesImages;
import com.progetto.animeuniverse.model.AnimeEpisodesImagesApiResponse;
import com.progetto.animeuniverse.repository.anime_episodes.AnimeEpisodesRepository;
import com.progetto.animeuniverse.repository.anime_episodes.AnimeEpisodesResponseCallback;
import com.progetto.animeuniverse.service.AnimeApiService;
import com.progetto.animeuniverse.util.ServiceLocator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimeEpisodesImagesRepository implements IAnimeEpisodesImagesRepository{
    public static final String TAG = AnimeEpisodesImagesRepository.class.getSimpleName();

    private final Application application;
    private final AnimeApiService animeApiService;
    private final AnimeEpisodesImagesDao animeEpisodesImagesDao;
    private final AnimeEpisodesImagesResponseCallback animeEpisodesImagesResponseCallback;

    public AnimeEpisodesImagesRepository(Application application, AnimeEpisodesImagesResponseCallback animeEpisodesImagesResponseCallback) {
        this.application = application;
        this.animeApiService = ServiceLocator.getInstance().getAnimeApiService();
        AnimeRoomDatabase animeRoomDatabase = ServiceLocator.getInstance().getAnimeEpisodesImagesDao(application);
        this.animeEpisodesImagesDao = animeRoomDatabase.animeEpisodesImagesDao();
        this.animeEpisodesImagesResponseCallback = animeEpisodesImagesResponseCallback;
    }

    @Override
    public void fetchAnimeEpisodesImages(int idAnime, long lastUpdate) {
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastUpdate > FRESH_TIMEOUT){
            Call<AnimeEpisodesImagesApiResponse> animeEpisodesImagesApiResponseCall = animeApiService.getAnimeEpisodesImages(idAnime);

            animeEpisodesImagesApiResponseCall.enqueue(new Callback<AnimeEpisodesImagesApiResponse>() {
                @Override
                public void onResponse(Call<AnimeEpisodesImagesApiResponse> call, Response<AnimeEpisodesImagesApiResponse> response) {
                    if(response.isSuccessful()){
                        List<AnimeEpisodesImages> animeEpisodesImagesList = response.body().getAnimeEpisodesImagesList();
                        saveDataInDatabase(animeEpisodesImagesList);
                    }else{
                        animeEpisodesImagesResponseCallback.onFailure(application.getString(R.string.error_retrieving_anime));
                    }
                }

                @Override
                public void onFailure(Call<AnimeEpisodesImagesApiResponse> call, Throwable t) {
                    animeEpisodesImagesResponseCallback.onFailure(t.getMessage());
                }
            });

        }else {
            Log.d(TAG, application.getString(R.string.data_read_from_local_database));
        }
    }

    private void saveDataInDatabase(List<AnimeEpisodesImages> animeEpisodesImagesList){
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            List<AnimeEpisodesImages> allAnimeEpisodesImages = animeEpisodesImagesDao.getAll();
            for (AnimeEpisodesImages animeEpisodesImages : allAnimeEpisodesImages){
                if(animeEpisodesImagesList.contains(animeEpisodesImages)){
                    animeEpisodesImagesList.set(animeEpisodesImagesList.indexOf(animeEpisodesImages), animeEpisodesImages);
                }
            }
            List<Long> insertedAnimeEpisodesImagesIds = animeEpisodesImagesDao.insertEpisodesImagesList(animeEpisodesImagesList);
            for(int i=0; i<animeEpisodesImagesList.size(); i++){
                animeEpisodesImagesList.get(i).setId(Math.toIntExact(insertedAnimeEpisodesImagesIds.get(i)));
            }
            animeEpisodesImagesResponseCallback.onSuccess(animeEpisodesImagesList,System.currentTimeMillis());
        });
    }

    private void readDataFromDatabase(long lastUpdate){
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            animeEpisodesImagesResponseCallback.onSuccess(animeEpisodesImagesDao.getAll(), lastUpdate);
        });
    }
}
