package com.progetto.animeuniverse;

import static com.progetto.animeuniverse.util.Constants.SELECT_PICTURE;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;
import com.progetto.animeuniverse.databinding.FragmentAccountBinding;
import com.progetto.animeuniverse.repository.IUserRepository;
import com.progetto.animeuniverse.util.ServiceLocator;

import java.io.IOException;


public class AccountFragment extends Fragment {

    private FragmentAccountBinding fragmentAccountBinding;
    private UserViewModel userViewModel;

    ImageView iVPreviewImage;


    public AccountFragment() {
        // Required empty public constructor
    }

    public AccountFragment newInstance(){
        return new AccountFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IUserRepository userRepository = ServiceLocator.getInstance().getUserRepository(requireActivity().getApplication());
        userViewModel = new ViewModelProvider(requireActivity(), new UserViewModelFactory(userRepository)).get(UserViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentAccountBinding = FragmentAccountBinding.inflate(inflater, container, false);
        return fragmentAccountBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragmentAccountBinding.btnLogout.setOnClickListener(v -> {
           userViewModel.logout().observe(getViewLifecycleOwner(), result ->{
               if(result.isSuccess()){
                   Navigation.findNavController(view).navigate(
                           R.id.action_accountFragment_return_loginFragment);

               }else{
                   Snackbar.make(view, requireActivity().getString(R.string.unexpected_error), Snackbar.LENGTH_SHORT).show();
               }
           });
        });

        iVPreviewImage = view.findViewById(R.id.immagineprofilo);
        iVPreviewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
    }

    private void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        launchSomeActivity.launch(i);
    }

    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result ->{
                if(result.getResultCode() == requireActivity().RESULT_OK){
                    Intent data = result.getData();
                    if(data != null && data.getData() != null){
                        Uri selectedImageUri = data.getData();
                        Bitmap selectedImageBitmap = null;
                        try{
                            selectedImageBitmap = MediaStore.Images.Media.getBitmap(
                                    requireActivity().getContentResolver(),
                                    selectedImageUri);
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                        iVPreviewImage.setImageBitmap(selectedImageBitmap);
                    }
                }
            });


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentAccountBinding = null;
    }
}