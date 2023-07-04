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

import android.text.Html;
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
import com.progetto.animeuniverse.adapter.ReviewsRecyclerViewAdapter;
import com.progetto.animeuniverse.databinding.FragmentAnimeMovieDetailsBinding;
import com.progetto.animeuniverse.model.AnimeGenres;
import com.progetto.animeuniverse.model.AnimeProducers;
import com.progetto.animeuniverse.model.AnimeStudios;
import com.progetto.animeuniverse.model.AnimeMovie;
import com.progetto.animeuniverse.model.Review;
import com.progetto.animeuniverse.repository.reviews.IReviewsRepository;
import com.progetto.animeuniverse.repository.reviews.ReviewsRepository;
import com.progetto.animeuniverse.repository.reviews.ReviewsResponseCallback;
import com.progetto.animeuniverse.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class AnimeMovieDetailsFragment extends Fragment implements ReviewsResponseCallback {

    private static final String TAG = AnimeMovieDetailsFragment.class.getSimpleName();

    private FragmentAnimeMovieDetailsBinding fragmentAnimeMovieDetailsBinding;
    private List<Review> reviewsList;
    private SharedPreferencesUtil sharedPreferencesUtil;
    private IReviewsRepository reviewsRepository;

    private ReviewsRecyclerViewAdapter reviewsRecyclerViewAdapter;


    public AnimeMovieDetailsFragment() {
        // Required empty public constructor
    }

    public static AnimeMovieDetailsFragment newInstance(){
        return new AnimeMovieDetailsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferencesUtil = new SharedPreferencesUtil(requireActivity().getApplication());

        reviewsRepository = new ReviewsRepository(requireActivity().getApplication(), this);

        reviewsList = new ArrayList<>();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentAnimeMovieDetailsBinding = FragmentAnimeMovieDetailsBinding.inflate(inflater, container, false);
        return fragmentAnimeMovieDetailsBinding.getRoot();
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

        AnimeMovie animeMovie = AnimeMovieDetailsFragmentArgs.fromBundle(getArguments()).getAnimeMovie();
        Glide.with(fragmentAnimeMovieDetailsBinding.imageViewDetailsBlurry.getContext())
                .load(animeMovie.getImages().getJpgImages().getLargeImageUrl())
                .placeholder(R.drawable.ic_home).apply(RequestOptions.bitmapTransform(new BlurTransformation(25, 3))).into(fragmentAnimeMovieDetailsBinding.imageViewDetailsBlurry);


        Glide.with(fragmentAnimeMovieDetailsBinding.imageViewDetails.getContext())
                .load(animeMovie.getImages().getJpgImages().getLargeImageUrl())
                .placeholder(R.drawable.ic_home).into(fragmentAnimeMovieDetailsBinding.imageViewDetails);

        fragmentAnimeMovieDetailsBinding.textViewDetailsTitleIn.setText(animeMovie.getTitle());
        fragmentAnimeMovieDetailsBinding.textViewDurationIn.setText(String.valueOf(animeMovie.getDuration()));

        fragmentAnimeMovieDetailsBinding.textViewDetailsYearIn.setText(String.valueOf(animeMovie.getYear()));
        fragmentAnimeMovieDetailsBinding.textViewDescriptionIn.setText(animeMovie.getSynopsis());
        List<AnimeGenres> genres = animeMovie.getGenres();
        if(genres.size() >= 3){
            fragmentAnimeMovieDetailsBinding.textViewGenresIn.setText(genres.get(0).getNameGenre() + " - " + genres.get(1).getNameGenre() +" - "+ genres.get(2).getNameGenre());
        }else {
            fragmentAnimeMovieDetailsBinding.textViewGenresIn.setText(genres.get(0).getNameGenre());
        }
        List<AnimeStudios> studios = animeMovie.getStudios();
        if(studios.size() >= 3){
            fragmentAnimeMovieDetailsBinding.textViewStudiosIn.setText(studios.get(0).getNameStudio() + " - " + studios.get(1).getNameStudio() +" - "+ studios.get(2).getNameStudio());
        }else{
            fragmentAnimeMovieDetailsBinding.textViewStudiosIn.setText(studios.get(0).getNameStudio());
        }
        List<AnimeProducers> producers = animeMovie.getProducers();
        if(producers.size() >= 3){
            fragmentAnimeMovieDetailsBinding.textViewProducersIn.setText(producers.get(0).getNameProducer() + " - " + producers.get(1).getNameProducer() + " - "+ producers.get(2).getNameProducer());
        }else if(producers.size() != 0 && producers.size() < 3){
            fragmentAnimeMovieDetailsBinding.textViewProducersIn.setText(producers.get(0).getNameProducer());
        }else{
            fragmentAnimeMovieDetailsBinding.textViewProducersIn.setText("not found");
        }
        if(animeMovie.getTrailer().getUrlTrailer() != null){
            fragmentAnimeMovieDetailsBinding.textViewTrailerLinkIn.setText(Html.fromHtml(animeMovie.getTrailer().getUrlTrailer(), Html.FROM_HTML_MODE_COMPACT));
        }else{
            fragmentAnimeMovieDetailsBinding.textViewTrailerLinkIn.setText("not found");
        }



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
                AnimeMovieDetailsFragmentDirections.ActionAnimeMovieDetailsFragmentToReviewDetailsFragment action =
                        AnimeMovieDetailsFragmentDirections.actionAnimeMovieDetailsFragmentToReviewDetailsFragment(review);
                Navigation.findNavController(view).navigate(action);
            }
        });

        ReviewsRecyclerViewItem.setAdapter(reviewsRecyclerViewAdapter);
        ReviewsRecyclerViewItem.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

        reviewsRepository.fetchReviewsById(animeMovie.getId(), Long.parseLong(lastUpdate));



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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentAnimeMovieDetailsBinding = null;
    }
}