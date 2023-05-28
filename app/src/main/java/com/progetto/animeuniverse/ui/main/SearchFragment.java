package com.progetto.animeuniverse.ui.main;

import static com.progetto.animeuniverse.util.Constants.LAST_UPDATE;
import static com.progetto.animeuniverse.util.Constants.SHARED_PREFERENCES_FILE_NAME;
import static com.progetto.animeuniverse.util.Constants.TOP_HEADLINES_ENDPOINT;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
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
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.adapter.SearchListAdapter;

import com.progetto.animeuniverse.database.AnimeByNameDao;
import com.progetto.animeuniverse.database.AnimeRoomDatabase;
import com.progetto.animeuniverse.databinding.FragmentSearchBinding;
import com.progetto.animeuniverse.model.AnimeByName;
import com.progetto.animeuniverse.model.AnimeByNameResponse;
import com.progetto.animeuniverse.model.Result;

import com.progetto.animeuniverse.repository.anime_by_name.AnimeByNameResponseCallback;
import com.progetto.animeuniverse.repository.anime_by_name.IAnimeByNameRepositoryWithLiveData;
import com.progetto.animeuniverse.util.ServiceLocator;
import com.progetto.animeuniverse.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SearchFragment extends Fragment implements AnimeByNameResponseCallback {

    private List<AnimeByName> animeList;
    private IAnimeByNameRepositoryWithLiveData iAnimeRepository;
    private SharedPreferencesUtil sharedPreferencesUtil;
    private AnimeByNameViewModel animeViewModel;
    private SearchListAdapter searchListAdapter;

    private FragmentSearchBinding fragmentSearchBinding;
    private ProgressBar progressBar;

    private final String q = TOP_HEADLINES_ENDPOINT;
    private final int threshold = 1;
    //String finalLastUpdate = "0";
    
    private ServiceLocator serviceLocator;
    private AnimeRoomDatabase animeRoomDatabase;
    private AnimeByNameDao animeByNameDao;
    private MutableLiveData<Result.AnimeByNameSuccess> animeSearchResult = new MutableLiveData<>();





    public SearchFragment() {
        // Required empty public constructor
    }


    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferencesUtil = new SharedPreferencesUtil(requireActivity().getApplication());

        IAnimeByNameRepositoryWithLiveData animeRepositoryWithLiveData =
                ServiceLocator.getInstance().getAnimeByNameRepository(requireActivity().getApplication());
        if(animeRepositoryWithLiveData != null){
            animeViewModel = new ViewModelProvider(requireActivity(),
                    new AnimeByNameViewModelFactory(animeRepositoryWithLiveData)).get(AnimeByNameViewModel.class);
        }else{
            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                    getString(R.string.unexpected_error), Snackbar.LENGTH_SHORT).show();
        }
        animeRoomDatabase = AnimeRoomDatabase.getDatabase(getContext());
        if (animeRoomDatabase != null) {
            animeByNameDao = animeRoomDatabase.animeByNameDao();
            if (animeByNameDao == null) {
                System.out.println("è null");
            }
        }


        animeList = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        fragmentSearchBinding = FragmentSearchBinding.inflate(inflater, container, false);
        return fragmentSearchBinding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.clear();
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        });
        int numberOfColumns = 3;
        GridLayoutManager layoutManager = new GridLayoutManager(this.getContext(), numberOfColumns);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_search_anime);
        searchListAdapter = new SearchListAdapter(animeList,
                requireActivity().getApplication(),
                new SearchListAdapter.OnItemClickListener() {

                    @Override
                    public void onAnimeClick(AnimeByName anime) {
                        /*SearchFragmentDirections.ActionSearchFragmentToAnimeDetailsFragment action =
                                SearchFragmentDirections.actionSearchFragmentToAnimeDetailsFragment(anime);
                        Navigation.findNavController(view).navigate(action);*/
                    }

        });
        NavBackStackEntry navBackStackEntry = Navigation.findNavController(view).getPreviousBackStackEntry();
        if(navBackStackEntry != null && navBackStackEntry.getDestination().getId() == R.id.searchFragment){
            ((BottomNavigationView) requireActivity().findViewById(R.id.bottom_navigation)).
                    getMenu().findItem(R.id.searchFragment).setChecked(true);

        }else if(navBackStackEntry != null && navBackStackEntry.getDestination().getId() == R.id.listFragment){
            ((BottomNavigationView) requireActivity().findViewById(R.id.bottom_navigation)).
                    getMenu().findItem(R.id.listFragment).setChecked(true);
        }
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(searchListAdapter);
        String lastUpdate = "0";
        if(sharedPreferencesUtil.readStringData(SHARED_PREFERENCES_FILE_NAME, LAST_UPDATE) != null){
            lastUpdate = sharedPreferencesUtil.readStringData(
                    SHARED_PREFERENCES_FILE_NAME, LAST_UPDATE);
        }

        SearchView searchView = view.findViewById(R.id.search_view);
        searchView.clearFocus();
        String finalLastUpdate = lastUpdate;


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String query) {
                animeViewModel.getAnimeByName(query, Long.parseLong(finalLastUpdate)).observe(getViewLifecycleOwner(), result -> {
                    if (result.isSuccess()) {
                        AnimeByNameResponse animeByNameResponse = ((Result.AnimeByNameSuccess) result).getData();
                        onSuccess(animeByNameResponse.getAnimeByNameList(), Long.parseLong(finalLastUpdate));
                    } else {
                        onFailure(((Result.Error) result).getMessage());
                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });



    }

    //@Override
    @Override
    public void onSuccess(List<AnimeByName> animeList, long lastUpdate) {
        if (animeList != null) {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                // Cancella le tabelle nel database
                animeByNameDao.deleteAll();

                // Inserisci i nuovi dati nel database
                animeByNameDao.insertAnimeByNameList(animeList);

                requireActivity().runOnUiThread(() -> {
                    this.animeList.clear(); // Rimuovi i dati precedenti dalla lista
                    this.animeList.addAll(animeList); // Aggiungi i nuovi dati alla lista
                    searchListAdapter.notifyDataSetChanged(); // Aggiorna l'adapter
                });
            });
            searchListAdapter.notifyDataSetChanged();
            executor.shutdown();
        }
    }

    @Override
    public void onFailure(String errorMessage) {

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
        fragmentSearchBinding = null;
    }


}