package test.tampilan;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import test.controller.PengendaliForum;
import test.controller.PengendaliGamifikasi;
import test.helper.session;
import test.model.Pengguna;
import test.model.Gamifikasi;

public class HalamanUtama extends Application {

    private Stage primaryStage;
    private VBox root;

    public static Stage primaryStageGlobal;

    public HalamanUtama() {

    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStageGlobal = primaryStage;
        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
        inisialisasiRoot(); // panggil inisialisasi root di sini

        Scene scene = new Scene(root, 400, 650);
        primaryStage.setScene(scene);
        primaryStage.setTitle("SiPeka (Sistem Pelaporan Kebersihan)");
        primaryStage.show();
    }

    private void inisialisasiRoot() {
        // Header dengan gradien hijau yang lebih modern
        VBox headerContainer = new VBox();
        headerContainer.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #2E7D32, #1B5E20); " +
                "-fx-background-radius: 0 0 30 30; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 3);");
        headerContainer.setPadding(new Insets(20, 20, 30, 20));

        Label header = new Label("SiPeka");
        header.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 36));
        header.setTextFill(Color.WHITE);
        header.setEffect(new DropShadow(5, Color.web("#1B5E20")));

        Label welcomeLabel = new Label("Sistem Pelaporan Kebersihan");
        welcomeLabel.setFont(Font.font("Arial", FontWeight.MEDIUM, 14));
        welcomeLabel.setTextFill(Color.WHITE);
        welcomeLabel.setOpacity(0.9);

        headerContainer.getChildren().addAll(header, welcomeLabel);

        // Area konten dengan latar belakang tekstur halus
        VBox contentArea = new VBox(20);
        contentArea.setPadding(new Insets(30, 20, 20, 20));
        contentArea.setStyle("-fx-background-color: linear-gradient(to bottom, #f1f8e9, #e8f5e9);");

        // Grid menu dengan efek lebih menarik
        GridPane menuGrid = new GridPane();
        menuGrid.setHgap(15);
        menuGrid.setVgap(15);
        menuGrid.setAlignment(Pos.CENTER);

        menuGrid.add(createMenuItem("Laporkan\nTitik Sampah", "Laporkan lokasi\ntitik sampah liar", "laporScene",
                createLocationIcon(), "#2E7D32"), 0, 0);
        menuGrid.add(createMenuItem("Jadwal\nPengangkutan", "Lihat jadwal\npengangkutan\nsampah", "jadwalScene",
                createCalendarIcon(), "#388E3C"), 1, 0);
        menuGrid.add(createMenuItem("Gamifikasi", "Ambil bagian\ndalam tantangan!", "gameScene", 
                createStarIcon(), "#FF8F00"), 0, 1);
        menuGrid.add(createMenuItem("Forum\nKomunitas", "Diskusi dengan\nwarga lainnya", "forumScene",
                createForumIcon(), "#00796B"), 1, 1);

        // Footer dengan informasi tambahan
        VBox footer = new VBox(10);
        footer.setPadding(new Insets(15, 20, 15, 20));
        footer.setAlignment(Pos.CENTER);
        footer.setStyle("-fx-background-color: #e8f5e9; -fx-border-color: #c8e6c9; -fx-border-width: 1 0 0 0;");
        
        Label footerText = new Label("SiPeka © 2025 - Aplikasi Kebersihan Lingkungan");
        footerText.setFont(Font.font("Arial", FontWeight.NORMAL, 10));
        footerText.setTextFill(Color.web("#455A64"));
    
        footer.getChildren().addAll(footerText);

        // Bottom navigation dengan efek lebih halus
        HBox bottomNav = new HBox();
        bottomNav.setPadding(new Insets(15, 20, 15, 20));
        bottomNav.setAlignment(Pos.CENTER);
        bottomNav.setStyle(
                "-fx-background-color: #ffffff; " +
                "-fx-border-color: #c8e6c9; " +
                "-fx-border-width: 1 0 0 0; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, -3);");

        VBox berandaNav = createNavItem("Beranda", createHomeIcon(), true);
        VBox forumNav = createNavItem("Forum", createForumIcon(), false);

        berandaNav.setOnMouseClicked(e -> primaryStageGlobal.setScene(new Scene(root, 400, 600)));
        forumNav.setOnMouseClicked(e -> showScene("forumScene"));

        bottomNav.getChildren().addAll(berandaNav, forumNav);

        root = new VBox();
        root.getChildren().addAll(headerContainer, contentArea, footer);
        contentArea.getChildren().addAll(menuGrid);
        root.getChildren().add(bottomNav);
        root.setStyle("-fx-background-color: #f5f5f5;");
    }

    private VBox createMenuItem(String title, String subtitle, String sceneId, StackPane icon, String color) {
        VBox box = new VBox(10);
        box.setPadding(new Insets(20));
        box.setAlignment(Pos.CENTER);
        box.setStyle(
                "-fx-background-color: #ffffff; " +
                "-fx-border-radius: 15; " +
                "-fx-background-radius: 15; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");
        
        // Menambahkan aksen warna di bagian atas
        Rectangle colorAccent = new Rectangle(60, 4);
        colorAccent.setFill(Color.web(color));
        colorAccent.setArcWidth(5);
        colorAccent.setArcHeight(5);

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        titleLabel.setTextFill(Color.web("#333333"));
        titleLabel.setAlignment(Pos.CENTER);

        Label subtitleLabel = new Label(subtitle);
        subtitleLabel.setFont(Font.font("Arial", 11));
        subtitleLabel.setTextFill(Color.web("#666666"));
        subtitleLabel.setAlignment(Pos.CENTER);
        subtitleLabel.setWrapText(true);

        // Efek hover yang lebih halus
        box.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            box.setStyle(
                    "-fx-background-color: #f1f8e9; " +
                    "-fx-border-radius: 15; " +
                    "-fx-background-radius: 15; " +
                    "-fx-effect: dropshadow(gaussian, rgba(46,125,50,0.2), 8, 0, 0, 3);");
            titleLabel.setTextFill(Color.web(color));
        });

        box.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            box.setStyle(
                    "-fx-background-color: #ffffff; " +
                    "-fx-border-radius: 15; " +
                    "-fx-background-radius: 15; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");
            titleLabel.setTextFill(Color.web("#333333"));
        });

        box.setOnMouseClicked(e -> showScene(sceneId));

        box.getChildren().addAll(colorAccent, icon, titleLabel, subtitleLabel);
        return box;
    }

    private VBox createNavItem(String text, StackPane icon, boolean isActive) {
        VBox navItem = new VBox(5);
        navItem.setAlignment(Pos.CENTER);
        navItem.setPrefWidth(80);
        navItem.setPadding(new Insets(5));

        Label label = new Label(text);
        label.setFont(Font.font("Arial", FontWeight.MEDIUM, 12));
        label.setTextFill(isActive ? Color.web("#1b5e20") : Color.web("#78909C"));
        
        if (isActive) {
            navItem.setStyle("-fx-background-color: #e8f5e9; -fx-background-radius: 10;");
        } else {
            navItem.setStyle("-fx-background-color: transparent;");
        }

        navItem.getChildren().addAll(icon, label);
        
        // Efek hover untuk nav item
        navItem.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            if (!isActive) {
                label.setTextFill(Color.web("#2E7D32"));
            }
        });
        
        navItem.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            if (!isActive) {
                label.setTextFill(Color.web("#78909C"));
            }
        });

        return navItem;
    }

    // Metode pembuatan ikon dengan warna yang lebih konsisten
    private StackPane createLocationIcon() {
        StackPane iconContainer = new StackPane();
        iconContainer.setPrefSize(40, 40);

        Circle background = new Circle(20, Color.web("#4db6ac"));
        background.setEffect(new DropShadow(3, Color.web("#00796B")));

        Circle pin = new Circle(8, Color.web("#2e7d32"));
        pin.setTranslateY(-2);

        Circle innerPin = new Circle(4, Color.WHITE);
        innerPin.setTranslateY(-2);

        iconContainer.getChildren().addAll(background, pin, innerPin);
        return iconContainer;
    }

    private StackPane createCalendarIcon() {
        StackPane iconContainer = new StackPane();
        iconContainer.setPrefSize(40, 40);

        Circle background = new Circle(20, Color.web("#81c784"));
        background.setEffect(new DropShadow(3, Color.web("#388E3C")));

        Rectangle calendar = new Rectangle(16, 14, Color.WHITE);
        calendar.setArcWidth(2);
        calendar.setArcHeight(2);

        Rectangle calendarTop = new Rectangle(16, 4, Color.web("#2e7d32"));
        calendarTop.setTranslateY(-5);
        calendarTop.setArcWidth(2);
        calendarTop.setArcHeight(2);

        iconContainer.getChildren().addAll(background, calendar, calendarTop);
        return iconContainer;
    }

    private StackPane createStarIcon() {
        StackPane iconContainer = new StackPane();
        iconContainer.setPrefSize(40, 40);

        Circle background = new Circle(20, Color.web("#ffb74d"));
        background.setEffect(new DropShadow(3, Color.web("#F57F17")));

        Polygon star = new Polygon();
        star.getPoints().addAll(new Double[] {
                0.0, -8.0, 2.4, -2.4, 8.0, -2.4, 3.2, 1.6, 5.6, 8.0,
                0.0, 4.8, -5.6, 8.0, -3.2, 1.6, -8.0, -2.4, -2.4, -2.4
        });
        star.setFill(Color.web("#f57f17"));

        iconContainer.getChildren().addAll(background, star);
        return iconContainer;
    }

    private StackPane createHomeIcon() {
        StackPane iconContainer = new StackPane();
        iconContainer.setPrefSize(24, 24);

        Polygon house = new Polygon();
        house.getPoints()
                .addAll(new Double[] { 0.0, -8.0, -8.0, 0.0, -4.0, 0.0, -4.0, 6.0, 4.0, 6.0, 4.0, 0.0, 8.0, 0.0 });
        house.setFill(Color.web("#2e7d32"));
        house.setEffect(new DropShadow(2, Color.web("#1B5E20")));

        iconContainer.getChildren().add(house);
        return iconContainer;
    }

    private StackPane createForumIcon() {
        StackPane iconContainer = new StackPane();
        iconContainer.setPrefSize(40, 40);

        Circle background = new Circle(20, Color.web("#4db6ac"));
        background.setEffect(new DropShadow(3, Color.web("#00796B")));

        Circle bubble1 = new Circle(6, Color.WHITE);
        bubble1.setTranslateX(-4);
        bubble1.setTranslateY(2);

        Circle bubble2 = new Circle(8, Color.WHITE);
        bubble2.setTranslateX(4);
        bubble2.setTranslateY(-2);

        Circle dot1 = new Circle(1, Color.web("#00796B"));
        dot1.setTranslateX(-6);
        dot1.setTranslateY(2);

        Circle dot2 = new Circle(1, Color.web("#00796B"));
        dot2.setTranslateX(-2);
        dot2.setTranslateY(2);

        Circle dot3 = new Circle(1, Color.web("#00796B"));
        dot3.setTranslateX(2);
        dot3.setTranslateY(-4);

        Circle dot4 = new Circle(1, Color.web("#00796B"));
        dot4.setTranslateX(6);
        dot4.setTranslateY(-4);

        iconContainer.getChildren().addAll(background, bubble1, bubble2, dot1, dot2, dot3, dot4);
        return iconContainer;
    }
    // ## Metode createStatistikIcon() telah dihapus ##

    // Fungsi ganti scene (saat klik fitur)
    private void showScene(String sceneId) {
        Scene scene;

        switch (sceneId) {
            case "laporScene":
                Scene laporScene = new Scene(new FormLaporanSampah(), 400, 400);
                primaryStageGlobal.setScene(laporScene);
                return;

            case "jadwalScene":
                // TODO: Ganti argumen berikut sesuai konstruktor TampilanJadwalPengangkutan
                TampilanJadwalPengangkutan jadwal = new TampilanJadwalPengangkutan(primaryStage/*
                                                                                                 * masukkan argumen yang
                                                                                                 * diperlukan di sin
                                                                                                 */);

                Scene jadwalScene = new Scene(jadwal, 800, 600);
                primaryStageGlobal.setScene(jadwalScene);
                return;

            case "gameScene":
                Gamifikasi gamifikasi = new Gamifikasi();
                GamifikasiView gamifikasiView = new GamifikasiView(gamifikasi);
                primaryStageGlobal.setScene(new Scene(gamifikasiView, 900, 700));
                return;

            case "forumScene":
                PengendaliForum pengendaliForum = new PengendaliForum();
                ForumWarga forumWarga = new ForumWarga(pengendaliForum);
                Scene forumScene = new Scene(forumWarga.getView(), 600, 400);
                primaryStageGlobal.setScene(forumScene);
                return;

        }
    }

    private void showError(String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Kesalahan");
        alert.setHeaderText("Terjadi Kesalahan");
        alert.setContentText(message);
        alert.showAndWait();
        Label errorLabel = new Label(message);
        errorLabel.setFont(Font.font(16));
        errorLabel.setTextFill(Color.RED);
        VBox layout = new VBox(20, errorLabel);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 400, 200);
        primaryStage.setScene(scene);
    }

    public static void tampilkan() {
        Platform.runLater(() -> {
            try {
                HalamanUtama halaman = new HalamanUtama();
                Stage mainStage = new Stage();
                mainStage.setTitle("SiPeka - Sistem Pelaporan Kebersihan");
                halaman.start(mainStage);
                mainStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Parent getView(int i, int j) {
        if (root == null) {
            inisialisasiRoot(); // buat root jika belum ada
        }
        return root;
    }
}
