package com.example.library;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DataStore {
    private static DataStore instance;
    private final List<Book> bookList;

    private DataStore() {
        bookList = new CopyOnWriteArrayList<>();
        // Inisialisasi minimal 15 buku dummy
        bookList.add(new Book("1", "Sistem Informasi Manajemen", "Budi Santoso", 2026, "Panduan implementasi sistem informasi modern.", "", "Pendidikan", 4.8));
        bookList.add(new Book("2", "Keamanan Jaringan", "Andi Wijaya", 2025, "Dasar-dasar Nmap, Snort, dan keamanan server.", "", "Teknologi", 4.5));
        bookList.add(new Book("3", "Pemrograman Java", "Eko Kurniawan", 2024, "Belajar Java dari dasar hingga mahir.", "", "Teknologi", 4.7));
        bookList.add(new Book("4", "Struktur Data", "Siti Aminah", 2023, "Konsep Stack, Queue, dan Tree dalam pemrograman.", "", "Pendidikan", 4.6));
        bookList.add(new Book("5", "Basis Data SQL", "Rahmat Hidayat", 2025, "Optimasi query dan perancangan database.", "", "Teknologi", 4.4));
        bookList.add(new Book("6", "Kecerdasan Buatan", "Dr. Lukman", 2026, "Pengantar Machine Learning dan Neural Networks.", "", "Teknologi", 4.9));
        bookList.add(new Book("7", "Psikologi Populer", "Indah Sari", 2022, "Memahami perilaku manusia di era digital.", "", "Sosial", 4.3));
        bookList.add(new Book("8", "Ekonomi Syariah", "Zainal Abidin", 2023, "Prinsip dasar ekonomi berbasis syariah.", "", "Ekonomi", 4.5));
        bookList.add(new Book("9", "Sejarah Dunia", "Bambang Susilo", 2021, "Peristiwa penting yang mengubah peradaban.", "", "Sejarah", 4.2));
        bookList.add(new Book("10", "Resep Masakan Nusantara", "Chef Renata", 2024, "Kumpulan resep masakan tradisional Indonesia.", "", "Kuliner", 4.8));
        bookList.add(new Book("11", "Desain Grafis", "Aris Setiawan", 2025, "Panduan menggunakan Adobe Illustrator dan Photoshop.", "", "Seni", 4.6));
        bookList.add(new Book("12", "Fisika Modern", "Prof. Yohanes", 2026, "Teori relativitas dan mekanika kuantum.", "", "Sains", 4.7));
        bookList.add(new Book("13", "Astronomi Dasar", "Dewi Lestari", 2023, "Mengenal tata surya dan galaksi jauh.", "", "Sains", 4.4));
        bookList.add(new Book("14", "Manajemen Proyek", "Hendra Wijaya", 2024, "Metode Agile dan Scrum untuk tim IT.", "", "Bisnis", 4.5));
        bookList.add(new Book("15", "Filosofi Teras", "Henry Manampiring", 2022, "Penerapan Stoikisme dalam kehidupan sehari-hari.", "", "Filsafat", 4.9));
    }

    public static synchronized DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    public List<Book> getBooks() {
        return bookList;
    }

    public void addBook(Book book) {
        // Menambahkan di index 0 agar buku terbaru tampil di urutan atas
        bookList.add(0, book);
    }
}
