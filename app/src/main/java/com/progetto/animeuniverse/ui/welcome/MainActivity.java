package com.progetto.animeuniverse.ui.welcome;


import static com.progetto.animeuniverse.util.Constants.FIREBASE_FAVORITE_ANIME_COLLECTION;
import static com.progetto.animeuniverse.util.Constants.FIREBASE_USERS_COLLECTION;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.progetto.animeuniverse.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}