package test.model;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class NotifikasiJadwal {

    public static void tampilkanInfo(String judul, String pesan) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(judul);
        alert.setHeaderText(null);
        alert.setContentText(pesan);
        alert.showAndWait();
    }

    public static void tampilkanPeringatan(String judul, String pesan) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(judul);
        alert.setHeaderText(null);
        alert.setContentText(pesan);
        alert.showAndWait();
    }

    public static void tampilkanError(String judul, String pesan) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(judul);
        alert.setHeaderText(null);
        alert.setContentText(pesan);
        alert.showAndWait();
    }
}
