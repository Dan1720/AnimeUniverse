package com.progetto.animeuniverse.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
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
import com.google.android.material.snackbar.Snackbar;
import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.adapter.AnimeSpecificGenresRecyclerViewAdapter;
import com.progetto.animeuniverse.databinding.FragmentAnimeSpecificGenresBinding;
import com.progetto.animeuniverse.model.Anime;
import com.progetto.animeuniverse.model.AnimeSpecificGenres;
import com.progetto.animeuniverse.model.AnimeSpecificGenresResponse;
import com.progetto.animeuniverse.model.Genre;
import com.progetto.animeuniverse.model.Result;
import com.progetto.animeuniverse.repository.anime_specific_genres.IAnimeSpecificGenresRepositoryWithLiveData;
import com.progetto.animeuniverse.util.ErrorMessagesUtil;
import com.progetto.animeuniverse.util.ServiceLocator;
import com.progetto.animeuniverse.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

public class AnimeSpecificGenresFragment extends Fragment {



    private FragmentAnimeSpecificGenresBinding fragmentAnimeSpecificGenresBinding;
    private List<AnimeSpecificGenres> animeSpecificGenresList;
    private SharedPreferencesUtil sharedPreferencesUtil;
    private AnimeSpecificGenresViewModel animeSpecificGenresViewModel;

    public AnimeSpecificGenresFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferencesUtil = new SharedPreferencesUtil(requireActivity().getApplication());

        IAnimeSpecificGenresRepositoryWithLiveData animeSpecificGenresRepositoryWithLiveData =
                ServiceLocator.getInstance().getAnimeSpecificGenresRepository(requireActivity().getApplication());
        if(animeSpecificGenresRepositoryWithLiveData != null){
            animeSpecificGenresViewModel = new ViewModelProvider(requireActivity(), new AnimeSpecificGenresViewModelFactory(animeSpecificGenresRepositoryWithLiveData)).get(AnimeSpecificGenresViewModel.class);
        }else{
            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                    getString(R.string.unexpected_error), Snackbar.LENGTH_SHORT).show();
        }

        animeSpecificGenresList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentAnimeSpecificGenresBinding = FragmentAnimeSpecificGenresBinding.inflate(inflater, container, false);
        return fragmentAnimeSpecificGenresBinding.getRoot();
    }

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

        Genre genre = AnimeSpecificGenresFragmentArgs.fromBundle(getArguments()).getGenres();

        RecyclerView AnimeSpecificGenresRecyclerViewItem = view.findViewById(R.id.recyclerView_gridSpecificGenres);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        AnimeSpecificGenresRecyclerViewAdapter animeSpecificGenresRecyclerViewAdapter = new AnimeSpecificGenresRecyclerViewAdapter(animeSpecificGenresList, requireActivity().getApplication(), new AnimeSpecificGenresRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onAnimeSpecificGenresClick(AnimeSpecificGenres animeSpecificGenres) {
                AnimeSpecificGenresFragmentDirections.ActionAnimeSpecificGenresFragmentToAnimeSpecificGenresDetailsFragment action =
                        AnimeSpecificGenresFragmentDirections.actionAnimeSpecificGenresFragmentToAnimeSpecificGenresDetailsFragment(animeSpecificGenres);
                Navigation.findNavController(view).navigate(action);
            }
        });
        AnimeSpecificGenresRecyclerViewItem.setAdapter(animeSpecificGenresRecyclerViewAdapter);
        AnimeSpecificGenresRecyclerViewItem.setLayoutManager(layoutManager);

        String lastUpdate = "0";
        animeSpecificGenresViewModel.getAnimeSpecificGenres(genre.getId(), Long.parseLong(lastUpdate)).observe(getViewLifecycleOwner(), result ->{
            System.out.println("Result anime SpecificGenres: " + result.isSuccess());
            if(result.isSuccess()){
                AnimeSpecificGenresResponse animeSpecificGenresResponse = ((Result.AnimeSpecificGenresSuccess) result).getData();
                List<AnimeSpecificGenres> fetchedAnimeSpecificGenres = animeSpecificGenresResponse.getAnimeSpecificGenresList();
                this.animeSpecificGenresList.addAll(fetchedAnimeSpecificGenres);
                animeSpecificGenresRecyclerViewAdapter.notifyDataSetChanged();
            }else{
                ErrorMessagesUtil errorMessagesUtil =
                        new ErrorMessagesUtil(requireActivity().getApplication());
                Snackbar.make(view, errorMessagesUtil.
                                getErrorMessage(((Result.Error) result).getMessage()),
                        Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}