package com.progetto.animeuniverse.ui.welcome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.progetto.animeuniverse.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Toolbar toolbar = findViewById(R.id.top_appbar);
        setSupportActionBar(toolbar);
        ImageView toolbarLogo = findViewById(R.id.toolbar_logo);
        //toolbar.setLogo(R.drawable.logo_intero);
        //toolbar.setDisplayUseLogoEnabled(true);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.listFragment, R.id.homeFragment,
                R.id.searchFragment, R.id.accountFragment
        ).build();

        // For the Toolbar
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // For the BottomNavigationView
        NavigationUI.setupWithNavController(bottomNav, navController);

        /*Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            Notification notification = bundle.getParcelable("notification");
            if(notification != null){
                NotificationsFragment notificationsFragment = NotificationsFragment.newInstance(notification);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, notificationsFragment)
                        .commit();

            }
        }*/


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}