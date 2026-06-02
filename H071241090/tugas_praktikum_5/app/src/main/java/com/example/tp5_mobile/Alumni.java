package com.example.tp5_mobile;

public class Alumni {
    public String id;
    public String nama;
    public String kampus;
    public String jurusan;
    public String angkatan;
    public String status;
    public String instansi;
    public String bidang;
    public String ipk;
    public int fotoRes;
    public String fotoUri; // Tambahkan untuk foto dari galeri
    public String phone;
    public String email;

    public Alumni(String nama, String kampus, String jurusan, String angkatan, String status, String instansi, String bidang, String ipk, String phone, String email, int fotoRes) {
        this.nama = nama;
        this.kampus = kampus;
        this.jurusan = jurusan;
        this.angkatan = angkatan;
        this.status = status;
        this.instansi = instansi;
        this.bidang = bidang;
        this.ipk = ipk;
        this.phone = phone;
        this.email = email;
        this.fotoRes = fotoRes;
    }

    // Constructor baru untuk foto dari galeri
    public Alumni(String nama, String kampus, String jurusan, String angkatan, String status, String instansi, String bidang, String ipk, String phone, String email, String fotoUri) {
        this.nama = nama;
        this.kampus = kampus;
        this.jurusan = jurusan;
        this.angkatan = angkatan;
        this.status = status;
        this.instansi = instansi;
        this.bidang = bidang;
        this.ipk = ipk;
        this.phone = phone;
        this.email = email;
        this.fotoUri = fotoUri;
    }
}