package com.progetto.animeuniverse.repository.user;

import androidx.lifecycle.MutableLiveData;

import com.progetto.animeuniverse.data.source.anime.AnimeCallback;
import com.progetto.animeuniverse.data.source.anime.BaseAnimeLocalDataSource;
import com.progetto.animeuniverse.model.Anime;
import com.progetto.animeuniverse.model.AnimeApiResponse;
import com.progetto.animeuniverse.model.Result;
import com.progetto.animeuniverse.model.User;
import com.progetto.animeuniverse.data.source.users.BaseUserAuthenticationRemoteDataSource;
import com.progetto.animeuniverse.data.source.users.BaseUserDataRemoteDataSource;

import java.util.List;

public class UserRepository implements IUserRepository, UserResponseCallback, AnimeCallback {
    private final MutableLiveData<Result> userMutableLiveData;
    private final BaseUserAuthenticationRemoteDataSource userRemoteDataSource;
    private final BaseUserDataRemoteDataSource userDataRemoteDataSource;
    private final BaseAnimeLocalDataSource animeLocalDataSource;
    private final MutableLiveData<Result> userFavoriteAnimeMutableLiveData;

    public UserRepository(BaseUserAuthenticationRemoteDataSource userRemoteDataSource,
                          BaseUserDataRemoteDataSource userDataRemoteDataSource, BaseAnimeLocalDataSource animeLocalDataSource) {
        this.userRemoteDataSource = userRemoteDataSource;
        this.userDataRemoteDataSource = userDataRemoteDataSource;
        this.animeLocalDataSource = animeLocalDataSource;
        this.userFavoriteAnimeMutableLiveData = new MutableLiveData<>();
        this.userMutableLiveData = new MutableLiveData<>();
        this.userRemoteDataSource.setUserResponseCallback(this);
        this.userDataRemoteDataSource.setUserResponseCallback(this);
        this.animeLocalDataSource.setAnimeCallback(this);
    }

    @Override
    public MutableLiveData<Result> getUser(String email, String password, boolean isUserRegistered) {
        if(isUserRegistered){
            signIn(email, password);
        }else{
            signUp(email, password);
        }
        return userMutableLiveData;
    }

    @Override
    public MutableLiveData<Result> getGoogleUser(String idToken) {
        signInWithGoogle(idToken);
        return userMutableLiveData;
    }

    @Override
    public MutableLiveData<Result> logout() {
        userRemoteDataSource.logout();
        return userMutableLiveData;
    }

    @Override
    public User getLoggedUser() {
        return userRemoteDataSource.getLoggedUser();
    }

    @Override
    public void signUp(String email, String password) {
        userRemoteDataSource.signUp(email, password);
    }

    @Override
    public void signIn(String email, String password) {
        userRemoteDataSource.signIn(email, password);
    }

    @Override
    public void signInWithGoogle(String token) {
        userRemoteDataSource.signInWithGoogle(token);
    }

    @Override
    public MutableLiveData<Result> getUserFavoriteAnime(String idToken) {
        userDataRemoteDataSource.getUserFavoriteAnime(idToken);
        return userFavoriteAnimeMutableLiveData;
    }

    @Override
    public void onSuccessFromAuthentication(User user) {
        if(user != null){
            userDataRemoteDataSource.saveUserData(user);
        }
    }

    @Override
    public void onFailureFromAuthentication(String message) {
        Result.Error result = new Result.Error(message);
        userMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessFromRemoteDatabase(User user) {
        Result.UserResponseSuccess result = new Result.UserResponseSuccess(user);
        userMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessFromRemoteDatabase(List<Anime> animeList) {
        animeLocalDataSource.insertAnime(animeList);
    }

    @Override
    public void onFailureFromRemoteDatabase(String message) {
        Result.Error result = new Result.Error(message);
        userMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessLogout() {
        animeLocalDataSource.deleteAll();
    }
    @Override
    public void onSuccessFromRemote(AnimeApiResponse animeApiResponse, long lastUpdate) {

    }

    @Override
    public void onFailureFromRemote(Exception exception) {

    }

    @Override
    public void onSuccessFromLocal(AnimeApiResponse animeApiResponse) {
        Result.AnimeResponseSuccess result = new Result.AnimeResponseSuccess(animeApiResponse);
        userFavoriteAnimeMutableLiveData.postValue(result);
    }

    @Override
    public void onFailureFromLocal(Exception exception) {

    }

    @Override
    public void onAnimeFavoriteStatusChanged(Anime anime, List<Anime> favoriteAnime) {

    }

    @Override
    public void onAnimeFavoriteStatusChanged(List<Anime> anime) {

    }

    @Override
    public void onDeleteFavoriteAnimeSuccess(List<Anime> favoriteAnime) {

    }

    @Override
    public void onSuccessFromCloudReading(List<Anime> animeList) {

    }

    @Override
    public void onSuccessFromCloudWriting(Anime anime) {

    }

    @Override
    public void onFailureFromCloud(Exception exception) {

    }

    @Override
    public void onSuccessSynchronization() {
        userFavoriteAnimeMutableLiveData.postValue(new Result.AnimeResponseSuccess(null));

    }

    @Override
    public void onSuccessDeletion() {
        Result.UserResponseSuccess result = new Result.UserResponseSuccess(null);
        userMutableLiveData.postValue(result);
    }
}
