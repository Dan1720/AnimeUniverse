package com.progetto.animeuniverse.ui.main;

import static com.progetto.animeuniverse.util.Constants.LAST_UPDATE;
import static com.progetto.animeuniverse.util.Constants.SHARED_PREFERENCES_FILE_NAME;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.adapter.AnimeSpecificGenresRecyclerViewAdapter;
import com.progetto.animeuniverse.databinding.FragmentAnimeSpecificGenresBinding;
import com.progetto.animeuniverse.model.AnimeSpecificGenres;
import com.progetto.animeuniverse.model.Genre;
import com.progetto.animeuniverse.repository.anime_specific_genres.AnimeSpecificGenresRepository;
import com.progetto.animeuniverse.repository.anime_specific_genres.AnimeSpecificGenresResponseCallback;
import com.progetto.animeuniverse.repository.anime_specific_genres.IAnimeSpecificGenresRepository;
import com.progetto.animeuniverse.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

public class AnimeSpecificGenresFragment extends Fragment implements AnimeSpecificGenresResponseCallback {
    private FragmentAnimeSpecificGenresBinding fragmentAnimeSpecificGenresBinding;
    private List<AnimeSpecificGenres> animeSpecificGenresList;
    private SharedPreferencesUtil sharedPreferencesUtil;

    private IAnimeSpecificGenresRepository animeSpecificGenresRepository;

    private AnimeSpecificGenresRecyclerViewAdapter animeSpecificGenresRecyclerViewAdapter;
    public AnimeSpecificGenresFragment() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferencesUtil = new SharedPreferencesUtil(requireActivity().getApplication());

        animeSpecificGenresRepository = new AnimeSpecificGenresRepository(requireActivity().getApplication(), this);

        animeSpecificGenresList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentAnimeSpecificGenresBinding = FragmentAnimeSpecificGenresBinding.inflate(inflater, container, false);
        return fragmentAnimeSpecificGenresBinding.getRoot();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.clear();
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == android.R.id.home){
                    Navigation.findNavController(requireView()).navigateUp();
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

        NavBackStackEntry navBackStackEntry = Navigation.findNavController(view).getPreviousBackStackEntry();
        if(navBackStackEntry != null && navBackStackEntry.getDestination().getId() == R.id.homeFragment){
            ((BottomNavigationView) requireActivity().findViewById(R.id.bottom_navigation)).
                    getMenu().findItem(R.id.homeFragment).setChecked(true);

        }else if(navBackStackEntry != null && navBackStackEntry.getDestination().getId() == R.id.listFragment){
            ((BottomNavigationView) requireActivity().findViewById(R.id.bottom_navigation)).
                    getMenu().findItem(R.id.listFragment).setChecked(true);
        }

        assert getArguments() != null;
        Genre genre = AnimeSpecificGenresFragmentArgs.fromBundle(getArguments()).getGenres();

        String lastUpdate = "0";
        if (sharedPreferencesUtil.readStringData(
                SHARED_PREFERENCES_FILE_NAME, LAST_UPDATE) != null) {
            lastUpdate = sharedPreferencesUtil.readStringData(
                    SHARED_PREFERENCES_FILE_NAME, LAST_UPDATE);
        }

        RecyclerView AnimeSpecificGenresRecyclerViewItem = view.findViewById(R.id.recyclerView_gridSpecificGenres);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        animeSpecificGenresRecyclerViewAdapter = new AnimeSpecificGenresRecyclerViewAdapter(animeSpecificGenresList, requireActivity().getApplication(), new AnimeSpecificGenresRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onAnimeSpecificGenresClick(AnimeSpecificGenres animeSpecificGenres) {
                AnimeSpecificGenresFragmentDirections.ActionAnimeSpecificGenresFragmentToAnimeSpecificGenresDetailsFragment action =
                        AnimeSpecificGenresFragmentDirections.actionAnimeSpecificGenresFragmentToAnimeSpecificGenresDetailsFragment(animeSpecificGenres);
                Navigation.findNavController(view).navigate(action);
            }
        });
        AnimeSpecificGenresRecyclerViewItem.setAdapter(animeSpecificGenresRecyclerViewAdapter);
        AnimeSpecificGenresRecyclerViewItem.setLayoutManager(layoutManager);

        animeSpecificGenresRepository.fetchAnimeSpecificGenres(genre.getId(), Long.parseLong(lastUpdate));


    }

    @Override
    public void onSuccess(List<AnimeSpecificGenres> animeSpecificGenresList, long lastUpdate) {
        if(animeSpecificGenresList != null){
            this.animeSpecificGenresList.clear();
            this.animeSpecificGenresList.addAll(animeSpecificGenresList);
            sharedPreferencesUtil.writeStringData(SHARED_PREFERENCES_FILE_NAME, LAST_UPDATE, String.valueOf(lastUpdate));
        }

        requireActivity().runOnUiThread(new Runnable() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void run() {
                animeSpecificGenresRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onFailure(String errorMessage) {

    }
}