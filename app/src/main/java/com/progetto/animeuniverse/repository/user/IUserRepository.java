package com.progetto.animeuniverse.repository.user;

import androidx.lifecycle.MutableLiveData;

import com.progetto.animeuniverse.model.Result;
import com.progetto.animeuniverse.model.User;

public interface IUserRepository {
    MutableLiveData<Result> getUser(String email, String password, boolean isUserRegistered);
    MutableLiveData<Result> getGoogleUser(String idToken);
    MutableLiveData<Result> logout();
    User getLoggedUser();

    void signUp(String email, String password);
    void signIn(String email, String password);
    void signInWithGoogle(String token);
}
