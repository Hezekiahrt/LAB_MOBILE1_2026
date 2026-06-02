package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    private static final String PREF_NAME = "InstagramClonePref";
    private static final String IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_IS_DARK_MODE = "isDarkMode";

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    // --- FITUR REGISTER & LOGIN ---
    public void registerUser(String username, String password) {
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);
        editor.commit();
    }

    public boolean checkLoginCredentials(String username, String password) {
        String savedUser = pref.getString(KEY_USERNAME, null);
        String savedPass = pref.getString(KEY_PASSWORD, null);
        return (savedUser != null && savedPass != null && savedUser.equals(username) && savedPass.equals(password));
    }

    public void createLoginSession() {
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGGED_IN, false);
    }

    public void logoutUser() {
        editor.putBoolean(IS_LOGGED_IN, false);
        editor.commit();
    }

    // --- FITUR DARK MODE ---
    public void setDarkMode(boolean isDark) {
        editor.putBoolean(KEY_IS_DARK_MODE, isDark);
        editor.commit();
    }

    public boolean isDarkMode() {
        // Default false agar aplikasi dimulai dengan Mode Terang (Light Mode)
        return pref.getBoolean(KEY_IS_DARK_MODE, false);
    }
}