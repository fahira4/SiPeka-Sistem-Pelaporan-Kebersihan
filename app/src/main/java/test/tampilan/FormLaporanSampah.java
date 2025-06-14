package test.tampilan;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import test.connector.dbConnector;
import test.helper.session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FormLaporanSampah extends VBox {

    public FormLaporanSampah() {
        // Judul Form
        Label title = new Label("🗑️ Form Pelaporan Titik Sampah");
        title.setFont(Font.font("System", FontWeight.BOLD, 20));
        title.setStyle("-fx-text-fill: #2e7d32;");
        title.setPadding(new Insets(10, 0, 20, 0));

        // Komponen input
        TextField lokasiField = new TextField();
        lokasiField.setPromptText("Contoh: Jalan Bunga No. 12");

        TextArea deskripsiArea = new TextArea();
        deskripsiArea.setPromptText("Tuliskan deskripsi masalah sampah di lokasi ini...");
        deskripsiArea.setWrapText(true);

        DatePicker tanggalPicker = new DatePicker();
        tanggalPicker.setPromptText("Pilih tanggal pelaporan");

        ComboBox<String> statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll("Belum diproses", "Sedang diproses", "Selesai");
        statusComboBox.setPromptText("Pilih status laporan");

        Button simpanButton = new Button("💾 Simpan Laporan");
        simpanButton.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-font-weight: bold;");

        Button kembaliButton = new Button("⬅ Kembali");
        kembaliButton.setStyle("-fx-background-color: #9e9e9e; -fx-text-fill: white;");

        // Layout grid
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15));
        grid.setVgap(12);
        grid.setHgap(10);

        // Tambahkan label deskriptif untuk inputan
        grid.add(new Label("📍 Lokasi Titik Sampah:"), 0, 0);
        grid.add(lokasiField, 1, 0);

        grid.add(new Label("📝 Deskripsi Masalah:"), 0, 1);
        grid.add(deskripsiArea, 1, 1);

        grid.add(new Label("📅 Tanggal Pelaporan:"), 0, 2);
        grid.add(tanggalPicker, 1, 2);

        grid.add(new Label("📌 Status Awal:"), 0, 3);
        grid.add(statusComboBox, 1, 3);

        // Button bar
        HBox buttonBox = new HBox(10, simpanButton, kembaliButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        grid.add(buttonBox, 1, 4);

        // Event tombol simpan
        simpanButton.setOnAction(e -> {
            String lokasi = lokasiField.getText().trim();
            String deskripsi = deskripsiArea.getText().trim();
            String tanggal = tanggalPicker.getValue() != null ? tanggalPicker.getValue().toString() : "";
            String status = statusComboBox.getValue();

            String userId = session.penggunaAktif != null ? session.penggunaAktif.getUsername() : null;

            if (userId == null) {
                showAlert(Alert.AlertType.ERROR, "Pengguna tidak ditemukan. Silakan login ulang.");
                return;
            }

            if (lokasi.isEmpty() || tanggal.isEmpty() || status == null) {
                showAlert(Alert.AlertType.ERROR, "Lokasi, Tanggal, dan Status wajib diisi!");
            } else {
                insertData(userId, lokasi, deskripsi, tanggal, status);
            }
        });

        // Tombol kembali
        kembaliButton.setOnAction(e -> {
            try {
                HalamanUtama halamanUtama = new HalamanUtama();
                halamanUtama.start(HalamanUtama.primaryStageGlobal);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Atur tampilan utama VBox
        this.getChildren().addAll(title, new Separator(), grid);
        this.setPadding(new Insets(25));
        this.setSpacing(20);
        this.setStyle("-fx-background-color: #f9fbe7;");
        this.setAlignment(Pos.TOP_CENTER);
    }

    private void insertData(String userId, String lokasi, String deskripsi, String tanggal, String status) {
        String sql = "INSERT INTO laporan_sampah(nama_pelapor, lokasi, deskripsi, tanggal, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = dbConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);
            pstmt.setString(2, lokasi);
            pstmt.setString(3, deskripsi);
            pstmt.setString(4, tanggal);
            pstmt.setString(5, status);

            pstmt.executeUpdate();

            tambahPoinPengguna(10); // Tambah 10 poin

            showAlert(Alert.AlertType.INFORMATION, "Data berhasil disimpan! Anda mendapatkan 10 poin 🌟");

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Gagal menyimpan data: " + e.getMessage());
        }
    }

    private void tambahPoinPengguna(int poinBaru) {
        if (session.penggunaAktif != null) {
            int poinSaatIni = session.penggunaAktif.getPoin();
            int totalBaru = poinSaatIni + poinBaru;

            session.penggunaAktif.setPoin(totalBaru);

            try (Connection conn = dbConnector.connect();
                 PreparedStatement stmt = conn.prepareStatement("UPDATE user SET poin = ? WHERE username = ?")) {

                stmt.setInt(1, totalBaru);
                stmt.setString(2, session.penggunaAktif.getUsername());
                stmt.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Gagal memperbarui poin pengguna di database.");
            }
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Informasi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
