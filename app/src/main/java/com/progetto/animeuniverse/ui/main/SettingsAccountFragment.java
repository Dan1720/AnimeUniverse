package com.progetto.animeuniverse.ui.main;

import static com.progetto.animeuniverse.util.Constants.KEY_CAMBIO_NOMEUTENTE;
import static com.progetto.animeuniverse.util.Constants.KEY_CAMBIO_PW;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.progetto.animeuniverse.R;

public class SettingsAccountFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_account_preferences, rootKey);
        requireActivity().setTheme(R.style.SettingsFragmentStyle);
        Preference cambio_pw = findPreference(KEY_CAMBIO_PW);
        Preference cambio_nome_utente = findPreference(KEY_CAMBIO_NOMEUTENTE);
        cambio_pw.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Navigation.findNavController(getView()).navigate(R.id.action_settingsAccountFragment_to_forgotPasswordFragment);
                return true;
            }
        });

        cambio_nome_utente.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Navigation.findNavController(getView()).navigate(R.id.action_settingsAccountFragment_to_changeNameFragment);
                return true;
            }
        });



    }
}