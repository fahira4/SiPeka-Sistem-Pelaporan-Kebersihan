package test.controller;

import test.database.RankingRTDAO;
import test.model.Gamifikasi;
import test.model.Pengguna;
import test.model.RT;
import test.model.NotifikasiJadwal;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class PengendaliGamifikasi {
    private final Gamifikasi gamifikasi;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final Runnable updateTask;

    public PengendaliGamifikasi(Gamifikasi gamifikasi) {
        if (gamifikasi == null) {
            throw new IllegalArgumentException("Objek gamifikasi tidak boleh null");
        }
        this.gamifikasi = gamifikasi;
        
        this.updateTask = () -> {
            // Logika pembaruan real-time
            updateRanking();
            checkAchievements();
        };
        
        // Jadwalkan pembaruan setiap 5 menit
        scheduler.scheduleAtFixedRate(updateTask, 0, 5, TimeUnit.MINUTES);
    }

    public void tambahPoin(Pengguna penggunaAktif, int poin, String aktivitas) {
        if (poin <= 0) {
            NotifikasiJadwal.tampilkanPeringatan("Invalid Input", "Poin harus lebih besar dari 0");
            return;
        }
        
        gamifikasi.tambahPoin(poin);
        gamifikasi.tambahLaporan();
        
        String notif = String.format(
            "+%d Poin untuk %s\nTotal Poin: %d\nLevel: %d",
            poin, aktivitas, gamifikasi.getPoin(), gamifikasi.getLevel()
        );
        
        NotifikasiJadwal.tampilkanInfo("Poin Bertambah!", notif);
    }

    private void updateRanking() {
        // Implementasi pembaruan ranking dari database
        // Akan dipanggil secara periodic oleh scheduler
    }

    private void checkAchievements() {
        // Cek pencapaian baru berdasarkan poin
        int poin = gamifikasi.getPoin();
        if (poin >= 100 && poin < 200 && !gamifikasi.getBadges().contains("🥉 Pelapor Aktif")) {
            gamifikasi.setBadge("🥉 Pelapor Aktif");
            NotifikasiJadwal.tampilkanInfo("Achievement Unlocked!", "Anda mendapatkan badge baru: 🥉 Pelapor Aktif");
        }
        // Tambahkan kondisi achievement lainnya
    }

    public void shutdown() {
        scheduler.shutdown();
    }

    public ObservableList<RT> getRankingRT() {
        // Implementasi pengambilan data ranking dari database
        RankingRTDAO rankingRTDAO = new RankingRTDAO();
        return rankingRTDAO.getRanking();
    }

    public static List<Gamifikasi> getRankingByRT(String userRT) {
        RankingRTDAO rankingRTDAO = new RankingRTDAO();
        List<RT> semuaData = rankingRTDAO.getRanking(); // Assumes this method exists and returns List<RT>
        
        if (semuaData == null || userRT == null) {
            return new ArrayList<>();
        }

        return semuaData.stream()
                .filter(data -> userRT.equalsIgnoreCase(data.getNama()))  // Null-safe
                .sorted(Comparator.comparingInt(RT::getPoin).reversed())
                .map(rt -> {
                    Gamifikasi gamifikasi = new Gamifikasi();
                    gamifikasi.setPoin(rt.getPoin());
                    gamifikasi.setRtName(rt.getNama());
                    // Set other fields as needed
                    return gamifikasi;
                })
                .collect(Collectors.toList());
    }
}