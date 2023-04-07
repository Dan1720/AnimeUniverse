package com.progetto.animeuniverse;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class RegistrationFragment extends Fragment {

    private static final String TAG = RegistrationFragment.class.getSimpleName();

    private DataEncryptionUtil dataEncryptionUtil;



    public RegistrationFragment() {
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
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Button btnCreaAccount = (Button)view.findViewById(R.id.crea_account);
        final Button btnToLogin = (Button)view.findViewById(R.id.btn_login_registration);

        btnToLogin.setOnClickListener(v -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_registrationFragment_to_loginFragment);
        });

        btnCreaAccount.setOnClickListener(v -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_registrationFragment_to_welcomeActivity);
        });



    }
}