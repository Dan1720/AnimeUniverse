package com.progetto.animeuniverse.util;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.progetto.animeuniverse.User;
import com.progetto.animeuniverse.data.source.BaseUserAuthenticationRemoteDataSource;
import com.progetto.animeuniverse.data.source.BaseUserDataRemoteDataSource;
import com.progetto.animeuniverse.data.source.UserAuthenticationRemoteDataSource;
import com.progetto.animeuniverse.data.source.UserDataRemoteDataSource;
import com.progetto.animeuniverse.repository.IUserRepository;
import com.progetto.animeuniverse.repository.UserRepository;
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
    public static AnimeApiService getAnimeApiService(){
        //Creazione oggetto retrofit
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.ANIME_API_BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(AnimeApiService.class); //associazione di retrofit alla classe
    }
}
