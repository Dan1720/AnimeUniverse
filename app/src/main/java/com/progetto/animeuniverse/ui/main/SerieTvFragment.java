package com.progetto.animeuniverse.ui.main;

import static com.progetto.animeuniverse.util.Constants.LAST_UPDATE;
import static com.progetto.animeuniverse.util.Constants.SHARED_PREFERENCES_FILE_NAME;
import static com.progetto.animeuniverse.util.Constants.TOP_HEADLINES_ENDPOINT;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.adapter.ChildItemAdapter;
import com.progetto.animeuniverse.adapter.ParentItemAdapter;
import com.progetto.animeuniverse.databinding.FragmentSerieTvBinding;
import com.progetto.animeuniverse.model.Anime;

import com.progetto.animeuniverse.model.AnimeGenres;
import com.progetto.animeuniverse.model.AnimeResponse;
import com.progetto.animeuniverse.model.Result;
import com.progetto.animeuniverse.repository.anime.AnimeResponseCallback;
import com.progetto.animeuniverse.repository.anime.IAnimeRepositoryWithLiveData;
import com.progetto.animeuniverse.util.ErrorMessagesUtil;
import com.progetto.animeuniverse.util.ServiceLocator;
import com.progetto.animeuniverse.util.SharedPreferencesUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class SerieTvFragment extends Fragment implements AnimeResponseCallback {

    private List<Anime> animeList;
    private SharedPreferencesUtil sharedPreferencesUtil;
    private AnimeViewModel animeViewModel;

    private FragmentSerieTvBinding fragmentSerieTvBinding;

    private final String q = TOP_HEADLINES_ENDPOINT;
    private final int threshold = 1;
    public SerieTvFragment() {
        // Required empty public constructor
    }

    public static SerieTvFragment newInstance(){
        return new SerieTvFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferencesUtil = new SharedPreferencesUtil(requireActivity().getApplication());

        IAnimeRepositoryWithLiveData animeRepositoryWithLiveData =
                ServiceLocator.getInstance().getAnimeRepository(
                        requireActivity().getApplication()
                );
        if(animeRepositoryWithLiveData != null){
            animeViewModel = new ViewModelProvider(requireActivity(),
                    new AnimeViewModelFactory(animeRepositoryWithLiveData)).get(AnimeViewModel.class);
        }else{
            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                    getString(R.string.unexpected_error), Snackbar.LENGTH_SHORT).show();
        }
        animeList = new ArrayList<>();



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentSerieTvBinding = FragmentSerieTvBinding.inflate(inflater, container, false);
        return fragmentSerieTvBinding.getRoot();
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView ParentRecyclerViewItem = view.findViewById(R.id.parent_recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        ParentItemAdapter parentItemAdapter = new ParentItemAdapter(ParentItemList(animeList), requireActivity().getApplication(),new ChildItemAdapter.OnItemClickListener(){
            @Override
            public void onAnimeItemClick(Anime anime){
                SerieTvFragmentDirections.ActionSerieTvFragmentToAnimeDetailsFragment action =
                        SerieTvFragmentDirections.actionSerieTvFragmentToAnimeDetailsFragment(anime);
                Navigation.findNavController(view).navigate(action);
            }
        });
        NavBackStackEntry navBackStackEntry = Navigation.findNavController(view).getPreviousBackStackEntry();
        if(navBackStackEntry != null && navBackStackEntry.getDestination().getId() == R.id.serieTvFragment){
            ((BottomNavigationView) requireActivity().findViewById(R.id.bottom_navigation)).
                    getMenu().findItem(R.id.serieTvFragment).setChecked(true);

        }else if(navBackStackEntry != null && navBackStackEntry.getDestination().getId() == R.id.listFragment){
            ((BottomNavigationView) requireActivity().findViewById(R.id.bottom_navigation)).
                    getMenu().findItem(R.id.listFragment).setChecked(true);
        }
        ParentRecyclerViewItem.setAdapter(parentItemAdapter);
        ParentRecyclerViewItem.setLayoutManager(layoutManager);



        String lastUpdate ="0";
        animeViewModel.getAnimeTop(Long.parseLong(lastUpdate)).observe(getViewLifecycleOwner(), result -> {
            System.out.println("Result: "+ result.isSuccess());
            if(result.isSuccess()){
                AnimeResponse animeResponse = ((Result.AnimeResponseSuccess) result).getData();
                List<Anime> fetchedAnime = animeResponse.getAnimeList();
                this.animeList.addAll(fetchedAnime);
                parentItemAdapter.notifyDataSetChanged();
                setImageHomeCover(animeList);
            }else {
                ErrorMessagesUtil errorMessagesUtil =
                        new ErrorMessagesUtil(requireActivity().getApplication());
                Snackbar.make(view, errorMessagesUtil.
                                getErrorMessage(((Result.Error) result).getMessage()),
                        Snackbar.LENGTH_SHORT).show();
            }
        });

        fragmentSerieTvBinding.txtCategorie.setOnClickListener(v -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_serieTvFragment_to_genresFragment);
        });
        fragmentSerieTvBinding.txtFilm.setOnClickListener(v -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_serieTvFragment_to_filmFragment);
        });
        fragmentSerieTvBinding.txtHome.setOnClickListener(v -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_serieTvFragment_to_homeFragment);
        });



    }


    @Override
    public void onSuccess(List<Anime> animeList, long lastUpdate) {
        if(animeList != null) {
            this.animeList.clear();
            this.animeList.addAll(animeList);
            sharedPreferencesUtil.writeStringData(SHARED_PREFERENCES_FILE_NAME, LAST_UPDATE, String.valueOf(lastUpdate));
        }
    }

    @Override
    public void onFailure(String errorMessage) {

    }

    @Override
    public void onAnimeFavoriteStatusChanged(Anime anime) {
        /*if(anime.isFavorite()){
            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                    R.string.add_anime_favorite, Snackbar.LENGTH_LONG).show();
        }else{
            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                    R.string.remove_anime_favorite, Snackbar.LENGTH_LONG).show();
        }*/
    }

    private boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        animeViewModel.setFirstLoading(true);
        animeViewModel.setLoading(false);
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        fragmentSerieTvBinding = null;
    }

    private List<ParentItem> ParentItemList(List<Anime> animeList)
    {
        List<ParentItem> itemList
                = new ArrayList<>();

        ParentItem item
                = new ParentItem(
                "Anime del momento", animeList
        );
        itemList.add(item);

        return itemList;
    }

    @SuppressLint("SetTextI18n")
    public void setImageHomeCover(List<Anime>animeList) {
        ImageView homeCover = getView().findViewById(R.id.serieTvCover);
        TextView categories = getView().findViewById(R.id.txt_btn_categorie);
        Random random = new Random();
        int number = random.nextInt(animeList.size());
        Anime animeSerieTvCover = animeList.get(number);
        Picasso.get()
                .load(animeSerieTvCover.getImages().getJpgImages().getLargeImageUrl())
                .into(homeCover);
        List<AnimeGenres> genres = animeSerieTvCover.getGenres();
        if(genres.size() >= 3){
            categories.setText(genres.get(0).getNameGenre() + " - " + genres.get(1).getNameGenre() +" - "+ genres.get(2).getNameGenre());
        }else{
            categories.setText(genres.get(0).getNameGenre());
        }
        fragmentSerieTvBinding.imageViewInfo.setOnClickListener(v ->{
            SerieTvFragmentDirections.ActionSerieTvFragmentToAnimeDetailsFragment action =
                    SerieTvFragmentDirections.actionSerieTvFragmentToAnimeDetailsFragment(animeSerieTvCover);
            Navigation.findNavController(requireView()).navigate(action);
        });

        fragmentSerieTvBinding.serieTvCover.setOnClickListener(v ->{
            SerieTvFragmentDirections.ActionSerieTvFragmentToAnimeDetailsFragment action =
                    SerieTvFragmentDirections.actionSerieTvFragmentToAnimeDetailsFragment(animeSerieTvCover);
            Navigation.findNavController(requireView()).navigate(action);
        });


    }


}