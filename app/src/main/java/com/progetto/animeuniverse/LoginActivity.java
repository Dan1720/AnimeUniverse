package com.progetto.animeuniverse;

import static com.progetto.animeuniverse.Constants.APP_DATA_FILE;
import static com.progetto.animeuniverse.Constants.EMAIL_ADDRESS;
import static com.progetto.animeuniverse.Constants.PASSWORD;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.security.crypto.EncryptedFile;
import androidx.security.crypto.MasterKey;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;


import org.apache.commons.validator.routines.EmailValidator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final boolean NAV_COMPONENT = true;
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputPassword;
    private DataEncryptionUtil dataEncryptionUtil;
    public LoginActivity(){}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

        dataEncryptionUtil = new DataEncryptionUtil(getApplication());

        textInputEmail = findViewById(R.id.emailInput);
        textInputPassword = findViewById(R.id.passwordinput);
        final Button provaAccesso = findViewById(R.id.provaaccesso);
        final Button buttonGoogleLogin = findViewById(R.id.google_login);

        provaAccesso.setOnClickListener(v -> {
            String email = textInputEmail.getEditText().getText().toString();
            String password = textInputPassword.getEditText().getText().toString();

            if (isEmailOk(email) & isPasswordOk(password)){
                Log.d(TAG, "Email and password are ok");
                try{
                    saveLoginData(textInputPassword.getEditText().getText().toString());
                }catch (GeneralSecurityException | IOException e){
                    e.printStackTrace();
                }

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

        Button btnReg = (Button)findViewById(R.id.registrati);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
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

    private String readLoginData() throws GeneralSecurityException, IOException{
        MasterKey mainKey = new MasterKey.Builder(this)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build();

        EncryptedFile encryptedFile = new EncryptedFile.Builder(this,
                new File(getFilesDir(),APP_DATA_FILE),
                mainKey,
                EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB).build();

        InputStream inputStream = encryptedFile.openFileInput();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int nextByte = inputStream.read();
        while (nextByte != -1){
            byteArrayOutputStream.write(nextByte);
            nextByte = inputStream.read();
        }

        byte[] plaintext = byteArrayOutputStream.toByteArray();
        return new String(plaintext, StandardCharsets.UTF_8);
    }
    private void saveLoginData(String credentials) throws GeneralSecurityException, IOException{
        MasterKey mainKey = new MasterKey.Builder(this)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build();

        File fileToWrite = new File(getFilesDir(), APP_DATA_FILE);
        EncryptedFile encryptedFile = new EncryptedFile.Builder(this,
                new File(getFilesDir(),APP_DATA_FILE),
                mainKey,
                EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB).build();

        if(fileToWrite.exists()){
            fileToWrite.delete();
        }

        byte[] fileContent = credentials.getBytes(StandardCharsets.UTF_8);
        OutputStream outputStream = encryptedFile.openFileOutput();
        outputStream.write(fileContent);
        outputStream.flush();
        outputStream.close();
    }





}