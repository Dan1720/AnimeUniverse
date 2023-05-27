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
import com.progetto.animeuniverse.adapter.EpisodesImagesRecyclerViewAdapter;
import com.progetto.animeuniverse.adapter.EpisodesRecyclerViewAdapter;
import com.progetto.animeuniverse.adapter.ReviewsRecyclerViewAdapter;
import com.progetto.animeuniverse.databinding.FragmentAnimeDetailsBinding;
import com.progetto.animeuniverse.model.Anime;
import com.progetto.animeuniverse.model.AnimeEpisodes;
import com.progetto.animeuniverse.model.AnimeEpisodesImages;
import com.progetto.animeuniverse.model.AnimeEpisodesImagesResponse;
import com.progetto.animeuniverse.model.AnimeEpisodesResponse;
import com.progetto.animeuniverse.model.AnimeGenres;
import com.progetto.animeuniverse.model.AnimeProducers;
import com.progetto.animeuniverse.model.AnimeSpecificGenres;
import com.progetto.animeuniverse.model.AnimeStudios;
import com.progetto.animeuniverse.model.Result;
import com.progetto.animeuniverse.model.Review;
import com.progetto.animeuniverse.model.ReviewsResponse;
import com.progetto.animeuniverse.repository.anime_episodes.IAnimeEpisodesRepositoryWithLiveData;
import com.progetto.animeuniverse.repository.anime_episodes_images.IAnimeEpisodesImagesRepositoryWithLiveData;
import com.progetto.animeuniverse.repository.reviews.IReviewsRepositoryWithLiveData;
import com.progetto.animeuniverse.repository.reviews.ReviewsResponseCallback;
import com.progetto.animeuniverse.util.ErrorMessagesUtil;
import com.progetto.animeuniverse.util.ServiceLocator;
import com.progetto.animeuniverse.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

public class AnimeDetailsFragment extends Fragment implements ReviewsResponseCallback {
    private static final String TAG = AnimeDetailsFragment.class.getSimpleName();
    private FragmentAnimeDetailsBinding fragmentAnimeDetailsBinding;
    private List<Review> reviewsList;
    private List<AnimeEpisodes> animeEpisodesList;
    private List<AnimeEpisodesImages> animeEpisodesImagesList;
    private SharedPreferencesUtil sharedPreferencesUtil;
    private ReviewsViewModel reviewsViewModel;
    private AnimeEpisodesViewModel animeEpisodesViewModel;
    private AnimeEpisodesImagesViewModel animeEpisodesImagesViewModel;

    public AnimeDetailsFragment() {
        // Required empty public constructor
    }

    public static AnimeDetailsFragment newInstance() {

        return new AnimeDetailsFragment();
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

        IAnimeEpisodesImagesRepositoryWithLiveData animeEpisodesImagesRepositoryWithLiveData =
                ServiceLocator.getInstance().getAnimeEpisodesImagesRepository(requireActivity().getApplication());
        if(animeEpisodesImagesRepositoryWithLiveData != null){
            animeEpisodesImagesViewModel = new ViewModelProvider(requireActivity(), new AnimeEpisodesImagesViewModelFactory(animeEpisodesImagesRepositoryWithLiveData)).get(AnimeEpisodesImagesViewModel.class);
        }else{
            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                    getString(R.string.unexpected_error), Snackbar.LENGTH_SHORT).show();
        }

        animeEpisodesImagesList = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentAnimeDetailsBinding = FragmentAnimeDetailsBinding.inflate(inflater, container, false);
        return fragmentAnimeDetailsBinding.getRoot();
    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
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
        if(genres.size() >= 3){
            fragmentAnimeDetailsBinding.textViewGenresIn.setText(genres.get(0).getNameGenre() + " - " + genres.get(1).getNameGenre() +" - "+ genres.get(2).getNameGenre());
        }else {
            fragmentAnimeDetailsBinding.textViewGenresIn.setText(genres.get(0).getNameGenre());
        }
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

        RecyclerView ReviewsRecyclerViewItem = view.findViewById(R.id.recyclerView_reviews);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        ReviewsRecyclerViewAdapter reviewsRecyclerViewAdapter = new ReviewsRecyclerViewAdapter(reviewsList, requireActivity().getApplication(), new ReviewsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onReviewItemClick(Review review) {
                com.progetto.animeuniverse.ui.main.AnimeDetailsFragmentDirections.ActionAnimeDetailsFragmentToReviewDetailsFragment action =
                        AnimeDetailsFragmentDirections.actionAnimeDetailsFragmentToReviewDetailsFragment(review);
                Navigation.findNavController(view).navigate(action);
            }
        });

        ReviewsRecyclerViewItem.setAdapter(reviewsRecyclerViewAdapter);
        ReviewsRecyclerViewItem.setLayoutManager(layoutManager);

        String lastUpdate = "0";
        reviewsViewModel.getReviewsByIdAnime(anime.getId(), Long.parseLong(lastUpdate)).observe(getViewLifecycleOwner(), result -> {
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
        animeEpisodesViewModel.getAnimeEpisodes(anime.getId(), Long.parseLong(lastUpdate)).observe(getViewLifecycleOwner(), result ->{
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

    @Override
    public void onSuccess(List<Review> reviewList, long lastUpdate) {
        if(reviewList != null){
            this.reviewsList.clear();
            this.reviewsList.addAll(reviewList);
            sharedPreferencesUtil.writeStringData(SHARED_PREFERENCES_FILE_NAME, LAST_UPDATE, String.valueOf(lastUpdate));
        }
    }

    @Override
    public void onFailure(String errorMessage) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        reviewsViewModel.setFirstLoading(true);
        reviewsViewModel.setLoading(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentAnimeDetailsBinding = null;
    }
}