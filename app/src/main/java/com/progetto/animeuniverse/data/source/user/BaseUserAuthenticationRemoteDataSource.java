package com.progetto.animeuniverse.data.source.user;

import com.progetto.animeuniverse.User;
import com.progetto.animeuniverse.repository.UserResponseCallback;

public abstract class BaseUserAuthenticationRemoteDataSource {
    protected UserResponseCallback userResponseCallback;

    public void setUserResponseCallback(UserResponseCallback userResponseCallback){
        this.userResponseCallback = userResponseCallback;
    }

    public abstract void saveUserData(User user);
    public abstract void getUserPreferences(String idToken);
}
