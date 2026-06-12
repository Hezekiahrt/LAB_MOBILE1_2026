package com.example.dropby;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.dropby.data.local.AppDatabase;
import com.example.dropby.data.local.PostEntity;
import com.example.dropby.utils.TokenManager;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddPostViewModel extends AndroidViewModel {
    private final MutableLiveData<Boolean> isSuccess = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private final AppDatabase db;
    private final TokenManager tokenManager;

    public AddPostViewModel(@NonNull Application application) {
        super(application);
        db = AppDatabase.getInstance(application);
        tokenManager = new TokenManager(application);
    }

    public LiveData<Boolean> getIsSuccess() { return isSuccess; }
    public LiveData<Boolean> getIsLoading() { return isLoading; }
    public LiveData<String> getErrorMessage() { return errorMessage; }

    public void submitPost(String placeName, String placeType, int rating, String caption, String mediaUri) {
        if (placeName.isEmpty() || placeType.isEmpty()) {
            errorMessage.setValue("Nama tempat dan jenis tempat wajib diisi!");
            return;
        }

        isLoading.setValue(true);

        executor.execute(() -> {
            try {
                // 1. (Opsional) API Retrofit secara Synchronous
                try {
                    // ApiClient.getService(getApplication()).uploadPost(...).execute();
                } catch (Exception apiException) {
                    // Abaikan error jaringan jika tidak ada backend
                }

                // 2. Simpan ke SQLite secara lokal (agar langsung muncul di Beranda saat offline/selesai)
                PostEntity newPost = new PostEntity();
                newPost.authorName = tokenManager.isLoggedIn() ? "Anda" : "User";
                newPost.placeName = placeName;
                newPost.placeType = placeType;
                newPost.rating = rating;
                newPost.caption = caption;
                newPost.imageUrl = mediaUri; // Menyimpan URI gambar lokal untuk ditampilkan
                newPost.isBookmarked = false;
                newPost.isLiked = false;

                db.postDao().insertPosts(java.util.Collections.singletonList(newPost));

                mainHandler.post(() -> {
                    isLoading.setValue(false);
                    isSuccess.setValue(true);
                });
            } catch (Exception e) {
                mainHandler.post(() -> {
                    isLoading.setValue(false);
                    errorMessage.setValue("Terjadi kesalahan saat memproses data.");
                });
            }
        });
    }
}