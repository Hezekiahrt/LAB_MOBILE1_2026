package com.example.dropby;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.bumptech.glide.Glide;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputEditText;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.views.overlay.MapEventsOverlay;
import android.content.Context;

public class AddPostFragment extends Fragment {
    private AddPostViewModel viewModel;
    private String selectedMediaUri = ""; // Untuk menyimpan URI gambar sementara

    // Konfigurasi PickVisualMedia (Modern Android 15 standard)
    private final ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                if (uri != null) {
                    selectedMediaUri = uri.toString();
                    ImageView ivPreview = getView().findViewById(R.id.ivMediaPreview);
                    View llContainer = getView().findViewById(R.id.llPickMediaContainer);

                    // Hilangkan container sepenuhnya, tampilkan gambar
                    if (llContainer != null) llContainer.setVisibility(View.GONE);
                    ivPreview.setVisibility(View.VISIBLE);
                    Glide.with(this).load(uri).centerCrop().into(ivPreview);
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Load osmdroid configuration before inflating view
        Context ctx = requireContext().getApplicationContext();
        Configuration.getInstance().load(ctx, ctx.getSharedPreferences("osmdroid", Context.MODE_PRIVATE));
        
        return inflater.inflate(R.layout.fragment_add_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(AddPostViewModel.class);

        // Inisialisasi View
        Button btnPickMedia = view.findViewById(R.id.btnPickMedia);
        ImageView ivMediaPreview = view.findViewById(R.id.ivMediaPreview);
        TextInputEditText etPlaceName = view.findViewById(R.id.etPlaceName);
        AutoCompleteTextView actvPlaceType = view.findViewById(R.id.actvPlaceType);
        Slider sliderRating = view.findViewById(R.id.sliderRating);
        TextView tvRatingLabel = view.findViewById(R.id.tvRatingLabel);
        TextInputEditText etCaption = view.findViewById(R.id.etCaption);
        Button btnSubmit = view.findViewById(R.id.btnSubmitPost);
        MapView mapView = view.findViewById(R.id.mapView);

        // Setup Map
        mapView.setMultiTouchControls(true);
        mapView.getController().setZoom(15.0);
        GeoPoint startPoint = new GeoPoint(-6.200000, 106.816666); // Default ke Jakarta
        mapView.getController().setCenter(startPoint);

        Marker locationMarker = new Marker(mapView);
        locationMarker.setPosition(startPoint);
        locationMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mapView.getOverlays().add(locationMarker);

        MapEventsReceiver mReceive = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                locationMarker.setPosition(p);
                locationMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                mapView.getController().animateTo(p);
                mapView.invalidate();
                // Optionally save coordinates if we had columns for it, here we just visually set it
                return true;
            }
            @Override
            public boolean longPressHelper(GeoPoint p) { return false; }
        };
        mapView.getOverlays().add(new MapEventsOverlay(mReceive));

        // 1. Setup Data Dropdown Jenis Tempat
        String[] placeTypes = new String[]{"Restoran", "Kafe", "Toko", "Tempat Wisata", "Mall", "Lainnya"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, placeTypes);
        actvPlaceType.setAdapter(adapter);

        // 2. Aksi Tombol Pilih Media
        View.OnClickListener mediaClickListener = v -> {
            // Memanggil pemilih media bawaan sistem (Hanya foto)
            pickMedia.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                    .build());
        };
        btnPickMedia.setOnClickListener(mediaClickListener);
        ivMediaPreview.setOnClickListener(mediaClickListener); // Gambar bisa diklik untuk ganti foto

        // 3. Update Label Rating secara Realtime
        sliderRating.addOnChangeListener((slider, value, fromUser) -> {
            tvRatingLabel.setText("Rating: " + (int) value + "/10");
        });

        // 4. Aksi Tombol Submit
        btnSubmit.setOnClickListener(v -> {
            String placeName = etPlaceName.getText() != null ? etPlaceName.getText().toString() : "";
            String placeType = actvPlaceType.getText() != null ? actvPlaceType.getText().toString() : "";
            String caption = etCaption.getText() != null ? etCaption.getText().toString() : "";
            int rating = (int) sliderRating.getValue();

            GeoPoint p = locationMarker.getPosition();
            String locationString = "";
            if (p != null) {
                locationString = p.getLatitude() + "," + p.getLongitude();
            }

            viewModel.submitPost(placeName, placeType, rating, caption, selectedMediaUri, locationString);
        });

        // 5. Observasi ViewModel
        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            btnSubmit.setEnabled(!isLoading);
            btnSubmit.setText(isLoading ? "Memproses..." : "Posting Ulasan");
        });

        viewModel.getIsSuccess().observe(getViewLifecycleOwner(), isSuccess -> {
            if (isSuccess) {
                Toast.makeText(getContext(), "Postingan berhasil ditambahkan!", Toast.LENGTH_SHORT).show();
                // Kembali ke Beranda setelah berhasil
                Navigation.findNavController(view).navigate(R.id.nav_home);
            }
        });

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), error -> {
            if (error != null) Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        MapView mapView = getView() != null ? getView().findViewById(R.id.mapView) : null;
        if (mapView != null) {
            mapView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        MapView mapView = getView() != null ? getView().findViewById(R.id.mapView) : null;
        if (mapView != null) {
            mapView.onPause();
        }
    }
}