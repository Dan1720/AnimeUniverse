package com.progetto.animeuniverse.ui.preferences;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.adapter.AnimeFavoriteRecyclerViewAdapter;
import com.progetto.animeuniverse.model.Anime;
import com.progetto.animeuniverse.model.AnimeResponse;
import com.progetto.animeuniverse.model.Result;
import com.progetto.animeuniverse.ui.main.AnimeViewModel;
import com.progetto.animeuniverse.util.Constants;
import com.progetto.animeuniverse.util.ErrorMessagesUtil;
import com.progetto.animeuniverse.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {
    private static final String TAG = ListFragment.class.getSimpleName();
    private List<Anime> animeList;
    private AnimeViewModel animeViewModel;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        animeList = new ArrayList<>();
        animeViewModel = new ViewModelProvider(requireActivity()).get(AnimeViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        RecyclerView AnimeFavoriteRecyclerViewItem = view.findViewById(R.id.recyclerView_gridFavorite);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        AnimeFavoriteRecyclerViewAdapter animeFavoriteRecyclerViewAdapter = new AnimeFavoriteRecyclerViewAdapter(animeList, requireActivity().getApplication(), new AnimeFavoriteRecyclerViewAdapter.OnItemClickListener(){

            @Override
            public void onAnimeFavoriteClick(Anime Anime) {

            }
        });

        AnimeFavoriteRecyclerViewItem.setAdapter(animeFavoriteRecyclerViewAdapter);
        AnimeFavoriteRecyclerViewItem.setLayoutManager(layoutManager);



        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(requireActivity().getApplication());

        boolean isFirstLoading = sharedPreferencesUtil.readBooleanData(Constants.SHARED_PREFERENCES_FILE_NAME,
                Constants.SHARED_PREFERENCES_FIRST_LOADING);

        animeViewModel.getFavoriteAnimeLiveData(isFirstLoading).observe(getViewLifecycleOwner(), result -> {
            if(result.isSuccess()){
                animeList.clear();
                AnimeResponse animeResponse = ((Result.AnimeResponseSuccess) result).getData();
                List<Anime> fetchedAnime = animeResponse.getAnimeList();
                this.animeList.addAll(fetchedAnime);
                animeFavoriteRecyclerViewAdapter.notifyDataSetChanged();
                if(isFirstLoading){
                    sharedPreferencesUtil.writeBooleanData(Constants.SHARED_PREFERENCES_FILE_NAME,
                            Constants.SHARED_PREFERENCES_FIRST_LOADING, false);
                }
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
