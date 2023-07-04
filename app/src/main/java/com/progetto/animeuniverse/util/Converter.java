package com.progetto.animeuniverse.util;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.progetto.animeuniverse.model.AnimeEntry;
import com.progetto.animeuniverse.model.AnimeGenres;
import com.progetto.animeuniverse.model.AnimeProducers;
import com.progetto.animeuniverse.model.AnimeStudios;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class Converter {
    static Gson gson = new Gson();

    @TypeConverter
    public static List<AnimeProducers> stringToAnimeProducersList(String value){
        if(value == null){
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<AnimeProducers>>(){}.getType();
        return gson.fromJson(value, listType);
    }

    @TypeConverter
    public static String someAnimeProducersListToString(List<AnimeProducers> object){
        return gson.toJson(object);
    }

    @TypeConverter
    public static List<AnimeGenres> stringToAnimeGenresList(String value){
        if(value == null){
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<AnimeGenres>>(){}.getType();
        return gson.fromJson(value, listType);
    }

    @TypeConverter
    public static String someAnimeGenresListToString(List<AnimeGenres> object){
        return gson.toJson(object);
    }

    @TypeConverter
    public static List<AnimeStudios> stringToAnimeStudiosList(String value){
        if(value == null){
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<AnimeStudios>>(){}.getType();
        return gson.fromJson(value, listType);
    }

    @TypeConverter
    public static String someAnimeStudiosListToString(List<AnimeStudios> object){
        return gson.toJson(object);
    }

    @TypeConverter
    public static List<AnimeEntry> stringToAnimeEntryList(String value){
        if(value == null){
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<AnimeEntry>>(){}.getType();
        return gson.fromJson(value, listType);
    }

    @TypeConverter
    public static String someAnimeEntryListToString(List<AnimeEntry> object){
        return gson.toJson(object);
    }



}
