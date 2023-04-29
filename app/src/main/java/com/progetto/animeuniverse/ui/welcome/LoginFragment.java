package com.progetto.animeuniverse.ui.welcome;

import static com.progetto.animeuniverse.util.Constants.EMAIL_ADDRESS;
import static com.progetto.animeuniverse.util.Constants.ENCRYPTED_DATA_FILE_NAME;
import static com.progetto.animeuniverse.util.Constants.ENCRYPTED_SHARED_PREFERENCES_FILE_NAME;
import static com.progetto.animeuniverse.util.Constants.ID_TOKEN;
import static com.progetto.animeuniverse.util.Constants.INVALID_CREDENTIALS_ERROR;
import static com.progetto.animeuniverse.util.Constants.INVALID_USER_ERROR;
import static com.progetto.animeuniverse.util.Constants.PASSWORD;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.model.Result;
import com.progetto.animeuniverse.model.User;
import com.progetto.animeuniverse.repository.user.IUserRepository;
import com.progetto.animeuniverse.util.DataEncryptionUtil;
import com.progetto.animeuniverse.util.ServiceLocator;
import com.progetto.animeuniverse.util.SharedPreferencesUtil;

import org.apache.commons.validator.routines.EmailValidator;

import java.io.IOException;
import java.security.GeneralSecurityException;


public class LoginFragment extends Fragment {
    private static final String TAG = LoginFragment.class.getSimpleName();
    private static final boolean USE_NAVIGATION_COMPONENT = true;

    private ActivityResultLauncher<IntentSenderRequest> activityResultLauncher;
    private ActivityResultContracts.StartIntentSenderForResult startIntentSenderForResult;

    private UserViewModel userViewModel;
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputPassword;
    private DataEncryptionUtil dataEncryptionUtil;
    private ProgressBar progressBar;

    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(){
        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IUserRepository userRepository = ServiceLocator.getInstance().
                getUserRepository(requireActivity().getApplication());
        userViewModel = new ViewModelProvider(requireActivity(), new UserViewModelFactory(userRepository)).get(UserViewModel.class);
        dataEncryptionUtil = new DataEncryptionUtil(requireActivity().getApplication());
        oneTapClient = Identity.getSignInClient(requireActivity());
        signInRequest = BeginSignInRequest.builder()
                .setPasswordRequestOptions(BeginSignInRequest.PasswordRequestOptions.builder()
                        .setSupported(true)
                        .build())
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        .setServerClientId(getString(R.string.default_web_client_id))
                        .setFilterByAuthorizedAccounts(false)
                        .build())

                .setAutoSelectEnabled(true)
                .build();

        startIntentSenderForResult = new ActivityResultContracts.StartIntentSenderForResult();

        activityResultLauncher = registerForActivityResult(startIntentSenderForResult, activityResult ->{
            if(activityResult.getResultCode() == Activity.RESULT_OK){
                Log.d(TAG, "result.getResultCode() == Activity.RESULT_OK");
                try{
                    SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(activityResult.getData());
                    String idToken = credential.getGoogleIdToken();
                    if(idToken != null){
                        userViewModel.getGoogleUserMutableLiveData(idToken).observe(getViewLifecycleOwner(), authenticationResult ->{
                            if(authenticationResult.isSuccess()){
                                User user = ((Result.UserResponseSuccess) authenticationResult).getData();
                                try {
                                    saveLoginData(user.getEmail(), null, user.getIdToken());
                                    userViewModel.setAuthenticationError(false);
                                    retrieveUserInformationAndStartActivity(user, R.id.action_loginFragment_to_welcomeActivity);
                                } catch (GeneralSecurityException e) {
                                    throw new RuntimeException(e);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }else{
                                userViewModel.setAuthenticationError(true);
                                progressBar.setVisibility(View.GONE);
                                Snackbar.make(requireActivity().findViewById(android.R.id.content),
                                        getErrorMessage(((Result.Error) authenticationResult).getMessage()),
                                        Snackbar.LENGTH_SHORT).show();
                            }
                        });
                    }
                }catch (ApiException e) {
                    Snackbar.make(requireActivity().findViewById(android.R.id.content),
                            requireActivity().getString(R.string.unexpected_error),
                            Snackbar.LENGTH_SHORT).show();
                } ;
            }
        });
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

        textInputEmail = view.findViewById(R.id.emailInput_forgotPassword);
        textInputPassword = view.findViewById(R.id.passwordinput);

        final Button provaAccesso = view.findViewById(R.id.btn_accedi);
        final Button buttonGoogleLogin = view.findViewById(R.id.google_login);
        final Button btnReg = (Button)view.findViewById(R.id.registrati);
        final Button btnForgotPassword = (Button)view.findViewById(R.id.forgotpassword);
        progressBar = view.findViewById(R.id.progress_bar_login);

        if(userViewModel.getLoggedUser() != null){
            SharedPreferencesUtil sharedPreferencesUtil =
                    new SharedPreferencesUtil(requireActivity().getApplication());
            startActivityBasedOnCondition(WelcomeActivity.class, R.id.action_loginFragment_to_welcomeActivity);
            //da gestire la parte dei preferiti
        }

        provaAccesso.setOnClickListener(v -> {
            String email = textInputEmail.getEditText().getText().toString();
            Log.d(TAG, email);
            String password = textInputPassword.getEditText().getText().toString();

            if (isEmailOk(email) & isPasswordOk(password)){
                if(!userViewModel.isAuthenticationError()){
                    progressBar.setVisibility(View.VISIBLE);
                    userViewModel.getUserMutableLiveData(email, password, true).observe(
                            getViewLifecycleOwner(), result -> {
                                if(result.isSuccess()){
                                    User user = ((Result.UserResponseSuccess) result).getData();
                                    try {
                                        saveLoginData(email, password, user.getIdToken());
                                    } catch (GeneralSecurityException e) {
                                        throw new RuntimeException(e);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                    userViewModel.setAuthenticationError(false);
                                    retrieveUserInformationAndStartActivity(user, R.id.action_loginFragment_to_welcomeActivity);

                                }else{
                                    userViewModel.setAuthenticationError(true);
                                    progressBar.setVisibility(View.GONE);
                                    Snackbar.make(requireActivity().findViewById(android.R.id.content),
                                            getErrorMessage(((Result.Error) result).getMessage()),
                                            Snackbar.LENGTH_SHORT).show();
                                }
                            });
                }else{
                    userViewModel.getUser(email, password, true);
                }

            }else{
                Snackbar.make(requireActivity().findViewById(android.R.id.content),
                        R.string.check_login_data_message, Snackbar.LENGTH_SHORT).show();
            }
        });





        buttonGoogleLogin.setOnClickListener(v -> oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(requireActivity(), new OnSuccessListener<BeginSignInResult>() {
                    @Override
                    public void onSuccess(BeginSignInResult result) {
                        Log.d(TAG, "onSuccess from oneTapClient.beginSignIn(BeginSignInRequest)");
                        IntentSenderRequest intentSenderRequest =
                                new IntentSenderRequest.Builder(result.getPendingIntent()).build();
                        activityResultLauncher.launch(intentSenderRequest);

                    }
                })
                .addOnFailureListener(requireActivity(), new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(requireActivity().findViewById(android.R.id.content),
                                requireActivity().getString(R.string.error_no_google_account_found_message),
                                Snackbar.LENGTH_SHORT).show();
                    }
                }));


        btnReg.setOnClickListener(v-> {
            Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_registrationFragment);
        });

        btnForgotPassword.setOnClickListener(v ->{
            Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_forgotPasswordFragment);
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

    private void retrieveUserInformationAndStartActivity(User user, int destination) {
        progressBar.setVisibility(View.VISIBLE);
        startActivityBasedOnCondition(WelcomeActivity.class, destination);
        //gestire preferiti


    }

    private void startActivityBasedOnCondition(Class<?> destinationActivity, int destination) {
        if (USE_NAVIGATION_COMPONENT) {
            Navigation.findNavController(requireView()).navigate(destination);
        } else {
            Intent intent = new Intent(requireContext(), destinationActivity);
            startActivity(intent);
        }
        requireActivity().finish();
    }
    private void saveLoginData(String email, String password, String idToken) throws GeneralSecurityException, IOException {
        try{
            dataEncryptionUtil.writeSecretDataWithEncryptedSharedPreferences(ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, EMAIL_ADDRESS, email);
            dataEncryptionUtil.writeSecretDataWithEncryptedSharedPreferences(ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, PASSWORD, password);
            dataEncryptionUtil.writeSecretDataWithEncryptedSharedPreferences(
                    ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, ID_TOKEN, idToken);
            if(password != null){
                dataEncryptionUtil.writeSecreteDataOnFile(ENCRYPTED_DATA_FILE_NAME,email.concat(":").concat(password));
            }
        }catch (GeneralSecurityException|IOException e){
            e.printStackTrace();
        }
    }

    private String getErrorMessage(String errorType) {
        switch (errorType) {
            case INVALID_CREDENTIALS_ERROR:
                return requireActivity().getString(R.string.error_login_password_message);
            case INVALID_USER_ERROR:
                return requireActivity().getString(R.string.error_login_user_message);
            default:
                return requireActivity().getString(R.string.unexpected_error);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        userViewModel.setAuthenticationError(false);
    }

}