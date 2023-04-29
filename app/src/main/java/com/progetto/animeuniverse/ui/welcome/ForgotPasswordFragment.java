package com.progetto.animeuniverse.ui.welcome;

import static com.progetto.animeuniverse.util.Constants.EMAIL_SEND_FORGOT_PASSWORD;
import static com.progetto.animeuniverse.util.Constants.SOMETHING_WRONG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.databinding.FragmentForgotPasswordBinding;

import org.apache.commons.validator.routines.EmailValidator;


public class ForgotPasswordFragment extends Fragment {
    private FirebaseAuth auth;
    private FragmentForgotPasswordBinding binding;
    private String text = EMAIL_SEND_FORGOT_PASSWORD;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentForgotPasswordBinding.inflate(inflater,container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnReimpostaPassword.setOnClickListener(v -> {
            String email = binding.emailInputForgotPasswordEditText.getText().toString().trim();
            if(isEmailOk(email)){
                binding.progressBarLogin.setVisibility(view.VISIBLE);
                auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(requireContext(), EMAIL_SEND_FORGOT_PASSWORD, Toast.LENGTH_LONG).show();
                            try {
                                Thread.sleep(4);
                                Navigation.findNavController(requireView()).navigate(R.id.action_forgotPasswordFragment_to_loginFragment);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }else{
                            Toast.makeText(requireContext(), SOMETHING_WRONG, Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }
        });
    }

    private boolean isEmailOk(String email) {
        if(!EmailValidator.getInstance().isValid((email))){
            binding.emailInputForgotPassword.setError(getString(R.string.error_email));
            return false;
        }else {
            binding.emailInputForgotPassword.setError(null);
            return true;
        }
    }
}