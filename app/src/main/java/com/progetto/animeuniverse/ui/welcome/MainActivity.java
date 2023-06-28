package com.progetto.animeuniverse.ui.welcome;


import static com.progetto.animeuniverse.util.Constants.FIREBASE_FAVORITE_ANIME_COLLECTION;
import static com.progetto.animeuniverse.util.Constants.FIREBASE_USERS_COLLECTION;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.progetto.animeuniverse.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#0A0B0D"));
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}