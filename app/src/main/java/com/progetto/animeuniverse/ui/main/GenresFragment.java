package com.progetto.animeuniverse.ui.main;

import static com.progetto.animeuniverse.util.Constants.LAST_UPDATE;
import static com.progetto.animeuniverse.util.Constants.SHARED_PREFERENCES_FILE_NAME;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.progetto.animeuniverse.adapter.GenresRecyclerViewAdapter;
import com.progetto.animeuniverse.database.AnimeSpecificGenresDao;
import com.progetto.animeuniverse.databinding.FragmentGenresBinding;
import com.progetto.animeuniverse.model.Genre;
import com.progetto.animeuniverse.model.GenresResponse;
import com.progetto.animeuniverse.model.Result;
import com.progetto.animeuniverse.repository.genres.GenresResponseCallback;
import com.progetto.animeuniverse.repository.genres.IGenresRepositoryWithLiveData;
import com.progetto.animeuniverse.ui.main.GenresViewModel;
import com.progetto.animeuniverse.ui.main.GenresViewModelFactory;
import com.progetto.animeuniverse.util.ErrorMessagesUtil;
import com.progetto.animeuniverse.util.ServiceLocator;
import com.progetto.animeuniverse.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

public class GenresFragment extends Fragment implements GenresResponseCallback {
    private List<Genre> genresList;
    private AnimeSpecificGenresDao animeSpecificGenresDao;
    private FragmentGenresBinding genresFragmentBinding;
    private SharedPreferencesUtil sharedPreferencesUtil;
    private GenresViewModel genresViewModel;

    public GenresFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferencesUtil = new SharedPreferencesUtil(requireActivity().getApplication());

        IGenresRepositoryWithLiveData genresRepositoryWithLiveData =
                ServiceLocator.getInstance().getGenresRepository(requireActivity().getApplication());
        if(genresRepositoryWithLiveData != null){
            genresViewModel = new ViewModelProvider(requireActivity(), new GenresViewModelFactory(genresRepositoryWithLiveData)).get(GenresViewModel.class);
        }else {
            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                    getString(R.string.unexpected_error), Snackbar.LENGTH_SHORT).show();
        }

        genresList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        genresFragmentBinding = FragmentGenresBinding.inflate(inflater, container, false);
        return genresFragmentBinding.getRoot();
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

        RecyclerView GenresRecyclerViewItem = view.findViewById(R.id.recyclerView_genres);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());


        GenresRecyclerViewAdapter genresRecyclerViewAdapter = new GenresRecyclerViewAdapter(genresList, requireActivity().getApplication(), new GenresRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onGenreItemClick(Genre genre) {
                GenresFragmentDirections.ActionGenresFragmentToAnimeSpecificGenresFragment action =
                        GenresFragmentDirections.actionGenresFragmentToAnimeSpecificGenresFragment(genre);
                Navigation.findNavController(view).navigate(action);


            }
        });

        GenresRecyclerViewItem.setAdapter(genresRecyclerViewAdapter);
        GenresRecyclerViewItem.setLayoutManager(layoutManager);

        String lastUpdate = "0";
        genresViewModel.getGenres(Long.parseLong(lastUpdate)).observe(getViewLifecycleOwner(), result -> {
            System.out.println("Result genre: "+ result.isSuccess());
            if(result.isSuccess()){
                GenresResponse genresResponse = ((Result.GenresResponseSuccess) result).getData();
                List<Genre> fetchedGenres = genresResponse.getGernesList();
                this.genresList.addAll(fetchedGenres);
                genresRecyclerViewAdapter.notifyDataSetChanged();
            }else{
                ErrorMessagesUtil errorMessagesUtil =
                        new ErrorMessagesUtil(requireActivity().getApplication());
                Snackbar.make(view, errorMessagesUtil.
                                getErrorMessage(((Result.Error) result).getMessage()),
                        Snackbar.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onSuccess(List<Genre> genresList, long lastUpdate) {
        if(genresList != null){
            this.genresList.clear();
            this.genresList.addAll(genresList);
            sharedPreferencesUtil.writeStringData(SHARED_PREFERENCES_FILE_NAME, LAST_UPDATE, String.valueOf(lastUpdate));
        }
    }

    @Override
    public void onFailure(String errorMessage) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        genresViewModel.setFirstLoading(true);
        genresViewModel.setLoading(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        genresFragmentBinding = null;
    }
}