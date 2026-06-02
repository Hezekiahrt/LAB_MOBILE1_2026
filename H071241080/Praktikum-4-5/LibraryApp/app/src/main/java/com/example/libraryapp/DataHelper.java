package com.example.libraryapp;

import com.example.libraryapp.model.Book;
import java.util.ArrayList;
import java.util.List;

public class DataHelper {
    private static List<Book> bookList = null;

    public static List<Book> getBooks() {
        if (bookList == null) {
            bookList = new ArrayList<>();

            Book b1 = new Book("Bumi", "Tere Liye", "2014", "Raib, seorang gadis 15 tahun, memiliki kemampuan menghilang yang misterius.", "Fantasi", 4.5f);
            b1.setDrawableResId(R.drawable.bumi);

            Book b2 = new Book("Bulan", "Tere Liye", "2015", "Petualangan Ali dan kawan-kawan berlanjut ke dunia paralel yang lebih berbahaya.", "Fantasi", 4.4f);
            b2.setDrawableResId(R.drawable.bulan);

            Book b3 = new Book("Matahari", "Tere Liye", "2016", "Seri ketiga petualangan Raib menghadapi ancaman yang lebih besar.", "Fantasi", 4.3f);
            b3.setDrawableResId(R.drawable.matahari);

            Book b4 = new Book("Laskar Pelangi", "Andrea Hirata", "2005", "Kisah perjuangan 10 anak Belitung dalam meraih pendidikan di tengah keterbatasan.", "Fiksi", 4.8f);
            b4.setDrawableResId(R.drawable.laskarpelangi);

            Book b5 = new Book("Sang Pemimpi", "Andrea Hirata", "2006", "Ikal dan Arai bermimpi besar untuk mengubah nasib mereka.", "Fiksi", 4.6f);
            b5.setDrawableResId(R.drawable.sangpemimpi);

            Book b6 = new Book("Negeri 5 Menara", "Ahmad Fuadi", "2009", "Alif meninggalkan kampung untuk belajar di pesantren dan menggapai dunia.", "Inspirasi", 4.7f);
            b6.setDrawableResId(R.drawable.negeri5menara);

            Book b7 = new Book("Perahu Kertas", "Dee Lestari", "2009", "Kugy dan Keenan, dua jiwa seni yang bertemu dan saling mengubah hidup.", "Romansa", 4.5f);
            b7.setDrawableResId(R.drawable.perahukertas);

            Book b8 = new Book("Supernova: Ksatria", "Dee Lestari", "2001", "Ferre dan Rana terjebak dalam hubungan yang mengubah pandangan mereka tentang cinta.", "Fiksi Ilmiah", 4.4f);
            b8.setDrawableResId(R.drawable.supernova);

            Book b9 = new Book("Dilan 1990", "Pidi Baiq", "2014", "Kisah cinta Milea dan Dilan, sang ketua geng motor yang puitis.", "Romansa", 4.3f);
            b9.setDrawableResId(R.drawable.dilan1990);

            Book b10 = new Book("Ayah", "Andrea Hirata", "2015", "Kisah seorang ayah yang berjuang demi putrinya dengan cara yang unik.", "Drama", 4.5f);
            b10.setDrawableResId(R.drawable.ayah);

            Book b11 = new Book("Sejarah Tuhan", "Karen Armstrong", "1993", "Eksplorasi mendalam tentang konsep Tuhan dalam tiga agama monoteisme.", "Non-fiksi", 4.6f);
            b11.setDrawableResId(R.drawable.sejarahtuhan);

            Book b12 = new Book("Sapiens", "Yuval Noah Harari", "2011", "Sejarah singkat umat manusia dari zaman prasejarah hingga era modern.", "Non-fiksi", 4.8f);
            b12.setDrawableResId(R.drawable.sapiens);

            Book b13 = new Book("Atomic Habits", "James Clear", "2018", "Panduan membangun kebiasaan kecil yang menghasilkan perubahan luar biasa.", "Pengembangan Diri", 4.9f);
            b13.setDrawableResId(R.drawable.atomichabits);

            Book b14 = new Book("The Alchemist", "Paulo Coelho", "1988", "Santiago, seorang gembala muda, mengikuti mimpinya melintasi gurun Sahara.", "Fiksi", 4.7f);
            b14.setDrawableResId(R.drawable.thealchemist);

            Book b15 = new Book("Harry Potter 1", "J.K. Rowling", "1997", "Harry Potter menemukan dirinya adalah seorang penyihir dan memulai petualangan di Hogwarts.", "Fantasi", 4.9f);
            b15.setDrawableResId(R.drawable.harrypotter);

            Book b16 = new Book("Clean Code", "Robert C. Martin", "2008", "Panduan menulis kode yang bersih, mudah dibaca, dan dipelihara.", "Teknologi", 4.7f);
            b16.setDrawableResId(R.drawable.cleancode);

            Book b17 = new Book("Rich Dad Poor Dad", "Robert Kiyosaki", "1997", "Pelajaran tentang uang dan investasi dari dua sosok ayah yang berbeda.", "Keuangan", 4.5f);
            b17.setDrawableResId(R.drawable.richdadpoordad);

            bookList.add(b1); bookList.add(b2); bookList.add(b3);
            bookList.add(b4); bookList.add(b5); bookList.add(b6);
            bookList.add(b7); bookList.add(b8); bookList.add(b9);
            bookList.add(b10); bookList.add(b11); bookList.add(b12);
            bookList.add(b13); bookList.add(b14); bookList.add(b15);
            bookList.add(b16); bookList.add(b17);
        }
        return bookList;
    }

    public static void addBook(Book book) {
        getBooks().add(0, book);
    }
}