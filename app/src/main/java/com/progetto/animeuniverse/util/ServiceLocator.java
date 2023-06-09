package com.progetto.animeuniverse.util;

import static com.progetto.animeuniverse.util.Constants.ENCRYPTED_SHARED_PREFERENCES_FILE_NAME;
import static com.progetto.animeuniverse.util.Constants.ID_TOKEN;

import android.app.Application;

import com.progetto.animeuniverse.data.source.anime.AnimeLocalDataSource;
import com.progetto.animeuniverse.data.source.anime.AnimeRemoteDataSource;
import com.progetto.animeuniverse.data.source.anime.BaseAnimeLocalDataSource;
import com.progetto.animeuniverse.data.source.anime.BaseAnimeRemoteDataSource;
import com.progetto.animeuniverse.data.source.anime.BaseFavoriteAnimeDataSource;
import com.progetto.animeuniverse.data.source.anime.FavoriteAnimeDataSource;
import com.progetto.animeuniverse.data.source.anime_movie.AnimeMovieLocalDataSource;
import com.progetto.animeuniverse.data.source.anime_movie.AnimeMovieRemoteDataSource;
import com.progetto.animeuniverse.data.source.anime_movie.BaseAnimeMovieLocalDataSource;
import com.progetto.animeuniverse.data.source.anime_movie.BaseAnimeMovieRemoteDataSource;
import com.progetto.animeuniverse.data.source.anime_new.AnimeNewLocalDataSource;
import com.progetto.animeuniverse.data.source.anime_new.AnimeNewRemoteDataSource;
import com.progetto.animeuniverse.data.source.anime_new.BaseAnimeNewLocalDataSource;
import com.progetto.animeuniverse.data.source.anime_new.BaseAnimeNewRemoteDataSource;
import com.progetto.animeuniverse.data.source.anime_recommendations.AnimeRecommendationsLocalDataSource;
import com.progetto.animeuniverse.data.source.anime_recommendations.AnimeRecommendationsRemoteDataSource;
import com.progetto.animeuniverse.data.source.anime_recommendations.BaseAnimeRecommendationsLocalDataSource;
import com.progetto.animeuniverse.data.source.anime_recommendations.BaseAnimeRecommendationsRemoteDataSource;
import com.progetto.animeuniverse.data.source.anime_tv.AnimeTvLocalDataSource;
import com.progetto.animeuniverse.data.source.anime_tv.AnimeTvRemoteDataSource;
import com.progetto.animeuniverse.data.source.anime_tv.BaseAnimeTvLocalDataSource;
import com.progetto.animeuniverse.data.source.anime_tv.BaseAnimeTvRemoteDataSource;
import com.progetto.animeuniverse.data.source.genres.BaseGenresLocalDataSource;
import com.progetto.animeuniverse.data.source.genres.BaseGenresRemoteDataSource;
import com.progetto.animeuniverse.data.source.genres.GenresLocalDataSource;
import com.progetto.animeuniverse.data.source.genres.GenresRemoteDataSource;
import com.progetto.animeuniverse.data.source.users.BaseUserAuthenticationRemoteDataSource;
import com.progetto.animeuniverse.data.source.users.BaseUserDataRemoteDataSource;
import com.progetto.animeuniverse.data.source.users.UserAuthenticationRemoteDataSource;
import com.progetto.animeuniverse.data.source.users.UserDataRemoteDataSource;
import com.progetto.animeuniverse.database.AnimeRoomDatabase;
import com.progetto.animeuniverse.repository.anime.AnimeRepositoryWithLiveData;
import com.progetto.animeuniverse.repository.anime.IAnimeRepositoryWithLiveData;
import com.progetto.animeuniverse.repository.anime_movie.AnimeMovieRepositoryWithLiveData;
import com.progetto.animeuniverse.repository.anime_movie.IAnimeMovieRepositoryWithLiveData;
import com.progetto.animeuniverse.repository.anime_new.AnimeNewRepositoryWithLiveData;
import com.progetto.animeuniverse.repository.anime_new.IAnimeNewRepositoryWithLiveData;
import com.progetto.animeuniverse.repository.anime_recommendations.AnimeRecommendationsRepositoryWithLiveData;
import com.progetto.animeuniverse.repository.anime_recommendations.IAnimeRecommendationsRepositoryWithLiveData;
import com.progetto.animeuniverse.repository.anime_tv.AnimeTvRepositoryWithLiveData;
import com.progetto.animeuniverse.repository.anime_tv.IAnimeTvRepositoryWithLiveData;
import com.progetto.animeuniverse.repository.genres.GenresRepositoryWithLiveData;
import com.progetto.animeuniverse.repository.genres.IGenresRepositoryWithLiveData;
import com.progetto.animeuniverse.repository.user.IUserRepository;
import com.progetto.animeuniverse.repository.user.UserRepository;
import com.progetto.animeuniverse.service.AnimeApiService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceLocator {
    private static volatile ServiceLocator INSTANCE = null;


    private ServiceLocator() {}

    public static ServiceLocator getInstance() {
        if (INSTANCE == null) {
            synchronized(ServiceLocator.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ServiceLocator();
                }
            }
        }
        return INSTANCE;
    }

    public IUserRepository getUserRepository(Application application) {
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(application);

        BaseUserAuthenticationRemoteDataSource userAuthenticationRemoteDataSource =
                new UserAuthenticationRemoteDataSource();

        BaseUserDataRemoteDataSource userDataRemoteDataSource =
                new UserDataRemoteDataSource(sharedPreferencesUtil);

        DataEncryptionUtil dataEncryptionUtil = new DataEncryptionUtil(application);

        BaseAnimeLocalDataSource animeLocalDataSource =
                new AnimeLocalDataSource(getAnimeDao(application), sharedPreferencesUtil,
                        dataEncryptionUtil);

        return new UserRepository(userAuthenticationRemoteDataSource, userDataRemoteDataSource, animeLocalDataSource);
    }

    public AnimeApiService getAnimeApiService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(10,TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS).addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.ANIME_API_BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).client(client).build();
        return retrofit.create(AnimeApiService.class);
    }

    public AnimeRoomDatabase getAnimeDao(Application application) {
        return AnimeRoomDatabase.getDatabase(application);
    }

    public AnimeRoomDatabase getReviewsDao(Application application){
        return AnimeRoomDatabase.getDatabase(application);
    }

    public AnimeRoomDatabase getGenresDao(Application application){
        return AnimeRoomDatabase.getDatabase(application);
    }

    public AnimeRoomDatabase getAnimeRecommendationsDao(Application application){
        return AnimeRoomDatabase.getDatabase(application);
    }

    public AnimeRoomDatabase getAnimeByNameDao(Application application){
        return AnimeRoomDatabase.getDatabase(application);
    }

    public AnimeRoomDatabase getAnimeNewDao(Application application){
        return AnimeRoomDatabase.getDatabase(application);
    }

    public AnimeRoomDatabase getAnimeEpisodesDao(Application application){
        return AnimeRoomDatabase.getDatabase(application);
    }

    public AnimeRoomDatabase getAnimeTvDao(Application application){
        return AnimeRoomDatabase.getDatabase(application);
    }
    public AnimeRoomDatabase getAnimeMovieDao(Application application){
        return AnimeRoomDatabase.getDatabase(application);
    }

    public AnimeRoomDatabase getAnimeSpecificGenresDao(Application application){
        return AnimeRoomDatabase.getDatabase(application);
    }

    public IAnimeRepositoryWithLiveData getAnimeRepository(Application application){
        BaseAnimeRemoteDataSource animeRemoteDataSource;
        BaseAnimeLocalDataSource animeLocalDataSource;
        BaseFavoriteAnimeDataSource favoriteAnimeDataSource;
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(application);
        DataEncryptionUtil dataEncryptionUtil = new DataEncryptionUtil(application);

        animeRemoteDataSource = new AnimeRemoteDataSource();

        animeLocalDataSource = new AnimeLocalDataSource(getAnimeDao(application),
                sharedPreferencesUtil, dataEncryptionUtil);

        try{
            favoriteAnimeDataSource = new FavoriteAnimeDataSource(dataEncryptionUtil.
                    readSecretDataWithEncryptedSharedPreferences(ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, ID_TOKEN)
            );

        } catch (GeneralSecurityException | IOException e) {
            return null;
        }
        return new AnimeRepositoryWithLiveData(animeRemoteDataSource, animeLocalDataSource, favoriteAnimeDataSource);
    }

    public IGenresRepositoryWithLiveData getGenresRepository(Application application){
        BaseGenresLocalDataSource genresLocalDataSource;
        BaseGenresRemoteDataSource genresRemoteDataSource;
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(application);
        DataEncryptionUtil dataEncryptionUtil = new DataEncryptionUtil(application);

        genresRemoteDataSource = new GenresRemoteDataSource();
        genresLocalDataSource = new GenresLocalDataSource(getGenresDao(application), sharedPreferencesUtil, dataEncryptionUtil);
        return new GenresRepositoryWithLiveData(genresRemoteDataSource, genresLocalDataSource);
    }

    public IAnimeRecommendationsRepositoryWithLiveData getAnimeRecommendationsRepository(Application application){
        BaseAnimeRecommendationsLocalDataSource animeRecommendationsLocalDataSource;
        BaseAnimeRecommendationsRemoteDataSource animeRecommendationsRemoteDataSource;
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(application);
        DataEncryptionUtil dataEncryptionUtil = new DataEncryptionUtil(application);

        animeRecommendationsRemoteDataSource = new AnimeRecommendationsRemoteDataSource();
        animeRecommendationsLocalDataSource = new AnimeRecommendationsLocalDataSource(getAnimeRecommendationsDao(application), sharedPreferencesUtil, dataEncryptionUtil);
        return new AnimeRecommendationsRepositoryWithLiveData(animeRecommendationsRemoteDataSource, animeRecommendationsLocalDataSource);
    }



    public IAnimeNewRepositoryWithLiveData getAnimeNewRepository(Application application){
        BaseAnimeNewRemoteDataSource animeNewRemoteDataSource;
        BaseAnimeNewLocalDataSource animeNewLocalDataSource;
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(application);
        DataEncryptionUtil dataEncryptionUtil = new DataEncryptionUtil(application);

        animeNewRemoteDataSource = new AnimeNewRemoteDataSource();
        animeNewLocalDataSource = new AnimeNewLocalDataSource(getAnimeNewDao(application), sharedPreferencesUtil, dataEncryptionUtil);
        return new AnimeNewRepositoryWithLiveData(animeNewRemoteDataSource, animeNewLocalDataSource);
    }



    public IAnimeTvRepositoryWithLiveData getAnimeTvRepository(Application application){
        BaseAnimeTvRemoteDataSource animeTvRemoteDataSource;
        BaseAnimeTvLocalDataSource animeTvLocalDataSource;
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(application);
        DataEncryptionUtil dataEncryptionUtil = new DataEncryptionUtil(application);

        animeTvRemoteDataSource = new AnimeTvRemoteDataSource();
        animeTvLocalDataSource = new AnimeTvLocalDataSource(getAnimeTvDao(application), sharedPreferencesUtil, dataEncryptionUtil);
        return new AnimeTvRepositoryWithLiveData(animeTvRemoteDataSource, animeTvLocalDataSource);
    }

    public IAnimeMovieRepositoryWithLiveData getAnimeMovieRepository(Application application){
        BaseAnimeMovieRemoteDataSource animeMovieRemoteDataSource;
        BaseAnimeMovieLocalDataSource animeMovieLocalDataSource;
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(application);
        DataEncryptionUtil dataEncryptionUtil = new DataEncryptionUtil(application);

        animeMovieRemoteDataSource = new AnimeMovieRemoteDataSource();
        animeMovieLocalDataSource = new AnimeMovieLocalDataSource(getAnimeMovieDao(application), sharedPreferencesUtil, dataEncryptionUtil);
        return new AnimeMovieRepositoryWithLiveData(animeMovieRemoteDataSource, animeMovieLocalDataSource);
    }


}