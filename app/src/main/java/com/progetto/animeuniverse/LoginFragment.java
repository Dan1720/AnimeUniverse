package com.progetto.animeuniverse;

import static com.progetto.animeuniverse.Constants.APP_DATA_FILE;
import static com.progetto.animeuniverse.Constants.EMAIL_ADDRESS;
import static com.progetto.animeuniverse.Constants.ENCRYPTED_DATA_FILE_NAME;
import static com.progetto.animeuniverse.Constants.ENCRYPTED_SHARED_PREFERENCES_FILE_NAME;
import static com.progetto.animeuniverse.Constants.PASSWORD;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.security.crypto.EncryptedFile;
import androidx.security.crypto.MasterKey;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.validator.routines.EmailValidator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;


public class LoginFragment extends Fragment {
    private static final String TAG = LoginFragment.class.getSimpleName();
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputPassword;
    private DataEncryptionUtil dataEncryptionUtil;


    public LoginFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataEncryptionUtil = new DataEncryptionUtil(requireContext());

        textInputEmail = view.findViewById(R.id.emailInput);
        textInputPassword = view.findViewById(R.id.passwordinput);
        final Button provaAccesso = view.findViewById(R.id.provaaccesso);
        final Button buttonGoogleLogin = view.findViewById(R.id.google_login);
        final Button btnReg = (Button)view.findViewById(R.id.registrati);

        provaAccesso.setOnClickListener(v -> {
            String email = textInputEmail.getEditText().getText().toString();
            Log.d(TAG, email);
            String password = textInputPassword.getEditText().getText().toString();

            if (isEmailOk(email) & isPasswordOk(password)){
                Log.d(TAG, "Email and password are ok");
                try{
                    saveLoginData(email, password);
                    Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_welcomeActivity);
                }catch (GeneralSecurityException | IOException e){
                    e.printStackTrace();
                }

            }else{
                Snackbar.make(requireActivity().findViewById(android.R.id.content),
                        R.string.login_not_ok, Snackbar.LENGTH_SHORT).show();
            }
        });





        buttonGoogleLogin.setOnClickListener(v -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_welcomeActivity);
        });

        btnReg.setOnClickListener(v-> {
            Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_registrationFragment);
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

    private void saveLoginData(String email, String password) throws GeneralSecurityException, IOException {
        try{
            dataEncryptionUtil.writeSecretDataWithEncryptedSharedPreferences(ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, EMAIL_ADDRESS, email);
            dataEncryptionUtil.writeSecretDataWithEncryptedSharedPreferences(ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, PASSWORD, password);
            dataEncryptionUtil.writeSecreteDataOnFile(ENCRYPTED_DATA_FILE_NAME,email.concat(":").concat(password));
        }catch (GeneralSecurityException|IOException e){
            e.printStackTrace();
        }
    }
}