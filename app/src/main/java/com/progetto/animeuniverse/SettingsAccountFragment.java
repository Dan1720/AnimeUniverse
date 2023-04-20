package com.progetto.animeuniverse;

import static com.progetto.animeuniverse.util.Constants.KEY_CAMBIO_PW;

import android.os.Bundle;

import androidx.navigation.Navigation;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsAccountFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_account_preferences, rootKey);
        requireActivity().setTheme(R.style.SettingsFragmentStyle);
        Preference cambio_pw = findPreference(KEY_CAMBIO_PW);
        cambio_pw.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Navigation.findNavController(getView()).navigate(R.id.action_settingsAccountFragment_to_forgotPasswordFragment);
                return true;
            }
        });
    }
}