package com.progetto.animeuniverse.ui.main;

import static com.progetto.animeuniverse.util.Constants.LAST_UPDATE;
import static com.progetto.animeuniverse.util.Constants.SHARED_PREFERENCES_FILE_NAME;
import static com.progetto.animeuniverse.util.Constants.TOP_HEADLINES_ENDPOINT;
import static com.progetto.animeuniverse.util.Constants.TOP_HEADLINES_PAGE_SIZE_VALUE;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.adapter.AnimeRecyclerViewAdapter;
import com.progetto.animeuniverse.databinding.FragmentHomeBinding;
import com.progetto.animeuniverse.model.Anime;

import com.progetto.animeuniverse.model.AnimeApiResponse;
import com.progetto.animeuniverse.model.AnimeResponse;
import com.progetto.animeuniverse.model.Result;
import com.progetto.animeuniverse.repository.anime.AnimeRepositoryWithLiveData;
import com.progetto.animeuniverse.repository.anime.AnimeResponseCallback;
import com.progetto.animeuniverse.repository.anime.IAnimeRepositoryWithLiveData;
import com.progetto.animeuniverse.util.ErrorMessagesUtil;
import com.progetto.animeuniverse.util.ServiceLocator;
import com.progetto.animeuniverse.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements AnimeResponseCallback {

    private List<Anime> animeList;
    private SharedPreferencesUtil sharedPreferencesUtil;
    private AnimeViewModel animeViewModel;

    private FragmentHomeBinding fragmentHomeBinding;

    private final String q = TOP_HEADLINES_ENDPOINT;
    private final int threshold = 1;
    private AnimeRecyclerViewAdapter animeRecyclerViewAdapter;
    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(){
        return new HomeFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferencesUtil = new SharedPreferencesUtil(requireActivity().getApplication());

        IAnimeRepositoryWithLiveData animeRepositoryWithLiveData =
                ServiceLocator.getInstance().getAnimeRepository(
                        requireActivity().getApplication(),
                        requireActivity().getApplication().getResources().getBoolean(R.bool.debug_mode)
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
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false);
        return fragmentHomeBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerViewAnimeTop = view.findViewById(R.id.recyclerView_AnimeTop);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.VERTICAL, false);


        AnimeRecyclerViewAdapter adapter = new AnimeRecyclerViewAdapter(animeList, requireActivity().getApplication(),
                new AnimeRecyclerViewAdapter.OnItemClickListener(){

                    @Override
                    public void onAnimeItemClick(Anime anime) {

                    }
                });
        recyclerViewAnimeTop.setLayoutManager(linearLayoutManager);
        recyclerViewAnimeTop.setAdapter(adapter);

        String lastUpdate = "0";
        if(sharedPreferencesUtil.readStringData(SHARED_PREFERENCES_FILE_NAME, LAST_UPDATE)!=null){
            lastUpdate = sharedPreferencesUtil.readStringData(SHARED_PREFERENCES_FILE_NAME, LAST_UPDATE);
        }



        animeViewModel.getAnimeTop(Long.parseLong(lastUpdate)).observe(getViewLifecycleOwner(), result -> {
            System.out.println("Result: "+ result.isSuccess());
            if(result.isSuccess()){
                AnimeResponse animeResponse = ((Result.AnimeResponseSuccess) result).getData();
                List<Anime> fetchedAnime = animeResponse.getAnimeList();
                if(!animeViewModel.isLoading()){
                    if(animeViewModel.isFirstLoading()){
                        animeViewModel.setCount(((AnimeApiResponse)animeResponse).getPagination().getItems().getCount());
                        animeViewModel.setFirstLoading(false);
                        this.animeList.addAll(fetchedAnime);
                        animeRecyclerViewAdapter.notifyItemRangeInserted(0, this.animeList.size());
                    }else {
                        animeList.clear();
                        animeList.addAll(fetchedAnime);
                        animeRecyclerViewAdapter.notifyItemChanged(0, fetchedAnime.size());
                    }

                }else {
                    animeViewModel.setLoading(false);
                    animeViewModel.setCurrentResults(animeList.size());
                    int initialSize = animeList.size();
                    for(int i=0; i<animeList.size(); i++){
                        if(animeList.get(i)==null){
                            animeList.remove(animeList.get(i));
                        }
                    }
                    int startIndex = (animeViewModel.getCurrent_page() * TOP_HEADLINES_PAGE_SIZE_VALUE)-TOP_HEADLINES_PAGE_SIZE_VALUE;
                    for(int i=startIndex; i<fetchedAnime.size(); i++){
                        animeList.add(fetchedAnime.get(i));
                    }
                    animeRecyclerViewAdapter.notifyItemRangeInserted(initialSize, animeList.size());
                }
            }else {
                ErrorMessagesUtil errorMessagesUtil =
                        new ErrorMessagesUtil(requireActivity().getApplication());
                Snackbar.make(view, errorMessagesUtil.
                                getErrorMessage(((Result.Error) result).getMessage()),
                        Snackbar.LENGTH_SHORT).show();
            }
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
        if(anime.isFavorite()){
            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                    R.string.add_anime_favorite, Snackbar.LENGTH_LONG).show();
        }else{
            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                    R.string.remove_anime_favorite, Snackbar.LENGTH_LONG).show();
        }
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
        fragmentHomeBinding = null;
    }
}