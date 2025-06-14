package test.tampilan;

import test.controller.PengendaliGamifikasi;
import test.model.Gamifikasi;
import test.helper.session;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GamifikasiView extends BorderPane {

    private final Gamifikasi gamifikasi;
    private final PengendaliGamifikasi pengendali;

    private final Label lblNama = new Label("Nama Pengguna (RT)");
    private final Label lblPoin = new Label("0 Poin");
    private final Label lblLevelText = new Label("Level 1"); // Label untuk teks level
    private final Label lblBadgeText = new Label("-"); // Label untuk teks badge
    private final ProgressBar progressLevel = new ProgressBar();

    public GamifikasiView(Gamifikasi gamifikasi) {
        this.gamifikasi = gamifikasi;
        this.pengendali = new PengendaliGamifikasi(gamifikasi);

        initializeUI();
        refreshData(); // Memuat data awal
        setupAutoRefresh(); // Mengatur pembaruan otomatis
    }

    private void initializeUI() {
        setPadding(new Insets(20));
        setStyle("-fx-background-color: linear-gradient(to bottom, #e8f5e9, #c8e6c9);");
        setCenter(createProfileCard());
        setBottom(createFooter());
    }

    public void refreshData() {
        try {
            if (session.penggunaAktif != null) {
                String userRT = validateRT(session.penggunaAktif.getRT());

                gamifikasi.setUserId(session.penggunaAktif.getUsername());
                gamifikasi.setPoin(session.penggunaAktif.getPoin());

                int level = gamifikasi.getLevel();
                String badge = gamifikasi.getBadge() != null ? gamifikasi.getBadge() : "Belum ada badge";

                lblNama.setText(gamifikasi.getUserId() + " (" + userRT + ")");
                lblPoin.setText(gamifikasi.getPoin() + " Poin");
                lblLevelText.setText("Level " + level);
                lblBadgeText.setText(badge);
                progressLevel.setProgress(calculateLevelProgress(gamifikasi.getPoin(), level));
            }
        } catch (IllegalArgumentException e) {
            showAlert("Error RT", "RT Tidak Valid", e.getMessage());
        }
    }

    private String validateRT(String rtInput) {
        if (rtInput == null || !rtInput.matches("RT [1-3]")) {
            throw new IllegalArgumentException("RT harus berupa RT 1, RT 2, atau RT 3");
        }
        return rtInput;
    }

    private double calculateLevelProgress(int poin, int currentLevel) {
        int basePoin = (currentLevel - 1) * 100;
        int nextLevelPoin = currentLevel * 100;

        if (currentLevel >= 4) return 1.0;
        return Math.min(1.0, (double) (poin - basePoin) / (nextLevelPoin - basePoin));
    }

    // Metode baru untuk membuat ikon perisai untuk Level
    private HBox createLevelDisplay() {
        StackPane shieldIcon = new StackPane();
        Polygon shieldShape = new Polygon(
            0.0, 0.0, 40.0, 0.0, 40.0, 40.0, 20.0, 50.0, 0.0, 40.0
        );
        shieldShape.setFill(Color.web("#66BB6A"));
        shieldShape.setStroke(Color.web("#4CAF50"));
        shieldShape.setStrokeWidth(2);

        Label iconText = new Label("Lvl");
        iconText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        iconText.setTextFill(Color.WHITE);
        
        shieldIcon.getChildren().addAll(shieldShape, iconText);
        shieldIcon.setPrefSize(40, 50);

        lblLevelText.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        lblLevelText.setTextFill(Color.web("#2E7D32"));

        HBox levelBox = new HBox(15, shieldIcon, lblLevelText);
        levelBox.setAlignment(Pos.CENTER_LEFT);
        return levelBox;
    }
    
    // Metode baru untuk membuat ikon medali untuk Badge
    private HBox createBadgeDisplay() {
        StackPane medalIcon = new StackPane();
        
        // Pita medali
        Polygon ribbon = new Polygon(
            0, 0, 10, 15, 20, 0, 30, 15, 40, 0, 20, 25, 0, 0
        );
        ribbon.setFill(Color.web("#E53935")); // Warna merah
        ribbon.setTranslateY(-12);

        // Badan medali
        Circle medalBase = new Circle(20, Color.web("#FFD700")); // Warna emas
        medalBase.setStroke(Color.web("#FFA000"));
        medalBase.setStrokeWidth(2);
        
        // Bintang di tengah medali
        Polygon star = new Polygon(
             0.0, -10.0,  2.9, -4.0,  9.5, -3.1,
             4.8,  1.5,   5.9,  8.1,  0.0,  5.0,
            -5.9,  8.1,  -4.8,  1.5, -9.5, -3.1,
            -2.9, -4.0
        );
        star.setFill(Color.web("#FFFFFF"));
        
        medalIcon.getChildren().addAll(ribbon, medalBase, star);
        medalIcon.setPrefSize(40, 50);

        lblBadgeText.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 16));
        lblBadgeText.setTextFill(Color.web("#424242"));
        lblBadgeText.setWrapText(true);

        HBox badgeBox = new HBox(15, medalIcon, lblBadgeText);
        badgeBox.setAlignment(Pos.CENTER_LEFT);
        return badgeBox;
    }

    private VBox createProfileCard() {
        lblNama.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 22));
        lblNama.setTextFill(Color.web("#1b5e20"));
        lblNama.setTextAlignment(TextAlignment.CENTER);

        lblPoin.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 16));
        lblPoin.setTextFill(Color.web("#333"));

        progressLevel.setPrefWidth(Double.MAX_VALUE);
        progressLevel.setStyle("-fx-accent: #FFB300;");
        
        Label progressLabel = new Label("Progress ke Level Selanjutnya:");
        progressLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        progressLabel.setTextFill(Color.web("#666"));

        VBox progressBox = new VBox(5, progressLabel, progressLevel);

        VBox card = new VBox(20);
        card.getChildren().addAll(
            lblNama,
            new Separator(),
            createLevelDisplay(),
            createBadgeDisplay(),
            lblPoin,
            progressBox
        );
        
        card.setPadding(new Insets(25));
        card.setStyle("""
                      -fx-background-color: white;
                      -fx-background-radius: 15;
                      -fx-border-color: #a5d6a7;
                      -fx-border-width: 2;
                      -fx-border-radius: 15;
                      -fx-effect: dropshadow(one-pass-box, rgba(0,0,0,0.1), 10, 0.5, 0, 5);
                      """);
        card.setMaxWidth(450);
        
        VBox centeringBox = new VBox(card);
        centeringBox.setAlignment(Pos.CENTER);
        
        return centeringBox;
    }

    private HBox createFooter() {
        Button btnKembali = new Button("← Kembali ke Beranda");
        btnKembali.setStyle("""
                      -fx-background-color: #81c784;
                      -fx-text-fill: white;
                      -fx-font-weight: bold;
                      -fx-font-size: 14px;
                      -fx-background-radius: 10;
                      -fx-padding: 8 16 8 16;
                      """);
        btnKembali.setOnAction(e -> navigateToHome());

        HBox footer = new HBox(btnKembali);
        footer.setPadding(new Insets(20, 10, 10, 10));
        footer.setAlignment(Pos.CENTER_LEFT);
        return footer;
    }

    private void setupAutoRefresh() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(30), e -> refreshData()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void navigateToHome() {
        try {
            Stage currentStage = (Stage) getScene().getWindow();
            HalamanUtama halaman = new HalamanUtama();
            Stage newStage = new Stage();
            halaman.start(newStage);
            currentStage.close();
        } catch (Exception ex) {
            showAlert("Kesalahan Navigasi", "Gagal kembali ke halaman utama", ex.getMessage());
        }
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}