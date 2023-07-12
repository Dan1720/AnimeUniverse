package com.progetto.animeuniverse.ui.main;

import static com.progetto.animeuniverse.util.Constants.LAST_UPDATE;
import static com.progetto.animeuniverse.util.Constants.SHARED_PREFERENCES_FILE_NAME;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.adapter.SearchListAdapter;

import com.progetto.animeuniverse.database.AnimeByNameDao;
import com.progetto.animeuniverse.database.AnimeRoomDatabase;
import com.progetto.animeuniverse.databinding.FragmentSearchBinding;
import com.progetto.animeuniverse.model.AnimeByName;
import com.progetto.animeuniverse.model.Result;

import com.progetto.animeuniverse.repository.anime_by_name.AnimeByNameRepository;
import com.progetto.animeuniverse.repository.anime_by_name.AnimeByNameResponseCallback;
import com.progetto.animeuniverse.repository.anime_by_name.IAnimeByNameRepository;
import com.progetto.animeuniverse.util.ServiceLocator;
import com.progetto.animeuniverse.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment implements AnimeByNameResponseCallback {

    private List<AnimeByName> animeByNameList;
    private IAnimeByNameRepository iAnimeByNameRepository;
    private SharedPreferencesUtil sharedPreferencesUtil;


    private FragmentSearchBinding fragmentSearchBinding;

    private AnimeRoomDatabase animeRoomDatabase;
    private AnimeByNameDao animeByNameDao;
    private MutableLiveData<Result.AnimeByNameSuccess> animeSearchResult = new MutableLiveData<>();

    private SearchListAdapter searchListAdapter;
    private int count = 0;
    private ImageView backgroundImageView;
    private boolean flag = true;








    public SearchFragment() {
        // Required empty public constructor
    }


    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferencesUtil = new SharedPreferencesUtil(requireActivity().getApplication());
        iAnimeByNameRepository = new AnimeByNameRepository(requireActivity().getApplication(), this);

        animeRoomDatabase = AnimeRoomDatabase.getDatabase(getContext());
        if (animeRoomDatabase != null) {
            this.animeByNameDao = animeRoomDatabase.animeByNameDao();
        }

        animeByNameList = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        backgroundImageView = view.findViewById(R.id.background);
        if(flag == true){
            backgroundImageView.setVisibility(View.VISIBLE);
        } else {
            backgroundImageView.setVisibility(View.GONE);
        }
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_search_anime);
        int numberOfColumns = 3;
        GridLayoutManager layoutManager = new GridLayoutManager(this.getContext(), numberOfColumns);
        searchListAdapter = new SearchListAdapter(animeByNameList,
                requireActivity().getApplication(), new SearchListAdapter.OnItemClickListener() {

            @Override
            public void onAnimeClick(AnimeByName anime) {
                backgroundImageView.setVisibility(View.GONE);
                SearchFragmentDirections.ActionSearchFragmentToAnimeByNameDetailsFragment action =
                        SearchFragmentDirections.actionSearchFragmentToAnimeByNameDetailsFragment(anime);
                Navigation.findNavController(view).navigate(action);
            }
        });


        String lastUpdate = "0";
        NavBackStackEntry navBackStackEntry = Navigation.findNavController(view).getPreviousBackStackEntry();
        if(navBackStackEntry != null && navBackStackEntry.getDestination().getId() == R.id.searchFragment){
            flag = false;
            ((BottomNavigationView) requireActivity().findViewById(R.id.bottom_navigation)).
                    getMenu().findItem(R.id.searchFragment).setChecked(true);

        }else if(navBackStackEntry != null && navBackStackEntry.getDestination().getId() == R.id.listFragment){
            flag = false;
            ((BottomNavigationView) requireActivity().findViewById(R.id.bottom_navigation)).
                    getMenu().findItem(R.id.listFragment).setChecked(true);
        }
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(searchListAdapter);
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
                iAnimeByNameRepository.fetchAnimeByName(query,Long.parseLong(finalLastUpdate));
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
            this.animeByNameList.clear();
            this.animeByNameList.addAll(animeList);
            sharedPreferencesUtil.writeStringData(SHARED_PREFERENCES_FILE_NAME, LAST_UPDATE, String.valueOf(lastUpdate));
            requireActivity().runOnUiThread(new Runnable() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void run() {
                    searchListAdapter.notifyDataSetChanged();
                }
            });
            requireActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    searchListAdapter.notifyDataSetChanged();
                    if (animeByNameList.size() > 0) {
                        flag = false;
                        backgroundImageView.setVisibility(View.GONE);
                    } else {
                        flag = true;
                        backgroundImageView.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }

    @Override
    public void onFailure(String errorMessage) {

    }

}