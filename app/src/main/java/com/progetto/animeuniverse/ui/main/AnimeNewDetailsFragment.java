package com.progetto.animeuniverse.ui.main;

import static com.progetto.animeuniverse.util.Constants.LAST_UPDATE;
import static com.progetto.animeuniverse.util.Constants.SHARED_PREFERENCES_FILE_NAME;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.adapter.EpisodesRecyclerViewAdapter;
import com.progetto.animeuniverse.adapter.ReviewsRecyclerViewAdapter;
import com.progetto.animeuniverse.databinding.FragmentAnimeNewDetailsBinding;
import com.progetto.animeuniverse.model.AnimeEpisodes;
import com.progetto.animeuniverse.model.AnimeNew;
import com.progetto.animeuniverse.model.Review;
import com.progetto.animeuniverse.repository.anime_episodes.AnimeEpisodesRepository;
import com.progetto.animeuniverse.repository.anime_episodes.AnimeEpisodesResponseCallback;
import com.progetto.animeuniverse.repository.anime_episodes.IAnimeEpisodesRepository;
import com.progetto.animeuniverse.repository.reviews.IReviewsRepository;
import com.progetto.animeuniverse.repository.reviews.ReviewsRepository;
import com.progetto.animeuniverse.repository.reviews.ReviewsResponseCallback;
import com.progetto.animeuniverse.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class AnimeNewDetailsFragment extends Fragment implements ReviewsResponseCallback, AnimeEpisodesResponseCallback {

    private static final String TAG = AnimeNewDetailsFragment.class.getSimpleName();
    private FragmentAnimeNewDetailsBinding fragmentAnimeNewDetailsBinding;

    private List<Review> reviewsList;
    private List<AnimeEpisodes> animeEpisodesList;
    private SharedPreferencesUtil sharedPreferencesUtil;

    private IAnimeEpisodesRepository animeEpisodesRepository;

    private EpisodesRecyclerViewAdapter episodesRecyclerViewAdapter;

    private IReviewsRepository reviewsRepository;

    private ReviewsRecyclerViewAdapter reviewsRecyclerViewAdapter;

    public AnimeNewDetailsFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferencesUtil = new SharedPreferencesUtil(requireActivity().getApplication());

        reviewsRepository = new ReviewsRepository(requireActivity().getApplication(), this);

        reviewsList = new ArrayList<>();

        animeEpisodesRepository = new AnimeEpisodesRepository(requireActivity().getApplication(), this);

        animeEpisodesList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentAnimeNewDetailsBinding = FragmentAnimeNewDetailsBinding.inflate(inflater, container, false);
        return fragmentAnimeNewDetailsBinding.getRoot();
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

        AnimeNew animeNew = AnimeNewDetailsFragmentArgs.fromBundle(getArguments()).getAnimeNew();

        Glide.with(fragmentAnimeNewDetailsBinding.imageViewDetailsBlurry.getContext())
                .load(animeNew.getEntry().getImages().getJpgImages().getLargeImageUrl())
                .placeholder(R.drawable.ic_home).apply(RequestOptions.bitmapTransform(new BlurTransformation(25, 3))).into(fragmentAnimeNewDetailsBinding.imageViewDetailsBlurry);

        Glide.with(fragmentAnimeNewDetailsBinding.imageViewDetails.getContext())
                .load(animeNew.getEntry().getImages().getJpgImages().getImageUrl())
                .placeholder(R.drawable.ic_home).into(fragmentAnimeNewDetailsBinding.imageViewDetails);

        fragmentAnimeNewDetailsBinding.textViewDetailsTitleIn.setText(animeNew.getEntry().getTitle());

        NavBackStackEntry navBackStackEntry = Navigation.findNavController(view).getPreviousBackStackEntry();
        if(navBackStackEntry != null && navBackStackEntry.getDestination().getId() == R.id.animeMovieFragment){
            ((BottomNavigationView) requireActivity().findViewById(R.id.bottom_navigation)).
                    getMenu().findItem(R.id.homeFragment).setChecked(true);

        }else if(navBackStackEntry != null && navBackStackEntry.getDestination().getId() == R.id.listFragment){
            ((BottomNavigationView) requireActivity().findViewById(R.id.bottom_navigation)).
                    getMenu().findItem(R.id.listFragment).setChecked(true);
        }

        String lastUpdate = "0";
        if (sharedPreferencesUtil.readStringData(
                SHARED_PREFERENCES_FILE_NAME, LAST_UPDATE) != null) {
            lastUpdate = sharedPreferencesUtil.readStringData(
                    SHARED_PREFERENCES_FILE_NAME, LAST_UPDATE);
        }

        RecyclerView ReviewsRecyclerViewItem = view.findViewById(R.id.recyclerView_reviews);
        reviewsRecyclerViewAdapter = new ReviewsRecyclerViewAdapter(reviewsList, requireActivity().getApplication(), new ReviewsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onReviewItemClick(Review review) {
                AnimeNewDetailsFragmentDirections.ActionAnimeNewDetailsFragmentToReviewDetailsFragment action =
                        AnimeNewDetailsFragmentDirections.actionAnimeNewDetailsFragmentToReviewDetailsFragment(review);
            }
        });

        ReviewsRecyclerViewItem.setAdapter(reviewsRecyclerViewAdapter);
        ReviewsRecyclerViewItem.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

        reviewsRepository.fetchReviewsById(animeNew.getEntry().getIdAnime(), Long.parseLong(lastUpdate));

        RecyclerView EpisodesRecyclerViewItem = view.findViewById(R.id.recyclerView_episodesIn);
        LinearLayoutManager layoutManagerEp = new LinearLayoutManager(requireContext());
        episodesRecyclerViewAdapter = new EpisodesRecyclerViewAdapter(animeEpisodesList, requireActivity().getApplication(), new EpisodesRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onEpisodeItemClick(AnimeEpisodes animeEpisodes) {

            }

        });

        EpisodesRecyclerViewItem.setAdapter(episodesRecyclerViewAdapter);
        EpisodesRecyclerViewItem.setLayoutManager(layoutManagerEp);

        animeEpisodesRepository.fetchAnimeEpisodes(animeNew.getEntry().getIdAnime(), Long.parseLong(lastUpdate));
    }

    @Override
    public void onSuccessEpisodes(List<AnimeEpisodes> animeEpisodesList, long lastUpdate) {
        if(animeEpisodesList != null){
            this.animeEpisodesList.clear();
            this.animeEpisodesList.addAll(animeEpisodesList);
            sharedPreferencesUtil.writeStringData(SHARED_PREFERENCES_FILE_NAME, LAST_UPDATE, String.valueOf(lastUpdate));
        }

        requireActivity().runOnUiThread(new Runnable() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void run() {
                episodesRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onSuccess(List<Review> reviewList, long lastUpdate) {
        if(reviewList != null){
            this.reviewsList.clear();
            this.reviewsList.addAll(reviewList);
            sharedPreferencesUtil.writeStringData(SHARED_PREFERENCES_FILE_NAME, LAST_UPDATE, String.valueOf(lastUpdate));
        }
        requireActivity().runOnUiThread(new Runnable() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void run() {
                reviewsRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onFailure(String errorMessage) {

    }
}