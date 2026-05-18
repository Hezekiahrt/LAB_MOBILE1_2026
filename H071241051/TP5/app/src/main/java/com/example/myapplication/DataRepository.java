package com.example.myapplication;

import android.net.Uri;
import java.util.ArrayList;
import java.util.List;

public class DataRepository {
    private static DataRepository instance;
    private List<Post> allPosts;
    private List<Post> userPosts;
    private List<Story> userStories;
    private User currentUser;

    private DataRepository() {
        allPosts = new ArrayList<>();
        userPosts = new ArrayList<>();
        userStories = new ArrayList<>();
        
        // Profile image dummy from drawable
        currentUser = new User("rey", "hezekiah_rt", "Suka Suka", R.drawable.alex_smith);

        // Dummy Highlights (min 7) menggunakan gambar dari drawable
        userStories.add(new Story("Holiday", R.drawable.traveler_pro));
        userStories.add(new Story("Food", R.drawable.chef_master));
        userStories.add(new Story("Workout", R.drawable.fitness_freak));
        userStories.add(new Story("Nature", R.drawable.nature_lover));
        userStories.add(new Story("Coding", R.drawable.tech_guru));
        userStories.add(new Story("Music", R.drawable.music_fan));
        userStories.add(new Story("Art", R.drawable.art_daily));

        // Dummy Home Feed (min 10) menggunakan gambar dari drawable
        allPosts.add(new Post("alex_smith", R.drawable.alex_smith, R.drawable.alex_smith, "Menikmati senja di pantai. #sunset #vibes"));
        allPosts.add(new Post("jane_doe", R.drawable.jane_doe, R.drawable.jane_doe, "Makan siang yang luar biasa hari ini!"));
        allPosts.add(new Post("nature_lover", R.drawable.nature_lover, R.drawable.nature_lover, "Keindahan alam yang tak ternilai."));
        allPosts.add(new Post("tech_guru", R.drawable.tech_guru, R.drawable.tech_guru, "Review gadget terbaru ada di link bio!"));
        allPosts.add(new Post("fitness_freak", R.drawable.fitness_freak, R.drawable.fitness_freak, "Jangan menyerah pada impianmu. #workout"));
        allPosts.add(new Post("traveler_pro", R.drawable.traveler_pro, R.drawable.traveler_pro, "Eksplorasi kota tua yang penuh sejarah."));
        allPosts.add(new Post("chef_master", R.drawable.chef_master, R.drawable.chef_master, "Resep kue coklat lumer sudah tayang!"));
        allPosts.add(new Post("art_daily", R.drawable.art_daily, R.drawable.art_daily, "Sketsa hari ini: Wajah dalam keramaian."));
        allPosts.add(new Post("movie_buff", R.drawable.movie_buff, R.drawable.movie_buff, "Film ini benar-hearted! Wajib nonton."));
        allPosts.add(new Post("music_fan", R.drawable.music_fan, R.drawable.music_fan, "Konser semalam pecah banget!"));

        // Dummy User Feed (min 5)
        userPosts.add(new Post(currentUser.getUsername(), currentUser.getProfileImageRes(), R.drawable.traveler_pro, "Postingan pertama saya di aplikasi ini!"));
        userPosts.add(new Post(currentUser.getUsername(), currentUser.getProfileImageRes(), R.drawable.nature_lover, "Lagi belajar Android Development. Seru!"));
        userPosts.add(new Post(currentUser.getUsername(), currentUser.getProfileImageRes(), R.drawable.fitness_freak, "Kopi pagi ini terasa berbeda."));
        userPosts.add(new Post(currentUser.getUsername(), currentUser.getProfileImageRes(), R.drawable.chef_master, "Weekend yang produktif."));
        userPosts.add(new Post(currentUser.getUsername(), currentUser.getProfileImageRes(), R.drawable.art_daily, "Persiapan project baru. Bismillah."));

        // Gabungkan ke allPosts
        allPosts.addAll(0, userPosts);
    }

    public static synchronized DataRepository getInstance() {
        if (instance == null) {
            instance = new DataRepository();
        }
        return instance;
    }

    public List<Post> getAllPosts() { return allPosts; }
    public List<Post> getUserPosts() { return userPosts; }
    public List<Story> getUserStories() { return userStories; }
    public User getCurrentUser() { return currentUser; }

    public void addPost(Post post) {
        userPosts.add(0, post);
        allPosts.add(0, post);
    }

    public void updateCurrentUser(String name, String username, String bio) {
        if (currentUser != null) {
            currentUser.setName(name);
            currentUser.setUsername(username);
            currentUser.setBio(bio);
        }
    }
}