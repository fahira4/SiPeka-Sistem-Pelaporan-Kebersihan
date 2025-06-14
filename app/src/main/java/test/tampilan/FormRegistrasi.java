package test.tampilan;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import test.controller.PengendaliLogin;
import test.connector.dbConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FormRegistrasi {
    private PengendaliLogin pengendaliLogin;

    public FormRegistrasi(PengendaliLogin pengendaliLogin) {
        this.pengendaliLogin = pengendaliLogin;
    }

    public void tampilkan() {
        Stage stage = new Stage();
        stage.setTitle("Form Registrasi");
        stage.initModality(Modality.APPLICATION_MODAL);

        // 🔰 Judul dan Deskripsi
        Label titleLabel = new Label("🌿 Daftar Akun Baru");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 20));
        titleLabel.setStyle("-fx-text-fill: #2e7d32;");

        Label subtitleLabel = new Label("Silakan isi informasi berikut untuk membuat akun.");
        subtitleLabel.setStyle("-fx-text-fill: #4caf50;");

        // 📋 Field Input
        TextField usernameField = new TextField();
        usernameField.setPromptText("Masukkan username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Masukkan password");

        ComboBox<String> rtComboBox = new ComboBox<>();
        rtComboBox.getItems().addAll("RT 1", "RT 2", "RT 3");
        rtComboBox.setPromptText("Pilih RT Anda");

        Label pesanLabel = new Label();
        pesanLabel.setStyle("-fx-text-fill: red;");

        // 🟢 Tombol Simpan
        Button simpanButton = new Button("✅ Simpan");
        simpanButton.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-font-weight: bold;");
        simpanButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            String rt = rtComboBox.getValue();

            if (username.isEmpty() || password.isEmpty() || rt == null) {
                pesanLabel.setText("Semua field wajib diisi, termasuk RT.");
                return;
            }

            try (Connection conn = dbConnector.connect();
                 PreparedStatement cekStmt = conn.prepareStatement("SELECT * FROM user WHERE username = ?")) {

                cekStmt.setString(1, username);
                try (ResultSet rs = cekStmt.executeQuery()) {
                    if (rs.next()) {
                        pesanLabel.setText("Username sudah digunakan.");
                        return;
                    }
                }

                try (PreparedStatement insertStmt = conn.prepareStatement(
                        "INSERT INTO user(username, password, rt) VALUES (?, ?, ?)")) {
                    insertStmt.setString(1, username);
                    insertStmt.setString(2, password);
                    insertStmt.setString(3, rt);
                    insertStmt.executeUpdate();
                }

                pesanLabel.setStyle("-fx-text-fill: green;");
                pesanLabel.setText("Registrasi berhasil!");

            } catch (SQLException ex) {
                pesanLabel.setText("Terjadi kesalahan saat menyimpan.");
                ex.printStackTrace();
            }
        });

        VBox layout = new VBox(12,
                titleLabel,
                subtitleLabel,
                new Label("👤 Username:"), usernameField,
                new Label("🔒 Password:"), passwordField,
                new Label("🏠 RT:"), rtComboBox,
                simpanButton,
                pesanLabel
        );

        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-background-color: #f1f8e9;");

        Scene scene = new Scene(layout, 350, 420);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
