package com.example.dropby.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {
    private static final String PREF_NAME = "DropbyPrefs";
    private static final String KEY_TOKEN = "jwt_token";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PROFILE_PIC = "profile_pic";
    private SharedPreferences prefs;

    public TokenManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveSession(String token, String username) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_TOKEN, token);
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    public void updateUsername(String newUsername) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_USERNAME, newUsername);
        editor.apply();
    }

    public void saveProfilePicture(String uri) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_PROFILE_PIC, uri);
        editor.apply();
    }

    public String getProfilePicture() {
        return prefs.getString(KEY_PROFILE_PIC, null);
    }

    public String getToken() {
        return prefs.getString(KEY_TOKEN, null);
    }

    public String getUsername() {
        return prefs.getString(KEY_USERNAME, null);
    }

    public boolean isLoggedIn() {
        return getToken() != null;
    }

    public void logout() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }
}