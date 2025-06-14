package test.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import test.model.ForumDiskusi;
import test.model.NotifikasiJadwal;
import test.model.Pengguna;

import java.util.Optional;

public class PengendaliForum {
    private ObservableList<ForumDiskusi> daftarPost;

    public PengendaliForum() {
        this.daftarPost = FXCollections.observableArrayList();
        inisialisasiDataContoh();
    }

    private void inisialisasiDataContoh() {
        Pengguna daffa = new Pengguna("daffa", "pass123");
        Pengguna amar = new Pengguna("amar", "pass456");

        ForumDiskusi post1 = new ForumDiskusi("P001", "Jadwal Pengangkutan Sampah Baru", "Apakah ada info jadwal baru?", daffa);
        post1.tambahKomentar("Belum ada info resmi.", amar);

        ForumDiskusi post2 = new ForumDiskusi("P002", "Ide Bank Sampah", "Bagaimana kalau kita buat bank sampah di RW kita?", amar);

        daftarPost.addAll(post1, post2);
    }

    public ObservableList<ForumDiskusi> getSemuaPost() {
        return daftarPost;
    }

    public void tambahPost(String judul, String isi, Pengguna pengguna) {
        if (judul == null || isi == null || pengguna == null) {
            throw new IllegalArgumentException("Judul, isi, dan pengguna tidak boleh kosong.");
        }
        ForumDiskusi postBaru = new ForumDiskusi(judul, isi, pengguna);
        daftarPost.add(postBaru);
        NotifikasiJadwal.tampilkanInfo("Post Ditambahkan", "Post baru berhasil ditambahkan.");
    }

    public void hapusPost(ForumDiskusi post) {
        if (daftarPost.remove(post)) {
            NotifikasiJadwal.tampilkanInfo("Post Dihapus", "Post berhasil dihapus.");
        } else {
            NotifikasiJadwal.tampilkanPeringatan("Gagal", "Post tidak ditemukan.");
        }
    }

    public void tambahKomentar(String idPost, String isiKomentar, Pengguna penulis) {
        Optional<ForumDiskusi> post = daftarPost.stream()
                .filter(p -> p.getIdPost().equals(idPost))
                .findFirst();

        if (post.isPresent()) {
            post.get().tambahKomentar(isiKomentar, penulis);
            NotifikasiJadwal.tampilkanInfo("Komentar Ditambahkan", "Komentar berhasil ditambahkan.");
        } else {
            NotifikasiJadwal.tampilkanPeringatan("Post Tidak Ditemukan", "ID post tidak ditemukan.");
        }
    }

    public void updatePost(ForumDiskusi post, String judulBaru, String isiBaru) {
        if (post != null && judulBaru != null && isiBaru != null) {
            post.setJudul(judulBaru);
            post.setIsiPost(isiBaru);
            NotifikasiJadwal.tampilkanInfo("Post Diperbarui", "Post berhasil diperbarui.");
        } else {
            NotifikasiJadwal.tampilkanPeringatan("Input Tidak Valid", "Judul dan isi tidak boleh kosong.");
        }
    }
}
