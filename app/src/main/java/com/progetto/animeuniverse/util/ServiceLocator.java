package com.progetto.animeuniverse.util;

import static com.progetto.animeuniverse.util.Constants.ENCRYPTED_SHARED_PREFERENCES_FILE_NAME;
import static com.progetto.animeuniverse.util.Constants.ID_TOKEN;

import android.app.Application;

import com.progetto.animeuniverse.data.source.anime.AnimeLocalDataSource;
import com.progetto.animeuniverse.data.source.anime.AnimeMockRemoteDataSource;
import com.progetto.animeuniverse.data.source.anime.AnimeRemoteDataSource;
import com.progetto.animeuniverse.data.source.anime.BaseAnimeLocalDataSource;
import com.progetto.animeuniverse.data.source.anime.BaseAnimeRemoteDataSource;
import com.progetto.animeuniverse.data.source.anime.BaseFavoriteAnimeDataSource;
import com.progetto.animeuniverse.data.source.anime.FavoriteAnimeDataSource;
import com.progetto.animeuniverse.data.source.users.BaseUserAuthenticationRemoteDataSource;
import com.progetto.animeuniverse.data.source.users.BaseUserDataRemoteDataSource;
import com.progetto.animeuniverse.data.source.users.UserAuthenticationRemoteDataSource;
import com.progetto.animeuniverse.data.source.users.UserDataRemoteDataSource;
import com.progetto.animeuniverse.database.AnimeRoomDatabase;
import com.progetto.animeuniverse.repository.anime.AnimeRepositoryWithLiveData;
import com.progetto.animeuniverse.repository.anime.IAnimeRepositoryWithLiveData;
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

        return new UserRepository(userAuthenticationRemoteDataSource, userDataRemoteDataSource);
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
}