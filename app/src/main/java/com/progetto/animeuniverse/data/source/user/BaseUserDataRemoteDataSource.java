package com.progetto.animeuniverse.data.source.user;

import com.progetto.animeuniverse.Result;
import com.progetto.animeuniverse.User;
import com.progetto.animeuniverse.repository.UserResponseCallback;

public abstract class BaseUserDataRemoteDataSource {
    protected UserResponseCallback userResponseCallback;

    public void setUserResponseCallback(UserResponseCallback userResponseCallback){
        this.userResponseCallback = userResponseCallback;
    }

    public abstract void saveUserData(User user);

}
