package test.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ForumDiskusi {
    private String idPost;
    private String judul;
    private String isiPost;
    private Pengguna penulis;
    private LocalDateTime waktuPost;
    private List<String> komentar;

    public ForumDiskusi(String idPost, String judul, String isiPost, Pengguna penulis) {
        this.idPost = idPost;
        this.judul = judul;
        this.isiPost = isiPost;
        this.penulis = penulis;
        this.waktuPost = LocalDateTime.now();
        this.komentar = new ArrayList<>();
    }

    // Constructor untuk auto ID (misalnya saat tambahPost)
    public ForumDiskusi(String judul, String isiPost, Pengguna penulis) {
        this.idPost = "P" + System.currentTimeMillis(); // ID unik berdasarkan waktu
        this.judul = judul;
        this.isiPost = isiPost;
        this.penulis = penulis;
        this.waktuPost = LocalDateTime.now();
        this.komentar = new ArrayList<>();
    }

    public String getIdPost() {
        return idPost;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getIsiPost() {
        return isiPost;
    }

    public void setIsiPost(String isiPost) {
        this.isiPost = isiPost;
    }

    public Pengguna getPenulis() {
        return penulis;
    }

    public LocalDateTime getWaktuPost() {
        return waktuPost;
    }

    public List<String> getKomentar() {
        return komentar;
    }

    public void tambahKomentar(String isiKomentar, Pengguna pengomentar) {
        this.komentar.add(pengomentar.getUsername() + ": " + isiKomentar);
    }

    @Override
    public String toString() {
        return String.format("%s (oleh %s)", judul, penulis.getUsername());
    }
}
