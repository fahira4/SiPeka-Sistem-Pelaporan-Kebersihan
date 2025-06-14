package test.tampilan;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class NotifikasiJadwal {

    // Metode utama
    public static void tampilkanNotifikasi(String status) {
        try {
            if (status == null || status.trim().isEmpty()) {
                showError("Status notifikasi tidak boleh kosong.");
                return;
            }

            String pesan = getPesanDariStatus(status);
            AlertType jenisAlert = getAlertTypeDariStatus(status);

            if (pesan == null || pesan.trim().isEmpty()) {
                showInfo("Status '" + status + "' tidak dikenali.");
                return;
            }

            Alert alert = new Alert(jenisAlert);
            alert.setTitle("Notifikasi Jadwal");
            alert.setHeaderText(null);
            alert.setContentText(pesan);
            alert.showAndWait();

        } catch (Exception e) {
            showError("Terjadi kesalahan saat menampilkan notifikasi.");
            e.printStackTrace();
        }
    }

    // Konversi status ke pesan notifikasi
    private static String getPesanDariStatus(String status) {
        switch (status.toLowerCase()) {
            case "sukses_tambah":
                return "Jadwal berhasil ditambahkan!";
            case "sukses_hapus":
                return "Jadwal berhasil dihapus.";
            case "kosong":
                return "Belum ada jadwal yang tersedia.";
            case "gagal":
                return "Terjadi kesalahan saat memproses jadwal.";
            case "tidak_valid":
                return "Data jadwal tidak valid atau belum lengkap.";
            default:
                return ""; // Status tidak dikenali
        }
    }

    // Konversi status ke jenis AlertType
    private static AlertType getAlertTypeDariStatus(String status) {
        switch (status.toLowerCase()) {
            case "sukses_tambah":
            case "sukses_hapus":
            case "kosong":
                return AlertType.INFORMATION;
            case "tidak_valid":
                return AlertType.WARNING;
            case "gagal":
                return AlertType.ERROR;
            default:
                return AlertType.INFORMATION;
        }
    }

    // Menampilkan pesan kesalahan umum
    private static void showError(String pesan) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Kesalahan");
        alert.setHeaderText("Terjadi Kesalahan");
        alert.setContentText(pesan);
        alert.showAndWait();
    }

    // Menampilkan pesan informasi umum
    private static void showInfo(String pesan) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Informasi");
        alert.setHeaderText(null);
        alert.setContentText(pesan);
        alert.showAndWait();
    }

    public static void tampilkanPeringatan(String string, String string2) {

      
    }
}
