package com.progetto.animeuniverse.data.source;

import com.progetto.animeuniverse.User;
import com.progetto.animeuniverse.repository.user.UserResponseCallback;

public abstract class BaseUserAuthenticationRemoteDataSource {
    protected UserResponseCallback userResponseCallback;

    public void setUserResponseCallback(UserResponseCallback userResponseCallback){
        this.userResponseCallback = userResponseCallback;
    }
    public abstract User getLoggedUser();
    public abstract void logout();
    public abstract void signUp(String email, String password);
    public abstract void signIn(String email, String password);
    public abstract void signInWithGoogle(String idToken);
}
