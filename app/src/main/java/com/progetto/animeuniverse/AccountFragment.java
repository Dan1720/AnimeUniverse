package com.progetto.animeuniverse;

import android.app.ProgressDialog;
import android.app.usage.NetworkStats;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.progetto.animeuniverse.databinding.FragmentAccountBinding;
import com.progetto.animeuniverse.repository.IUserRepository;
import com.progetto.animeuniverse.util.ServiceLocator;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.UUID;


public class AccountFragment extends Fragment {
    private static final String TAG = AccountFragment.class.getSimpleName();
    private FragmentAccountBinding fragmentAccountBinding;
    private UserViewModel userViewModel;
    private ImageView iVPreviewImage;
    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private Uri filePath;
    private DatabaseReference databaseReference;
    private StorageReference ref;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String code = user.getUid();

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

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        fragmentAccountBinding.btnLogout.setOnClickListener(v -> {
            userViewModel.logout();
            Navigation.findNavController(requireView()).navigate(R.id.action_accountFragment_to_mainActivity);

        });

        fragmentAccountBinding.btnImpostazioni.setOnClickListener(v ->{
            Navigation.findNavController(view).navigate(R.id.action_accountFragment_to_settingsFragment);
        });

        fragmentAccountBinding.btnAccountImpostazioni.setOnClickListener(v ->{
            Navigation.findNavController(view).navigate(R.id.action_accountFragment_to_settingsAccountFragment);
        });

        iVPreviewImage = view.findViewById(R.id.immagine_profilo);

        iVPreviewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
        String isNomeUtente = userViewModel.getLoggedUser().getNomeUtente();
        if( isNomeUtente != null){
            fragmentAccountBinding.nomeutente.setText(userViewModel.getLoggedUser().getNomeUtente().toString());
        }else{
            fragmentAccountBinding.nomeutente.setText(userViewModel.getLoggedUser().getEmail().toString());
        }




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
                        filePath = data.getData();
                        Bitmap selectedImageBitmap = null;
                        try{
                            selectedImageBitmap = MediaStore.Images.Media.getBitmap(
                                    requireActivity().getContentResolver(),
                                    filePath);
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                        iVPreviewImage.setImageBitmap(selectedImageBitmap);
                        uploadImage(filePath);
                    }
                }
            });

    private void uploadImage(Uri filePath) {
        System.out.println(this.filePath);
        if(this.filePath != null) {
            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            ref = storageReference.child("images/" + UUID.randomUUID().toString());


            ref.putFile(this.filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(requireActivity(), "Image Uploaded", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(requireActivity(), "Failed"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded"+(int) progress + "%");
                }
            });
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentAccountBinding = null;
    }
}