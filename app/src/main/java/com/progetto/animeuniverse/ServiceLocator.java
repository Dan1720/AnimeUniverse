package com.progetto.animeuniverse;

import android.app.Application;

import com.progetto.animeuniverse.data.source.user.BaseUserAuthenticationRemoteDataSource;
import com.progetto.animeuniverse.data.source.user.UserAuthenticationRemoteDataSource;
import com.progetto.animeuniverse.repository.IUserRepository;
import com.progetto.animeuniverse.repository.UserRepository;

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
        BaseUserAuthenticationRemoteDataSource userRemoteAuthenticationDataSource =
                new UserAuthenticationRemoteDataSource();


        DataEncryptionUtil dataEncryptionUtil = new DataEncryptionUtil(application);
        return new UserRepository(userRemoteAuthenticationDataSource,
                null);
    }
}
