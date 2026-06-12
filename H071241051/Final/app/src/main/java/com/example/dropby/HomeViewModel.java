package com.example.dropby;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.dropby.data.local.AppDatabase;
import com.example.dropby.data.local.PostDao;
import com.example.dropby.data.local.PostEntity;
import com.example.dropby.data.remote.ApiClient;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import retrofit2.Response;

public class HomeViewModel extends AndroidViewModel {
    private final PostDao postDao;
    private final LiveData<List<PostEntity>> allPosts;
    private final MutableLiveData<Boolean> isRefreshing = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public HomeViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        postDao = db.postDao();

        // Mengambil data secara lokal (Offline Data langsung tampil)
        allPosts = postDao.getAllPosts();
    }

    public LiveData<List<PostEntity>> getPosts() { return allPosts; }
    public LiveData<Boolean> getIsRefreshing() { return isRefreshing; }
    public LiveData<String> getErrorMessage() { return errorMessage; }

    // Fungsi mengambil dari jaringan menggunakan Background Thread (Executor)
    public void fetchPostsFromApi() {
        isRefreshing.setValue(true);

        executor.execute(() -> {
            try {
                // Networking dengan Retrofit secara Synchronous
                Response<List<PostEntity>> response = ApiClient.getService(getApplication()).getPosts().execute();

                if (response.isSuccessful() && response.body() != null) {
                    // Simpan ke SQLite (Room) untuk persistent data
                    // postDao.clearAllPosts(); // Komentar ini agar post lokal pengguna tidak hilang
                    postDao.insertPosts(response.body());

                    mainHandler.post(() -> isRefreshing.setValue(false));
                } else {
                    mainHandler.post(() -> {
                        isRefreshing.setValue(false);
                        errorMessage.setValue("Gagal memuat data terbaru dari server.");
                    });
                }
            } catch (Exception e) {
                // Kondisi tidak ada jaringan, data SQLite tetap akan tertampil
                mainHandler.post(() -> {
                    isRefreshing.setValue(false);
                    errorMessage.setValue("Tidak ada koneksi jaringan. Menampilkan data offline.");
                });
            }
        });
    }

    public void updatePost(PostEntity post) {
        executor.execute(() -> {
            postDao.updatePost(post);
        });
    }
}