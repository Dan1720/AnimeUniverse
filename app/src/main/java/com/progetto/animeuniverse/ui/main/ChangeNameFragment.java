package com.progetto.animeuniverse.ui.main;

import static com.progetto.animeuniverse.util.Constants.CHANGE_NAME_OK;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.ui.welcome.UserViewModel;
import com.progetto.animeuniverse.databinding.FragmentChangeNameBinding;


public class ChangeNameFragment extends Fragment {

    private FragmentChangeNameBinding binding;
    private UserViewModel userViewModel;

    public ChangeNameFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChangeNameBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnChangeName.setOnClickListener(v -> {
            binding.progressBarLogin.setVisibility(view.VISIBLE);
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String code = user.getUid();
            String newName = binding.nameInputChangeNameEditText.getText().toString().trim();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(code);
            ref.child("nomeUtente").setValue(newName);
            Toast.makeText(requireContext(), CHANGE_NAME_OK + newName, Toast.LENGTH_LONG).show();
            Navigation.findNavController(requireView()).navigate(R.id.action_changeNameFragment_to_accountFragment);
        });


    }
}