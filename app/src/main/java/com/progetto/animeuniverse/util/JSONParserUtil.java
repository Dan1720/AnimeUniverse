package com.progetto.animeuniverse.util;

import android.app.Application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.progetto.animeuniverse.model.AnimeApiResponse;

public class JSONParserUtil {
    public enum JsonParserType {
        GSON,
        JSON_ERROR
    };
    private final Application application;

    public JSONParserUtil(Application application) {
        this.application = application;
    }

    public AnimeApiResponse parseJSONFileWithGSon(String fileName) throws IOException {
        InputStream inputStream = application.getAssets().open(fileName);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        return new Gson().fromJson(bufferedReader, AnimeApiResponse.class);
    }
}
