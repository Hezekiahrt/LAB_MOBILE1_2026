package com.example.praktikum6.helper;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.praktikum6.model.UserModel;

public class UserPreference {

    private static final String PREFS_NAME = "user_session";
    private static final String KEY_NAME = "name";
    private static final String KEY_NIM = "nim";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_JURUSAN = "jurusan";
    private static final String KEY_SEMESTER = "semester";
    private static final String KEY_IPK = "ipk";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";

    private final SharedPreferences sharedPreferences;

    public UserPreference(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveUser(UserModel user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NAME, user.getName());
        editor.putString(KEY_NIM, user.getNim());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_PASSWORD, user.getPassword());
        editor.putString(KEY_JURUSAN, user.getJurusan());
        editor.putString(KEY_SEMESTER, user.getSemester());
        editor.putString(KEY_IPK, user.getIpk());
        editor.putString(KEY_PHONE, user.getPhone());
        editor.putBoolean(KEY_IS_LOGGED_IN, user.isLoggedIn());
        editor.apply();
    }

    public UserModel getUser() {
        UserModel user = new UserModel();
        user.setName(sharedPreferences.getString(KEY_NAME, ""));
        user.setNim(sharedPreferences.getString(KEY_NIM, ""));
        user.setEmail(sharedPreferences.getString(KEY_EMAIL, ""));
        user.setPassword(sharedPreferences.getString(KEY_PASSWORD, ""));
        user.setJurusan(sharedPreferences.getString(KEY_JURUSAN, ""));
        user.setSemester(sharedPreferences.getString(KEY_SEMESTER, ""));
        user.setIpk(sharedPreferences.getString(KEY_IPK, ""));
        user.setPhone(sharedPreferences.getString(KEY_PHONE, ""));
        user.setLoggedIn(sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false));
        return user;
    }

    public void setLogin(boolean isLoggedIn) {
        sharedPreferences.edit().putBoolean(KEY_IS_LOGGED_IN, isLoggedIn).apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void logout() {
        sharedPreferences.edit().putBoolean(KEY_IS_LOGGED_IN, false).apply();
    }

    public void clearAll() {
        sharedPreferences.edit().clear().apply();
    }
}
