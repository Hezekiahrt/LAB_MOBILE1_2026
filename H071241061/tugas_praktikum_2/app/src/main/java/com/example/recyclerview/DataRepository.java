package com.example.recyclerview;

import com.example.recyclerview.model.Post;
import com.example.recyclerview.model.Story;

import java.util.ArrayList;

public class DataRepository {

    public static ArrayList<Post>  homeList      = new ArrayList<>();
    public static ArrayList<Story> highlightList = new ArrayList<>();
    public static ArrayList<Story> storyHomeList = new ArrayList<>();

    public static void initData() {
        if (!homeList.isEmpty()) return;

        homeList.add(new Post(R.drawable.profilsuci,  "With my bestieee 🥰",            "sucisriaulia_",   R.drawable.profilsuci));
        homeList.add(new Post(R.drawable.profilnayla, "Morning coffee ritual ☕",         "naylafazhh",      R.drawable.profilnayla));
        homeList.add(new Post(R.drawable.profileryn,  "Summit 🏔️",                      "erynnalsya",      R.drawable.profileryn));
        homeList.add(new Post(R.drawable.profilsarah, "CityLight 🌃",                    "srhsblaa",        R.drawable.profilsarah));
        homeList.add(new Post(R.drawable.profilirul,  "Kuliyeah 🎓",                     "_rullw",          R.drawable.profilirul));
        homeList.add(new Post(R.drawable.profildhiva, "Happy Sunday 🌸",                 "dhivasaa",        R.drawable.profildhiva));
        homeList.add(new Post(R.drawable.profilnanda, "Long time no see 😊",             "anandawahyuu",    R.drawable.profilnanda));
        homeList.add(new Post(R.drawable.profilomar,  "New Year New Me ✨",              "silvetersts",     R.drawable.profilomar));
        homeList.add(new Post(R.drawable.profilerly,  "Makassar Sunset 🌅",              "rlyy.zz",         R.drawable.profilerly));
        homeList.add(new Post(R.drawable.profilunhas, "Welcome to Hasanuddin University!", "hasanuddin_univ", R.drawable.profilunhas));

        storyHomeList.add(new Story(R.drawable.profilsuci,  "sucisriaulia_"));
        storyHomeList.add(new Story(R.drawable.profilnayla, "naylafazhh"));
        storyHomeList.add(new Story(R.drawable.profileryn,  "erynnalsya"));
        storyHomeList.add(new Story(R.drawable.profilsarah, "srhsblaa"));
        storyHomeList.add(new Story(R.drawable.profilirul,  "_rullw"));
        storyHomeList.add(new Story(R.drawable.profildhiva, "dhivasaa"));
        storyHomeList.add(new Story(R.drawable.profilnanda, "anandawahyuu"));
        storyHomeList.add(new Story(R.drawable.profilomar,  "silvetersts"));
        storyHomeList.add(new Story(R.drawable.profilerly,  "rlyy.zz"));
        storyHomeList.add(new Story(R.drawable.profilunhas, "hasanuddin_univ"));

        highlightList.add(new Story(R.drawable.highlightbali,      "Bali"));
        highlightList.add(new Story(R.drawable.highlightlombok,    "Lombok"));
        highlightList.add(new Story(R.drawable.highlightrajaampat, "Raja Ampat"));
        highlightList.add(new Story(R.drawable.highlightbromo,     "Bromo"));
        highlightList.add(new Story(R.drawable.highlightbajo,      "Labuan Bajo"));
        highlightList.add(new Story(R.drawable.highlightkomodo,    "Komodo"));
        highlightList.add(new Story(R.drawable.highlightwakatobi,  "Wakatobi"));
    }

    public static ArrayList<Post> getProfilePosts() {
        ArrayList<Post> list = new ArrayList<>();
        list.add(new Post(R.drawable.post1, "Hi 👋",                  "naailamzya", R.drawable.profil));
        list.add(new Post(R.drawable.post2, "Morning coffee ☕",       "naailamzya", R.drawable.profil));
        list.add(new Post(R.drawable.post3, "Hiking ke puncak 🏔️",   "naailamzya", R.drawable.profil));
        list.add(new Post(R.drawable.post4, "Street food hunting 🍜", "naailamzya", R.drawable.profil));
        list.add(new Post(R.drawable.post5, "Golden hour vibes ✨",   "naailamzya", R.drawable.profil));
        return list;
    }

    public static ArrayList<Story> getStories() {
        return highlightList;
    }

    public static ArrayList<Post> getPostsByUsername(String username) {
        ArrayList<Post> list = new ArrayList<>();

        int photoRes = R.drawable.ic_profile;
        for (Post p : homeList) {
            if (p.getUsername().equals(username)) {
                photoRes = p.getProfileImageResId();
                break;
            }
        }

        final int finalPhotoRes = photoRes;
        list.add(new Post(finalPhotoRes, "Hai semua! 👋",            username, finalPhotoRes));
        list.add(new Post(finalPhotoRes, "Hari yang menyenangkan ☀️", username, finalPhotoRes));
        list.add(new Post(finalPhotoRes, "Foto terbaru 📸",           username, finalPhotoRes));

        return list;
    }

    public static ArrayList<Story> getStoriesByUsername(String username) {
        ArrayList<Story> list = new ArrayList<>();

        for (Story s : storyHomeList) {
            if (s.getLabel().equals(username)) {
                list.add(new Story(s.getImageResId(), "Story 1"));
                list.add(new Story(s.getImageResId(), "Story 2"));
                list.add(new Story(s.getImageResId(), "Story 3"));
                break;
            }
        }
        return list;
    }
}