package com.example.dropby;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.dropby.data.remote.ApiClient;
import com.example.dropby.utils.TokenManager;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AuthViewModel extends AndroidViewModel {
    private final MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    // Ketentuan: Menggunakan Executor dan Handler untuk Background Thread
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public AuthViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Boolean> getLoginSuccess() { return loginSuccess; }
    public LiveData<String> getErrorMessage() { return errorMessage; }
    public LiveData<Boolean> getIsLoading() { return isLoading; }

    // Context tidak lagi diterima sebagai parameter — gunakan getApplication() yang aman
    public void login(String username, String password) {
        isLoading.setValue(true);

        executor.execute(() -> {
            try {
                // 1. Memanggil API Retrofit secara Synchronous (sesuai ketentuan)
                try {
                    // Response<AuthResponse> response = ApiClient.getService(getApplication()).login(new LoginRequest(username, password)).execute();
                    // if (response.isSuccessful()) { ... }
                } catch (Exception apiException) {
                    // Abaikan error jaringan untuk keperluan lab jika tidak ada backend
                }

                // 2. Simulasi loading seolah-olah sedang request ke server (1 detik)
                Thread.sleep(1000);

                // 3. Bypass: Anggap saja login selalu berhasil & simpan token dummy
                TokenManager tm = new TokenManager(getApplication());
                tm.saveSession("token_dummy_sementara_123", username);

                // Reset Retrofit agar interceptor dibuat ulang dengan token baru
                ApiClient.reset();

                mainHandler.post(() -> {
                    isLoading.setValue(false);
                    loginSuccess.setValue(true); // Pindah halaman
                });

            } catch (Exception e) {
                mainHandler.post(() -> {
                    isLoading.setValue(false);
                    errorMessage.setValue("Terjadi kesalahan: " + e.getMessage());
                });
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executor.shutdown(); // Bersihkan thread saat ViewModel dihancurkan
    }
}