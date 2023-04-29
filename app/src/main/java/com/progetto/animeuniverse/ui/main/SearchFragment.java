package com.progetto.animeuniverse.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.progetto.animeuniverse.model.Anime;
import com.progetto.animeuniverse.repository.anime.IAnimeRepository;
import com.progetto.animeuniverse.util.SharedPreferencesUtil;

import java.util.List;


public class SearchFragment extends Fragment{

   private IAnimeRepository iAnimeRepository;
    private List<Anime> animeList;
    private SearchListAdapter adapter;
    private ProgressBar progressBar;
    private SharedPreferencesUtil sharedPreferencesUtil;
    private SearchListAdapter animeRecyclerViewAdapter;

    public SearchFragment() {
        // Required empty public constructor
    }


    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
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

        progressBar = view.findViewById(R.id.progress_bar);
       
        RecyclerView recyclerViewSearch = view.findViewById(R.id.recyclerView_search_anime); //ha bisogno di un layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        recyclerViewSearch.setLayoutManager(linearLayoutManager);

        SearchListAdapter adapter = new SearchListAdapter(animeList, new SearchListAdapter.OnItemClickListener() {
            @Override
            public void onAnimeClick(Anime anime) {
                Snackbar.make(view, anime.getTitle(), Snackbar.LENGTH_SHORT).show();
            }
        });
        recyclerViewSearch.setAdapter(adapter);
        progressBar.setVisibility(View.VISIBLE);
        //iAnimeRepository.fetchAnime();



    }


    //@Override
    public void onSuccess(List<Anime> animeList) {
        if(animeList != null){
            this.animeList.clear();
            this.animeList.addAll(animeList);
        }
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                animeRecyclerViewAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    /*@Override
    public void onFailure(String errorMessage) {

    }

    @Override
    public void onAnimeFavoriteStatusChanged(Anime anime) {

    }
*/

}