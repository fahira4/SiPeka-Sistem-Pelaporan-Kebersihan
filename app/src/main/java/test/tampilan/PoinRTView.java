package test.tampilan;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import test.helper.session;

import java.util.Arrays;
import java.util.List;

// Tambahkan import untuk halamanUtama jika berada di package yang sama atau berbeda
import test.tampilan.HalamanUtama;

public class PoinRTView extends Application {

    public static class RTData {
        private final String namaRT;
        private final int totalPoin;

        public RTData(String namaRT, int totalPoin) {
            this.namaRT = namaRT;
            this.totalPoin = totalPoin;
        }

        public String getNamaRT() {
            return namaRT;
        }

        public int getTotalPoin() {
            return totalPoin;
        }
    }

    @Override
    public void start(Stage stage) {
        try {
            Label title = new Label("🏆 Ranking RT Terbersih");
            title.setStyle("""
                           -fx-font-size: 24px;
                           -fx-font-weight: bold;
                           -fx-text-fill: #1b5e20;
                           """);

            Label lblPengguna = new Label();
            if (session.penggunaAktif != null) {
                lblPengguna.setText("👤 Pengguna Aktif: " + session.penggunaAktif.getUsername());
                lblPengguna.setStyle("-fx-font-style: italic; -fx-text-fill: #33691e;");
            } else {
                lblPengguna.setText("🔒 Tidak ada pengguna aktif.");
                lblPengguna.setStyle("-fx-font-style: italic; -fx-text-fill: #a00;");
            }

            TableView<RTData> table = new TableView<>();

            TableColumn<RTData, String> colNama = new TableColumn<>("Nama RT");
            colNama.setCellValueFactory(new PropertyValueFactory<>("namaRT"));
            colNama.setPrefWidth(250);

            TableColumn<RTData, Integer> colPoin = new TableColumn<>("Total Poin");
            colPoin.setCellValueFactory(new PropertyValueFactory<>("totalPoin"));
            colPoin.setPrefWidth(150);

            table.getColumns().addAll(colNama, colPoin);
            table.setStyle("-fx-font-size: 14px;");
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
            table.setPlaceholder(new Label("📊 Data belum tersedia"));

            try {
                List<RTData> dataRT = ambilDataRT();
                table.getItems().addAll(dataRT);
            } catch (Exception e) {
                tampilkanError("Gagal memuat data ranking RT.\n" + e.getMessage());
            }

            Button btnKembali = new Button("← Kembali ke Beranda");
            btnKembali.setStyle("""
                                -fx-background-color: #66bb6a;
                                -fx-text-fill: white;
                                -fx-font-weight: bold;
                                -fx-font-size: 14px;
                                -fx-padding: 8 16 8 16;
                                -fx-background-radius: 10;
                                """);
            btnKembali.setOnAction(e -> {
                stage.close();
                new HalamanUtama().start(new Stage());
            });

            VBox card = new VBox(10, title, lblPengguna, table, btnKembali);
            card.setStyle("""
                           -fx-background-color: white;
                           -fx-padding: 20;
                           -fx-background-radius: 15;
                           -fx-border-color: #a5d6a7;
                           -fx-border-width: 2;
                           -fx-border-radius: 15;
                           -fx-effect: dropshadow(one-pass-box, rgba(0,0,0,0.1), 6, 0.3, 0, 2);
                           """);
            card.setSpacing(15);

            VBox root = new VBox(card);
            root.setStyle(
                    "-fx-padding: 30; -fx-alignment: center; -fx-background-color: linear-gradient(to bottom, #e8f5e9, #c8e6c9);");

            Scene scene = new Scene(root, 500, 400);
            stage.setScene(scene);
            stage.setTitle("Ranking RT");
            stage.show();

        } catch (Exception e) {
            tampilkanError("Terjadi kesalahan saat menampilkan tampilan utama.\n" + e.getMessage());
        }
    }

    private List<RTData> ambilDataRT() throws Exception {
        return Arrays.asList(
                new RTData("RT 01/RW 02", 450),
                new RTData("RT 05/RW 01", 370),
                new RTData("RT 03/RW 02", 290));
    }

    private void tampilkanError(String pesan) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Kesalahan");
        alert.setHeaderText("Terjadi Kesalahan");
        alert.setContentText(pesan);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}