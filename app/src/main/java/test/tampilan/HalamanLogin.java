package test.tampilan;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import test.controller.PengendaliLogin;
import test.helper.session;
import test.model.Pengguna;
import test.connector.dbConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HalamanLogin {
    private TextField userTextField;
    private PasswordField pwBox;
    private Button loginButton;
    private Label actionTarget;
    private ComboBox<String> rtComboBox;
    private Button daftarButton = new Button("Daftar");

    public Parent getView() {
        Font poppinsRegular = Font.font("System", 16);
        Font poppinsBold = Font.font("System", 24);

        daftarButton.setFont(poppinsRegular);
        daftarButton.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: #1B5E20;" +
                        "-fx-underline: true;" +
                        "-fx-font-size: 14px;");
        daftarButton.setOnAction(e -> {
            FormRegistrasi form = new FormRegistrasi(new PengendaliLogin());
            form.tampilkan();
        });

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(30);
        root.setPadding(new Insets(50));
        root.setStyle("-fx-background-color: #E8F5E9;");

        VBox card = new VBox(30);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(50, 40, 50, 40));
        card.setMaxWidth(480);
        card.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 24px; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 16, 0, 0, 6);");

        Text title = new Text("Login SiPeka");
        title.setFont(poppinsBold);
        title.setStyle("-fx-fill: #1B5E20;");

        VBox inputBox = new VBox(20);
        inputBox.setMaxWidth(400);

        Label userLabel = new Label("Login SiPeka");
        userLabel.setFont(poppinsRegular);
        userLabel.setStyle("-fx-text-fill: #1B5E20;");
        userTextField = new TextField();
        userTextField.setPromptText("Masukkan username");
        userTextField.setStyle("-fx-background-radius: 12; -fx-padding: 12 16; -fx-font-size: 16px;");

        Label passLabel = new Label("Password");
        passLabel.setFont(poppinsRegular);
        passLabel.setStyle("-fx-text-fill: #1B5E20;");
        pwBox = new PasswordField();
        pwBox.setPromptText("Masukkan password");
        pwBox.setStyle("-fx-background-radius: 12; -fx-padding: 12 16; -fx-font-size: 16px;");

        Label rtLabel = new Label("RT");
        rtLabel.setFont(poppinsRegular);
        rtLabel.setStyle("-fx-text-fill: #1B5E20;");
        rtComboBox = new ComboBox<>();
        rtComboBox.getItems().addAll("RT 1", "RT 2", "RT 3");
        rtComboBox.setPromptText("Pilih RT");
        rtComboBox.setStyle("-fx-background-radius: 12; -fx-font-size: 16px;");

        inputBox.getChildren().addAll(userLabel, userTextField, passLabel, pwBox,rtLabel, rtComboBox);

        loginButton = new Button("Login");
        loginButton.setFont(poppinsBold);
        loginButton.setMaxWidth(Double.MAX_VALUE);
        loginButton.setStyle(
                "-fx-background-color: #1B5E20;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 18px;" +
                        "-fx-background-radius: 12;" +
                        "-fx-padding: 14 0;");

        actionTarget = new Label();
        actionTarget.setFont(poppinsRegular);
        actionTarget.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");

        HBox tombolBox = new HBox(10, loginButton, daftarButton);
        tombolBox.setAlignment(Pos.CENTER);

        card.getChildren().addAll(title, inputBox, tombolBox, actionTarget);
        root.getChildren().add(card);

        // 🔐 Aksi saat tombol login ditekan
        loginButton.setOnAction(e -> {
            String username = userTextField.getText().trim();
            String password = pwBox.getText().trim();

            if (username.isEmpty() || password.isEmpty()) {
                actionTarget.setStyle("-fx-text-fill: red;");
                actionTarget.setText("Username dan password harus diisi.");
                System.err.println("⚠️ Username/password kosong.");
                return;
            }

            try (Connection conn = dbConnector.connect()) {
                String query = "SELECT * FROM user WHERE username = ? AND password = ?";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, username);
                pstmt.setString(2, password);

                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    actionTarget.setStyle("-fx-text-fill: green;");
                    actionTarget.setText("Login berhasil! Selamat datang, " + rs.getString("username"));

                    Pengguna pengguna = new Pengguna(
                            rs.getString("username"),
                            rs.getString("password"));
                    session.penggunaAktif = pengguna;
                    pengguna.setRt(rs.getString("rt"));

                    System.out.println("✅ Login berhasil untuk user: " + session.penggunaAktif.getUsername());
                    HalamanUtama.tampilkan();

                    // Tutup window login
                    Stage currentStage = (Stage) loginButton.getScene().getWindow();
                    currentStage.close();

                } else {
                    actionTarget.setStyle("-fx-text-fill: red;");
                    actionTarget.setText("Username atau password salah.");
                    System.err.println("❌ Login gagal: Username atau password salah.");
                }

                rs.close();
                pstmt.close();

            } catch (SQLException ex) {
                ex.printStackTrace();
                actionTarget.setStyle("-fx-text-fill: red;");
                actionTarget.setText("Terjadi kesalahan saat login.");
                System.err.println("❌ Kesalahan SQL saat login: " + ex.getMessage());
            }
        });

        return root;
    }

    public Button getDaftarButton() {
        return daftarButton;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public TextField getUserTextField() {
        return userTextField;
    }

    public PasswordField getPwBox() {
        return pwBox;
    }

    public Label getActionTarget() {
        return actionTarget;
    }

    public String getUsername() {
        return userTextField.getText();
    }

    public String getPassword() {
        return pwBox.getText();
    }
}
