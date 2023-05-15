package com.progetto.animeuniverse.ui.main;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.Navigation;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.databinding.FragmentAnimeDetailsBinding;
import com.progetto.animeuniverse.model.Anime;
import com.progetto.animeuniverse.model.AnimeGenres;
import com.progetto.animeuniverse.model.AnimeProducers;
import com.progetto.animeuniverse.model.AnimeStudios;

import java.util.List;

public class AnimeDetailsFragment extends Fragment {
    private static final String TAG = AnimeDetailsFragment.class.getSimpleName();
    private FragmentAnimeDetailsBinding fragmentAnimeDetailsBinding;
    public AnimeDetailsFragment() {
        // Required empty public constructor
    }

    public static AnimeDetailsFragment newInstance() {

        return new AnimeDetailsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentAnimeDetailsBinding = FragmentAnimeDetailsBinding.inflate(inflater, container, false);
        return fragmentAnimeDetailsBinding.getRoot();
    }

    @SuppressLint("SetTextI18n")
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

        Anime anime = AnimeDetailsFragmentArgs.fromBundle(getArguments()).getAnime();

        Glide.with(fragmentAnimeDetailsBinding.imageViewDetails.getContext())
                .load(anime.getImages().getJpgImages().getLargeImageUrl())
                .placeholder(R.drawable.ic_home).into(fragmentAnimeDetailsBinding.imageViewDetails);

        fragmentAnimeDetailsBinding.textViewDetailsTitleIn.setText(anime.getTitle());
        fragmentAnimeDetailsBinding.textViewNEpisodesIn.setText(String.valueOf(anime.getNumEpisodes()));

        fragmentAnimeDetailsBinding.textViewDetailsYearIn.setText(String.valueOf(anime.getYear()));
        fragmentAnimeDetailsBinding.textViewDescriptionIn.setText(anime.getSynopsis());
        List<AnimeGenres> genres = anime.getGenres();
        fragmentAnimeDetailsBinding.textViewGenresIn.setText(genres.get(0).getNameGenre() + " - " + genres.get(1).getNameGenre() +" - "+ genres.get(2).getNameGenre());
        List<AnimeStudios> studios = anime.getStudios();
        if(studios.size() >= 3){
            fragmentAnimeDetailsBinding.textViewStudiosIn.setText(studios.get(0).getNameStudio() + " - " + studios.get(1).getNameStudio() +" - "+ studios.get(2).getNameStudio());
        }else{
            fragmentAnimeDetailsBinding.textViewStudiosIn.setText(studios.get(0).getNameStudio());
        }
        List<AnimeProducers> producers = anime.getProducers();
        if(producers.size() >= 3){
            fragmentAnimeDetailsBinding.textViewProducersIn.setText(producers.get(0).getNameProducer() + " - " + producers.get(1).getNameProducer() + " - "+ producers.get(2).getNameProducer());
        }else{
            fragmentAnimeDetailsBinding.textViewProducersIn.setText(producers.get(0).getNameProducer());
        }
        if(anime.getTrailer().getUrlTrailer() != null){
            fragmentAnimeDetailsBinding.textViewTrailerLinkIn.setText(Html.fromHtml(anime.getTrailer().getUrlTrailer(), Html.FROM_HTML_MODE_COMPACT));
        }else{
            fragmentAnimeDetailsBinding.textViewTrailerLinkIn.setText("not found");
        }



        NavBackStackEntry navBackStackEntry = Navigation.findNavController(view).getPreviousBackStackEntry();
        if(navBackStackEntry != null && navBackStackEntry.getDestination().getId() == R.id.homeFragment){
            ((BottomNavigationView) requireActivity().findViewById(R.id.bottom_navigation)).
                    getMenu().findItem(R.id.homeFragment).setChecked(true);

        }else if(navBackStackEntry != null && navBackStackEntry.getDestination().getId() == R.id.listFragment){
            ((BottomNavigationView) requireActivity().findViewById(R.id.bottom_navigation)).
                    getMenu().findItem(R.id.listFragment).setChecked(true);
        }
    }
}