package test.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RT {
    private final StringProperty id;
    private final StringProperty nama;
    private final IntegerProperty poin;
    private final IntegerProperty level;

    public RT(String id, String nama, int poin) {
        this.id = new SimpleStringProperty(id);
        this.nama = new SimpleStringProperty(nama);
        this.poin = new SimpleIntegerProperty(poin);
        this.level = new SimpleIntegerProperty(calculateLevel(poin));
    }

    public RT(String nama, int poin) {
        this.id = new SimpleStringProperty(""); // Default id kosong
        this.nama = new SimpleStringProperty(nama);
        this.poin = new SimpleIntegerProperty(poin);
        this.level = new SimpleIntegerProperty(calculateLevel(poin));
    }

    // Property getters
    public StringProperty idProperty() { return id; }
    public StringProperty namaProperty() { return nama; }
    public IntegerProperty poinProperty() { return poin; }
    public IntegerProperty levelProperty() { return level; }

    // Regular getters
    public String getId() { return id.get(); }
    public String getNama() { return nama.get(); }
    public int getPoin() { return poin.get(); }
    public int getLevel() { return level.get(); }

    // Regular setters
    public void setPoin(int poin) {
        this.poin.set(poin);
        this.level.set(calculateLevel(poin)); // Update level otomatis
    }

    public void setLevel(int level) {
        this.level.set(level);
    }

    private int calculateLevel(int poin) {
        return Math.min(5, 1 + poin / 200); // Contoh logika level
    }

    public String getRt() {
        return getNama(); // Atau implementasi lain sesuai kebutuhan
    }
}
