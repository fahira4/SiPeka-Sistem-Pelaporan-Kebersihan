package test;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import test.controller.PengendaliLogin;
import test.helper.session;
import test.model.Pengguna;
import test.tampilan.*;

public class Main extends Application {

    private Stage primaryStage;
    private PengendaliLogin pengendaliLogin;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.pengendaliLogin = new PengendaliLogin();

        tampilkanHalamanLogin();
        primaryStage.setTitle("Aplikasi SiPeka");
        primaryStage.show();
    }

    private void tampilkanHalamanLogin() {
        HalamanLogin halamanLogin = new HalamanLogin();

        // WAJIB panggil getView() agar tombol dan komponen terinisialisasi!
        Parent root = halamanLogin.getView();

        // Baru setelah itu tombol-tombol bisa diakses
        halamanLogin.getLoginButton().setOnAction(e -> {
            String username = halamanLogin.getUsername();
            String password = halamanLogin.getPassword();

            Pengguna pengguna = pengendaliLogin.autentikasi(username, password);
            if (pengguna != null) {
                session.penggunaAktif = pengguna;
                tampilkanHalamanUtama();
            } else {
                halamanLogin.getActionTarget().setText("Username atau password salah!");
            }
        });

        halamanLogin.getDaftarButton().setOnAction(e -> tampilkanHalamanRegistrasi());

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
    }

    private void tampilkanHalamanRegistrasi() {
        HalamanLogin halamanRegistrasi = new HalamanLogin();

        halamanRegistrasi.getDaftarButton().setOnAction(e -> {
            String username = halamanRegistrasi.getUsername();
            String password = halamanRegistrasi.getPassword();

            if (username.isEmpty() || password.isEmpty()) {
                halamanRegistrasi.getActionTarget().setText("Username dan password harus diisi.");
                return;
            }

            boolean sukses = pengendaliLogin.daftar(username, password, "RT 1"); // Misalnya RT 1, bisa diubah sesuai
                                                                                 // kebutuhan
            if (sukses) {
                session.penggunaAktif = new Pengguna(username, password);
                tampilkanHalamanUtama();
            } else {
                halamanRegistrasi.getActionTarget().setText("Username sudah digunakan.");
            }
        });

        Scene scene = new Scene(halamanRegistrasi.getView(), 600, 400);
        primaryStage.setScene(scene);
    }

    private void tampilkanHalamanUtama() {
        HalamanUtama halamanUtama = new HalamanUtama();
        Scene scene = new Scene(halamanUtama.getView(800, 600));
        primaryStage.setScene(scene);
    }

    public void kembaliKeHalamanUtama() {
        tampilkanHalamanUtama();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
