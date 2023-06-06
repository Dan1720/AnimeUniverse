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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.adapter.EpisodesRecyclerViewAdapter;
import com.progetto.animeuniverse.adapter.ReviewsRecyclerViewAdapter;
import com.progetto.animeuniverse.databinding.FragmentAnimeRecommendationsDetailsBinding;
import com.progetto.animeuniverse.model.AnimeEpisodes;
import com.progetto.animeuniverse.model.AnimeEpisodesResponse;
import com.progetto.animeuniverse.model.AnimeGenres;
import com.progetto.animeuniverse.model.AnimeProducers;
import com.progetto.animeuniverse.model.AnimeRecommendations;
import com.progetto.animeuniverse.model.AnimeStudios;
import com.progetto.animeuniverse.model.Result;
import com.progetto.animeuniverse.model.Review;
import com.progetto.animeuniverse.model.ReviewsResponse;
import com.progetto.animeuniverse.repository.anime_episodes.IAnimeEpisodesRepositoryWithLiveData;
import com.progetto.animeuniverse.repository.reviews.IReviewsRepositoryWithLiveData;
import com.progetto.animeuniverse.ui.main.AnimeRecommendationsDetailsFragmentDirections;
import com.progetto.animeuniverse.util.ErrorMessagesUtil;
import com.progetto.animeuniverse.util.ServiceLocator;
import com.progetto.animeuniverse.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

public class AnimeRecommendationsDetailsFragment extends Fragment {

    private static final String TAG = AnimeMovieDetailsFragment.class.getSimpleName();

    private FragmentAnimeRecommendationsDetailsBinding fragmentAnimeRecommendationsDetailsBinding;
    private List<Review> reviewsList;
    private List<AnimeEpisodes> animeEpisodesList;
    private SharedPreferencesUtil sharedPreferencesUtil;
    private ReviewsViewModel reviewsViewModel;
    private AnimeEpisodesViewModel animeEpisodesViewModel;


    public AnimeRecommendationsDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferencesUtil = new SharedPreferencesUtil(requireActivity().getApplication());

        IReviewsRepositoryWithLiveData reviewsRepositoryWithLiveData =
                ServiceLocator.getInstance().getReviewsRepository(requireActivity().getApplication());
        if(reviewsRepositoryWithLiveData != null){
            reviewsViewModel = new ViewModelProvider(requireActivity(), new ReviewsViewModelFactory(reviewsRepositoryWithLiveData)).get(ReviewsViewModel.class);
        }else {
            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                    getString(R.string.unexpected_error), Snackbar.LENGTH_SHORT).show();
        }

        reviewsList = new ArrayList<>();

        IAnimeEpisodesRepositoryWithLiveData animeEpisodesRepositoryWithLiveData =
                ServiceLocator.getInstance().getAnimeEpisodesRepository(requireActivity().getApplication());
        if(animeEpisodesRepositoryWithLiveData != null){
            animeEpisodesViewModel = new ViewModelProvider(requireActivity(), new AnimeEpisodesViewModelFactory(animeEpisodesRepositoryWithLiveData)).get(AnimeEpisodesViewModel.class);
        }else {
            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                    getString(R.string.unexpected_error), Snackbar.LENGTH_SHORT).show();
        }

        animeEpisodesList = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentAnimeRecommendationsDetailsBinding = FragmentAnimeRecommendationsDetailsBinding.inflate(inflater, container, false);
        return fragmentAnimeRecommendationsDetailsBinding.getRoot();
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

        AnimeRecommendations animeRecommendations = AnimeRecommendationsDetailsFragmentArgs.fromBundle(getArguments()).getAnimeRecommendations();
        Glide.with(fragmentAnimeRecommendationsDetailsBinding.imageViewDetails.getContext())
                .load(animeRecommendations.getEntry().get(0).getImages().getJpgImages().getLargeImageUrl())
                .placeholder(R.drawable.ic_home).into(fragmentAnimeRecommendationsDetailsBinding.imageViewDetails);

        fragmentAnimeRecommendationsDetailsBinding.textViewDetailsTitleIn.setText(animeRecommendations.getEntry().get(0).getTitle());

        NavBackStackEntry navBackStackEntry = Navigation.findNavController(view).getPreviousBackStackEntry();
        if(navBackStackEntry != null && navBackStackEntry.getDestination().getId() == R.id.animeMovieFragment){
            ((BottomNavigationView) requireActivity().findViewById(R.id.bottom_navigation)).
                    getMenu().findItem(R.id.homeFragment).setChecked(true);

        }else if(navBackStackEntry != null && navBackStackEntry.getDestination().getId() == R.id.listFragment){
            ((BottomNavigationView) requireActivity().findViewById(R.id.bottom_navigation)).
                    getMenu().findItem(R.id.listFragment).setChecked(true);
        }

        RecyclerView ReviewsRecyclerViewItem = view.findViewById(R.id.recyclerView_reviews);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        ReviewsRecyclerViewAdapter reviewsRecyclerViewAdapter = new ReviewsRecyclerViewAdapter(reviewsList, requireActivity().getApplication(), new ReviewsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onReviewItemClick(Review review) {
                com.progetto.animeuniverse.ui.main.AnimeRecommendationsDetailsFragmentDirections.ActionAnimeRecommendationsDetailsFragmentToReviewDetailsFragment action =
                        AnimeRecommendationsDetailsFragmentDirections.actionAnimeRecommendationsDetailsFragmentToReviewDetailsFragment(review);
                Navigation.findNavController(view).navigate(action);
            }
        });

        ReviewsRecyclerViewItem.setAdapter(reviewsRecyclerViewAdapter);
        ReviewsRecyclerViewItem.setLayoutManager(layoutManager);

        String lastUpdate = "0";
        reviewsViewModel.getReviewsByIdAnime(animeRecommendations.getEntry().get(0).getIdAnime(), Long.parseLong(lastUpdate)).observe(getViewLifecycleOwner(), result -> {
            System.out.println("Result reviews: "+ result.isSuccess());
            if(result.isSuccess()){
                ReviewsResponse reviewsResponse = ((Result.ReviewsResponseSuccess) result).getData();
                List<Review> fetchedReviews = reviewsResponse.getReviewList();
                this.reviewsList.addAll(fetchedReviews);
                reviewsRecyclerViewAdapter.notifyDataSetChanged();

            }else{
                ErrorMessagesUtil errorMessagesUtil =
                        new ErrorMessagesUtil(requireActivity().getApplication());
                Snackbar.make(view, errorMessagesUtil.
                                getErrorMessage(((Result.Error) result).getMessage()),
                        Snackbar.LENGTH_SHORT).show();
            }
        });


        RecyclerView EpisodesRecyclerViewItem = view.findViewById(R.id.recyclerView_episodesIn);
        LinearLayoutManager layoutManagerEp = new LinearLayoutManager(requireContext());
        EpisodesRecyclerViewAdapter episodesRecyclerViewAdapter = new EpisodesRecyclerViewAdapter(animeEpisodesList, requireActivity().getApplication(), new EpisodesRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onEpisodeItemClick(AnimeEpisodes animeEpisodes) {

            }

        });

        EpisodesRecyclerViewItem.setAdapter(episodesRecyclerViewAdapter);
        EpisodesRecyclerViewItem.setLayoutManager(layoutManagerEp);


        lastUpdate = "0";
        animeEpisodesViewModel.getAnimeEpisodes(animeRecommendations.getEntry().get(0).getIdAnime(), Long.parseLong(lastUpdate)).observe(getViewLifecycleOwner(), result ->{
            System.out.println("Result episodes: "+ result.isSuccess());
            if(result.isSuccess()){
                AnimeEpisodesResponse animeEpisodesResponse = ((Result.AnimeEpisodesSuccess) result).getData();
                List<AnimeEpisodes> fetchedAnimeEpisodes = animeEpisodesResponse.getAnimeEpisodesList();
                this.animeEpisodesList.addAll(fetchedAnimeEpisodes);
                episodesRecyclerViewAdapter.notifyDataSetChanged();
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