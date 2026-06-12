package com.example.dropby.data.remote;

import android.content.Context;
import com.example.dropby.utils.TokenManager;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    // Ganti dengan URL API Anda (jika pakai localhost emulator, gunakan 10.0.2.2)
    private static final String BASE_URL = "http://10.0.2.2:8000/";
    private static volatile Retrofit retrofit = null;

    // Panggil ini setelah login/logout agar token di-refresh
    public static synchronized void reset() {
        retrofit = null;
    }

    public static ApiService getService(Context context) {
        if (retrofit == null) {
            TokenManager tokenManager = new TokenManager(context);

            // Interceptor untuk menyisipkan Token JWT
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {
                Request.Builder requestBuilder = chain.request().newBuilder();
                if (tokenManager.isLoggedIn()) {
                    requestBuilder.addHeader("Authorization", "Bearer " + tokenManager.getToken());
                }
                return chain.proceed(requestBuilder.build());
            }).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiService.class);
    }
}