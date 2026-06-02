package com.example.praktikum6.model;

public class UserModel {
    private String name;
    private String nim;
    private String email;
    private String password;
    private String jurusan;
    private String semester;
    private String ipk;
    private String phone;
    private boolean isLoggedIn;

    public UserModel() {}

    public UserModel(String name, String nim, String email, String password,
                     String jurusan, String semester, String ipk, String phone) {
        this.name = name;
        this.nim = nim;
        this.email = email;
        this.password = password;
        this.jurusan = jurusan;
        this.semester = semester;
        this.ipk = ipk;
        this.phone = phone;
        this.isLoggedIn = false;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getNim() { return nim; }
    public void setNim(String nim) { this.nim = nim; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getJurusan() { return jurusan; }
    public void setJurusan(String jurusan) { this.jurusan = jurusan; }

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }

    public String getIpk() { return ipk; }
    public void setIpk(String ipk) { this.ipk = ipk; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public boolean isLoggedIn() { return isLoggedIn; }
    public void setLoggedIn(boolean loggedIn) { isLoggedIn = loggedIn; }
}
