package com.progetto.animeuniverse.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.progetto.animeuniverse.R;


public class CondizioniUtilizzoFragment extends Fragment {



    public CondizioniUtilizzoFragment() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_condizioni_utilizzo, container, false);

        WebView webView = (WebView)rootView.findViewById(R.id.WebViewCondizioniUtilizzo);
        String HTML = "Condizioni Di Utilizzo";
        WebSettings settings = webView.getSettings();
        settings.setDefaultTextEncodingName("utf-8");
        webView.loadUrl("file:///android_asset/CondizioniDiUtilizzo.html");

        return rootView;
    }
}