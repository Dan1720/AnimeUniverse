package com.progetto.animeuniverse;

import static com.progetto.animeuniverse.Constants.EMAIL_ADDRESS;
import static com.progetto.animeuniverse.Constants.PASSWORD;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.validator.routines.EmailValidator;

import java.io.IOException;
import java.security.GeneralSecurityException;
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final boolean NAV_COMPONENT = true;

    private TextInputLayout textInputEmail;
    private TextInputLayout textInputPassword;

    public LoginActivity(){}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

        textInputEmail = findViewById(R.id.emailInput);
        textInputPassword = findViewById(R.id.passwordinput);
        final Button provaAccesso = findViewById(R.id.provaaccesso);
        final Button buttonGoogleLogin = findViewById(R.id.google_login);



        provaAccesso.setOnClickListener(v -> {
            String email = textInputEmail.getEditText().getText().toString();
            String password = textInputPassword.getEditText().getText().toString();

            if (isEmailOk(email) & isPasswordOk(password)){
                Log.d(TAG, "Email and password are ok");
                saveLoginData(email, password);
            }else{
                Snackbar.make(findViewById(android.R.id.content),
                        R.string.login_not_ok, Snackbar.LENGTH_SHORT).show();
            }
        });



        //QUESTO PEZZO E' SOLO PER LA FASE DI TEST
        Button btn = (Button)findViewById(R.id.google_login);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));
            }
        });


    }
    private boolean isEmailOk(String email) {
        if(!EmailValidator.getInstance().isValid((email))){
            textInputEmail.setError(getString(R.string.error_email));
            return false;
        }else {
            textInputEmail.setError(null);
            return true;
        }
    }
    private boolean isPasswordOk(String password) {
        if(password.isEmpty()){
            textInputPassword.setError(getString(R.string.error_password));
            return false;
        }else{
            textInputPassword.setError(null);
            return true;
        }
    }

    private void saveLoginData(String email, String password) {

    }





}