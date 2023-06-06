package com.progetto.animeuniverse.util;

public class Constants {
    public static final String ENCRYPTED_SHARED_PREFERENCES_FILE_NAME = "com.progetto.animeuniverse.encrypted_preferences";
    public static final String EMAIL_ADDRESS = "email";
    public static final String PASSWORD = "password";
    public static final String ID_TOKEN = "google_token";
    public static final String APP_DATA_FILE = "app_data.txt";
    public static final String ENCRYPTED_DATA_FILE_NAME = "com.progetto.animeuniverse.encrypted_file.txt";
    public static final String ANIMEUNIVERSE_DATABASE_NAME = "animeuniverse_db";

    public static final int DATABASE_VERSION = 23;

    // Constants for managing errors
    public static final String RETROFIT_ERROR = "retrofit_error";
    public static final String API_KEY_ERROR = "api_key_error";
    public static final String UNEXPECTED_ERROR = "unexpected_error";
    public static final String INVALID_USER_ERROR = "invalidUserError";
    public static final String INVALID_CREDENTIALS_ERROR = "invalidCredentials";
    public static final String USER_COLLISION_ERROR = "userCollisionError";
    public static final String WEAK_PASSWORD_ERROR = "passwordIsWeak";

    public static final int MINIMUM_PASSWORD_LENGTH = 6;

    // Constants for Firebase Realtime Database

    public static final String FIREBASE_REALTIME_DATABASE = "https://animeuniverse-f9823-default-rtdb.europe-west1.firebasedatabase.app/";
    public static final String FIREBASE_USERS_COLLECTION = "users";
    public static final String FIREBASE_FAVORITE_ANIME_COLLECTION = "favorite_anime";

    public static final int SELECT_PICTURE = 200;

    public static final String EMAIL_CHANGED = "Email changed, current email is: " ;
    public static final String EMAIL_SEND_FORGOT_PASSWORD = "Check your email to reset your password";
    public static final String SOMETHING_WRONG = "Try again! Something wrong happened!";

    public static final String CHANGE_NAME_OK = "Name changed, current name is: ";
    public static final String KEY_CAMBIO_PW = "cambio_password";
    public static final String KEY_CAMBIO_NOMEUTENTE = "cambio_nomeutente";
    public static final String KEY_PRIVACY = "privacy";
    public static final String KEY_PREFERENZE_COOKIE = "preferenze_cookie";
    public static final String KEY_CONDIZIONI_UTILIZZO = "condizioni_utilizzo";
    public static final String KEY_PREF_COOKIE = "preferenze_cookie";
    public static final String KEY_COND_UTILIZZO = "condizioni_utilizzo";

    public static final String ANIME_API_BASE_URL = "https://api.jikan.moe/v4/";
    public static final String ANIME_API_TEST_JSON_FILE = "animeapi-test.json";
    public static final String REVIEWS_API_TEST_JSON_FILE = "reviewsapiid-test.json";
    public static final String GENRES_API_TEST_JSON_FILE = "genresapi-test.json";

    public static final String ANIMERECOMMENDATIONS_API_TEST_JSON_FILE = "animerecommendationsapi-test.json";
    public static final String ANIMEBYNAME_API_TEST_JSON_FILE = "animebynameapi-test.json";
    public static final String ANIMENEW_API_TEST_JSON_FILE = "animenewapi-test.json";

    public static final String ANIMEEPISODES_API_TEST_JSON_FILE = "animeepisodesapi-test.json";
    public static final String ANIMEEPISODESIMAGES_API_TEST_JSON_FILE = "animeepisodesimagesapi-test.json";
    public static final String ANIMETV_API_TEST_JSON_FILE = "animetvapi-test.json";
    public static final String ANIMEMOVIE_API_TEST_JSON_FILE = "animemovieapi-test.json";
    public static final String ANIMEGENRES_API_TEST_JSON_FILE = "animegenresapi-test.json";

    public static final String ANIME_API_ENDPOINTS = "top/anime";
    public static final String TOP_HEADLINES_ENDPOINT = "anime";
    public static final String TOP_HEADLINES_Q_PARAMETER = "q";
    public static final String TOP_HEADLINES_ANIME_POPULAR_PARAMETER = "numberPopAnime";
    public static final String TOP_HEADLINES_ANIME_RECOMMENDED_PARAMETER = "numberRecommAnime";
    public static final String TOP_HEADLINES_ANIME_NEW_PARAMETER = "numberNewAnime";
    public static final String TOP_HEADLINES_PER_PAGE_PARAMETER = "per_page";
    public static final String TOP_HEADLINES_TOP_PARAMETER="top";
    public static final int TOP_HEADLINES_PAGE_SIZE_VALUE = 25;
    public static final String LAST_UPDATE = "last_update";
    public static final int FRESH_TIMEOUT = 60*60*1000;

    public static final String SHARED_PREFERENCES_FILE_NAME = "com.progetto.animeuniverse.preferences";
    public static final String SHARED_PREFERENCES_FIRST_LOADING = "first_loading";

    public static final int LIMIT_ITEM_RV = 15;

    public static final String CHANNEL_ID = "my_channel_id";
    public static final String ACTION_NOTIFICATION_RECEIVED = "com.progetto.animeuniverse.NotificationListenerService.ACTION_NOTIFICATION_RECEIVED";



}
