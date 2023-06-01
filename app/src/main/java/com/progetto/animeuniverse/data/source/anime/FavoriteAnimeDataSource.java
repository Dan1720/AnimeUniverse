package com.progetto.animeuniverse.data.source.anime;

import static com.progetto.animeuniverse.util.Constants.FIREBASE_FAVORITE_ANIME_COLLECTION;
import static com.progetto.animeuniverse.util.Constants.FIREBASE_REALTIME_DATABASE;
import static com.progetto.animeuniverse.util.Constants.FIREBASE_USERS_COLLECTION;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.progetto.animeuniverse.model.Anime;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAnimeDataSource extends BaseFavoriteAnimeDataSource{

    private static final String TAG = FavoriteAnimeDataSource.class.getSimpleName();
    private final DatabaseReference databaseReference;
    private final String idToken;

    public FavoriteAnimeDataSource(String idToken) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance(FIREBASE_REALTIME_DATABASE);
        this.databaseReference = firebaseDatabase.getReference().getRef();
        this.idToken = idToken;
    }


    @Override
    public void getFavoriteAnime() {
        databaseReference.child(FIREBASE_USERS_COLLECTION).child(idToken).
                child(FIREBASE_FAVORITE_ANIME_COLLECTION).get().addOnCompleteListener(task ->{
                    if(!task.isSuccessful()){
                        Log.d(TAG, "Error getting data", task.getException());
                    }else{
                        Log.d(TAG, "Successful read: " + task.getResult().getValue());
                        List<Anime> animeList = new ArrayList<>();
                        for(DataSnapshot ds : task.getResult().getChildren()){
                            Anime anime = ds.getValue(Anime.class);
                            anime.setSynchronized(true);
                            animeList.add(anime);
                        }
                        animeCallback.onSuccessFromCloudReading(animeList);
                    }
                });
    }

    @Override
    public void addFavoriteAnime(Anime anime) {
        databaseReference.child(FIREBASE_USERS_COLLECTION).child(idToken).
                child(FIREBASE_FAVORITE_ANIME_COLLECTION).child(String.valueOf(anime.hashCode())).setValue(anime)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        anime.setSynchronized(true);
                        animeCallback.onSuccessFromCloudWriting(anime);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        animeCallback.onFailureFromCloud(e);
                    }
                });
    }

    @Override
    public void synchronizeFavoriteAnime(List<Anime> notSynchronizedAnimeList) {
        databaseReference.child(FIREBASE_USERS_COLLECTION).child(idToken).
                child(FIREBASE_FAVORITE_ANIME_COLLECTION).get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Anime> animeList = new ArrayList<>();
                        for (DataSnapshot ds : task.getResult().getChildren()) {
                            Anime anime = ds.getValue(Anime.class);
                            anime.setSynchronized(true);
                            animeList.add(anime);
                        }

                        animeList.addAll(notSynchronizedAnimeList);

                        for (Anime anime : animeList) {
                            databaseReference.child(FIREBASE_USERS_COLLECTION).child(idToken).
                                    child(FIREBASE_FAVORITE_ANIME_COLLECTION).
                                    child(String.valueOf(anime.hashCode())).setValue(anime).addOnSuccessListener(
                                            new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    anime.setSynchronized(true);
                                                }
                                            }
                                    );
                        }
                    }
                });
    }

    @Override
    public void deleteFavoriteAnime(Anime anime) {
        databaseReference.child(FIREBASE_USERS_COLLECTION).child(idToken).
                child(FIREBASE_FAVORITE_ANIME_COLLECTION).child(String.valueOf(anime.hashCode())).
                removeValue().addOnSuccessListener(aVoid -> {
                    anime.setSynchronized(false);
                    animeCallback.onSuccessFromCloudWriting(anime);
                }).addOnFailureListener(e -> {
                    animeCallback.onFailureFromCloud(e);
                });
    }

    @Override
    public void deleteAllFavoriteAnime() {
        databaseReference.child(FIREBASE_USERS_COLLECTION).child(idToken).
                child(FIREBASE_FAVORITE_ANIME_COLLECTION).removeValue().addOnSuccessListener(aVoid -> {
                    animeCallback.onSuccessFromCloudWriting(null);
                }).addOnFailureListener(e -> {
                    animeCallback.onFailureFromCloud(e);
                });
    }

}

