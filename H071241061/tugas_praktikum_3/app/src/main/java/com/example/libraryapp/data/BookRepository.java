package com.example.libraryapp.data;

import com.example.libraryapp.R;
import com.example.libraryapp.model.Book;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookRepository {

    private static BookRepository instance;
    private ArrayList<Book> bookList;

    private BookRepository() {
        bookList = new ArrayList<>();
        loadDummyData();
    }

    public static BookRepository getInstance() {
        if (instance == null) {
            instance = new BookRepository();
        }
        return instance;
    }

    private void loadDummyData() {
        bookList.add(new Book("Laskar Pelangi", "Andrea Hirata", 2005,
                "Kisah sepuluh anak Belitung yang berjuang menggapai mimpi di tengah keterbatasan. Novel ini menjadi fenomena sastra Indonesia yang menginspirasi jutaan pembaca di seluruh dunia.",
                "Fiction", 4.8f, R.drawable.laskar_pelangi));
        bookList.add(new Book("Bumi Manusia", "Pramoedya Ananta Toer", 1980,
                "Kisah Minke, pemuda Jawa terpelajar di era kolonial Belanda. Sebuah tetralogi Buru yang mengeksplorasi tema nasionalisme, cinta, dan kemanusiaan dengan latar Hindia Belanda abad ke-20.",
                "Historical Fiction", 4.9f, R.drawable.bumi_manusia));
        bookList.add(new Book("Negeri 5 Menara", "Ahmad Fuadi", 2009,
                "Alif Fikri meninggalkan kampung halaman untuk belajar di Pondok Madani. Di sana ia bertemu 5 sahabat dari berbagai penjuru Indonesia yang bermimpi meraih bintang di langit yang berbeda.",
                "Inspirational", 4.7f, R.drawable.negeri_5_menara));
        bookList.add(new Book("Perahu Kertas", "Dee Lestari", 2009,
                "Kugy dan Keenan, dua jiwa yang bertemu di bangku kuliah. Keduanya terikat mimpi — Kugy ingin menjadi juru dongeng, Keenan ingin menjadi pelukis. Sebuah kisah cinta yang penuh makna.",
                "Romance", 4.6f, R.drawable.perahu_kertas));
        bookList.add(new Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", 1997,
                "Seorang anak yatim piatu bernama Harry Potter menemukan bahwa dirinya adalah seorang penyihir dan mulai bersekolah di Hogwarts School of Witchcraft and Wizardry.",
                "Fantasy", 4.9f, R.drawable.harry_potter));
        bookList.add(new Book("The Alchemist", "Paulo Coelho", 1988,
                "Santiago, seorang gembala Andalusia yang bermimpi tentang harta karun di Piramida Mesir. Sebuah perjalanan penuh hikmah tentang mengikuti impian dan mendengar suara alam semesta.",
                "Philosophy", 4.7f, R.drawable.the_alchemist));
        bookList.add(new Book("Atomic Habits", "James Clear", 2018,
                "Panduan praktis untuk membangun kebiasaan baik dan menghilangkan kebiasaan buruk. James Clear membuktikan bahwa perubahan kecil 1% setiap hari dapat menghasilkan hasil luar biasa.",
                "Self-Help", 4.8f, R.drawable.atomic_habits));
        bookList.add(new Book("Sapiens", "Yuval Noah Harari", 2011,
                "Sejarah singkat umat manusia dari zaman batu hingga era modern. Harari mengeksplorasi bagaimana Homo Sapiens menaklukkan dunia melalui bahasa, agama, uang, dan sains.",
                "Non-Fiction", 4.7f, R.drawable.sapiens));
        bookList.add(new Book("Dilan: Dia adalah Dilanku Tahun 1990", "Pidi Baiq", 2014,
                "Kisah cinta Milea dan Dilan di Bandung tahun 1990. Dilan, anak geng motor yang puitis, merebut hati Milea dengan cara-cara yang tak terduga dan penuh kejutan.",
                "Romance", 4.5f, R.drawable.dilan));
        bookList.add(new Book("Rich Dad Poor Dad", "Robert T. Kiyosaki", 1997,
                "Pelajaran finansial dari dua ayah dengan pandangan berbeda tentang uang. Buku ini mengubah cara pandang jutaan orang tentang investasi, aset, dan kebebasan finansial.",
                "Finance", 4.6f, R.drawable.rich_dad_poor_dad));
        bookList.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925,
                "Di tengah kemewahan New York tahun 1920-an, Jay Gatsby mengejar mimpi yang telah lama hilang. Sebuah novel tentang cinta, ambisi, dan tragedi American Dream.",
                "Classic", 4.4f, R.drawable.the_great_gatsby));
        bookList.add(new Book("Filosofi Teras", "Henry Manampiring", 2018,
                "Pengantar Stoisisme ala Indonesia. Henry menunjukkan bagaimana filsafat Yunani-Romawi kuno relevan untuk menghadapi kecemasan, amarah, dan tekanan hidup modern sehari-hari.",
                "Philosophy", 4.7f, R.drawable.filosofi_teras));
        bookList.add(new Book("1984", "George Orwell", 1949,
                "Di negara totaliter Oceania, Winston Smith berusaha melawan sistem pengawasan Big Brother yang mengontrol setiap aspek kehidupan manusia. Sebuah distopia yang mengerikan namun relevan.",
                "Dystopia", 4.8f, R.drawable.book_1984));
        bookList.add(new Book("Laut Bercerita", "Leila S. Chudori", 2017,
                "Biru Laut, mahasiswa aktivis yang menghilang di era Orde Baru. Kisah ini bergerak antara 1998 dan 2000, menyuarakan para korban penghilangan paksa dengan penuh emosi.",
                "Historical Fiction", 4.8f, R.drawable.laut_bercerita));
        bookList.add(new Book("Think and Grow Rich", "Napoleon Hill", 1937,
                "Berdasarkan riset 20 tahun terhadap 500 orang terkaya di Amerika. Hill mengidentifikasi 13 prinsip kesuksesan yang dapat diterapkan siapa saja untuk meraih kekayaan dan keberhasilan.",
                "Self-Help", 4.5f, R.drawable.think_and_grow_rich));
        bookList.add(new Book("Pulang", "Tere Liye", 2015,
                "Bujang, anak muda dari pedalaman Sumatera yang terjun ke dunia kejahatan keluarga terkuat di Asia Tenggara. Sebuah kisah aksi penuh pertarungan, kesetiaan, dan pencarian jati diri.",
                "Action", 4.6f, R.drawable.pulang));

        sortByNewest();
    }

    public void sortByNewest() {
        Collections.sort(bookList, (a, b) -> Long.compare(b.getAddedTime(), a.getAddedTime()));
    }

    public ArrayList<Book> getAllBooks() {
        return bookList;
    }

    public ArrayList<Book> getFavoriteBooks() {
        ArrayList<Book> favorites = new ArrayList<>();
        for (Book book : bookList) {
            if (book.isLiked()) favorites.add(book);
        }
        return favorites;
    }

    public Book getBookById(int id) {
        for (Book book : bookList) {
            if (book.getId() == id) return book;
        }
        return null;
    }

    public void addBook(Book book) {
        book.setAddedTime(System.currentTimeMillis());
        bookList.add(0, book);
    }

    public List<String> getAllGenres() {
        List<String> genres = new ArrayList<>();
        genres.add("All");
        for (Book book : bookList) {
            if (!genres.contains(book.getGenre())) {
                genres.add(book.getGenre());
            }
        }

        Collections.sort(genres.subList(1, genres.size()));
        return genres;
    }
}