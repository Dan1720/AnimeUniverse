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
import com.progetto.animeuniverse.adapter.AnimeMovieRecyclerViewAdapter;
import com.progetto.animeuniverse.databinding.FragmentAnimeMovieBinding;
import com.progetto.animeuniverse.model.AnimeMovie;
import com.progetto.animeuniverse.model.AnimeMovieResponse;
import com.progetto.animeuniverse.model.Result;
import com.progetto.animeuniverse.repository.anime_movie.IAnimeMovieRepositoryWithLiveData;
import com.progetto.animeuniverse.util.ErrorMessagesUtil;
import com.progetto.animeuniverse.util.ServiceLocator;
import com.progetto.animeuniverse.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;


public class AnimeMovieFragment extends Fragment {

    private FragmentAnimeMovieBinding fragmentAnimeMovieBinding;
    private List<AnimeMovie> animeMovieList;
    private SharedPreferencesUtil sharedPreferencesUtil;
    private AnimeMovieViewModel animeMovieViewModel;

    public AnimeMovieFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferencesUtil = new SharedPreferencesUtil(requireActivity().getApplication());

        IAnimeMovieRepositoryWithLiveData animeMovieRepositoryWithLiveData =
                ServiceLocator.getInstance().getAnimeMovieRepository(requireActivity().getApplication());
        if(animeMovieRepositoryWithLiveData != null){
            animeMovieViewModel = new ViewModelProvider(requireActivity(), new AnimeMovieViewModelFactory(animeMovieRepositoryWithLiveData)).get(AnimeMovieViewModel.class);
        }else{
            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                    getString(R.string.unexpected_error), Snackbar.LENGTH_SHORT).show();
        }

        animeMovieList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentAnimeMovieBinding = FragmentAnimeMovieBinding.inflate(inflater, container, false);
        return fragmentAnimeMovieBinding.getRoot();
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

        RecyclerView AnimeMovieRecyclerViewItem = view.findViewById(R.id.recyclerView_gridMovie);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        AnimeMovieRecyclerViewAdapter animeMovieRecyclerViewAdapter = new AnimeMovieRecyclerViewAdapter(animeMovieList, requireActivity().getApplication(), new AnimeMovieRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onAnimeMovieClick(AnimeMovie animeMovie) {
                AnimeMovieFragmentDirections.ActionAnimeMovieFragmentToAnimeMovieDetailsFragment action =
                        AnimeMovieFragmentDirections.actionAnimeMovieFragmentToAnimeMovieDetailsFragment(animeMovie);
                Navigation.findNavController(view).navigate(action);
            }
        });
        AnimeMovieRecyclerViewItem.setAdapter(animeMovieRecyclerViewAdapter);
        AnimeMovieRecyclerViewItem.setLayoutManager(layoutManager);

        String lastUpdate = "0";
        animeMovieViewModel.getAnimeMovie(Long.parseLong(lastUpdate)).observe(getViewLifecycleOwner(), result ->{
            System.out.println("Result anime Movie: " + result.isSuccess());
            if(result.isSuccess()){
                AnimeMovieResponse animeMovieResponse = ((Result.AnimeMovieSuccess) result).getData();
                List<AnimeMovie> fetchedAnimeMovie = animeMovieResponse.getAnimeMovieList();
                this.animeMovieList.addAll(fetchedAnimeMovie);
                animeMovieRecyclerViewAdapter.notifyDataSetChanged();
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