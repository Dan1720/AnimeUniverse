package com.progetto.animeuniverse.repository;

import androidx.lifecycle.MutableLiveData;

import com.progetto.animeuniverse.Result;
import com.progetto.animeuniverse.User;

public interface IUserRepository {
    MutableLiveData<Result> getUser(String email, String password, boolean isUserRegistered);
    MutableLiveData<Result> getGoogleUser(String idToken);
    MutableLiveData<Result> logout();
    User getLoggedUser();

    void signUp(String email, String password);
    void signIn(String email, String password);
    void signInWithGoogle(String token);
}
