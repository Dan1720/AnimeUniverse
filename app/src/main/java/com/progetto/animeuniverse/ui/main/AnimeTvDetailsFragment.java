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
import com.progetto.animeuniverse.adapter.EpisodesRecyclerViewAdapter;
import com.progetto.animeuniverse.adapter.ReviewsRecyclerViewAdapter;
import com.progetto.animeuniverse.databinding.FragmentAnimeTvBinding;
import com.progetto.animeuniverse.databinding.FragmentAnimeTvDetailsBinding;
import com.progetto.animeuniverse.model.AnimeEpisodes;
import com.progetto.animeuniverse.model.AnimeEpisodesImages;
import com.progetto.animeuniverse.model.AnimeEpisodesResponse;
import com.progetto.animeuniverse.model.AnimeGenres;
import com.progetto.animeuniverse.model.AnimeProducers;
import com.progetto.animeuniverse.model.AnimeStudios;
import com.progetto.animeuniverse.model.AnimeTv;
import com.progetto.animeuniverse.model.Result;
import com.progetto.animeuniverse.model.Review;
import com.progetto.animeuniverse.model.ReviewsResponse;
import com.progetto.animeuniverse.repository.anime_episodes.IAnimeEpisodesRepositoryWithLiveData;
import com.progetto.animeuniverse.repository.anime_episodes_images.IAnimeEpisodesImagesRepositoryWithLiveData;
import com.progetto.animeuniverse.repository.anime_tv.AnimeTvResponseCallback;
import com.progetto.animeuniverse.repository.reviews.IReviewsRepositoryWithLiveData;
import com.progetto.animeuniverse.repository.reviews.ReviewsResponseCallback;
import com.progetto.animeuniverse.util.ErrorMessagesUtil;
import com.progetto.animeuniverse.util.ServiceLocator;
import com.progetto.animeuniverse.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

public class AnimeTvDetailsFragment extends Fragment implements ReviewsResponseCallback {

    private static final String TAG = AnimeTvDetailsFragment.class.getSimpleName();

    private FragmentAnimeTvDetailsBinding fragmentAnimeTvDetailsBinding;
    private List<Review> reviewsList;
    private List<AnimeEpisodes> animeEpisodesList;
    private List<AnimeEpisodesImages> animeEpisodesImagesList;
    private SharedPreferencesUtil sharedPreferencesUtil;
    private ReviewsViewModel reviewsViewModel;
    private AnimeEpisodesViewModel animeEpisodesViewModel;

    public AnimeTvDetailsFragment() {
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
        // Inflate the layout for this fragment
        fragmentAnimeTvDetailsBinding = FragmentAnimeTvDetailsBinding.inflate(inflater, container, false);
        return fragmentAnimeTvDetailsBinding.getRoot();
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

        AnimeTv animeTv = AnimeTvDetailsFragmentArgs.fromBundle(getArguments()).getAnimeTv();
        Glide.with(fragmentAnimeTvDetailsBinding.imageViewDetails.getContext())
                .load(animeTv.getImages().getJpgImages().getLargeImageUrl())
                .placeholder(R.drawable.ic_home).into(fragmentAnimeTvDetailsBinding.imageViewDetails);

        fragmentAnimeTvDetailsBinding.textViewDetailsTitleIn.setText(animeTv.getTitle());
        fragmentAnimeTvDetailsBinding.textViewNEpisodesIn.setText(String.valueOf(animeTv.getNumEpisodes()));

        fragmentAnimeTvDetailsBinding.textViewDetailsYearIn.setText(String.valueOf(animeTv.getYear()));
        fragmentAnimeTvDetailsBinding.textViewDescriptionIn.setText(animeTv.getSynopsis());
        List<AnimeGenres> genres = animeTv.getGenres();
        if(genres.size() >= 3){
            fragmentAnimeTvDetailsBinding.textViewGenresIn.setText(genres.get(0).getNameGenre() + " - " + genres.get(1).getNameGenre() +" - "+ genres.get(2).getNameGenre());
        }else {
            fragmentAnimeTvDetailsBinding.textViewGenresIn.setText(genres.get(0).getNameGenre());
        }
        List<AnimeStudios> studios = animeTv.getStudios();
        if(studios.size() >= 3){
            fragmentAnimeTvDetailsBinding.textViewStudiosIn.setText(studios.get(0).getNameStudio() + " - " + studios.get(1).getNameStudio() +" - "+ studios.get(2).getNameStudio());
        }else{
            fragmentAnimeTvDetailsBinding.textViewStudiosIn.setText(studios.get(0).getNameStudio());
        }
        List<AnimeProducers> producers = animeTv.getProducers();
        if(producers.size() >= 3){
            fragmentAnimeTvDetailsBinding.textViewProducersIn.setText(producers.get(0).getNameProducer() + " - " + producers.get(1).getNameProducer() + " - "+ producers.get(2).getNameProducer());
        }else if(producers.size() != 0 && producers.size() < 3){
            fragmentAnimeTvDetailsBinding.textViewProducersIn.setText(producers.get(0).getNameProducer());
        }else{
            fragmentAnimeTvDetailsBinding.textViewProducersIn.setText("not found");
        }
        if(animeTv.getTrailer().getUrlTrailer() != null){
            fragmentAnimeTvDetailsBinding.textViewTrailerLinkIn.setText(Html.fromHtml(animeTv.getTrailer().getUrlTrailer(), Html.FROM_HTML_MODE_COMPACT));
        }else{
            fragmentAnimeTvDetailsBinding.textViewTrailerLinkIn.setText("not found");
        }



        NavBackStackEntry navBackStackEntry = Navigation.findNavController(view).getPreviousBackStackEntry();
        if(navBackStackEntry != null && navBackStackEntry.getDestination().getId() == R.id.animeTvFragment){
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
                AnimeTvDetailsFragmentDirections.ActionAnimeTvDetailsFragmentToReviewDetailsFragment action =
                        AnimeTvDetailsFragmentDirections.actionAnimeTvDetailsFragmentToReviewDetailsFragment(review);
                Navigation.findNavController(view).navigate(action);
            }
        });

        ReviewsRecyclerViewItem.setAdapter(reviewsRecyclerViewAdapter);
        ReviewsRecyclerViewItem.setLayoutManager(layoutManager);

        String lastUpdate = "0";
        reviewsViewModel.getReviewsByIdAnime(animeTv.getId(), Long.parseLong(lastUpdate)).observe(getViewLifecycleOwner(), result -> {
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
        animeEpisodesViewModel.getAnimeEpisodes(animeTv.getId(), Long.parseLong(lastUpdate)).observe(getViewLifecycleOwner(), result ->{
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
        fragmentAnimeTvDetailsBinding = null;
    }
}