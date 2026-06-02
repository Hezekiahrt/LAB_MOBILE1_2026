package com.example.libraryapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    private static final String PREF_NAME = "LibraryAppPref";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_DARK_MODE = "darkMode";

    private final SharedPreferences pref;
    private final SharedPreferences.Editor editor;

    public SharedPrefManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    // Auth
    public void saveLogin(String username, String password) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);
        editor.apply();
    }

    public void registerUser(String username, String password) {
        editor.putString("reg_username", username);
        editor.putString("reg_password", password);
        editor.apply();
    }

    public boolean isRegistered(String username, String password) {
        String savedUser = pref.getString("reg_username", "");
        String savedPass = pref.getString("reg_password", "");
        return savedUser.equals(username) && savedPass.equals(password);
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public String getUsername() {
        return pref.getString(KEY_USERNAME, "");
    }

    public void logout() {
        editor.putBoolean(KEY_IS_LOGGED_IN, false);
        editor.apply();
    }

    // Tema
    public void setDarkMode(boolean isDark) {
        editor.putBoolean(KEY_DARK_MODE, isDark);
        editor.apply();
    }

    public boolean isDarkMode() {
        return pref.getBoolean(KEY_DARK_MODE, false);
    }
}