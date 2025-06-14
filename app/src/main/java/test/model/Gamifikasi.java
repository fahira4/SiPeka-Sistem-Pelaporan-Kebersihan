package test.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.ArrayList;
import java.util.List;

public class Gamifikasi {
    private final StringProperty userId = new SimpleStringProperty();
    private final IntegerProperty poin = new SimpleIntegerProperty();
    private final IntegerProperty level = new SimpleIntegerProperty();
    private final StringProperty badge = new SimpleStringProperty();
    private final List<String> badges = new ArrayList<>();
    private final IntegerProperty laporanBulanIni = new SimpleIntegerProperty();
    private final IntegerProperty laporanMingguIni = new SimpleIntegerProperty();
    private final StringProperty rtId = new SimpleStringProperty();
    private final StringProperty rtName = new SimpleStringProperty();

    public Gamifikasi() {
        this.poin.set(0);
        this.level.set(1);
        this.laporanBulanIni.set(0);
        this.laporanMingguIni.set(0);
    }

    // Property getters
    public StringProperty userIdProperty() { return userId; }
    public IntegerProperty poinProperty() { return poin; }
    public IntegerProperty levelProperty() { return level; }
    public StringProperty badgeProperty() { return badge; }
    public IntegerProperty laporanBulanIniProperty() { return laporanBulanIni; }
    public IntegerProperty laporanMingguIniProperty() { return laporanMingguIni; }
    public StringProperty rtIdProperty() { return rtId; }
    public StringProperty rtNameProperty() { return rtName; }

    // Regular getters and setters
    public String getUserId() { return userId.get(); }
    public int getPoin() { return poin.get(); }
    public int getLevel() { return level.get(); }

    // ✅ Getter ini dihitung ulang berdasarkan poin terkini
    public String getBadge() { return calculateBadge(this.poin.get()); }

    public List<String> getBadges() { return new ArrayList<>(badges); }
    public int getLaporanBulanIni() { return laporanBulanIni.get(); }
    public int getLaporanMingguIni() { return laporanMingguIni.get(); }
    public String getRtId() { return rtId.get(); }
    public String getRtName() { return rtName.get(); }

    public void setUserId(String userId) { this.userId.set(userId); }
    public void setPoin(int poin) {
        this.poin.set(poin);
        updateLevel();
    }
    public void setLevel(int level) { this.level.set(level); }

    // Setter badge tetap ada jika kamu ingin manual
    public void setBadge(String badge) { this.badge.set(badge); }

    public void setBadges(List<String> badges) {
        this.badges.clear();
        this.badges.addAll(badges);
    }

    public void setLaporanBulanIni(int laporanBulanIni) { this.laporanBulanIni.set(laporanBulanIni); }
    public void setLaporanMingguIni(int laporanMingguIni) { this.laporanMingguIni.set(laporanMingguIni); }
    public void setRtId(String rtId) { this.rtId.set(rtId); }
    public void setRtName(String rtName) { this.rtName.set(rtName); }

    // Helper methods
    private void updateLevel() {
        int newLevel = calculateLevel(this.poin.get());
        if (newLevel != this.level.get()) {
            this.level.set(newLevel);
            updateBadge();
        }
    }

    private void updateBadge() {
        String newBadge = calculateBadge(this.poin.get());
        this.badge.set(newBadge);
        if (!badges.contains(newBadge)) {
            badges.add(newBadge);
        }
    }

    private int calculateLevel(int poin) {
        if (poin >= 300) return 4;
        else if (poin >= 200) return 3;
        else if (poin >= 100) return 2;
        else return 1;
    }

    private String calculateBadge(int poin) {
        if (poin >= 300) return "🌍 Eco Warrior";
        else if (poin >= 200) return "🥈 Kontributor Hebat";
        else if (poin >= 100) return "🥉 Pelapor Aktif";
        else return "🌱 Pemula Semangat";
    }

    public void tambahPoin(int tambahan) {
        if (tambahan > 0) {
            this.poin.set(this.poin.get() + tambahan);
            updateLevel();
        }
    }

    public void tambahLaporan() {
        this.laporanMingguIni.set(this.laporanMingguIni.get() + 1);
        this.laporanBulanIni.set(this.laporanBulanIni.get() + 1);
    }

    // ✅ Metode tambahan jika ingin ambil badge secara eksplisit
    public String hitungBadgeSaatIni() {
        return calculateBadge(this.poin.get());
    }
}
