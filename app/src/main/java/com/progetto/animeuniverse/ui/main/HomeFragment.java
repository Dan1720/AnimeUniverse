package com.progetto.animeuniverse.ui.main;

import static com.progetto.animeuniverse.util.Constants.CHANNEL_ID;
import static com.progetto.animeuniverse.util.Constants.LAST_UPDATE;
import static com.progetto.animeuniverse.util.Constants.SHARED_PREFERENCES_FILE_NAME;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.adapter.AnimeNewRecyclerViewAdapter;
import com.progetto.animeuniverse.adapter.AnimeRecommendationsRecyclerViewAdapter;
import com.progetto.animeuniverse.adapter.AnimeTopRecyclerViewAdapter;
import com.progetto.animeuniverse.databinding.FragmentHomeBinding;
import com.progetto.animeuniverse.model.Anime;


import com.progetto.animeuniverse.model.AnimeGenres;
import com.progetto.animeuniverse.model.AnimeNew;
import com.progetto.animeuniverse.model.AnimeNewResponse;
import com.progetto.animeuniverse.model.AnimeRecommendations;
import com.progetto.animeuniverse.model.AnimeRecommendationsResponse;
import com.progetto.animeuniverse.model.AnimeResponse;
import com.progetto.animeuniverse.model.Result;
import com.progetto.animeuniverse.repository.anime.AnimeResponseCallback;
import com.progetto.animeuniverse.repository.anime.IAnimeRepositoryWithLiveData;
import com.progetto.animeuniverse.repository.anime_new.IAnimeNewRepositoryWithLiveData;
import com.progetto.animeuniverse.repository.anime_recommendations.IAnimeRecommendationsRepositoryWithLiveData;
import com.progetto.animeuniverse.ui.welcome.WelcomeActivity;
import com.progetto.animeuniverse.util.ErrorMessagesUtil;
import com.progetto.animeuniverse.util.ServiceLocator;
import com.progetto.animeuniverse.util.SharedPreferencesUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class HomeFragment extends Fragment implements AnimeResponseCallback {

    private List<Anime> animeList;
    private List<AnimeRecommendations> animeRecommendationsList;
    private List<AnimeNew> animeNewList;

    private SharedPreferencesUtil sharedPreferencesUtil;
    private AnimeViewModel animeViewModel;
    private AnimeRecommendationsViewModel animeRecommendationsViewModel;
    private AnimeNewViewModel animeNewViewModel;

    private FragmentHomeBinding fragmentHomeBinding;
    private int countId = 0;
    private static boolean flag = false;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferencesUtil = new SharedPreferencesUtil(requireActivity().getApplication());

        IAnimeRepositoryWithLiveData animeRepositoryWithLiveData =
                ServiceLocator.getInstance().getAnimeRepository(
                        requireActivity().getApplication()
                );
        if (animeRepositoryWithLiveData != null) {
            animeViewModel = new ViewModelProvider(requireActivity(),
                    new AnimeViewModelFactory(animeRepositoryWithLiveData)).get(AnimeViewModel.class);
        } else {
            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                    getString(R.string.unexpected_error), Snackbar.LENGTH_SHORT).show();
        }
        animeList = new ArrayList<>();

        IAnimeRecommendationsRepositoryWithLiveData animeRecommendationsRepositoryWithLiveData =
                ServiceLocator.getInstance().getAnimeRecommendationsRepository(
                        requireActivity().getApplication()
                );

        if (animeRecommendationsRepositoryWithLiveData != null) {
            animeRecommendationsViewModel = new ViewModelProvider(requireActivity(),
                    new AnimeRecommendationsViewModelFactory(animeRecommendationsRepositoryWithLiveData)).get(AnimeRecommendationsViewModel.class);
        } else {
            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                    getString(R.string.unexpected_error), Snackbar.LENGTH_SHORT).show();
        }

        animeRecommendationsList = new ArrayList<>();

        IAnimeNewRepositoryWithLiveData animeNewRepositoryWithLiveData =
                ServiceLocator.getInstance().getAnimeNewRepository(
                        requireActivity().getApplication()
                );
        if (animeNewRepositoryWithLiveData != null) {
            animeNewViewModel = new ViewModelProvider(requireActivity(),
                    new AnimeNewViewModelFactory(animeNewRepositoryWithLiveData)).get(AnimeNewViewModel.class);
        } else {
            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                    getString(R.string.unexpected_error), Snackbar.LENGTH_SHORT).show();
        }

        animeNewList = new ArrayList<>();
        createNotificationChannel();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false);
        return fragmentHomeBinding.getRoot();
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView animeTopRecyclerViewItem = view.findViewById(R.id.animeTop_recyclerview);
        AnimeTopRecyclerViewAdapter animeTopRecyclerViewAdapter = new AnimeTopRecyclerViewAdapter(animeList, requireActivity().getApplication(), new AnimeTopRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onAnimeClick(Anime anime) {
                com.progetto.animeuniverse.ui.main.HomeFragmentDirections.ActionHomeFragmentToAnimeDetailsFragment action =
                        com.progetto.animeuniverse.ui.main.HomeFragmentDirections.actionHomeFragmentToAnimeDetailsFragment(anime);
                Navigation.findNavController(view).navigate(action);
            }

            @Override
            public void onFavoriteButtonPressed(int position) {
                animeList.get(position).setFavorite(!animeList.get(position).isFavorite());
                animeViewModel.updateAnime(animeList.get(position));
                if(flag == true){
                    inviaNotifica(animeList.get(position).getTitle());
                }

            }
        });
        animeTopRecyclerViewItem.setAdapter(animeTopRecyclerViewAdapter);
        animeTopRecyclerViewItem.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));


        String lastUpdate = "0";
        animeViewModel.getAnimeTop(Long.parseLong(lastUpdate)).observe(getViewLifecycleOwner(), result -> {
            System.out.println("Result: " + result.isSuccess());
            if (result.isSuccess()) {
                AnimeResponse animeResponse = ((Result.AnimeResponseSuccess) result).getData();
                List<Anime> fetchedAnime = animeResponse.getAnimeList();
                this.animeList.addAll(fetchedAnime);
                animeTopRecyclerViewAdapter.notifyDataSetChanged();
                setImageHomeCover(animeList);
            } else {
                ErrorMessagesUtil errorMessagesUtil = new ErrorMessagesUtil(requireActivity().getApplication());
                Snackbar.make(view, errorMessagesUtil.
                                getErrorMessage(((Result.Error) result).getMessage()),
                        Snackbar.LENGTH_SHORT).show();
            }
        });

        RecyclerView animeRecommendationsRecyclerViewItem = view.findViewById(R.id.animeReccomendations_recyclerview);
        AnimeRecommendationsRecyclerViewAdapter animeRecommendationsRecyclerViewAdapter = new AnimeRecommendationsRecyclerViewAdapter(animeRecommendationsList, requireActivity().getApplication(), new AnimeRecommendationsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onAnimeRecommendationsClick(AnimeRecommendations animeRecommendations) {
                com.progetto.animeuniverse.ui.main.HomeFragmentDirections.ActionHomeFragmentToAnimeRecommendationsDetailsFragment action =
                        HomeFragmentDirections.actionHomeFragmentToAnimeRecommendationsDetailsFragment(animeRecommendations);
                Navigation.findNavController(view).navigate(action);
            }
        });
        animeRecommendationsRecyclerViewItem.setAdapter(animeRecommendationsRecyclerViewAdapter);
        animeRecommendationsRecyclerViewItem.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

        animeRecommendationsViewModel.getAnimeRecommendations(Long.parseLong(lastUpdate)).observe(getViewLifecycleOwner(), result -> {
            System.out.println("Result reccomend: " + result.isSuccess());
            if (result.isSuccess()) {
                AnimeRecommendationsResponse animeRecommendationsResponse = ((Result.AnimeRecommendationsSuccess) result).getData();
                List<AnimeRecommendations> fetchedAnimeRecommendations = animeRecommendationsResponse.getAnimeRecommendationsList();
                this.animeRecommendationsList.addAll(fetchedAnimeRecommendations);
                animeRecommendationsRecyclerViewAdapter.notifyDataSetChanged();
            } else {
                ErrorMessagesUtil errorMessagesUtil = new ErrorMessagesUtil(requireActivity().getApplication());
                Snackbar.make(view, errorMessagesUtil.
                                getErrorMessage(((Result.Error) result).getMessage()),
                        Snackbar.LENGTH_SHORT).show();
            }
        });


        RecyclerView animeNewRecyclerViewItem = view.findViewById(R.id.animeNew_recyclerview);
        AnimeNewRecyclerViewAdapter animeNewRecyclerViewAdapter = new AnimeNewRecyclerViewAdapter(animeNewList, requireActivity().getApplication(), new AnimeNewRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onAnimeNewClick(AnimeNew animeNew) {
                com.progetto.animeuniverse.ui.main.HomeFragmentDirections.ActionHomeFragmentToAnimeNewDetailsFragment action =
                        HomeFragmentDirections.actionHomeFragmentToAnimeNewDetailsFragment(animeNew);
                Navigation.findNavController(view).navigate(action);
            }
        });
        animeNewRecyclerViewItem.setAdapter(animeNewRecyclerViewAdapter);
        animeNewRecyclerViewItem.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

        animeNewViewModel.getAnimeNew(Long.parseLong(lastUpdate)).observe(getViewLifecycleOwner(), result -> {
            System.out.println("Result new: " + result.isSuccess());
            if (result.isSuccess()) {
                AnimeNewResponse animeNewResponse = ((Result.AnimeNewSuccess) result).getData();
                List<AnimeNew> fetchedAnimeNew = animeNewResponse.getAnimeNewList();
                this.animeNewList.addAll(fetchedAnimeNew);
                animeNewRecyclerViewAdapter.notifyDataSetChanged();
            } else {
                ErrorMessagesUtil errorMessagesUtil = new ErrorMessagesUtil(requireActivity().getApplication());
                Snackbar.make(view, errorMessagesUtil.
                                getErrorMessage(((Result.Error) result).getMessage()),
                        Snackbar.LENGTH_SHORT).show();
            }
        });


        fragmentHomeBinding.txtCategorie.setOnClickListener(v -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_genresFragment);
        });
        fragmentHomeBinding.txtSerieTv.setOnClickListener(v -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_animeTvFragment);
        });
        fragmentHomeBinding.txtFilm.setOnClickListener(v -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_animeMovieFragment);
        });

    }


    @Override
    public void onSuccess(List<Anime> animeList, long lastUpdate) {
        if (animeList != null) {
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
        if (anime.isFavorite()) {
            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                    R.string.add_anime_favorite, Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                    R.string.remove_anime_favorite, Snackbar.LENGTH_LONG).show();
        }
    }

    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        animeViewModel.setFirstLoading(true);
        animeViewModel.setLoading(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentHomeBinding = null;
    }


    @SuppressLint("SetTextI18n")
    public void setImageHomeCover(List<Anime> animeList) {
        ImageView homeCover = getView().findViewById(R.id.homeCover);
        TextView categories = getView().findViewById(R.id.txt_btn_categorie);
        Random random = new Random();
        int number = random.nextInt(animeList.size());
        Anime animeHomeCover = animeList.get(number);
        Picasso.get()
                .load(animeHomeCover.getImages().getJpgImages().getLargeImageUrl())
                .into(homeCover);
        List<AnimeGenres> genres = animeHomeCover.getGenres();
        if (genres.size() >= 3) {
            categories.setText(" ҉ " + " " +genres.get(0).getNameGenre() + " " +  " " + " " + " ҉ " + " " + genres.get(1).getNameGenre() + " " +  " " + " " + " ҉ " + " " + genres.get(2).getNameGenre()+ " " + " " + " " +" ҉ " );
        } else {
            categories.setText(genres.get(0).getNameGenre());
        }
        fragmentHomeBinding.imageViewInfo.setOnClickListener(v -> {
            com.progetto.animeuniverse.ui.main.HomeFragmentDirections.ActionHomeFragmentToAnimeDetailsFragment action =
                    com.progetto.animeuniverse.ui.main.HomeFragmentDirections.actionHomeFragmentToAnimeDetailsFragment(animeHomeCover);
            Navigation.findNavController(requireView()).navigate(action);
        });

        fragmentHomeBinding.homeCover.setOnClickListener(v -> {
            com.progetto.animeuniverse.ui.main.HomeFragmentDirections.ActionHomeFragmentToAnimeDetailsFragment action =
                    HomeFragmentDirections.actionHomeFragmentToAnimeDetailsFragment(animeHomeCover);
            Navigation.findNavController(requireView()).navigate(action);
        });


        fragmentHomeBinding.imageViewFavoriteHome.setOnClickListener(v -> {
            animeList.get(number).setFavorite(!animeList.get(number).isFavorite());
            animeViewModel.updateAnime(animeList.get(number));
            setImageViewFavoriteAnime(animeList.get(number).isFavorite());
            if(flag == true){
                inviaNotifica(animeList.get(number).getTitle());
            }

        });


    }

    public void setImageViewFavoriteAnime(boolean isFavorite) {
        if (isFavorite) {
            fragmentHomeBinding.imageViewFavoriteHome.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.baseline_favorite_24));
            isChecked(true);
        } else {
            fragmentHomeBinding.imageViewFavoriteHome.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.baseline_not_favorite_24));
            isChecked(false);
        }
    }

    public void inviaNotifica(String animeTitle) {
        String animeText = "L'elemento è stato aggiunto ai preferiti.";
        
        Intent intent = new Intent(requireContext(), WelcomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, new Intent(), PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Elemento " + animeTitle + " aggiunto ai preferiti")
                .setContentText("L'elemento è stato aggiunto ai preferiti.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        countId++;
        flag = false;
        notificationManager.notify(countId, builder.build());




    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Anime Universe Channel";
            String description = "Canale di notifica per Anime Universe";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = requireContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    public static void isChecked(boolean check){
        if(check == true){
            if(flag != true){
                flag = true;

            }

        } else {
            if(flag == true){
                flag = false;
            }
        }
    }



}