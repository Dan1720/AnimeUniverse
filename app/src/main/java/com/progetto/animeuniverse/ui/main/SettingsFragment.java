package com.progetto.animeuniverse.ui.main;

import static com.progetto.animeuniverse.util.Constants.KEY_CONDIZIONI_UTILIZZO;
import static com.progetto.animeuniverse.util.Constants.KEY_PREFERENZE_COOKIE;
import static com.progetto.animeuniverse.util.Constants.KEY_PRIVACY;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.progetto.animeuniverse.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        requireActivity().setTheme(R.style.SettingsFragmentStyle);

        Preference privacy = findPreference(KEY_PRIVACY);
        Preference preferenze_cookie = findPreference(KEY_PREFERENZE_COOKIE);
        Preference condizioni_utilizzo = findPreference(KEY_CONDIZIONI_UTILIZZO);
        privacy.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Navigation.findNavController(getView()).navigate(R.id.action_settingsFragment_to_privacyFragment);
                return true;
            }
        });

        preferenze_cookie.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                Navigation.findNavController(getView()).navigate(R.id.action_settingsFragment_to_cookiePreferenceFragment);
                return true;
            }
        });


        condizioni_utilizzo.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                Navigation.findNavController(getView()).navigate(R.id.action_settingsFragment_to_condizioniUtilizzoFragment);
                return true;
            }
        });
    }
}