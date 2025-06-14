package test.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.DayOfWeek;

public class JadwalPengangkutan {
    private int id;
    private String lokasi;
    private LocalDate tanggal;
    private DayOfWeek hari;
    private LocalTime jam;
    private String status;

    public JadwalPengangkutan(int id, String lokasi, LocalDate tanggal, DayOfWeek hari, String status) {
        this.id = id;
        this.lokasi = lokasi;
        this.tanggal = tanggal;
        this.hari = hari;
        this.jam = LocalTime.of(16, 0); // Tetap jam 16:00 WITA
        this.status = status;
    }

    // Getter dan Setter
    public int getId() { return id; }
    public String getLokasi() { return lokasi; }
    public LocalDate getTanggal() { return tanggal; }
    public DayOfWeek getHari() { return hari; }
    public LocalTime getJam() { return jam; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // Method helper untuk tampilan
    public String getHariString() {
        String[] namaHari = {"Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu"};
        return namaHari[hari.getValue() - 1];
    }

    public String getTanggalString() {
        return String.format("%02d/%02d/%04d", 
            tanggal.getDayOfMonth(), tanggal.getMonthValue(), tanggal.getYear());
    }

    public String getJamString() {
        return String.format("%02d:%02d WITA", jam.getHour(), jam.getMinute());
    }
}