package test.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import test.Main;
import test.tampilan.HalamanUtama;

public class HalamanLogin {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Contoh login sederhana (kamu bisa sesuaikan dengan database nantinya)
        if (username.equals("admin") && password.equals("admin")) {
            try {
                Parent dashboardRoot = FXMLLoader.load(getClass().getResource("/test/fxml/dashboard.fxml"));
                HalamanUtama.primaryStageGlobal.setTitle("Dashboard SiPeka");
                HalamanUtama.primaryStageGlobal.setScene(new Scene(dashboardRoot));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            errorLabel.setText("Username atau Password salah!");
        }
    }
}
