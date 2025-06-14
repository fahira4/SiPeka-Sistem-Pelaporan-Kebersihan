package test.controller;

import javafx.scene.control.Alert;

public class LayananNotifikasi {

    public static String getPesanNotifikasi(String status) {
        switch (status.toLowerCase()) {
            case "diproses":
                return "Laporan Anda sedang diproses.";
            case "selesai":
                return "Sampah telah diangkut.";
            default:
                return "Status tidak diketahui.";
        }
    }

    public static void tampilkanInfo(String pesan) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informasi");
        alert.setHeaderText(null);
        alert.setContentText(pesan);
        alert.showAndWait();
    }

    public static void tampilkanError(String pesan) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Kesalahan");
        alert.setHeaderText("Terjadi Kesalahan");
        alert.setContentText(pesan);
        alert.showAndWait();
    }
}
