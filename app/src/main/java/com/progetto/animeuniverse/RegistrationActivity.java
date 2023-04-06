package com.progetto.animeuniverse;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class RegistrationActivity extends AppCompatActivity {

    private static final String TAG = RegistrationActivity.class.getSimpleName();

  
    private DataEncryptionUtil dataEncryptionUtil;

    public RegistrationActivity(){}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_registration);
    }
}