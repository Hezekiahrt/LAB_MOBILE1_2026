package com.example.tp5_mobile;

public class Kampus {
    public String nama;
    public int jumlahAlumni;
    public int logoRes;
    public String logoUri; // Gunakan String URI untuk gambar dari galeri
    public boolean isSelected = false;

    public Kampus(String nama, int jumlahAlumni, int logoRes) {
        this.nama = nama;
        this.jumlahAlumni = jumlahAlumni;
        this.logoRes = logoRes;
    }

    public Kampus(String nama, int jumlahAlumni, String logoUri) {
        this.nama = nama;
        this.jumlahAlumni = jumlahAlumni;
        this.logoUri = logoUri;
    }
}
