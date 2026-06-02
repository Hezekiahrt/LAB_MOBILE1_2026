package com.example.praktikum6.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingPreference {

    private static final String PREFS_NAME = "app_setting";
    private static final String KEY_DARK_MODE = "dark_mode";
    private static final String KEY_NOTIF = "notification";
    private static final String KEY_LANGUAGE = "language";

    private final SharedPreferences sharedPreferences;

    public SettingPreference(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public boolean isDarkMode() {
        return sharedPreferences.getBoolean(KEY_DARK_MODE, false);
    }

    public void setDarkMode(boolean isDark) {
        sharedPreferences.edit().putBoolean(KEY_DARK_MODE, isDark).apply();
    }

    public boolean isNotificationEnabled() {
        return sharedPreferences.getBoolean(KEY_NOTIF, true);
    }

    public void setNotification(boolean enabled) {
        sharedPreferences.edit().putBoolean(KEY_NOTIF, enabled).apply();
    }

    public String getLanguage() {
        return sharedPreferences.getString(KEY_LANGUAGE, "Indonesia");
    }

    public void setLanguage(String lang) {
        sharedPreferences.edit().putString(KEY_LANGUAGE, lang).apply();
    }
}
