package test.controller;

import test.model.JadwalPengangkutan;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PengendaliJadwal {
    private static List<JadwalPengangkutan> daftarJadwal = new ArrayList<>();
    private static final List<DayOfWeek> HARI_PENGAMBILAN = 
        Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY);

    static {
        generateJadwal();
    }

    private static void generateJadwal() {
        LocalDate today = LocalDate.now();
        int rt = 1;
        
        // Generate jadwal untuk 4 minggu ke depan
        for (int minggu = 0; minggu < 4; minggu++) {
            for (DayOfWeek hari : HARI_PENGAMBILAN) {
                LocalDate tanggal = today.plusWeeks(minggu).with(TemporalAdjusters.nextOrSame(hari));
                daftarJadwal.add(new JadwalPengangkutan(
                    daftarJadwal.size() + 1,
                    "RT " + rt,
                    tanggal,
                    hari,
                    today.isAfter(tanggal) ? "Selesai" : "Diproses"
                ));
                rt = rt % 3 + 1; // Rotasi RT 1-3
            }
        }
    }

    public static List<JadwalPengangkutan> getDaftarJadwal() {
        return new ArrayList<>(daftarJadwal);
    }

    public static void perbaruiStatusOtomatis() {
        LocalDate sekarang = LocalDate.now();
        for (JadwalPengangkutan jadwal : daftarJadwal) {
            if (sekarang.isAfter(jadwal.getTanggal()) && "Diproses".equals(jadwal.getStatus())) {
                jadwal.setStatus("Selesai");
            }
        }
    }
}