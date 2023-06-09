package com.progetto.animeuniverse.repository.anime_episodes;

import android.app.Application;

import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.database.AnimeEpisodesDao;
import com.progetto.animeuniverse.database.AnimeRoomDatabase;
import com.progetto.animeuniverse.model.AnimeEpisodes;
import com.progetto.animeuniverse.model.AnimeEpisodesApiResponse;
import com.progetto.animeuniverse.service.AnimeApiService;
import com.progetto.animeuniverse.util.ServiceLocator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimeEpisodesRepository implements IAnimeEpisodesRepository{
    public static final String TAG = AnimeEpisodesRepository.class.getSimpleName();

    private final Application application;
    private final AnimeApiService animeApiService;
    private final AnimeEpisodesDao animeEpisodesDao;
    private final AnimeEpisodesResponseCallback animeEpisodesResponseCallback;

    public AnimeEpisodesRepository(Application application, AnimeEpisodesResponseCallback animeEpisodesResponseCallback) {
        this.application = application;
        this.animeApiService = ServiceLocator.getInstance().getAnimeApiService();
        AnimeRoomDatabase animeRoomDatabase = ServiceLocator.getInstance().getAnimeEpisodesDao(application);
        this.animeEpisodesDao = animeRoomDatabase.animeEpisodesDao();
        this.animeEpisodesResponseCallback = animeEpisodesResponseCallback;
    }

    @Override
    public void fetchAnimeEpisodes(int idAnime, long lastUpdate) {
            Call<AnimeEpisodesApiResponse> animeEpisodesApiResponseCall = animeApiService.getAnimeEpisodes(idAnime);

            animeEpisodesApiResponseCall.enqueue(new Callback<AnimeEpisodesApiResponse>() {
                @Override
                public void onResponse(Call<AnimeEpisodesApiResponse> call, Response<AnimeEpisodesApiResponse> response) {
                    if(response.isSuccessful()){
                        assert response.body() != null;
                        List<AnimeEpisodes> animeEpisodesList = response.body().getAnimeEpisodesList();
                        saveDataInDatabase(animeEpisodesList);
                    }else{
                        animeEpisodesResponseCallback.onFailure(application.getString(R.string.error_retrieving_anime));
                    }
                }

                @Override
                public void onFailure(Call<AnimeEpisodesApiResponse> call, Throwable t) {
                    animeEpisodesResponseCallback.onFailure(t.getMessage());
                }
            });

    }

    private void saveDataInDatabase(List<AnimeEpisodes> animeEpisodesList){
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            List<AnimeEpisodes> allAnimeEpisodes = animeEpisodesDao.getAll();
            for (AnimeEpisodes animeEpisodes : allAnimeEpisodes){
                if(animeEpisodesList.contains(animeEpisodes)){
                    animeEpisodesList.set(animeEpisodesList.indexOf(animeEpisodes), animeEpisodes);
                }
            }
            List<Long> insertedAnimeEpisodesIds = animeEpisodesDao.insertEpisodesList(animeEpisodesList);
            for(int i=0; i<animeEpisodesList.size(); i++){
                animeEpisodesList.get(i).setId(Math.toIntExact(insertedAnimeEpisodesIds.get(i)));
            }
            animeEpisodesResponseCallback.onSuccessEpisodes(animeEpisodesList,System.currentTimeMillis());
        });
    }

    private void readDataFromDatabase(long lastUpdate){
        AnimeRoomDatabase.databaseWriteExecutor.execute(()->{
            animeEpisodesResponseCallback.onSuccessEpisodes(animeEpisodesDao.getAll(), lastUpdate);
        });
    }
}
