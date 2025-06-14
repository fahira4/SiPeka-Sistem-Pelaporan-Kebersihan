package test.tampilan;

import test.Main;
import test.controller.PengendaliJadwal;
import test.model.JadwalPengangkutan;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class TampilanJadwalPengangkutan extends VBox {
    private final TableView<JadwalPengangkutan> tabelJadwal = new TableView<>();
    private final ObservableList<JadwalPengangkutan> dataJadwal;
    private final Stage primaryStage;

    public TampilanJadwalPengangkutan(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // Inisialisasi data
        try {
            PengendaliJadwal.perbaruiStatusOtomatis();
            dataJadwal = FXCollections.observableArrayList(PengendaliJadwal.getDaftarJadwal());
        } catch (Exception e) {
            throw new RuntimeException("Gagal memuat data jadwal", e);
        }

        // Setup UI
        initializeUI();
    }

    private void initializeUI() {
        setSpacing(20);
        setPadding(new Insets(25));
        setStyle("-fx-background-color: #e8f5e9;");

        HBox headerBox = createHeader();
        setupTable();
        HBox filterControls = createFilterControls();

        VBox tableContainer = new VBox(12, filterControls, tabelJadwal);
        tableContainer.setPadding(new Insets(20));
        tableContainer.setStyle("""
                -fx-background-color: #ffffff;
                -fx-border-radius: 10;
                -fx-background-radius: 10;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0.3, 0, 2);
                """);

        getChildren().addAll(headerBox, tableContainer);
    }

    private HBox createHeader() {
        Button backButton = new Button("← Kembali");
        backButton.setStyle("""
                -fx-background-color: #66bb6a;
                -fx-text-fill: white;
                -fx-font-weight: bold;
                -fx-background-radius: 6;
                """);
        backButton.setOnAction(e -> {
            HalamanUtama halamanUtama = new HalamanUtama();
            Scene scene = new Scene(halamanUtama.getView(800, 600));
            primaryStage.setScene(scene);
        });

        Label title = new Label("📆 Jadwal Pengangkutan Sampah");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        title.setStyle("-fx-text-fill: #2e7d32;");

        HBox headerBox = new HBox(15, backButton, title);
        headerBox.setAlignment(Pos.CENTER_LEFT);
        headerBox.setPadding(new Insets(0, 0, 15, 0));
        return headerBox;
    }

    private void setupTable() {
        tabelJadwal.setStyle("-fx-font-family: 'Segoe UI'; -fx-selection-bar: #a5d6a7;");

        TableColumn<JadwalPengangkutan, String> kolomHari = new TableColumn<>("Hari");
        kolomHari.setCellValueFactory(cellData -> javafx.beans.binding.Bindings
                .createStringBinding(() -> cellData.getValue().getHariString()));
        kolomHari.setPrefWidth(120);

        TableColumn<JadwalPengangkutan, String> kolomTanggal = new TableColumn<>("Tanggal");
        kolomTanggal.setCellValueFactory(cellData -> javafx.beans.binding.Bindings
                .createStringBinding(() -> cellData.getValue().getTanggalString()));
        kolomTanggal.setPrefWidth(120);

        TableColumn<JadwalPengangkutan, String> kolomJam = new TableColumn<>("Jam");
        kolomJam.setCellValueFactory(cellData -> javafx.beans.binding.Bindings
                .createStringBinding(() -> cellData.getValue().getJamString()));
        kolomJam.setPrefWidth(100);

        TableColumn<JadwalPengangkutan, String> kolomLokasi = new TableColumn<>("Lokasi");
        kolomLokasi.setCellValueFactory(new PropertyValueFactory<>("lokasi"));
        kolomLokasi.setPrefWidth(120);

        TableColumn<JadwalPengangkutan, String> kolomStatus = new TableColumn<>("Status");
        kolomStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        kolomStatus.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(status);
                    if ("Selesai".equals(status)) {
                        setStyle("-fx-text-fill: #388e3c; -fx-font-weight: bold;");
                    } else {
                        setStyle("-fx-text-fill: #f57c00; -fx-font-weight: bold;");
                    }
                }
            }
        });
        kolomStatus.setPrefWidth(100);

        tabelJadwal.getColumns().addAll(kolomHari, kolomTanggal, kolomJam, kolomLokasi, kolomStatus);
        tabelJadwal.setItems(dataJadwal);

        tabelJadwal.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        tabelJadwal.setFixedCellSize(32);
    }

    private HBox createFilterControls() {
        ComboBox<String> filterCombo = new ComboBox<>();
        filterCombo.getItems().addAll("Semua", "Diproses", "Selesai");
        filterCombo.setValue("Semua");
        filterCombo.setStyle("""
                -fx-background-color: white;
                -fx-border-color: #c8e6c9;
                -fx-border-radius: 5;
                """);

        Button refreshBtn = new Button("⟳ Refresh");
        refreshBtn.setStyle("""
                -fx-background-color: #43a047;
                -fx-text-fill: white;
                -fx-background-radius: 6;
                """);
        refreshBtn.setOnAction(e -> refreshData());

        filterCombo.setOnAction(e -> filterData(filterCombo.getValue()));

        HBox controls = new HBox(10, new Label("Filter Status:"), filterCombo, refreshBtn);
        controls.setAlignment(Pos.CENTER_LEFT);
        controls.setPadding(new Insets(5, 0, 10, 0));
        return controls;
    }

    private void filterData(String status) {
        if ("Semua".equals(status)) {
            tabelJadwal.setItems(dataJadwal);
        } else {
            ObservableList<JadwalPengangkutan> filteredData = FXCollections
                    .observableArrayList(dataJadwal.filtered(j -> status.equals(j.getStatus())));
            tabelJadwal.setItems(filteredData);
        }
    }

    private void refreshData() {
        PengendaliJadwal.perbaruiStatusOtomatis();
        dataJadwal.setAll(PengendaliJadwal.getDaftarJadwal());
    }
}
