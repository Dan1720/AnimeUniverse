package com.progetto.animeuniverse.data.source.users;

import com.progetto.animeuniverse.model.User;
import com.progetto.animeuniverse.repository.user.UserResponseCallback;

public abstract class BaseUserDataRemoteDataSource {
    protected UserResponseCallback userResponseCallback;

    public void setUserResponseCallback(UserResponseCallback userResponseCallback){
        this.userResponseCallback = userResponseCallback;
    }
    public abstract void saveUserData(User user);

    public abstract void getUserFavoriteAnime(String idToken);
}
