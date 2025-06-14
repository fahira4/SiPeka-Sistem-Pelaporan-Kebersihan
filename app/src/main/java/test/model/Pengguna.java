package test.model;

import java.io.Serializable;

public class Pengguna implements Serializable {
    private String username;
    private String password;
    private int poin;
    private String badge;
    private Integer level;
    private String RT;

    public Pengguna(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Pengguna(String username2, String password2, String rt2) {
        this.username = username2;
        this.password = password2;
        this.RT = rt2;
        this.poin = 0; // Inisialisasi poin awal
        updateBadgeAndLevel(); // Perbarui badge dan level berdasarkan poin awal
    }

    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public String getUsername() {
        return username;
    }

    public int getPoin() {
        return poin;
    }

    public void setPoin(int poin) {
        this.poin = poin;
        updateBadgeAndLevel(); // setiap kali poin berubah, perbarui badge
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
        updateBadgeAndLevel(); // setiap kali badge diubah, perbarui level
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    private void updateBadgeAndLevel() {
        if (poin >= 300) {
            badge = "Emas";
            level = 3;
        } else if (poin >= 150) {
            badge = "Perak";
            level = 2;
        } else {
            badge = "Perunggu";
            level = 1;
        }
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return username; // Asumsi ID pengguna adalah username

    }

    public String getRT() {
        return RT;
    }

    public void setRt(String RT) {
        this.RT = RT;
    }
}
