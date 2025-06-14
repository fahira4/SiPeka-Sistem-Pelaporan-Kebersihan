package test.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class DashboardController {

    @FXML
    private Label totalLaporanLabel;

    @FXML
    private Label laporanSelesaiLabel;

    @FXML
    private Label rtAktifLabel;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private ListView<String> rtListView;

    @FXML
    public void initialize() {
        totalLaporanLabel.setText("120");
        laporanSelesaiLabel.setText("95");
        rtAktifLabel.setText("8");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("2025");
        series.getData().add(new XYChart.Data<>("Jan", 10));
        series.getData().add(new XYChart.Data<>("Feb", 15));
        series.getData().add(new XYChart.Data<>("Mar", 30));
        series.getData().add(new XYChart.Data<>("Apr", 25));

        barChart.getData().add(series);

        rtListView.getItems().addAll("RT 01", "RT 03", "RT 05", "RT 07", "RT 08");
    }
}
