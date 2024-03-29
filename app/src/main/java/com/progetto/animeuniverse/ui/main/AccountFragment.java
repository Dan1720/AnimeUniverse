package com.progetto.animeuniverse.ui.main;

import android.app.ProgressDialog;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.ui.welcome.UserViewModel;
import com.progetto.animeuniverse.ui.welcome.UserViewModelFactory;
import com.progetto.animeuniverse.databinding.FragmentAccountBinding;
import com.progetto.animeuniverse.repository.user.IUserRepository;
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
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String code = user.getUid();
    private String isNomeUtente = null;

    private StorageReference ImagesRef;
    public AccountFragment() {

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

        DatabaseReference yImage = FirebaseDatabase.getInstance().getReference().child("users").child(code).child("photoUrl");

        yImage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String urlImage = snapshot.getValue(String.class);
                if(urlImage != null){
                    Picasso.get().load(urlImage).into(iVPreviewImage);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireActivity(), "logout done", Toast.LENGTH_SHORT).show();
            }
        });


        iVPreviewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        setProfileName();

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
            ImagesRef = storageReference.child("images/"+code+".jpg");

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

        StorageReference storageRef=FirebaseStorage.getInstance().getReference().child("images/"+ UUID.randomUUID().toString());
        storageRef.putFile(filePath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Uri URL = uri;
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(code);
                            ref.child("photoUrl").setValue(URL.toString());
                        }
                    });
                }else {
                    Log.i("wentWrong","downloadUri failure");
                }
            }
        });


    }

    public void setProfileName(){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("users").child(code).child("nomeUtente");

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                isNomeUtente = dataSnapshot.getValue(String.class);
                if (isNomeUtente != null) {
                    System.out.println("nome: " + isNomeUtente );
                    if( isNomeUtente != null){
                        fragmentAccountBinding.nomeutente.setText(isNomeUtente);
                    }else{
                        fragmentAccountBinding.nomeutente.setText(userViewModel.getLoggedUser().getEmail().toString());
                    }
                } else {
                    // La stringa è null o non esiste nel percorso specificato
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Si è verificato un errore durante il recupero dei dati
            }
        });

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentAccountBinding = null;
    }
}