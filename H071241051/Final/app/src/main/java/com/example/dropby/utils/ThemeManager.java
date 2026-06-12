package com.example.dropby.utils;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatDelegate;

/**
 * Mengelola preferensi tema Dark/Light mode.
 * Preferensi disimpan di SharedPreferences dan diterapkan via AppCompatDelegate.
 */
public class ThemeManager {
    private static final String PREF_NAME = "DropbyPrefs";
    private static final String KEY_DARK_MODE = "dark_mode";

    // Nilai mode
    public static final int MODE_SYSTEM = 0; // Ikut sistem
    public static final int MODE_LIGHT  = 1;
    public static final int MODE_DARK   = 2;

    private final SharedPreferences prefs;

    public ThemeManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    /** Simpan pilihan mode (MODE_SYSTEM / MODE_LIGHT / MODE_DARK) */
    public void saveMode(int mode) {
        prefs.edit().putInt(KEY_DARK_MODE, mode).apply();
    }

    /** Ambil pilihan mode yang tersimpan, default: MODE_SYSTEM */
    public int getMode() {
        return prefs.getInt(KEY_DARK_MODE, MODE_SYSTEM);
    }

    /** Terapkan tema ke AppCompatDelegate sesuai pilihan tersimpan */
    public void applyTheme() {
        switch (getMode()) {
            case MODE_DARK:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case MODE_LIGHT:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            default: // MODE_SYSTEM
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
        }
    }

    /** Kembalikan true jika mode saat ini adalah dark */
    public boolean isDarkMode() {
        return getMode() == MODE_DARK;
    }
}
