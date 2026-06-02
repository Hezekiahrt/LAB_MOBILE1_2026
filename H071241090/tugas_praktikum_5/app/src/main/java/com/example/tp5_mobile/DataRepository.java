package com.example.tp5_mobile;

import java.util.ArrayList;
import java.util.List;

public class DataRepository {
    private static DataRepository instance;
    private List<Alumni> alumniList = new ArrayList<>();
    private List<Kampus> kampusList = new ArrayList<>();

    private DataRepository() {
        initDummyData();
    }

    public static synchronized DataRepository getInstance() {
        if (instance == null) {
            instance = new DataRepository();
        }
        return instance;
    }

    private void initDummyData() {
        // Reset data
        alumniList.clear();
        kampusList.clear();

        // 1. Nicholas Saputra (UI)
        alumniList.add(new Alumni("Nicholas Saputra", "UI", "Arsitektur", "2002", "Bekerja", "Aktor/Produser", "Seni & Hiburan", "3.75", "08123456789", "nicsap@example.com", R.drawable.foto_nicholas));
        
        // 2. Najwa Shihab (UI)
        alumniList.add(new Alumni("Najwa Shihab", "UI", "Hukum", "1996", "Bekerja", "Narasi TV", "Media", "3.85", "08129876543", "najwa@narasi.tv", R.drawable.foto_najwa));
        
        // 3. Jusuf Kalla (Unhas)
        alumniList.add(new Alumni("Jusuf Kalla", "Unhas", "Ekonomi", "1960", "Bekerja", "Kalla Group", "Bisnis & Pemerintahan", "3.60", "08111222333", "jk@kalla.com", R.drawable.foto_jk));
        
        // 4. Anies Baswedan (UGM)
        alumniList.add(new Alumni("Anies Baswedan", "UGM", "Ekonomi", "1989", "Bekerja", "Akademisi/Politisi", "Pemerintahan", "3.80", "081333444555", "anies@example.com", R.drawable.foto_anies));
        
        // 5. Ciputra (ITB)
        alumniList.add(new Alumni("Ciputra", "ITB", "Arsitektur", "1950", "Wirausaha", "Ciputra Group", "Properti", "3.70", "081555666777", "ciputra@ciputra.com", R.drawable.foto_ciputra));
        
        // 6. Hamdan Zoelva (Unhas)
        alumniList.add(new Alumni("Hamdan Zoelva", "Unhas", "Hukum", "1981", "Bekerja", "Eks Ketua MK", "Hukum", "3.82", "08122334455", "hamdan@zoelva.com", R.drawable.foto_hamdan));

        // Default Kampus dengan Logo
        kampusList.add(new Kampus("UI", 0, R.drawable.logo_ui));
        kampusList.add(new Kampus("ITB", 0, R.drawable.logo_itb));
        kampusList.add(new Kampus("UGM", 0, R.drawable.logo_ugm));
        kampusList.add(new Kampus("Unhas", 0, R.drawable.logo_unhas));
    }

    public List<Alumni> getAlumniList() {
        return alumniList;
    }

    public void addAlumni(Alumni alumni) {
        alumniList.add(0, alumni);
    }

    public void removeAlumni(Alumni alumni) {
        alumniList.remove(alumni);
    }

    public List<Kampus> getKampusList() {
        return kampusList;
    }

    public void addKampus(Kampus kampus) {
        kampusList.add(kampus);
    }
}