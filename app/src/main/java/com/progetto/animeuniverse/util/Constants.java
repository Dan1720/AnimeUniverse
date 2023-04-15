package com.progetto.animeuniverse.util;

public class Constants {
    public static final String ENCRYPTED_SHARED_PREFERENCES_FILE_NAME = "com.progetto.animeuniverse.encrypted_preferences";
    public static final String EMAIL_ADDRESS = "email";
    public static final String PASSWORD = "password";
    public static final String ID_TOKEN = "google_token";
    public static final String APP_DATA_FILE = "app_data.txt";
    public static final String ENCRYPTED_DATA_FILE_NAME = "com.progetto.animeuniverse.encrypted_file.txt";
    public static final String ANIMEUNIVERSE_DATABASE_NAME = "animeuniverse_db";

    public static final int DATABASE_VERSION = 1;

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

    public static final int SELECT_PICTURE = 200;

    public static final String EMAIL_SEND_FORGOT_PASSWORD = "Check your email to reset your password";
    public static final String SOMETHING_WRONG = "Try again! Something wrong happened!";
}
