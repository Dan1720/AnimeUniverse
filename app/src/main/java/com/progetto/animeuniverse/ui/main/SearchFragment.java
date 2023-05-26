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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;
import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.adapter.SearchListAdapter;
import com.progetto.animeuniverse.databinding.FragmentSearchBinding;
import com.progetto.animeuniverse.model.Anime;
import com.progetto.animeuniverse.model.AnimeResponse;
import com.progetto.animeuniverse.model.Result;
import com.progetto.animeuniverse.repository.anime.AnimeResponseCallback;
import com.progetto.animeuniverse.repository.anime.IAnimeRepositoryWithLiveData;
import com.progetto.animeuniverse.util.ErrorMessagesUtil;
import com.progetto.animeuniverse.util.ServiceLocator;
import com.progetto.animeuniverse.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment implements AnimeResponseCallback {

    private List<Anime> animeList;
    private IAnimeRepositoryWithLiveData iAnimeRepository;
    private SharedPreferencesUtil sharedPreferencesUtil;
    private AnimeViewModel animeViewModel;
    private SearchListAdapter searchListAdapter;

    private FragmentSearchBinding fragmentSearchBinding;
    private ProgressBar progressBar;

    private final String q = TOP_HEADLINES_ENDPOINT;
    private final int threshold = 1;

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
                    public void onAnimeClick(Anime anime) {
                        Snackbar.make(view, anime.getTitle(), Snackbar.LENGTH_SHORT).show();
                    }
        });

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(searchListAdapter);
        String lastUpdate = "0";
        if(sharedPreferencesUtil.readStringData(SHARED_PREFERENCES_FILE_NAME, LAST_UPDATE) != null){
            lastUpdate = sharedPreferencesUtil.readStringData(
                    SHARED_PREFERENCES_FILE_NAME, LAST_UPDATE);
        }
        animeViewModel.getAnimeTop(Long.parseLong(lastUpdate)).observe(getViewLifecycleOwner(), result ->{
            System.out.println("Result anime top:" + result.isSuccess());
            if(result.isSuccess()) {
                AnimeResponse animeResponse = ((Result.AnimeResponseSuccess) result).getData();
                List<Anime> fetchedAnime = animeResponse.getAnimeList();
                this.animeList.addAll(fetchedAnime);
                searchListAdapter.notifyDataSetChanged();

            } else{
                ErrorMessagesUtil errorMessagesUtil =
                        new ErrorMessagesUtil(requireActivity().getApplication());
                Snackbar.make(view, errorMessagesUtil.
                        getErrorMessage(((Result.Error) result).getMessage()),
                        Snackbar.LENGTH_SHORT).show();
            }
        });


    }


    //@Override
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
        fragmentSearchBinding = null;
    }


}