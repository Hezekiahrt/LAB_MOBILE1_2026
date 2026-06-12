package com.example.dropby;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import com.example.dropby.utils.ThemeManager;
import com.example.dropby.utils.TokenManager;

public class MainActivity extends AppCompatActivity {

    // Variabel untuk menahan Splash Screen
    private boolean keepShowingSplash = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 0. Terapkan tema (dark/light) SEBELUM apapun di-render
        new ThemeManager(this).applyTheme();

        // 1. Inisialisasi Splash Screen API
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);

        // 2. Tahan Splash Screen agar logo tetap muncul (misal: 2 detik)
        splashScreen.setKeepOnScreenCondition(() -> keepShowingSplash);

        // Menggunakan Handler (Background Thread) untuk mengatur timer
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            keepShowingSplash = false; // Splash screen akan hilang setelah 2 detik
            checkLoginStatus();
        }, 2000);
    }

    private void checkLoginStatus() {
        TokenManager tokenManager = new TokenManager(this);
        Intent intent;

        if (tokenManager.isLoggedIn()) {
            intent = new Intent(this, HomeActivity.class);
        } else {
            intent = new Intent(this, AuthActivity.class);
        }

        startActivity(intent);
        finish();
    }
}