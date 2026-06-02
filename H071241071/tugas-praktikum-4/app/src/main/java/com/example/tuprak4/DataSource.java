package com.example.tuprak4;

import java.util.ArrayList;

public class DataSource {
    public static ArrayList<Book> books = new ArrayList<>();

    public static void initDummyData() {
        if (books.isEmpty()) {
            books.add(new Book("1", "Cerita dari Negeri Dongeng", "Sacha", "2005", "Lorem ipsum dolor sit amet.", R.drawable.book1, 4.8f, "Novel"));
            books.add(new Book("2", "Cara Menjadi Pengusaha Sukses", "Anonym", "1980", "Lorem ipsum dolor sit amet.", R.drawable.book2, 4.9f, "Sejarah"));
            books.add(new Book("3", "Good Dog", "Jonathan Patterson", "2009", "Lorem ipsum dolor sit amet.", R.drawable.book3, 4.7f, "Novel"));
            books.add(new Book("4", "Kisah Kasih Senja", "Lorna Alvarado", "2004", "Lorem ipsum dolor sit amet.", R.drawable.book4, 4.6f, "Romansa"));
            books.add(new Book("5", "Surat untuk Senja", "Soo Jin Ae", "2009", "Lorem ipsum dolor sit amet.", R.drawable.book5, 4.5f, "Fiksi"));
            books.add(new Book("6", "Bahagia Itu Sederhana", "Francois Mercer", "2006", "Lorem ipsum dolor sit amet.", R.drawable.book6, 4.4f, "Fiksi"));
            books.add(new Book("7", "Gurun Pasir", "Lars Peeters", "2012", "Lorem ipsum dolor sit amet.", R.drawable.book7, 4.6f, "Fiksi Sejarah"));
            books.add(new Book("8", "Si Anak Spesial", "Olivia Wilson", "2016", "Lorem ipsum dolor sit amet.", R.drawable.book8, 4.7f, "Sci-Fi"));
            books.add(new Book("9", "Salam Sayang", "Renata Airlangga", "2014", "Lorem ipsum dolor sit amet.", R.drawable.book9, 4.8f, "Fantasi"));
            books.add(new Book("10", "Senja", "Cahaya Dewi", "2012", "Lorem ipsum dolor sit amet.", R.drawable.book10, 4.8f, "Sejarah"));
            books.add(new Book("11", "Gerimis Pagi", "Ketut Susilo", "2017", "Lorem ipsum dolor sit amet.", R.drawable.book11, 4.9f, "Fiksi Sejarah"));
            books.add(new Book("12", "Perahu Kertas", "Cahaya Dewi", "2014", "Lorem ipsum dolor sit amet.", R.drawable.book12, 4.5f, "Romansa"));
            books.add(new Book("13", "Ayah di Setiap Langkahku", "Olivia Wilson", "1982", "Lorem ipsum dolor sit amet.", R.drawable.book13, 4.7f, "Budaya"));
            books.add(new Book("14", "Ayahku Pahlawanku", "Olivia Wilson", "2002", "Lorem ipsum dolor sit amet.", R.drawable.book14, 4.8f, "Fiksi"));
            books.add(new Book("15", "Gatau Bahasa Arab", "Orang Arab", "2016", "Lorem ipsum dolor sit amet.", R.drawable.book15, 4.3f, "Fiksi"));
        }
    }
}