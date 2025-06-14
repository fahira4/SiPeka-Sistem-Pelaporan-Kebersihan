package test.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AcaraEdukasi {
    private String idAcara;
    private String judul;
    private String deskripsi;
    private String lokasi;
    private LocalDateTime waktuAcara;

    public AcaraEdukasi(String idAcara, String judul, String deskripsi, String lokasi, LocalDateTime waktuAcara) {
        this.idAcara = idAcara;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.lokasi = lokasi;
        this.waktuAcara = waktuAcara;
    }

    // Getters
    public String getIdAcara() {
        return idAcara;
    }

    public String getJudul() {
        return judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getLokasi() {
        return lokasi;
    }

    public LocalDateTime getWaktuAcara() {
        return waktuAcara;
    }

    /**
     * Mengembalikan representasi String untuk ditampilkan di UI.
     * @return String format "Judul - Tanggal & Waktu di Lokasi"
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm");
        return String.format("%s - [%s di %s]", judul, waktuAcara.format(formatter), lokasi);
    }
}