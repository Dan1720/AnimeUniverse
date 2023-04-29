package com.progetto.animeuniverse.util;

import android.app.Application;

import com.progetto.animeuniverse.data.source.users.BaseUserAuthenticationRemoteDataSource;
import com.progetto.animeuniverse.data.source.users.BaseUserDataRemoteDataSource;
import com.progetto.animeuniverse.data.source.users.UserAuthenticationRemoteDataSource;
import com.progetto.animeuniverse.data.source.users.UserDataRemoteDataSource;
import com.progetto.animeuniverse.database.AnimeRoomDatabase;
import com.progetto.animeuniverse.repository.anime.IAnimeRepositoryWithLiveData;
import com.progetto.animeuniverse.repository.user.IUserRepository;
import com.progetto.animeuniverse.repository.user.UserRepository;
import com.progetto.animeuniverse.service.AnimeApiService;

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

        return new UserRepository(userAuthenticationRemoteDataSource, userDataRemoteDataSource);
    }

    public AnimeApiService getAnimeApiService() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.ANIME_API_BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(AnimeApiService.class);
    }

    public AnimeRoomDatabase getAnimeDao(Application application) {
        return AnimeRoomDatabase.getDatabase(application);
    }

    /*public IAnimeRepositoryWithLiveData getAnimeRepository(Application application, boolean debugMode){

    }*/
}