package com.progetto.animeuniverse;

import static com.progetto.animeuniverse.util.Constants.EMAIL_ADDRESS;
import static com.progetto.animeuniverse.util.Constants.ENCRYPTED_DATA_FILE_NAME;
import static com.progetto.animeuniverse.util.Constants.ENCRYPTED_SHARED_PREFERENCES_FILE_NAME;
import static com.progetto.animeuniverse.util.Constants.ID_TOKEN;
import static com.progetto.animeuniverse.util.Constants.MINIMUM_PASSWORD_LENGTH;
import static com.progetto.animeuniverse.util.Constants.PASSWORD;
import static com.progetto.animeuniverse.util.Constants.USER_COLLISION_ERROR;
import static com.progetto.animeuniverse.util.Constants.WEAK_PASSWORD_ERROR;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.progetto.animeuniverse.databinding.FragmentRegistrationBinding;
import com.progetto.animeuniverse.util.DataEncryptionUtil;

import org.apache.commons.validator.routines.EmailValidator;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class RegistrationFragment extends Fragment {

    private static final String TAG = RegistrationFragment.class.getSimpleName();
    private FragmentRegistrationBinding binding;
    private DataEncryptionUtil dataEncryptionUtil;
    private UserViewModel userViewModel;


    public RegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        userViewModel.setAuthenticationError(false);
        dataEncryptionUtil = new DataEncryptionUtil(requireActivity().getApplication());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_registration, container, false);
        binding = FragmentRegistrationBinding.inflate(inflater,container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Button btnToLogin = (Button)view.findViewById(R.id.btn_login_registration);

        btnToLogin.setOnClickListener(v -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_registrationFragment_to_loginFragment);
        });

        binding.creaAccount.setOnClickListener(v -> {
            String email = binding.emailTextInputEditText.getText().toString().trim();
            String password = binding.passwordTextInputEditText.getText().toString().trim();
            if(isEmailOk(email) & isPasswordOk(password)){
                binding.progressBar.setVisibility(View.VISIBLE);
                if(!userViewModel.isAuthenticationError()){
                    userViewModel.getUserMutableLiveData(email, password, false).observe(
                            getViewLifecycleOwner(), result -> {
                                if (result.isSuccess()) {
                                    User user = ((Result.UserResponseSuccess) result).getData();
                                    try {
                                        saveLoginData(email, password, user.getIdToken());
                                    } catch (GeneralSecurityException e) {
                                        throw new RuntimeException(e);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                    userViewModel.setAuthenticationError(false);
                                    Navigation.findNavController(view).navigate(R.id.action_registrationFragment_to_welcomeActivity);

                                } else {
                                    userViewModel.setAuthenticationError(true);
                                    Snackbar.make(requireActivity().findViewById(android.R.id.content),
                                            getErrorMessage(((Result.Error) result).getMessage()),
                                            Snackbar.LENGTH_SHORT).show();
                                }
                            });
                }else{
                    userViewModel.getUser(email, password, false);
                }
                binding.progressBar.setVisibility(View.GONE);
            }else{
                userViewModel.setAuthenticationError(true);
                Snackbar.make(requireActivity().findViewById(android.R.id.content),
                        R.string.check_login_data_message, Snackbar.LENGTH_SHORT).show();
            }
        });



    }

    private boolean isEmailOk(String email) {
        if(!EmailValidator.getInstance().isValid((email))){
            binding.emailInputRegistration.setError(getString(R.string.error_email));
            return false;
        }else {
            binding.emailInputRegistration.setError(null);
            return true;
        }
    }
    private boolean isPasswordOk(String password) {
        if(password.isEmpty() || password.length() < MINIMUM_PASSWORD_LENGTH){
            binding.passwordInputRegistration.setError(getString(R.string.error_password));
            return false;
        }else{
            binding.passwordInputRegistration.setError(null);
            return true;
        }
    }

    private void saveLoginData(String email, String password, String idToken) throws GeneralSecurityException, IOException {
        try{
            dataEncryptionUtil.writeSecretDataWithEncryptedSharedPreferences(ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, EMAIL_ADDRESS, email);
            dataEncryptionUtil.writeSecretDataWithEncryptedSharedPreferences(ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, PASSWORD, password);
            dataEncryptionUtil.writeSecretDataWithEncryptedSharedPreferences(ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, ID_TOKEN, idToken);
            dataEncryptionUtil.writeSecreteDataOnFile(ENCRYPTED_DATA_FILE_NAME,email.concat(":").concat(password));
        }catch (GeneralSecurityException|IOException e){
            e.printStackTrace();
        }
    }

    private String getErrorMessage(String message) {
        switch(message) {
            case WEAK_PASSWORD_ERROR:
                return requireActivity().getString(R.string.error_password);
            case USER_COLLISION_ERROR:
                return requireActivity().getString(R.string.error_user_collision_message);
            default:
                return requireActivity().getString(R.string.unexpected_error);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}