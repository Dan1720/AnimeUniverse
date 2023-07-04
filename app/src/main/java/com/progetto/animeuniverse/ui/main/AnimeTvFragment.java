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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.progetto.animeuniverse.R;
import com.progetto.animeuniverse.adapter.AnimeTvRecyclerViewAdapter;
import com.progetto.animeuniverse.databinding.FragmentAnimeTvBinding;
import com.progetto.animeuniverse.model.AnimeTv;
import com.progetto.animeuniverse.model.AnimeTvResponse;
import com.progetto.animeuniverse.model.Result;
import com.progetto.animeuniverse.repository.anime_tv.IAnimeTvRepositoryWithLiveData;
import com.progetto.animeuniverse.ui.main.AnimeTvFragmentDirections;
import com.progetto.animeuniverse.util.ErrorMessagesUtil;
import com.progetto.animeuniverse.util.ServiceLocator;
import com.progetto.animeuniverse.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

public class AnimeTvFragment extends Fragment {

    private FragmentAnimeTvBinding fragmentAnimeTvBinding;
    private List<AnimeTv> animeTvList;
    private SharedPreferencesUtil sharedPreferencesUtil;
    private AnimeTvViewModel animeTvViewModel;

    public AnimeTvFragment() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferencesUtil = new SharedPreferencesUtil(requireActivity().getApplication());

        IAnimeTvRepositoryWithLiveData animeTvRepositoryWithLiveData =
                ServiceLocator.getInstance().getAnimeTvRepository(requireActivity().getApplication());
        if(animeTvRepositoryWithLiveData != null){
            animeTvViewModel = new ViewModelProvider(requireActivity(), new AnimeTvViewModelFactory(animeTvRepositoryWithLiveData)).get(AnimeTvViewModel.class);
        }else{
            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                    getString(R.string.unexpected_error), Snackbar.LENGTH_SHORT).show();
        }

        animeTvList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentAnimeTvBinding = FragmentAnimeTvBinding.inflate(inflater, container, false);
        return fragmentAnimeTvBinding.getRoot();
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

        NavBackStackEntry navBackStackEntry = Navigation.findNavController(view).getPreviousBackStackEntry();
        if(navBackStackEntry != null && navBackStackEntry.getDestination().getId() == R.id.homeFragment){
            ((BottomNavigationView) requireActivity().findViewById(R.id.bottom_navigation)).
                    getMenu().findItem(R.id.homeFragment).setChecked(true);

        }else if(navBackStackEntry != null && navBackStackEntry.getDestination().getId() == R.id.listFragment){
            ((BottomNavigationView) requireActivity().findViewById(R.id.bottom_navigation)).
                    getMenu().findItem(R.id.listFragment).setChecked(true);
        }

        RecyclerView AnimeTvRecyclerViewItem = view.findViewById(R.id.recyclerView_gridTv);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        AnimeTvRecyclerViewAdapter animeTvRecyclerViewAdapter = new AnimeTvRecyclerViewAdapter(animeTvList, requireActivity().getApplication(), new AnimeTvRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onAnimeTvClick(AnimeTv animeTv) {
                com.progetto.animeuniverse.ui.main.AnimeTvFragmentDirections.ActionAnimeTvFragmentToAnimeTvDetailsFragment action =
                        AnimeTvFragmentDirections.actionAnimeTvFragmentToAnimeTvDetailsFragment(animeTv);
                Navigation.findNavController(view).navigate(action);
            }
        });
        AnimeTvRecyclerViewItem.setAdapter(animeTvRecyclerViewAdapter);
        AnimeTvRecyclerViewItem.setLayoutManager(layoutManager);

        String lastUpdate = "0";
        animeTvViewModel.getAnimeTv(Long.parseLong(lastUpdate)).observe(getViewLifecycleOwner(), result ->{
            System.out.println("Result anime tv: " + result.isSuccess());
            if(result.isSuccess()){
                AnimeTvResponse animeTvResponse = ((Result.AnimeTvSuccess) result).getData();
                List<AnimeTv> fetchedAnimeTv = animeTvResponse.getAnimeTvList();
                this.animeTvList.addAll(fetchedAnimeTv);
                animeTvRecyclerViewAdapter.notifyDataSetChanged();
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