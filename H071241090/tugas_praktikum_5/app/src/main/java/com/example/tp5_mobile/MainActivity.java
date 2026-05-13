package com.example.tp5_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity implements KampusAdapter.OnKampusClickListener, AlumniAdapter.OnAlumniDeletedListener {

    private RecyclerView rvKampus, rvAlumni;
    private AlumniAdapter alumniAdapter;
    private KampusAdapter kampusAdapter;
    private List<Alumni> displayAlumni = new ArrayList<>();
    private List<Kampus> displayKampus = new ArrayList<>();
    
    private TextView tvTotalAlumni, tvTotalKampus, tvPersenBekerja, tvGreeting;
    private Spinner spinnerAngkatan;
    private String selectedKampus = "Semua";
    private String selectedStatus = "Semua";
    private String searchQuery = "";
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferenceManager = new PreferenceManager(this);
        if (preferenceManager.isDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (tvGreeting != null) {
            tvGreeting.setText("Halo, " + preferenceManager.getUsername());
        }
        setupData();
        applyFilters();
        updateStats();
    }

    private void initViews() {
        rvKampus = findViewById(R.id.rv_kampus);
        rvAlumni = findViewById(R.id.rv_alumni);
        tvTotalAlumni = findViewById(R.id.tv_total_alumni);
        tvTotalKampus = findViewById(R.id.tv_total_kampus);
        tvPersenBekerja = findViewById(R.id.tv_persen_bekerja);
        tvGreeting = findViewById(R.id.tv_greeting);
        spinnerAngkatan = findViewById(R.id.spinner_angkatan);

        rvKampus.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvAlumni.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.btn_tambah_kampus).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TambahKampusActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.fab_add).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TambahAlumniActivity.class);
            startActivity(intent);
        });

        setupFilters();

        SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) { searchQuery = query; applyFilters(); return false; }
            @Override public boolean onQueryTextChange(String newText) { searchQuery = newText; applyFilters(); return false; }
        });
    }

    private void setupData() {
        List<Alumni> allAlumni = DataRepository.getInstance().getAlumniList();
        
        displayKampus.clear();
        displayKampus.add(new Kampus("Semua", allAlumni.size(), 0));
        displayKampus.get(0).isSelected = selectedKampus.equals("Semua");
        
        for (Kampus k : DataRepository.getInstance().getKampusList()) {
            int count = (int) allAlumni.stream().filter(a -> a.kampus.equals(k.nama)).count();
            Kampus newKampus;
            if (k.logoUri != null) {
                newKampus = new Kampus(k.nama, count, k.logoUri);
            } else {
                newKampus = new Kampus(k.nama, count, k.logoRes);
            }
            newKampus.isSelected = selectedKampus.equals(k.nama);
            displayKampus.add(newKampus);
        }

        if (kampusAdapter == null) {
            kampusAdapter = new KampusAdapter(displayKampus, this);
            rvKampus.setAdapter(kampusAdapter);
        } else {
            kampusAdapter.notifyDataSetChanged();
        }

        if (alumniAdapter == null) {
            alumniAdapter = new AlumniAdapter(new ArrayList<>(allAlumni), this);
            rvAlumni.setAdapter(alumniAdapter);
        } else {
            alumniAdapter.updateList(new ArrayList<>(allAlumni));
        }
    }

    private void setupFilters() {
        List<String> angkatan = new ArrayList<>();
        angkatan.add("Semua Angkatan");
        for (int i = 2024; i >= 1950; i--) angkatan.add(String.valueOf(i));
        ArrayAdapter<String> adapterAngkatan = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, angkatan);
        adapterAngkatan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAngkatan.setAdapter(adapterAngkatan);

        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> p, View v, int pos, long id) { applyFilters(); }
            @Override public void onNothingSelected(AdapterView<?> p) {}
        };
        spinnerAngkatan.setOnItemSelectedListener(listener);
    }

    private void applyFilters() {
        List<Alumni> allAlumni = DataRepository.getInstance().getAlumniList();
        String angkatan = spinnerAngkatan.getSelectedItem().toString();

        List<Alumni> filtered = allAlumni.stream().filter(a -> {
            boolean matchKampus = selectedKampus.equals("Semua") || a.kampus.equals(selectedKampus);
            boolean matchStatus = selectedStatus.equals("Semua") || a.status.contains(selectedStatus);
            boolean matchAngkatan = angkatan.equals("Semua Angkatan") || a.angkatan.equals(angkatan);
            boolean matchSearch = searchQuery.isEmpty() || a.nama.toLowerCase().contains(searchQuery.toLowerCase());
            return matchKampus && matchStatus && matchAngkatan && matchSearch;
        }).collect(Collectors.toList());

        if (alumniAdapter != null) alumniAdapter.updateList(filtered);
        TextView tvHeader = findViewById(R.id.tv_header_alumni);
        if (tvHeader != null) tvHeader.setText("DAFTAR ALUMNI (" + filtered.size() + ")");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onKampusClick(Kampus kampus) {
        selectedKampus = kampus.nama;
        applyFilters();
    }

    @Override
    public void onAlumniDeleted() {
        setupData();
        applyFilters();
        updateStats();
    }

    private void updateStats() {
        List<Alumni> allAlumni = DataRepository.getInstance().getAlumniList();
        tvTotalAlumni.setText(String.valueOf(allAlumni.size()));
        tvTotalKampus.setText(String.valueOf(DataRepository.getInstance().getKampusList().size()));
        long countBekerja = allAlumni.stream().filter(a -> a.status.contains("Bekerja") || a.status.contains("Wirausaha")).count();
        int persen = allAlumni.isEmpty() ? 0 : (int) ((countBekerja * 100) / allAlumni.size());
        tvPersenBekerja.setText(persen + "%");
    }
}
